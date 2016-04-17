package code.actions;

/**
 * Created by a.belash on 17.04.2016.
 */
public class ActionInSSUtils {
    /**
     * Умножение результата на count порядков с учетом СС
     * @param sb    результат
     * @param count количество порядков
     */
    public static void appendZeroes(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append("0");
        }
    }

    /**
     * Удаляет начальные нули
     */
    public static void deleteZeroes(StringBuilder sb) {
        while(sb.charAt(0) == '0' && sb.length() > 1) {
            sb.deleteCharAt(0);
        }
    }

    /**
     * Возвращает true, если arg1 < arg2
     */
    public static boolean secondIsBigger(String arg1, String arg2) {
        if(arg1.length() > arg2.length()) {
            return false;
        } else if(arg1.length() < arg2.length()) {
            return true;
        } else {
            char[] x = arg1.toCharArray(), y = arg2.toCharArray();
            int a,b;
            for (int i = 0; i < x.length; i++) {
                a = ActionInSS.MAX_ALPH.indexOf(Character.toUpperCase(x[i]));
                b = ActionInSS.MAX_ALPH.indexOf(Character.toUpperCase(y[i]));
                if(a != b) {
                    if(a > b) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * Возвращает max(coeff), при условии: coeff * arg1 <= arg2
     * @param arg1
     * @param arg2
     */
    public static char getMaxCoeff(int ss, String arg1, String arg2) {
        MultiplicationInSS mulAction = new MultiplicationInSS(ss);

        String res;
        for (int i = 0; i < ActionInSS.MAX_ALPH.length(); i++) {
            res = mulAction.calculate(ActionInSS.MAX_ALPH.charAt(i) + "", arg1);
            if(ActionInSSUtils.secondIsBigger(arg2, res)) {
                return ActionInSS.MAX_ALPH.charAt(i-1);
            }
        }
        throw new IllegalArgumentException("getMaxCoeff: arguments error!");
    }
}
