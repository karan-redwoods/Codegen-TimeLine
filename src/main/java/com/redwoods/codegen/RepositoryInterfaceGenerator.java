package com.redwoods.codegen;

import com.redwoods.codegen.config.ApplicationConfig;
import org.springframework.stereotype.Component;

@Component
public class RepositoryInterfaceGenerator {

    private ApplicationConfig applicationConfig;
    private CodeWriter codeWriter;

    public RepositoryInterfaceGenerator(ApplicationConfig applicationConfig, CodeWriter codeWriter){
        this.applicationConfig = applicationConfig;
        this.codeWriter = codeWriter;
    }
    public void generateRepository(String entityName){
        String basePackage = applicationConfig.getPackageName();
        String repositoryName = entityName+"Repository";

        String content = String.format("package %s.repos;\n\n" +
                        "import org.springframework.data.jpa.repository.JpaRepository;\n\n" +
                        "import %s.models.%s;\n\n" +
                        "public interface %s extends JpaRepository<%s, Long> {\n" +
                        "}\n",
                basePackage, basePackage, entityName, repositoryName, entityName);

        codeWriter.writeToFile(entityName+"Repository" + ".java", content);
    }
}
