package models;

/*
 * PrinterModel.java
 *
 * @author Nathan Ryan, x13448212
 */

public class PrinterModel {

    public enum Action {
        STATUS, CHECK_QUEUE, ADD_QUEUE, REMOVE_QUEUE, PRINT_QUEUE
    }

    private Action action;
    private String message;
    private boolean value;

    public PrinterModel() {
    }

    public PrinterModel(Action action) {
        this.action = action;
    }

    public PrinterModel(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public PrinterModel(Action action, String message, boolean value) {
        this.action = action;
        this.message = message;
        this.value = value;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

}
