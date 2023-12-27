package com.geeksforless.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public final class Checkers {

    private Checkers() {
    }

    public static boolean isParenthesisCorrect(String userInput) {
        if (isUserInputNotExist(userInput) || !userInput.contains("=")) {
            return false;
        }
        String[] twoPartsEquation  = userInput.split("=");
        return isParenthesisInOneSideOfEquals(twoPartsEquation[0]) && isParenthesisInOneSideOfEquals(twoPartsEquation[1]);
    }

    private static boolean isParenthesisInOneSideOfEquals(String userInput) {
        userInput = userInput.replaceAll("[^()]", "");
        Deque<Character> parenthesis = new ArrayDeque<>();
        for (int i = 0; i < userInput.length(); i++) {
            char ch = userInput.charAt(i);
            if (ch == '(') {
                parenthesis.addFirst(ch);
                continue;
            }
            if (parenthesis.isEmpty()) {
                return false;
            }
            if (ch == ')') {
                parenthesis.removeFirst();
            }
        }
        return parenthesis.isEmpty();
    }

    public static boolean isUserInputMathEquation(String userInput) {
        if (!userInput.contains("=") || !userInput.contains("x")) {
            return false;
        }
        if (!isContainsMathSymbols(userInput) || !isParenthesisCorrect(userInput)
            || isContainsEquationSymbol(userInput) || !isBeginAndEndEquationCorrect(userInput)) {
            return false;
        }
        userInput = userInput.replaceAll("[+/*]{2,}|-{2,}", " ");
        return !userInput.contains(" ");
    }

    public static boolean isNumber(String userInput) {
        if (isUserInputNotExist(userInput)) {
            return false;
        }
        return userInput.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isUserInputNotExist(String userInput) {
        return Objects.isNull(userInput) || userInput.isBlank();
    }

    private static boolean isContainsEquationSymbol(String userInput) {
        return userInput.replaceFirst("=", " ").contains("=");
    }

    private static boolean isBeginAndEndEquationCorrect(String userInput) {
        return userInput.matches("^[-x\\d(].+[x\\d)]$");
    }

    private static boolean isContainsMathSymbols(String userInput) {
        if (isUserInputNotExist(userInput)) {
            return false;
        }
        return userInput.matches("[-/*+0-9.()xX= ]+");
    }
}
