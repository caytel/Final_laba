package project;
public class Fraction implements Number {
    private final int numerator;
    private final int denominator;
    public Fraction(int numerator, int denominator) throws IllegalArgumentException{
        if(denominator == 0) throw new IllegalArgumentException("Знаменатель не может быть равен 0");
        this.numerator = numerator;
        this.denominator = denominator;
    }
    public static Fraction parse(String frac) throws IllegalArgumentException, NumberFormatException {
        String[] nums = frac.split("/");
        try {
            int denominator = Integer.parseInt(nums[1]);
            if (denominator == 0) throw new IllegalArgumentException("Знаменатель не может быть равен 0");
            int nominator = Integer.parseInt(nums[0]);
            return new Fraction(nominator, denominator);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Некорректное выражение  3");
        }
    }
    public static boolean tryParse(String frac) {
        String[] nums = frac.split("/");
        return nums.length == 2;
    }
    @Override
    public double sum(Number number) throws IllegalArgumentException {
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Суммировать можно только дроби");
        return this.getValue() + number.getValue();
    }
    @Override
    public Number add(Number number) throws IllegalArgumentException {
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Суммировать можно только дроби");
        if (denominator != ((Fraction) number).denominator) {
            return new Fraction(numerator * ((Fraction) number).denominator
                    + ((Fraction) number).numerator * denominator, denominator * ((Fraction) number).denominator);
        } else {
            return new Fraction(numerator + ((Fraction) number).numerator, denominator);
        }
    }
    public static Fraction add(Fraction number, Fraction number2) throws IllegalArgumentException {
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Аргументы не заданы: argument is null");

        if (number.denominator != number2.denominator) {
            return new Fraction(number.numerator * number2.denominator
                    + number2.numerator * number.denominator, number.denominator * number2.denominator);
        } else {
            return new Fraction(number.numerator + number2.numerator, number.denominator);
        }
    }
   @Override
    public double sub(Number number) {
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Вычитать можно только дроби");
        return getValue() - number.getValue();
    }
    @Override
    public Number subtract(Number number) {
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Вычитать можно только дроби");
        if (denominator != ((Fraction) number).denominator) {
            return new Fraction(numerator * ((Fraction) number).denominator
                    - ((Fraction) number).numerator * denominator, denominator * ((Fraction) number).denominator);
        } else {
            return new Fraction(numerator - ((Fraction) number).numerator, denominator);
        }
    }
    public static Fraction subtract(Fraction number, Fraction number2) throws IllegalArgumentException {
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Аргументы не заданы: argument is null");

        if (number.denominator != number2.denominator) {
            return new Fraction(number.numerator * number2.denominator
                    - number2.numerator * number.denominator, number.denominator * number2.denominator);
        } else {
            return new Fraction(number.numerator - number2.numerator, number.denominator);
        }
    }

    @Override
    public double mult(Number number) throws IllegalArgumentException{
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Умножать можно только дроби");
        return getValue() * number.getValue();
    }

    @Override
    public Number multiply(Number number) throws IllegalArgumentException{
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Умноженать можно только дроби");
        return new Fraction(numerator * ((Fraction) number).numerator, denominator * ((Fraction) number).denominator);
    }

    public static Number multiply(Fraction number, Fraction number2) throws IllegalArgumentException{
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Аргументы не заданы: argument is null");
        return new Fraction(number.numerator * number2.numerator, number.denominator * number2.denominator);
    }

   @Override
    public double divide(Number number) {
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Делить можно только дроби");
        if(number.getValue() == 0) throw new ArithmeticException("Деление на 0");
        return getValue() / number.getValue();
    }

    @Override
    public Number division(Number number) {
        if (number == null) throw new IllegalArgumentException("Аргумент не задан: argument is null");
        if (!(number instanceof Fraction)) throw new IllegalArgumentException("Делить можно только дроби");
        if(number.getValue() == 0) throw new ArithmeticException("Деление на 0");
        return new Fraction(numerator * ((Fraction) number).denominator, denominator * ((Fraction) number).numerator);
    }

    public static Number division(Fraction number, Fraction number2) throws IllegalArgumentException{
        if (number == null || number2 == null)
            throw new IllegalArgumentException("Аргументы не заданы: argument is null");
        if(number2.getValue() == 0) throw new ArithmeticException("Деление на 0");
        return new Fraction(number.numerator * number2.denominator, number.denominator * number2.numerator);
    }

    public static Fraction reduce(Fraction fraction) {
        int numerator = fraction.numerator;
        int denominator = fraction.denominator;
        for(int i = 2; i <= Math.abs(denominator); i++) {
            if(denominator % i == 0 && numerator % i == 0) {
                numerator /= i;
                denominator /= i;
                i = 1;
            }
        }
        if(numerator < 0 && denominator < 0) return new Fraction(-numerator, -denominator);
        if(numerator < 0 || denominator < 0) return new Fraction(-Math.abs(numerator), Math.abs(denominator));
        return new Fraction(numerator, denominator);
    }

    @Override
    public double getValue() throws ArithmeticException {
        if (denominator == 0) throw new ArithmeticException("Знаменатель равен 0");
        return (double) numerator / (double) denominator;
    }

    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }
}
