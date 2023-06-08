package com.ronanski11.demo.webservice.interfaces;

import org.apache.commons.text.StringSubstitutor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class TemplateUtils {

    public static String loadAndProcessTemplate(String templatePath, Map<String, String> values) {
        Path path = Paths.get(templatePath);
        try {
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
            StringSubstitutor stringSubstitutor = new StringSubstitutor(values);
            return stringSubstitutor.replace(content);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading email template", e);
        }
    }
}
