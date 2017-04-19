package models;

/*
 * LightsModel.java
 *
 * @author Karl King, x13565467
 */

public class LightsModel {

    public enum Action {
        BRIGHTEN_LIGHTS, DIM_LIGHTS, STATUS, TURN_OFF_LIGHTS, TURN_ON_LIGHTS
    }

    private Action action;
    private String message;
    private boolean value;

    public LightsModel() {
    }

    public LightsModel(Action action) {
        this.action = action;
    }

    public LightsModel(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public LightsModel(Action action, String message, boolean value) {
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
