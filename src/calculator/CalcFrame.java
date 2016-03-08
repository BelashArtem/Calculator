package calculator;

import actions.ActionInSS;
import actions.AdditionInSS;
import actions.MultiplicationInSS;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Артем on 04.03.2016.
 */
public class CalcFrame extends JFrame {

    //Компоненты
    private JTextField tfX = null;
    private JTextField tfY = null;
    private JTextField tfResult = null;
    private JButton btMultiply = null;
    private JTextField statusBar = null;

    //Размеры компонентов
    private static final int XY_WIDTH = 300;
    private static final int RESULT_WIDTH = 620;
    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 115;

    //Действия калькулятора
    public static final int ADDITION = 0;
    public static final int MULTIPLICATION = 1;

    private int ss = 10;
    private ResultAction resultAction = null;
    private ActionInSS action = null;

    public CalcFrame() {
        this(10, ADDITION);
    }

    public CalcFrame(int ss, int actionType) {
        super();
        resultAction = new ResultAction();
        setSs(ss);
        setAction(actionType);
        createGUI();
    }

    public void setSs(int ss) {
        this.ss = ss;
        resultAction.setSs(ss);
    }

    /**
     * Установка соответствующего действия
     * @param actionType действие
     */
    private void setAction(int actionType) {
        ActionInSS tempAction = null;
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
        resultAction.setAction(action);
    }

    private void createGUI() {
        setTitle(resultAction.getActionName() + " в " + ss + "-ичной системе счисления");

        tfX = createTextField(XY_WIDTH, true);
        tfY = createTextField(XY_WIDTH, true);
        tfResult = createTextField(RESULT_WIDTH, false);

        btMultiply = new JButton(resultAction);

        JPanel panel = new JPanel(new FlowLayout());

        panel.add(tfX);
        panel.add(new JLabel(" " + resultAction.getActionSymbol() + " "));
        panel.add(tfY);
        panel.add(btMultiply);
        panel.add(tfResult);

        getContentPane().add(panel);

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Возвращает настроенный объект JTextField
     * @param width ширина
     * @return настроенный объект JTextField
     */
    private JTextField createTextField(int width, boolean inputControl) {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(width, (int)tf.getPreferredSize().getHeight()));
        if (inputControl) {
            tf.setDocument(new PlainDocument() {
                ActionInSS act = resultAction.getAction();
                @Override
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    String text = tf.getText() + str;
                    if (text == null || text.length() == 0 || action.isValid(text)) {
                        super.insertString(offs, str, a);
                    }
                }
            });
        }
        return tf;
    }

    /**
     * Класс события для кнопки "="
     */
    private class ResultAction extends AbstractAction {
        private ActionInSS action = null;

        public ResultAction() {
            super();
            putValue(Action.NAME, " = ");
            action = new AdditionInSS(10);
        }

        public void setSs(int ss) {
            action.setSs(ss);
        }

        public void setAction(ActionInSS action) {
            this.action = action;
        }

        public ActionInSS getAction() {
            return action;
        }

        public String getActionSymbol() {
            return action.getSymbol();
        }

        public String getActionName() {
            return action.getName();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tfResult.setText(action.calculate(tfX.getText(), tfY.getText()));
        }
    }

}
