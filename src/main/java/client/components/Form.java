package client.components;

import utils.InputUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Form {
    private final Map<String, String> fields;
    private final Scanner scanner;

    public Form() {
        fields = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Add a field to the form
    public void addField(String fieldName, String prompt) {
        System.out.print(prompt + " ");
        String input = InputUtils.stringInput();
        fields.put(fieldName, input);
    }

    // Get the value of a field
    public String getField(String fieldName) {
        return fields.get(fieldName);
    }

    // Display all fields and their values
    public void displayFields() {
        System.out.println("Form Data:");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

}
