package com.geeksforless.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public  class UserInput {

    private UserInput() {
    }

    public static String getUserEquation() {
        String result;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            result = reader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return result;
    }

    public static String getRidOfAllSpacesAndTransformInLowercase(String userInput) {
        return userInput.replaceAll("\\s+", "").toLowerCase();
    }
}
