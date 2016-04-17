package code.actions;

/**
 * Created by a.belash on 17.04.2016.
 */
public class SubtractionInSS extends ActionInSS {

    public SubtractionInSS() {
        super();
    }

    public SubtractionInSS(int ss) {
        super(ss);
    }

    @Override
    protected String calculateWithValidArgs(String arg1, String arg2) {
        if(ActionInSSUtils.secondIsBigger(arg1, arg2)) {
            return "-" + calc(arg2, arg1);
        } else {
            return calc(arg1, arg2);
        }
    }

    private String calc(String arg1, String arg2) {
        char[] x = arg1.toCharArray(), y = arg2.toCharArray();
        StringBuilder result = new StringBuilder();

        int a, b, mi = 0, res;
        for (int i = 0; i < x.length; i++) {
            a = characters.indexOf(Character.toUpperCase(x[x.length - i - 1]));
            b = (y.length > i) ? characters.indexOf(Character.toUpperCase(y[y.length - i - 1])) : 0;

            res = a - b - mi;
            if(res < 0) {
                res += ss;
                mi = 1;
            } else {
                mi = 0;
            }

            result.insert(0, characters.charAt(res));
        }
        ActionInSSUtils.deleteZeroes(result);
        return result.toString();
    }

    @Override
    public String getSymbol() {
        return "-";
    }

    @Override
    public String getName() {
        return "Вычитание";
    }
}
