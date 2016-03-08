package code.actions;

/**
 * Created by Артем on 07.03.2016.
 */
public class AdditionInSS extends ActionInSS {

    public AdditionInSS() {
        super();
    }

    public AdditionInSS(int ss) {
        super(ss);
    }

    @Override
    protected String calculateWithValidArgs(String arg1, String arg2) {
        char[] x = arg1.toCharArray(), y = arg2.toCharArray();
        StringBuilder result = new StringBuilder();

        int maxLength = (x.length > y.length) ? x.length : y.length;
        int a, b, res, pl = 0;
        for (int i = 0; i < maxLength; i++) {
            a = (x.length > i) ? characters.indexOf(Character.toUpperCase(x[x.length - i - 1])) : 0;
            b = (y.length > i) ? characters.indexOf(Character.toUpperCase(y[y.length - i - 1])) : 0;
            res = a + b + pl;
            pl = res/ss;
            result.insert(0, characters.charAt(res%ss));
        }
        if (pl != 0) {
            result.insert(0, characters.charAt(pl));
        }
        return result.toString();
    }

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public String getName() {
        return "Сложение";
    }
}
