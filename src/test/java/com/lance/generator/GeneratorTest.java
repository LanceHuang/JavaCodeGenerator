package com.lance.generator;

import org.junit.Test;

public class GeneratorTest {

    @Test
    public void test() {
        String configFile = "generator.properties";
        String templatePath = "template";
        String savePath = "C:\\software\\code\\test\\generator";

        GeneratorConfig config = new GeneratorConfig()
                .setConfigFile(configFile)
                .setTemplatePath(templatePath)
                .setSavePath(savePath);
        Generator generator = new Generator(config);
        generator.generate();
    }
}
