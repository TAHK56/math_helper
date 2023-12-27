package com.geeksforless.util;

import com.geeksforless.dao.impl.EquationDaoImpl;
import com.geeksforless.dao.impl.EquationRootDaoImpl;
import com.geeksforless.dao.impl.RootDaoImpl;
import com.geeksforless.entity.Equation;
import com.geeksforless.entity.Root;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Menu {

    public void showPreview() {
        System.out.println("Hello user! This is the math equation helper!");
        System.out.println("Please input a math equation!");
        System.out.println("You are allowed to input: +, -, /, *, decimal numbers, (), =, spaces and " +
                           "an unknown variable X(or x)!");
        System.out.println("If you input spaces, they will be replaced!");
    }

    public void showCorrectnessParenthesis(String userInput) {
        System.out.println("Your equation parenthesis are " + (Checkers.isParenthesisCorrect(userInput)
                ? "correct!" : "incorrect!"
        ));
    }

    public void showUserInput(String userInput) {
        System.out.println("You have just inputted " + userInput);
    }

    public void showCorrectnessMathEquation(String userInput) {
        System.out.println("Your equation  are " + getAnswer(Checkers.isUserInputMathEquation(userInput)));
    }

    public void addEquationInDb(String userInput) {
        if (Checkers.isUserInputMathEquation(userInput)) {
            Equation equation = new Equation();
            equation.setViewInDb(userInput);
            Long id;
            EquationDaoImpl equationDao = new EquationDaoImpl();
            Equation equationInDBExist = equationDao.findByViewInDB(userInput);
            if (Objects.isNull(equationInDBExist)) {
                equationDao.create(equation);
                id = equationDao.findByViewInDB(userInput).getId();
            } else {
                System.out.println("Equation is exist!");
                id = equationInDBExist.getId();

            }
            showInvitationInputRoots(userInput, id);
        }

    }

    private void showInvitationInputRoots(String equation, Long id) {
        System.out.println("Do you want to input roots?");
        System.out.println("If you want to input roots enter Y or we create your equation with null key!!");
        String answer = UserInput.getUserEquation().toLowerCase();
        EquationRootDaoImpl equationRootDao = new EquationRootDaoImpl();
        if (!answer.equals("y")) {
            equationRootDao.create(id, null);
            return;
        }
        SolverEquation solverEquation = new SolverEquation(equation);
        while (answer.equals("y")) {
            System.out.println("Please input root:");
            String root = UserInput.getRidOfAllSpacesAndTransformInLowercase(UserInput.getUserEquation());
            System.out.println("Your root  is " + getAnswer(solverEquation.isRoot(root)));
            if (solverEquation.isRoot(root)) {
                Root rootInDb = new Root();
                rootInDb.setViewInDb(root);
                RootDaoImpl rootDao = new RootDaoImpl();
                Root rootInDBExist = rootDao.findByViewInDB(root);
                if (Objects.isNull(rootInDBExist)) {
                    rootDao.create(rootInDb);
                    equationRootDao.create(id, rootDao.findByViewInDB(root).getId());
                } else {
                    System.out.println("Root is exist!");
                    equationRootDao.create(id, rootInDBExist.getId());
                }
            }
            System.out.println("Do you want to go on ?");
            answer = UserInput.getRidOfAllSpacesAndTransformInLowercase(UserInput.getUserEquation());
        }

    }

    public void showEquationsByRoot(String root) {
        RootDaoImpl rootDao = new RootDaoImpl();
        System.out.println(displayInOneString(rootDao.findAllEquationsByRoot(root)));
        System.out.println("There are some equations have only one root");
        System.out.println(displayInOneString(rootDao.findAllEquationsHasOneRoot(root)));
    }

    private String displayInOneString(Set<Equation> equations) {
        return equations.stream()
                .map(Equation::getViewInDb)
                .collect(Collectors.joining("; "));
    }

    private String getAnswer(boolean answer) {
        return answer ? "correct!" : "incorrect!";
    }

}
