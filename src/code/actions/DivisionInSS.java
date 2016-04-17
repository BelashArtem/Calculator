package code.actions;

import javax.swing.plaf.ActionMapUIResource;

/**
 * Created by a.belash on 17.04.2016.
 */
public class DivisionInSS extends ActionInSS {

    private SubtractionInSS subAction;
    private MultiplicationInSS mulAction;

    public DivisionInSS() {
        super();
    }

    public DivisionInSS(int ss) {
        super(ss);
    }

    @Override
    protected String calculateWithValidArgs(String arg1, String arg2) {
        if(arg1.equals(arg2)) {
            return "1";
        } else if (ActionInSSUtils.secondIsBigger(arg1, arg2)) {
            return "0";
        } else {
            if (subAction == null)
                subAction = new SubtractionInSS(ss);
            if (mulAction == null)
                mulAction = new MultiplicationInSS(ss);

            StringBuilder result = new StringBuilder();
            StringBuilder ost = new StringBuilder();
            char ch;

            for (int i = 0; i < arg1.length(); i++) {
                //Добавляем к остатку след. цифру
                ost.append(arg1.charAt(i));
                ActionInSSUtils.deleteZeroes(ost);
                if(ActionInSSUtils.secondIsBigger(ost.toString(), arg2)) {
                    result.append("0");
                } else {
                    ch = ActionInSSUtils.getMaxCoeff(ss, arg2, ost.toString());
                    result.append(ch);
                    ost = new StringBuilder(subAction.calculate(ost.toString(), mulAction.calculate(ch + "", arg2)));
                }
            }
            ActionInSSUtils.deleteZeroes(result);
            return result.toString();
        }
    }

    @Override
    protected void validatePairOfArgs(String arg1, String arg2) throws IllegalArgumentException {
        super.validatePairOfArgs(arg1, arg2);
        if("0".equals(arg2)) {
            throw new IllegalArgumentException("Нельзя делить на ноль!");
        }
    }

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public String getName() {
        return "Целочисленное деление";
    }
}
