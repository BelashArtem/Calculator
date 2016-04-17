package code.calculator;

import code.actions.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Артем on 04.03.2016.
 */
public class CalcFrame extends JFrame {

    //Размеры компонентов
    private static final int XY_WIDTH = ActionInSS.MAX_LENGTH * 12;//~300;
    private static final int RESULT_WIDTH = XY_WIDTH * 2 + 30;//620;
    private static final int FRAME_WIDTH = RESULT_WIDTH + 30;//650;
    private static final int FRAME_HEIGHT = 115;
    //Компоненты
    private JTextField tfX;
    private JTextField tfY;
    private JTextField tfResult;
    private int ss;
    private ResultAction resultAction;
    private ActionInSS action;

//    public CalcFrame() {
//        this(ActionInSS.DEFAULT_SS, ActionType.ADDITION);
//    }

    public CalcFrame(int ss) {
        this(ss, null);
    }

    public CalcFrame(int ss, ActionType actionType) {
        super();
        resultAction = new ResultAction();
        setSs(ss);
        setAction(actionType);
        createGUI();
    }

    public void setSs(int ss) {
        this.ss = ss;
        if (resultAction != null) {
            resultAction.setSs(ss);
        }
    }

    /**
     * Установка соответствующего действия
     *
     * @param actionType действие
     */
    private void setAction(ActionType actionType) {
        ActionInSS tempAction;

        if(actionType != null) {
            switch (actionType) {
                case ADDITION:
                    tempAction = new AdditionInSS(ss);
                    break;
                case MULTIPLICATION:
                    tempAction = new MultiplicationInSS(ss);
                    break;
                case SUBTRACTION:
                    tempAction = new SubtractionInSS(ss);
                    break;
                case DIVISION:
                    tempAction = new DivisionInSS(ss);
                    break;
                //Можно добавлять новые действия
                default:
                    tempAction = new AdditionInSS(ss);
                    break;
            }
            action = tempAction;
            resultAction.setAction(action);
        }
    }

    private JComboBox cbActionChooser;

    private ArrayList<String> actionSymbols;
    private ArrayList<ActionInSS> actionsToChoose;


    private void fillActionMap() {
        actionSymbols = new ArrayList<>();
        actionsToChoose = new ArrayList<>();
        ActionInSS act;

        //Сложение
        act = new AdditionInSS(ss);
        actionSymbols.add(act.getSymbol());
        actionsToChoose.add(act);

        //Умножение
        act = new MultiplicationInSS(ss);
        actionSymbols.add(act.getSymbol());
        actionsToChoose.add(act);

        //Вычитание
        act = new SubtractionInSS(ss);
        actionSymbols.add(act.getSymbol());
        actionsToChoose.add(act);

        //Деление
        act = new DivisionInSS(ss);
        actionSymbols.add(act.getSymbol());
        actionsToChoose.add(act);
    }

    private void createGUI() {
        String actionNameForTitle = (resultAction != null && resultAction.action != null) ? resultAction.getActionName() : "Действия";
        setTitle(actionNameForTitle + " в " + ss + "-ичной системе счисления");

        try {
            setIconImage(ImageIO.read(new File("src\\resource\\icons\\calc_icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tfX = createTextField(XY_WIDTH, true);
        tfY = createTextField(XY_WIDTH, true);
        tfResult = createTextField(RESULT_WIDTH, false);

        //Срабатвыание кнопки "=" по Enter
        int condition = JPanel.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = ((JPanel) getContentPane()).getInputMap(condition);
        ActionMap actionMap = ((JPanel) getContentPane()).getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");
        actionMap.put("enter", resultAction);

        JPanel panel = new JPanel(new FlowLayout());

        panel.add(tfX);

        if(resultAction != null && resultAction.action != null) {
            panel.add(new JLabel(" " + resultAction.getActionSymbol() + " "));
        } else {
            fillActionMap();
            cbActionChooser = new JComboBox(actionSymbols.toArray());
            cbActionChooser.setSelectedIndex(-1);
            cbActionChooser.addItemListener(e -> {
                String selectedItem = e.getItem().toString();
                int index = actionSymbols.indexOf(selectedItem);
                if (index != -1) {
                    action = actionsToChoose.get(index);
                    resultAction.setAction(action);
                    tfX.setEnabled(true);
                    tfY.setEnabled(true);
                }
            });
            panel.add(cbActionChooser);
            tfX.setEnabled(false);
            tfY.setEnabled(false);
        }

        panel.add(tfY);
        panel.add(new JButton(resultAction));
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
     *
     * @param width ширина
     * @return настроенный объект JTextField
     */
    private JTextField createTextField(int width, boolean inputControl) {
        JTextField tf = new JTextField();
        tf.setPreferredSize(new Dimension(width, (int) tf.getPreferredSize().getHeight()));
        if (inputControl) {
            tf.setDocument(new PlainDocument() {
                @Override
                public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                    String text = tf.getText() + str;
                    if (text.length() == 0 || action.isValid(text)) {
                        super.insertString(offs, str.toUpperCase(), a);
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
        private ActionInSS action;
        private int ss;

        public ResultAction() {
            super();
            putValue(Action.NAME, " = ");
//            action = new AdditionInSS(10);
        }

        public void setSs(int ss) {
            this.ss = ss;
            if (action != null) {
                action.setSs(ss);
            }
        }

        public ActionInSS getAction() {
            return action;
        }

        public void setAction(ActionInSS action) {
            this.action = action;
        }

        public String getActionSymbol() {
            return action.getSymbol();
        }

        public String getActionName() {
            return action.getName();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(action != null) {
                tfResult.setText(action.calculate(tfX.getText(), tfY.getText()));
            } else {
                tfResult.setText("Выберите действие!");
            }
        }
    }

}
