package test;

import code.actions.ActionInSS;
import code.actions.AdditionInSS;
import code.actions.MultiplicationInSS;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dmitry Murich on 05.04.2016.
 */
public class AdditionInSSTest {
    private int ss;
    private ActionInSS actionInSS;
    private String firstOperand;
    private String secondOperand;

    @Test
    public void testAdditionInDecimalSystem() throws Exception {
        ss = 10;
        actionInSS = new AdditionInSS(ss);
        firstOperand = "10000";
        secondOperand = "20000";
        assertEquals("30000", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testAdditionIn27System() throws Exception {
        ss = 27;
        actionInSS = new AdditionInSS(ss);
        firstOperand = "123456";
        secondOperand = "12345a";
        assertEquals("2468AG", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testMultiplicationDecimalSystem() throws Exception {
        ss = 10;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "10000";
        secondOperand = "20000";
        assertEquals("200000000", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testMultiplicationIn27System() throws Exception {
        ss = 27;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "123456";
        secondOperand = "12345a";
        assertEquals("14ALA90AB16", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testValidationOddSymbolMessage() throws Exception {
        ss = 27;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "123456z";
        try {
            actionInSS.validate(firstOperand);
        } catch (IllegalArgumentException e) {
            assertEquals("Символ 'z' не входит в 27-ичную систему счисления!", e.getMessage());
        }
    }

    @Test
    public void testValidationTooLongOperandMessage() throws Exception {
        ss = 27;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "123456123123124124124124124124124124";
        try {
            actionInSS.validate(firstOperand);
        } catch (IllegalArgumentException e) {
            assertEquals("Размер операнда не должен превышать 32 символа!", e.getMessage());
        }
    }
}