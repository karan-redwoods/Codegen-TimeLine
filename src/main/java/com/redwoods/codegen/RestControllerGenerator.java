package com.redwoods.codegen;

import com.redwoods.codegen.config.ApplicationConfig;
import org.apache.commons.text.CaseUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestControllerGenerator {

    private ApplicationConfig applicationConfig;
    private CodeWriter codeWriter;

    public RestControllerGenerator(ApplicationConfig applicationConfig, CodeWriter codeWriter){
        this.applicationConfig = applicationConfig;
        this.codeWriter = codeWriter;
    }

    public void generateRestControllerClass(String entityName){
        String controllerClass = entityName+"Controller";
        String basePackage = applicationConfig.getPackageName();
        String basePath = applicationConfig.getBase_path();
        String responseDto = applicationConfig.getResponseDto();
        String requestDto = applicationConfig.getRequestDto();
        String serviceClass = entityName+"Service";
        String serviceClassVariable = entityName.toLowerCase()+"Service";
        String idVariable = entityName.toLowerCase()+"Id";
        String requestDtoVariable = CaseUtils.toCamelCase(requestDto, true, ' ') + "Dto";
        String responseDtoVariable = CaseUtils.toCamelCase(responseDto, true, ' ') + "Dto";
        String modelVariable = entityName.toLowerCase();

        String content =
                "package " + applicationConfig.getPackageName() + ".controllers;\n\n" +
                        "import java.util.List;\n" +
                        "import org.modelmapper.ModelMapper;\n" +
                        "import org.slf4j.Logger;\n" +
                        "import org.slf4j.LoggerFactory;\n" +
                        "import org.springframework.http.HttpStatus;\n" +
                        "import org.springframework.http.ResponseEntity;\n" +
                        "import org.springframework.web.bind.annotation.*;\n\n" +
                        "import " + basePackage + ".dtos." + responseDto + ";\n" +
                        "import " + basePackage + ".dtos." + requestDto + ";\n" +
                        "import " + basePackage + ".models." + entityName + ";\n" +
                        "import " + basePackage + ".service." + entityName + "Service;\n\n" +
                        "@CrossOrigin\n" +
                        "@RestController\n" +
                        "@RequestMapping(\"/api/" + basePath + "\")\n" +
                        "public class " + controllerClass + " {\n\n" +
                        "    private " + serviceClass + " " + entityName.toLowerCase() + "Service;\n" +
                        "    private ModelMapper modelMapper;\n" +
                        "    private final Logger LOGGER = LoggerFactory.getLogger(" + controllerClass + ".class);\n\n" +
                        "    public " + controllerClass + "(" + serviceClass + " " + serviceClassVariable + ", ModelMapper modelMapper) {\n" +
                        "        this." + serviceClassVariable + " = " + serviceClassVariable + ";\n" +
                        "        this.modelMapper = modelMapper;\n" +
                        "    }\n\n" +
                        "    @GetMapping(\"/{" + idVariable + "}\")\n" +
                        "    public ResponseEntity<?> get" + entityName + "(@PathVariable(\"" + idVariable + "\") Long " + idVariable + ") {\n" +
                        "        try {\n" +
                        "            " + responseDto + " " + responseDtoVariable + " = " + serviceClassVariable + ".get" + entityName + "(" + idVariable + ");\n" +
                        "            return new ResponseEntity<>(" + responseDtoVariable + ", HttpStatus.OK);\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing /" + basePath + "/\" + " + idVariable + ", ex);\n" +
                        "            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @GetMapping(\"\")\n" +
                        "    public ResponseEntity<?> getAll" + entityName + "s() {\n" +
                        "        try{\n" +
                        "            List<" + responseDto + "> " + requestDtoVariable + " = " + serviceClassVariable + ".get" + entityName + "s();\n" +
                        "            if (" + requestDtoVariable + " != null) {\n" +
                        "                return new ResponseEntity<>(" + requestDtoVariable + ", HttpStatus.OK);\n" +
                        "            } else {\n" +
                        "                return new ResponseEntity<>(\"No " + entityName + "s available!!\", HttpStatus.OK);\n" +
                        "            }\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing /" + basePath + "\", ex);\n" +
                        "            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @PostMapping(\"\")\n" +
                        "    public ResponseEntity<String> add" + entityName + "(@RequestBody " + requestDto + " " + requestDtoVariable + ") {\n" +
                        "        try{\n" +
                        "            " + entityName + " " + modelVariable + " = modelMapper.map(" + requestDtoVariable + ", " + entityName + ".class);\n" +
                        "            Long " + idVariable + " = " + serviceClassVariable + ".add" + entityName + "(" + modelVariable + ");\n" +
                        "            return new ResponseEntity<>(String.format(\"New " + entityName + " added %s successfully.\", " + idVariable + "), HttpStatus.CREATED);\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing /" + basePath + "\", ex);\n" +
                        "            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @PutMapping(\"/{" + idVariable + "}\")\n" +
                        "    public ResponseEntity<?> update" + entityName + "(@PathVariable(\"" + idVariable + "\") Long " + idVariable + ", @RequestBody " + requestDto + " " + requestDtoVariable + ") {\n" +
                        "        try{\n" +
                        "            " + responseDto + " " + responseDtoVariable + " = " + serviceClassVariable + ".update" + entityName + "(" + idVariable + ", " + requestDtoVariable + ");\n" +
                        "            if (" + responseDtoVariable + " != null)\n" +
                        "                return new ResponseEntity<>(" + responseDtoVariable + ", HttpStatus.CREATED);\n" +
                        "            else\n" +
                        "                return new ResponseEntity<>(String.format(\"No " + entityName + " exist in the database with provided id %s.\", " + idVariable + "), HttpStatus.OK);\n" +
                        "        } catch (Exception ex) {\n" +
                        "            LOGGER.error(\"Error occurred while processing /" + basePath + "/\" + " + idVariable + ", ex);\n" +
                        "            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);\n" +
                        "        }\n" +
                        "    }\n\n" +
                        "    @DeleteMapping(\"/{" + idVariable + "}\")\n" +
                        "    public ResponseEntity<String> delete" + entityName + "(@PathVariable(\"" + idVariable + "\") Long " + idVariable + ") {\n" +
                        "        String message = " + serviceClassVariable + ".delete" + entityName + "(" + idVariable + ");\n" +
                        "        return new ResponseEntity<>(message, HttpStatus.OK);\n" +
                        "    }\n" +
                        "}\n";

        codeWriter.writeToFile(entityName+"Controller" + ".java",content);
    }
}
