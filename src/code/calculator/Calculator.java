package code.calculator;

import java.awt.*;

/**
 * Created by Артем on 04.03.2016.
 */
public class Calculator {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CalcFrame frame = new CalcFrame(2, CalcFrame.ADDITION);
            }
        });

    }
}