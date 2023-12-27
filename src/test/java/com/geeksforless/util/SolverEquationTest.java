package com.geeksforless.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SolverEquationTest {

    private SolverEquation solverEquation;

    @BeforeEach
    void init() {
        solverEquation = new SolverEquation("(2*x*x*x+7*x*x+7*x)+2=0");

    }


    @ParameterizedTest
    @ValueSource(strings = {"-1", "-2", "-0.5"})
    void isRootCorrect(String root) {
        Assertions.assertThat(solverEquation.isRoot(root)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"5", "-7", "-0.1"})
    void isNotRoot(String root) {
        Assertions.assertThat(solverEquation.isRoot(root)).isFalse();
    }
}