package com.redwoods.codegen;

import com.redwoods.codegen.config.ApplicationConfig;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ServiceInterfaceGenerator {

    private ApplicationConfig applicationConfig;
    private CodeWriter codeWriter;

    public ServiceInterfaceGenerator(ApplicationConfig applicationConfig, CodeWriter codeWriter){
        this.applicationConfig = applicationConfig;
        this.codeWriter = codeWriter;
    }

    public void generateServiceInterface(String entityName){
        String basePackage = applicationConfig.getPackageName();
        String responseDtoClass = applicationConfig.getResponseDto();
        String requestDtoClass = applicationConfig.getRequestDto();



        String content =
                "package " + basePackage + ".service;\n\n" +
                        "import java.util.List;\n\n" +
                        "import " + basePackage + ".dtos." + responseDtoClass + ";\n" +
                        "import " + basePackage + ".dtos." + requestDtoClass + ";\n" +
                        "import " + basePackage + ".models." + entityName + ";\n\n" +
                        "public interface " + entityName + "Interface {\n\n" +
                        "    " + responseDtoClass + " get" + entityName + "(Long " + entityName.toLowerCase() + "Id);\n\n" +
                        "    List<" + responseDtoClass + "> get" + entityName + "s();\n\n" +
                        "    String delete" + entityName + "(Long " + entityName.toLowerCase() + "Id);\n\n" +
                        "    " + responseDtoClass + " update" + entityName + "(Long " + entityName.toLowerCase() + "Id, " + requestDtoClass + " " + entityName.toLowerCase() + "Request);\n\n" +
                        "    Long add" + entityName + "(" + entityName + " " + entityName.toLowerCase() + ");\n\n" +
                        "}\n";


        codeWriter.writeToFile(entityName+"Service" + ".java", content);
    }
}
