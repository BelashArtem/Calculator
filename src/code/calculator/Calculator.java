package code.calculator;

import code.actions.ActionType;

import java.awt.*;

/**
 * Created by Артем on 04.03.2016.
 */
public class Calculator {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new CalcFrame(27);
//            new CalcFrame(36, ActionType.SUBTRACTION);
//            new CalcConsole(27, ActionType.MULTIPLICATION);
        });
    }
}