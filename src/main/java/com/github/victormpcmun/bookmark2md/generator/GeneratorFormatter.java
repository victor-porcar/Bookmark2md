package com.github.victormpcmun.bookmark2md.generator;

import java.util.function.UnaryOperator;

public class GeneratorFormatter {

    private static final String BOLD_BEGIN="**";
    private static final String BOLD_END="**";

    private static final String ITALIC_BEGIN="*";
    private static final String ITALIC_END="*";

    private static final String BOLD_ITALIC_BEGIN="***";
    private static final String BOLD_ITALIC_END="***";
    

    public static String replaceBold(String s, UnaryOperator<String> replacementFunctionBold) {
        return replaceInBetween(s, BOLD_BEGIN, BOLD_END, replacementFunctionBold);
    }

    public static String replaceItalic(String s, UnaryOperator<String> replacementFunctionItalic) {
        return replaceInBetween(s, ITALIC_BEGIN, ITALIC_END, replacementFunctionItalic);
    }

    public static String replaceBoldItalic(String s, UnaryOperator<String> replacementFunctionBoldItalic) {
        return replaceInBetween(s, BOLD_ITALIC_BEGIN, BOLD_ITALIC_END, replacementFunctionBoldItalic);
    }


    protected static String replaceInBetween(String s, String s1, String s2, UnaryOperator<String> replacementFunction) {

        StringBuilder result = new StringBuilder();

        boolean foundReplacement;
        int index = 0;
        int s1Position;
        int s2Position;

        do {
            s1Position = getIndexOfIgnoringOnLeft(s, s1, index);

            if (s1Position >= 0) {
                s2Position = getIndexOfIgnoringOnLeft(s, s2, s1Position + s1.length() + 1);
            } else {
                s2Position = -1;
            }
            foundReplacement = s1Position >= 0 && s2Position > s1Position;

            if (foundReplacement) {
                result.append(s, index, s1Position);
                String wordToReplace = s.substring(s1Position + s1.length(), s2Position);
                String replacement = replacementFunction.apply(wordToReplace);
                result.append(replacement);
                index = s2Position + s2.length();
            } else {
                result.append(s.substring(index));
            }


        } while (foundReplacement && index < s.length());

        return result.toString();
    }

    private static int getIndexOfIgnoringOnLeft(String string, String search, int countToIgnoreFromLeft) {
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
