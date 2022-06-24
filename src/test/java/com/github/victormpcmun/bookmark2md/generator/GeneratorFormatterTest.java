package com.github.victormpcmun.bookmark2md.generator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeneratorFormatterTest {

    @Test
    public void replaceInBetweenOneOccurrenceTest() {
        String result = GeneratorFormatter.replaceInBetween("this is a *text* middle", "*", "*", s -> "<B>" + s + "</B>");
        assertEquals("this is a <B>text</B> middle", result);
    }

    @Test
    public void replaceInBetweenTwoOccurrencesTest() {
        String result = GeneratorFormatter.replaceInBetween("this is a *text* middle and the *final*", "*", "*", s -> "<B>" + s + "</B>");
        assertEquals("this is a <B>text</B> middle and the <B>final</B>", result);
    }

    @Test
    public void replaceInBetweenThreeOccurrencesTest() {
        String result = GeneratorFormatter.replaceInBetween("*begin* this is a *text* middle and the *final*", "*", "*", s -> "<B>" + s + "</B>");
        assertEquals("<B>begin</B> this is a <B>text</B> middle and the <B>final</B>", result);
    }

    @Test
    public void replaceInBetweenSimpleTest() {
        String result = GeneratorFormatter.replaceInBetween("this is a *text* middle", "*", "*", s -> "<B>" + s + "</B>");
        assertEquals("this is a <B>text</B> middle", result);
    }

    @Test
    public void replaceInBetweenComplexPattern1Test() {
        String result = GeneratorFormatter.replaceInBetween("this is a *text* middle", "**", "*", s -> "<B>" + s + "</B>");
        assertEquals("this is a *text* middle", result);
    }

    @Test
    public void replaceInBetweenComplexPattern2Test() {
        String result = GeneratorFormatter.replaceInBetween("this is a *text* middle", "**", "***", s -> "<B>" + s + "</B>");
        assertEquals("this is a *text* middle", result);
    }

    @Test
    public void replaceInBetweenComplexPattern3Test() {
        String result = GeneratorFormatter.replaceInBetween("this is a *text* middle", "**", "***", s -> "<B>" + s + "</B>");
        assertEquals("this is a *text* middle", result);
    }

    @Test
    public void replaceInBetweenComplexPattern4Test() {
        String result = GeneratorFormatter.replaceInBetween("this is a *******text** middle", "**", "***", s -> "<B>" + s + "</B>");
        assertEquals("this is a <B>*</B>*text** middle", result);
    }

    @Test
    public void replaceInBetweenComplexPattern5Test() {
        String result = GeneratorFormatter.replaceInBetween("this is a ********text*** middle", "**", "***", s -> "<B>" + s + "</B>");
        assertEquals("this is a <B>*</B><B>text</B> middle", result);
    }
}
