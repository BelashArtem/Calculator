package code.calculator;

import code.actions.ActionInSS;
import code.actions.ActionType;
import code.actions.AdditionInSS;
import code.actions.MultiplicationInSS;
import code.exception.EndCalculationException;

import java.util.Scanner;

/**
 * Created by Dmitry Murich on 05.04.2016.
 */
public class CalcConsole {
    private static final String FIRST_ARGUMENT = "первый операнд";
    private static final String SECOND_ARGUMENT = "второй операнд";
    private static final String END_CALCULATIONS = ":q";

    private ActionInSS action;
    private String leftArgument;
    private String rightArgument;

    private int ss;

    public CalcConsole() {
        this(ActionInSS.DEFAULT_SS, ActionType.ADDITION);
    }

    public CalcConsole(int ss, ActionType actionType) {
        this.ss = ss;
        setAction(actionType);
        action.setSs(ss);
        while (true) {
            try {
                leftArgument = readAndValidateArgument(FIRST_ARGUMENT);
                rightArgument = readAndValidateArgument(SECOND_ARGUMENT);
                System.out.printf("Результат: %s \n", action.calculate(leftArgument, rightArgument));
            } catch (EndCalculationException e) {
                break;
            }
        }
    }

    private String readAndValidateArgument(String argumentName) throws EndCalculationException {
        String argument;
        // Цикл выполняется до тех пор, пока пользователь не введет валидный аргумент.
        while (true) {
            System.out.printf("Введите %s:\n", argumentName);
            try {
                argument = new Scanner(System.in).nextLine();
                if (argument.contains(END_CALCULATIONS)) {
                    throw new EndCalculationException();
                }
                action.validate(argument);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return argument;
    }

    private void setAction(ActionType actionType) {
        ActionInSS tempAction;
        switch (actionType) {
            case ADDITION:
                tempAction = new AdditionInSS(ss);
                break;
            case MULTIPLICATION:
                tempAction = new MultiplicationInSS(ss);
                break;
            //Можно добавлять новые действия
            default:
                tempAction = new AdditionInSS(ss);
                break;
        }
        action = tempAction;
    }
}
