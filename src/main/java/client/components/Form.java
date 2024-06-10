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

    // Add a string field to the form with custom condition
    public boolean addStringField(String fieldName, String prompt, Predicate<String> condition, String errorMsg) {
        boolean isValid;
        String input;

        do {
            input = InputUtils.stringInput(prompt, cancelString);
            isValid = condition.test(input);

            if (!isValid) { System.out.println(errorMsg); }
            if (input == null) { return false; }
        } while (!isValid);

        fields.put(fieldName, input);
        return true;
    }

    // Add an int field, returns false if cancel string is entered
    public boolean addIntField(String fieldName, String prompt) {
        int input = InputUtils.intInput(prompt, cancelString);
        if (input == Integer.MIN_VALUE) { return false; }
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

    // Get the value of a field
    public Object getField(String fieldName) {
        return fields.get(fieldName);
    }

    // Display all fields and their values
    public void displayFields() {
        System.out.println("Form Data:");
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
