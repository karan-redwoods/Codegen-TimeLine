package com.redwoods.codegen;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class CodeWriter {
    public void writeToFile(String fileName, String content) {
        try (FileWriter writer = new FileWriter(new File(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
