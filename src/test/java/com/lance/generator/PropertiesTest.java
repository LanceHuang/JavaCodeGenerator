package com.lance.generator;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Lance
 * @since 2019/1/4 20:58
 */
public class PropertiesTest {
    @Test
    public void test() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\software\\project\\java\\JavaCodeGenerator\\src\\test\\resources\\generator.properties"));
        System.out.println(properties.size());
    }
}
