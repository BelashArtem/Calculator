package code.calculator;

import code.actions.ActionType;

import java.awt.*;

/**
 * Created by Артем on 04.03.2016.
 */
public class Calculator {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new CalcFrame(10);
//            new CalcFrame(10, ActionType.MULTIPLICATION);
//            new CalcConsole(27, ActionType.MULTIPLICATION);
        });
    }
}