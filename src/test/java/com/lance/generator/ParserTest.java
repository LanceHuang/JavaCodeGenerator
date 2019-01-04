package com.lance.generator;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {

    @Test
    public void test() {
        String template = "Hello ${name}, my name is ${name2}";
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Alice");
        properties.put("name2", "Lance");

        Parser parser = new Parser();
        System.out.println(parser.parse(template, properties));
    }

}