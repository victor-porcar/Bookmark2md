package com.github.victormpcmun.bookmark2md.generator;

import java.util.function.UnaryOperator;

public class GeneratorFormatter {


    public static String replaceBold(String s, UnaryOperator<String> replacementFunctionBold) {
        return replaceInBetween(s, "**", "**", replacementFunctionBold);
    }

    public static String replaceItalic(String s, UnaryOperator<String> replacementFunctionItalic) {
        return replaceInBetween(s, "*", "*", replacementFunctionItalic);
    }

    public static String replaceBoldItalic(String s, UnaryOperator<String> replacementFunctionBoldItalic) {
        return replaceInBetween(s, "***", "***", replacementFunctionBoldItalic);
    }


    protected static String replaceInBetween(String s, String s1, String s2, UnaryOperator<String> replacementFunction) {

        StringBuilder result = new StringBuilder();

        boolean foundReplacement;
        int index = 0;
        int s1Posi;
        int s2Posi;

        do {
            s1Posi = getIndexOfIgnoringOnLeft(s, s1, index);

            if (s1Posi >= 0) {
                s2Posi = getIndexOfIgnoringOnLeft(s, s2, s1Posi + s1.length() + 1);
            } else {
                s2Posi = -1;
            }
            foundReplacement = s1Posi >= 0 && s2Posi > s1Posi;

            if (foundReplacement) {
                result.append(s, index, s1Posi);
                String wordToReplace = s.substring(s1Posi + s1.length(), s2Posi);
                String replacement = replacementFunction.apply(wordToReplace);
                result.append(replacement);
                index = s2Posi + s2.length();
            } else {
                result.append(s.substring(index));
            }


        } while (foundReplacement && index < s.length());

        return result.toString();
    }

    public static int getIndexOfIgnoringOnLeft(String string, String search, int countToIgnoreFromLeft) {
        int stringSize = string.length();
        if (stringSize <= countToIgnoreFromLeft) {
            return -1;
        }

        int partial = string.substring(countToIgnoreFromLeft).indexOf(search);
        if (partial < 0) {
            return -1;
        }
        return partial + countToIgnoreFromLeft;
    }

}
