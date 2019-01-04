package com.lance.generator;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Lance
 * @since 2018/12/31 18:18
 */
public class Generator {

    private static final String KEY_FILENAME    = "project.file.name";
    private static final String KEY_FILE_PREFIX = "project.file.prefix";

    private GeneratorConfig config;
    private Parser          parser;

    public Generator(GeneratorConfig config) {
        if (null == config) {
            throw new IllegalArgumentException("GeneratorConfig config is null");
        }
        this.config = config;
        this.parser = new Parser();
    }


    public void generate() {
        //Read properties
        System.out.println("==================== Read properties ===================");
        Properties properties = readProperties(config.getConfigFile());
        if (null == properties) {
            System.out.println("!!! Please provide properties");
            return;
        }

        String filename = properties.getProperty(KEY_FILENAME);
        if (null == filename || filename.isEmpty()) {
            System.out.println("!!! Please provide " + KEY_FILENAME);
            return;
        }

        //Search templates
        System.out.println("==================== Search all template files ===================");
        Set<File> templates = searchAllTemplate();
        for (File template : templates) {
            System.out.println(template.getAbsolutePath());
        }

        //Read templates
        System.out.println("==================== Generate files ===================");
        Map<File, String> generateFiles = new HashMap<>();
        for (File template : templates) {
            String templateCnt = readFileAsString(template);
            generateFiles.put(template, parser.parse(templateCnt, properties));
        }

        //Save file
        String prefix = properties.getProperty(KEY_FILE_PREFIX);
        for (Map.Entry<File, String> entry : generateFiles.entrySet()) {
            File templateFile = entry.getKey();

            String pureName = templateFile.getName();
            int index = pureName.lastIndexOf('.');
            if (index > -1) {
                pureName = pureName.substring(0, index);
            }

            String newName = toCamelCase(filename) + toCamelCase(pureName) + prefix;
            saveFile(newName, entry.getValue());
        }

    }

    private void saveFile(String newName, String data) {
        System.out.println(config.getSavePath() + File.separator + newName);
        try (BufferedWriter bufw = new BufferedWriter(
                new FileWriter(config.getSavePath() + File.separator + newName))) {

            bufw.write(data);
            bufw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toCamelCase(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    private Properties readProperties(String configFile) {
        URL resource = this.getClass().getClassLoader().getResource(configFile);
        if (null == resource) {
            return null;
        }

        InputStream inputStream = null;
        Properties properties = new Properties();
        try {
            inputStream = resource.openStream();
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    //Do nothing
                }
            }
        }

        return properties;
    }

    private String readFileAsString(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader bufr = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufr.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private Set<File> searchAllTemplate() {
        URL resource = this.getClass().getClassLoader().getResource(config.getTemplatePath());
        if (null == resource) {
            return Collections.emptySet();
        }

        File root = new File(resource.getFile());
        if (!root.isDirectory()) {
            return Collections.emptySet();
        }

        File[] files = root.listFiles();
        if (null == files || files.length == 0) {
            System.out.println("!!! Please provide templates");
            return Collections.emptySet();
        }

        Set<File> templates = new HashSet<>();
        for (File file : files) {
            if (file.isFile()) {
                templates.add(file);
            }
        }
        return templates;
    }

}
