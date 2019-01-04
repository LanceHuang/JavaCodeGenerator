package com.lance.generator;

/**
 * Configuration file
 *
 * @author Lance
 * @since 2018/12/31 18:18
 */
public class GeneratorConfig {
    private String configFile   = "generator.properties";
    private String templatePath = "template/*";
    private String savePath;

    public String getConfigFile() {
        return configFile;
    }

    public GeneratorConfig setConfigFile(String configFile) {
        this.configFile = configFile;
        return this;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public GeneratorConfig setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
        return this;
    }

    public String getSavePath() {
        return savePath;
    }

    public GeneratorConfig setSavePath(String savePath) {
        this.savePath = savePath;
        return this;
    }
}
