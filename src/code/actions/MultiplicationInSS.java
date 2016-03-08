package code.actions;

/**
 * Created by Артем on 07.03.2016.
 */
public class MultiplicationInSS extends ActionInSS {

    AdditionInSS addAction = null;

    public MultiplicationInSS() {
        super();
    }

    public MultiplicationInSS(int ss) {
        super(ss);
    }

    @Override
    protected String calculateWithValidArgs(String arg1, String arg2) {
        if (addAction == null)
            addAction = new AdditionInSS(ss);

        char[] x = arg1.toCharArray(), y = arg2.toCharArray();
        String result = "0";
        StringBuilder tempRes;
        int a, b, res, pl;
        for(int i = y.length - 1; i >= 0; i--) {
            tempRes = new StringBuilder();
            pl = 0;
            for(int j = x.length - 1; j >= 0; j--) {
                a = characters.indexOf(Character.toUpperCase(x[j]));
                b = characters.indexOf(Character.toUpperCase(y[i]));
                res = a * b + pl;
                pl = res/ss;
                tempRes.insert(0, characters.charAt(res%ss));
            }
            if (pl != 0) {
                tempRes.insert(0, characters.charAt(pl));
            }
            appendZeroes(tempRes, y.length - i - 1);
            result = addAction.calculateWithValidArgs(result, tempRes.toString());
        }

        return result;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public String getName() {
        return "Умножение";
    }

    /**
     * Умножение результата на count порядков с учетом СС
     * @param sb результат
     * @param count порядок
     */
    private void appendZeroes(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) {
            sb.append("0");
        }
    }
}
