package test;

import code.actions.*;
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
        secondOperand = "12345A";
        assertEquals("2468AG", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testMultiplicationDecimalSystem() throws Exception {
        ss = 10;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "1";
        secondOperand = "2";
        assertEquals("2", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testMultiplicationIn27System() throws Exception {
        ss = 27;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "123456";
        secondOperand = "12345A";
        assertEquals("14ALA90AB16", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testSubtractionDecimalSystem() throws Exception {
        ss = 10;
        actionInSS = new SubtractionInSS(ss);
        firstOperand = "12345";
        secondOperand = "9999";
        assertEquals("2346", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testSubtractionIn27System() throws Exception {
        ss = 27;
        actionInSS = new SubtractionInSS(ss);
        firstOperand = "74GB78F4832";
        secondOperand = "56862G6G3DQFG";
        assertEquals("-5611D4Q7F9ICE", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testDivisionDecimalSystem() throws Exception {
        ss = 10;
        actionInSS = new DivisionInSS(ss);
        firstOperand = "12345678";
        secondOperand = "893";
        assertEquals("13824", actionInSS.calculate(firstOperand, secondOperand));
    }

    @Test
    public void testDivisionIn27System() throws Exception {
        ss = 27;
        actionInSS = new DivisionInSS(ss);
        firstOperand = "12345678";
        secondOperand = "893";
    }

    @Test
    public void testValidationOddSymbolMessage() throws Exception {
        ss = 27;
        actionInSS = new MultiplicationInSS(ss);
        firstOperand = "123456Z";
        try {
            actionInSS.validate(firstOperand);
        } catch (IllegalArgumentException e) {
            assertEquals("Символ 'Z' не входит в 27-ичную систему счисления!", e.getMessage());
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