package com.geeksforless.util;

import java.util.ArrayList;
import java.util.List;

public class SolverEquation {

    private static final double STANDARD_DEVIATION = 10E-9;

    private static final int NUMBER_FOR_REMOVE_EQUAL_SIMPLE_OPERATION = 3;

    private static final int PAIR_OF_PARENTHESIS = 2;

    private final String equation;

    public SolverEquation(String equation) {
        this.equation = equation;
    }

    public boolean isRoot(String root) {
        String[] twoPartsEquation = equation.replace("x", root).split("=");
        return Math.abs(solve(twoPartsEquation[0]) - solve(twoPartsEquation[1])) <= STANDARD_DEVIATION;
    }

    private double solve(String expression) {
        List<String> numbersAndSymbols = transformSomeNumbersInNegative(getAllMathSymbols(expression));
        return doOperations(numbersAndSymbols);
    }

    private List<String> getAllMathSymbols(String expression) {
        List<String> numbers = getAllPositiveNumbers(expression);
        expression = expression.replaceAll("[\\d.]+", "n");
        return addSymbolsToNumbers(expression, numbers);
    }

    private List<String> addSymbolsToNumbers(String expression, List<String> numbers) {
        List<String> allSymbols = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (ch == 'n') {
                allSymbols.add(numbers.removeFirst());
                continue;
            }
            allSymbols.add(String.valueOf(ch));
        }
        return allSymbols;
    }

    private List<String> getAllPositiveNumbers(String expression) {
        List<String> numbers = new ArrayList<>();
        String[] numbersWithEmpties = expression.split("[-+/*()]");
        for (String number : numbersWithEmpties) {
            if (number.isEmpty()) {
                continue;
            }
            numbers.add(number);
        }
        return numbers;
    }

    private List<String> transformSomeNumbersInNegative(List<String> allMathSymbols) {
        List<String> numbersAndSymbols = new ArrayList<>();
        for (int i = 0; i < allMathSymbols.size(); i++) {
            String temp = allMathSymbols.get(i);
            if (temp.equals("-") && i != 0 && allMathSymbols.get(i - 1).matches("[+/*]")) {
                if (allMathSymbols.get(i + 1).equals("(")) {
                    numbersAndSymbols.add(temp);
                } else {
                    numbersAndSymbols.add(temp.concat(allMathSymbols.get(++i)));
                }

            } else {
                numbersAndSymbols.add(temp);
            }
        }
        changeFirstElementsIfNeed(numbersAndSymbols);
        return numbersAndSymbols;
    }

    private void changeFirstElementsIfNeed(List<String> numbersAndSymbols) {
        String firstElement = numbersAndSymbols.getFirst();
        if (firstElement.equals("-")) {
            numbersAndSymbols.remove(0);
            numbersAndSymbols.add(0, "-1");
            numbersAndSymbols.add(1, "*");
        }
    }

    private void doOperationInParenthesis(List<String> numbersAndSymbols) {
        List<String> simpleExpressionInOrdinaryParenthesis = new ArrayList<>();
        while (numbersAndSymbols.contains("(")) {
            int firstIndex = numbersAndSymbols.indexOf("(");
            int lastIndex = numbersAndSymbols.lastIndexOf(")");
            for (int i = firstIndex; i < lastIndex; i++) {
                String temp = numbersAndSymbols.get(i);
                simpleExpressionInOrdinaryParenthesis.add(temp);
                if (temp.equals("(")) {
                    firstIndex = i;
                }
                if (temp.equals(")")) {
                    lastIndex = i;
                    break;
                }
            }
            List<String> copyOriginal = new ArrayList<>(numbersAndSymbols);
            simpleExpressionInOrdinaryParenthesis = copyOriginal.subList(firstIndex + 1, lastIndex);

            removeGivenNumbersElementsFrom(numbersAndSymbols, firstIndex,
                    simpleExpressionInOrdinaryParenthesis.size() + PAIR_OF_PARENTHESIS);
            doDivideOrMultiple(simpleExpressionInOrdinaryParenthesis);
            doPlusAndMinus(simpleExpressionInOrdinaryParenthesis);
            numbersAndSymbols.addAll(firstIndex, simpleExpressionInOrdinaryParenthesis);
        }
    }

    private void doDivideOrMultiple(List<String> numbersAndSymbols) {
        while (numbersAndSymbols.contains("*") || numbersAndSymbols.contains("/")) {
            for (int i = 1; i < numbersAndSymbols.size(); i++) {
                String temp = numbersAndSymbols.get(i);
                if (temp.equals("*") || temp.equals("/")) {
                    String result = getResultFromSimpleOperation(numbersAndSymbols.get(i - 1), numbersAndSymbols.get(i + 1),
                            temp);
                    addResultInGivenIndex(numbersAndSymbols, result, i - 1);
                }
            }
        }

    }

    private void doPlusAndMinus(List<String> numbersAndSymbols) {
        while (numbersAndSymbols.contains("+") || numbersAndSymbols.contains("-")) {
            for (int i = 1; i < numbersAndSymbols.size(); i++) {
                String temp = numbersAndSymbols.get(i);
                if (temp.equals("+") || temp.equals("-")) {
                    String result = getResultFromSimpleOperation(numbersAndSymbols.get(i - 1), numbersAndSymbols.get(i + 1),
                            temp);
                    addResultInGivenIndex(numbersAndSymbols, result, i - 1);
                }
            }
        }

    }

    private void addResultInGivenIndex(List<String> numbersAndSymbols, String result, int index) {
        removeGivenNumbersElementsFrom(numbersAndSymbols, index, NUMBER_FOR_REMOVE_EQUAL_SIMPLE_OPERATION);
        numbersAndSymbols.add(index, result);
    }

    private String getResultFromSimpleOperation(String first, String second, String operator) {
        var firstOperand = Double.parseDouble(first);
        var secondOperand = Double.parseDouble(second);
        return String.valueOf(doMathOperation(firstOperand, secondOperand, operator));
    }

    private void removeGivenNumbersElementsFrom(List<String> numbersAndSymbols, int indexFrom, int size) {
        for (int j = 0; j < size; j++) {
            numbersAndSymbols.remove(indexFrom);
        }
    }

    private double doOperations(List<String> numbersAndSymbols) {
        doOperationInParenthesis(numbersAndSymbols);
        doDivideOrMultiple(numbersAndSymbols);
        doPlusAndMinus(numbersAndSymbols);
        return Double.parseDouble(numbersAndSymbols.removeFirst());
    }

    private double doMathOperation(double operand1, double operand2, String operation) {
        return switch (operation) {
            case "+" -> operand1 + operand2;
            case "-" -> operand1 - operand2;
            case "/" -> operand1 / operand2;
            case "*" -> operand1 * operand2;
            default -> Double.NaN;
        };
    }
}

