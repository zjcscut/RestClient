package cn.zjc.utils;

import javafx.scene.control.TextField;

/**
 *
 * @author Wies Kueter
 */
public class Validation {

    public static boolean minMax(String field, int min, int max)  {
        
        if(field.length() >= min && field.length() <= max) {
            return true;
        }
        
        return false;
    }
    
    public static boolean min(String field, int min) {
        
        return field.length() >= min;
    }
    
    public static boolean max(String field, int max) {
        
        return field.length() <= max;
    }
    
    public static void errorMessage(TextField field, String message) {

        field.setText("");
        field.setPromptText(message);
        field.getStyleClass().add("error_prompt");
    }
}
