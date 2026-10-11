package com.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testGetMessage() {
        App app = new App();

        String expected = "Online Examination System is running!";
        String actual = app.getMessage();

        assertEquals(expected, actual);
    }
}
