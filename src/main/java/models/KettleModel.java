/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Karl
 */
public class KettleModel {

    public enum Action {
        BOIL, STATUS
    }

    private Action action;
    private String message;
    private boolean value;

    public KettleModel() {
    }

    public KettleModel(Action action) {
        this.action = action;
    }

    public KettleModel(Action action, String message) {
        this.action = action;
        this.message = message;
    }

    public KettleModel(Action action, String message, boolean value) {
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
