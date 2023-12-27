package com.geeksforless.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CheckersTest {


    @ParameterizedTest
    @ValueSource(strings = {"(x + 1) = 7", "(((x+8)))() = 9", "25*(x)+(78)=9"})
    void checkParenthesisCorrectInput(String parentheses) {
        Assertions.assertThat(Checkers.isParenthesisCorrect(parentheses)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"(x+8)((( = 9", "(((26*5))(((((((", "2*x)+(7+8)", "(x+9=0)+5"})
    void checkParenthesisIncorrectInput(String parentheses) {
        Assertions.assertThat(Checkers.isParenthesisCorrect(parentheses)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2*x+5=17", "-1.3*5/x=1.2", "2*x*x=10", "2*(x+5+x)+5=10", "17=2*x+5"})
    void checkCorrectMathEquation(String userMathEquation) {
        Assertions.assertThat(Checkers.isUserInputMathEquation(userMathEquation)).isTrue();
    }


    @ParameterizedTest
    @ValueSource(strings = {"2*x+5=17=0", "-1.3*+5/x=1.2", "2*x*-x=10(", "2*(x+5+x)+5=10-", "17=2*x+5--"})
    void checkInCorrectMathEquation(String userMathEquation) {
        Assertions.assertThat(Checkers.isUserInputMathEquation(userMathEquation)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(doubles = {4.5, -3.5, 3, -2, 0, 3222.455, -325.78})
    void checkCorrectRootNumbers(double userNumber) {
       Assertions.assertThat(Checkers.isNumber(String.valueOf(userNumber))).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "-3.5t", "kjj", "+24", "-324..78" })
    void checkInCorrectRootNumbers(String userNumber) {
        Assertions.assertThat(Checkers.isNumber(userNumber)).isFalse();
    }
}