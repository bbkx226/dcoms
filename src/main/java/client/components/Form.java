package client.components;

import utils.InputUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

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
        boolean isValid;
        int input;

        do {
            input = InputUtils.intInput(prompt, cancelString);
            isValid = condition.test(input);

            if (!isValid) { System.out.println(errorMsg); }
            if (input == Integer.MIN_VALUE) { return false; }
        } while (!isValid);

        fields.put(fieldName, input);
        return true;
    }

    // Add a double field to the form with custom condition
    public boolean addDoubleField(String fieldName, String prompt, Predicate<Double> condition, String errorMsg) {
        boolean isValid;
        double input;

        do {
            input = InputUtils.doubleInput(prompt, cancelString);
            isValid = condition.test(input);

            if (!isValid) { System.out.println(errorMsg); }
            if (Double.isNaN(input)) { return false; }
        } while (!isValid);

        fields.put(fieldName, input);
        return true;
    }

    // Get the value of a field
    public Object getField(String fieldName) {
        return fields.get(fieldName);
    }
}
