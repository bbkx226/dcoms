package client.components;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import utils.InputUtils;

public class Form {
    private final Map<String, Object> fields;
    private final String cancelString;

    public Form() {
        this.fields = new HashMap<>();
        this.cancelString = "b";
    }

    // Add a string field, returns false if cancel string is entered
    public boolean addStringField(String fieldName, String prompt) {
        String input = InputUtils.stringInput(prompt, cancelString);
        if (input == null) { return false; }
        fields.put(fieldName, input);
        return true;
    }

    // Add an int field to the form with custom condition
    public boolean addIntField(String fieldName, String prompt, Predicate<Integer> condition, String errorMsg) {
        int input = getInputWithCondition(prompt, condition, errorMsg, InputType.INTEGER);
        if (input == Integer.MIN_VALUE) return false;
        fields.put(fieldName, input);
        return true;
    }

    // Add a double field to the form with custom condition
    public boolean addDoubleField(String fieldName, String prompt, Predicate<Double> condition, String errorMsg) {
        double input = getInputWithCondition(prompt, condition, errorMsg, InputType.DOUBLE);
        if (Double.isNaN(input)) return false;
        fields.put(fieldName, input);
        return true;
    }

    // Get the value of a field
    public Object getField(String fieldName) {
        return fields.get(fieldName);
    }

    @SuppressWarnings("unchecked")
    private <T> T getInputWithCondition(String prompt, Predicate<T> condition, String errorMsg, InputType type) {
        T input;
        boolean isValid;
        do {
            input = switch (type) {
                case INTEGER -> (T) (Integer) InputUtils.intInput(prompt, cancelString);
                case DOUBLE -> (T) (Double) InputUtils.doubleInput(prompt, cancelString);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };
            isValid = condition.test(input);

            if (!isValid) System.out.println(errorMsg);
            if (input instanceof Integer && input.equals(Integer.MIN_VALUE) || input instanceof Double && ((Double) input).isNaN()) {
                return null;
            }
        } while (!isValid);
        return input;
    }

    private enum InputType {
        INTEGER, DOUBLE
    }
}
