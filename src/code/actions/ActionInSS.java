package code.actions;

/**
 * Created by Артем on 07.03.2016.
 */
public abstract class ActionInSS {

    /**
     * Максимальная длина операнда
     */
    public static final int MAX_LENGTH = 32;
    /**
     * Система счисления по умолчанию
     */
    public static final int DEFAULT_SS = 10;
    /**
     * Максимально большой алфавит
     */
    public static final String MAX_ALPH = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Основание системы счисления (СС)
     */
    protected int ss;
    /**
     * Строка алфавита текущей СС
     */
    protected String characters;

    public ActionInSS() {
        this(DEFAULT_SS);
    }

    public ActionInSS(int ss) {
        setSs(ss);
    }

    /**
     * Возвращает основание СС
     *
     * @return основание системы счисления
     */
    public int getSs() {
        return ss;
    }

    /**
     * Установка оснвания СС
     *
     * @param ss основание СС
     * @throws IllegalArgumentException ss должно принадлежать промежутку [2, {MAX_ALPH.length()}]
     */
    public void setSs(int ss) throws IllegalArgumentException {
        if (ss < 2 || ss > MAX_ALPH.length()) {
            throw new IllegalArgumentException("Основание СС должно принадлежать промежутку [2, " + MAX_ALPH.length() + "]!");
        }
        this.ss = ss;
        updateCharacters();
    }

    /**
     * Возвращает строку алфавита текущей СС
     *
     * @return строка
     */
    public String getCharacters() {
        return characters;
    }

    /**
     * Обновление строки алфавита текущей СС
     */
    private void updateCharacters() {
        characters = (MAX_ALPH).substring(0, ss);
    }

    /**
     * Производит действие над операндами с предварительной валидацией
     *
     * @param arg1 операнд 1
     * @param arg2 операнд 2
     * @return результат или сообщение об ошибке
     */
    public String calculate(String arg1, String arg2) {
        try {
            //Валидация 1-го аргумента
            validate(arg1);
            //Валидация 2-го аргумента
            validate(arg2);
            //Валидация пары аргументов
            validatePairOfArgs(arg1, arg2);
            //Подсчет результата для валидных аргументов
            return calculateWithValidArgs(arg1, arg2);
        } catch (IllegalArgumentException ex) {
            return ex.getMessage();
        } catch (Exception ex) {
            return "Неизвестная ошибка: " + ex.getMessage();
        }
    }

    /**
     * Метод валидации операнда
     *
     * @param s операнд
     * @throws IllegalArgumentException не валидный операнд
     */
    public void validate(String s) throws IllegalArgumentException {
        if (s == null || ("").equals(s.trim())) {
            throw new IllegalArgumentException("Операнд не должен быть пустым!");
        }

        if (s.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Размер операнда не должен превышать " + MAX_LENGTH + " символа!");
        }

        for (char ch : s.toCharArray()) {
            if (characters.indexOf(Character.toUpperCase(ch)) == -1) {
                throw new IllegalArgumentException("Символ '" + ch + "' не входит в " + ss + "-ичную систему счисления!");
            }
        }

        if ((s.length() > 1) && (s.charAt(0) == '0')) {
            throw new IllegalArgumentException("Операнд не может начинаться с 0!");
        }
    }

    /**
     * Проверка валидности строки
     *
     * @param s строка
     * @return валидность
     */
    public boolean isValid(String s) {
        try {
            validate(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Валидация пары аргументов<br>
     * Для переопределения в дочерних классах<br>
     * По умолчанию ничего не делает
     * @param arg1 первый аргумент
     * @param arg2 второй аргумент
     * @throws IllegalArgumentException если пара аргументов не валидна
     */
    protected void validatePairOfArgs(String arg1, String arg2) throws IllegalArgumentException {
        //do nothing
    }

    /**
     * Подсчет результата с учетом того, что оба аргумента валидны
     *
     * @param arg1 операнд 1
     * @param arg2 операнд 2
     * @return результат
     */
    protected abstract String calculateWithValidArgs(String arg1, String arg2);

    /**
     * Получение символа действия
     *
     * @return символ
     */
    public abstract String getSymbol();

    /**
     * Получение названия действия
     *
     * @return название
     */
    public abstract String getName();
}
