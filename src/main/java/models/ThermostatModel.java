package models;

/*
 * ThermostatModel.java
 *
 * @author Nathan Ryan, x13448212
 */

public class ThermostatModel {

    public enum Action {
        INCREASE_TEMP, DECREASE_TEMP, STATUS, RESET
    }

    private Action action;
    private String message;
    private boolean value;

    public ThermostatModel() {
    }

    public ThermostatModel(Action action) {
        this.action = action;
    }

    public ThermostatModel(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public ThermostatModel(Action action, String message, boolean value) {
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
