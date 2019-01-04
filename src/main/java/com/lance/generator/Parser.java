package com.lance.generator;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Hello ${name}, my name is ${name2}
 * name-Jack
 * name2-Lance
 * Hello Jack, my name is Lance
 *
 * @author Lance
 * @since 2019/1/4 18:04
 */
public class Parser {

    private static final char C_DOLLAR = '$';
    private static final char C_LEFT   = '{';
    private static final char C_RIGHT  = '}';

    public String parse(String template, Properties properties) {
        Map<String, Object> props = new HashMap<>();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            props.put(entry.getKey().toString(), entry.getValue());
        }
        return parse(template, props);
    }

    public String parse(String template, Map<String, Object> properties) {
        StringBuilder builder = new StringBuilder();
        StringBuilder key = new StringBuilder();

        boolean flag = false;
        for (int i = 0; i < template.length(); i++) {
            switch (template.charAt(i)) {
                case C_DOLLAR: {
                    if (i < template.length() - 1 && template.charAt(i + 1) == C_LEFT) {
                        i++;
                        flag = true;
                        continue;
                    }

                }
                break;
                case C_RIGHT: {
                    if (flag) {
                        flag = false;
                        if (properties.containsKey(key.toString().trim())) {
                            builder.append(properties.get(key.toString().trim()));
                        }
                        key = new StringBuilder();
                        continue;
                    }
                }
                break;
                default:
                    break;
            }

            if (flag) {
                key.append(template.charAt(i));
            } else {
                builder.append(template.charAt(i));
            }
        }

        //Wrong
        if (flag) {
            throw new IllegalStateException();
        }

        return builder.toString();
    }
}
