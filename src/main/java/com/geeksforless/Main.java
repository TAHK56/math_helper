package com.geeksforless;

import com.geeksforless.util.Checkers;
import com.geeksforless.util.Menu;
import com.geeksforless.util.UserInput;

public class Main {
    public static void main(String[] args) {
        startApplication(new Menu());
    }

    private static void startApplication(Menu menu) {
        menu.showPreview();

        String userInput = UserInput.getRidOfAllSpacesAndTransformInLowercase(UserInput.getUserEquation());

        menu.showUserInput(userInput);
        menu.showCorrectnessParenthesis(userInput);
        menu.showCorrectnessMathEquation(userInput);

        System.out.println("If the equation is correct, it will be added in database!");
        menu.addEquationInDb(userInput);

        System.out.println("Do you want to see some equations in DB by root? Please input root: ");
        String root  = UserInput.getRidOfAllSpacesAndTransformInLowercase(UserInput.getUserEquation());
        if (!Checkers.isNumber(root)) {
            System.out.println("Sorry, but it's not a number!");
        } else {
            menu.showEquationsByRoot(root);
        }


        System.out.println("Do you want to go on inputting equations?(Y/N)");
        String answer = UserInput.getRidOfAllSpacesAndTransformInLowercase(UserInput.getUserEquation());
        if (answer.equals("y")) {
            startApplication(menu);
        }
    }

}
