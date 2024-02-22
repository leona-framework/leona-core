package com.sylvona.leona.core.commons.naming;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class CamelCaseNamingConventionTest {

    @Test
    public void correctlyIdentifiesCamelCaseStrings() {
        assertTrue(CamelCaseNamingConvention.INSTANCE.followsConvention("helloWorld"));
        assertTrue(CamelCaseNamingConvention.INSTANCE.followsConvention("helloWorldAwesome"));
        assertTrue(CamelCaseNamingConvention.INSTANCE.followsConvention("hello0World1Three"));
        assertTrue(CamelCaseNamingConvention.INSTANCE.followsConvention("acronymAWSisCool"));
    }


    @Test
    public void correctlySplitsTextIntoSegments() {
        assertArrayEquals(new String[] { "hello", "World" }, CamelCaseNamingConvention.INSTANCE.getSegments("helloWorld"));
        assertArrayEquals(new String[] { "hello", "World", "Awesome" }, CamelCaseNamingConvention.INSTANCE.getSegments("helloWorldAwesome"));
        assertArrayEquals(new String[] { "hello0", "World1", "Three" }, CamelCaseNamingConvention.INSTANCE.getSegments("hello0World1Three"));
        assertArrayEquals(new String[] { "acronym", "AWS", "is", "Cool" }, CamelCaseNamingConvention.INSTANCE.getSegments("acronymAWSisCool"));
    }


    @Test
    public void correctlyFormatsFormattableStrings() {
        NamingConvention convention = TrainCaseNamingConvention.INSTANCE;

        String out = convention.format("Hello_World");
        log.info(out);

        out = convention.format("Hello-World");
        log.info(out);

        out = convention.format("Hello-AWS-Cool");
        log.info(out);

        out = convention.format("helloAWSisCool");
        log.info(out);

        out = convention.format("Hello World");
        log.info(out);

        out = convention.format("hello_world");
        log.info(out);

        out = convention.format("kebab-case");
        log.info(out);

        out = convention.format("t_h_i_s_c_a_s_e_s_u_c_k_s");
        log.info(out);

        out = convention.format("HELLO_WORLD");
        log.info(out);
    }




}
