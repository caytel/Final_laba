package project;
import java.util.*;
import static project.Fraction.reduce;
import static project.Fraction.tryParse;

public class Calculator {
    private static int getPriority(String operation) {
        return operation.equals("/") || operation.equals("*") ? 1 : 0;
    }
    private static void sort(List<Map.Entry<Integer, String>> operations) {
        for (int i = 0; i < operations.size() - 1; i++) {
            for (int j = 0; j < operations.size() - i - 1; j++) {
                if (getPriority(operations.get(i).getValue()) < getPriority(operations.get(i + 1).getValue())) {
                    Map.Entry<Integer, String> temp = operations.get(i);
                    operations.set(i, operations.get(i + 1));
                    operations.set(i + 1, temp);
                }
            }
        }
    }

    private static Number calculateOO(Fraction one, String operation, Fraction two) {
        switch (operation) {
            case "*" -> {
                return Fraction.multiply(one, two);
            }
            case "-" -> {
                return Fraction.subtract(one, two);
            }
            case "+" -> {
                return Fraction.add(one, two);
            }
            case "/" -> {
                return Fraction.division(one, two);
            }
            default -> throw new IllegalArgumentException("Некорректная операция");
        }
    }
    public static void isCorrectBracket(String operation) {
        operation = operation.replace("[0-9]*", "");
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < operation.length(); i++) {
            if(operation.charAt(i) == '(') {
                stack.push(operation.charAt(i));
            } else if(operation.charAt(i) == ')') {
                if(stack.isEmpty() || stack.peek() != '(') throw new IllegalArgumentException("Некорректное выражение 1");
                stack.pop();
            }
        }
        if(!stack.isEmpty()) throw new IllegalArgumentException("Некорректное выражение 2");
    }

    public static Fraction calculate(String operation) throws IllegalArgumentException {
        Fraction result = null;
        isCorrectBracket(operation);
        while(true) {
            String[] brackets = operation.split("\\(|\\)");
            if(brackets.length == 0) break;
            for (String bracket : brackets) {
                if (bracket.isEmpty() || bracket.equals(" ")) continue;
                List<Map.Entry<Integer, String>> operations = new ArrayList<>();
                List<String> fractions = new ArrayList<>(Arrays.asList(bracket.split(" ")));
                fractions.removeIf(a-> a.equals("") || a.equals(" "));
                for (int i = 0; i < fractions.size(); i++) {
                    if (!fractions.get(i).equals("") && !tryParse(fractions.get(i))) {
                        operations.add(Map.entry(i, fractions.get(i)));
                    } else if (i > 0 && tryParse(fractions.get(i - 1))) {
                        fractions.add(i, "*");
                        operations.add(Map.entry(i++, "*"));
                    }
                }
                sort(operations);
                for (Map.Entry<Integer, String> op : operations) {
                    int index = op.getKey();
                    Fraction one = Fraction.parse(fractions.get(index - 1));
                    Fraction two = Fraction.parse(fractions.get(index + 1));
                    result = (Fraction) calculateOO(one, op.getValue(), two);
                    fractions.set(index + 1, result.toString());
                    fractions.set(index - 1, result.toString());
                }
                if(brackets.length == 1 && brackets[brackets.length - 1].equals(bracket)) {
                    assert result != null;
                    return reduce(result);
                }
                assert result != null;
                operation = operation.replace("(" + bracket + ")", result + " ");
                operation = operation.replace(bracket, result.toString());
                break;
            }
        }
        assert result != null;
        return reduce(result);
    }
}