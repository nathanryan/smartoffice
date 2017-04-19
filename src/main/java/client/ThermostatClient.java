package client;

import clientui.ThermostatUI;
import com.google.gson.Gson;
import models.ThermostatModel;

/*
 * ThermostatClient.java
 *
 * @reference Dominic Carr https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 * @author Nathan Ryan, x13448212
 */

public class ThermostatClient extends Client {

    private final String INCREASE_TEMP = "INCREASE_TEMP";
    private final String DECREASE_TEMP = "DECREASE_TEMP";
    private final String RESET_TEMP = "RESET_TEMP";
    private boolean isWarming = false;

    /**
     * Bed Client Constructor.
     */
    public ThermostatClient() {
        super();
        serviceType = "_thermostat._udp.local.";
        ui = new ThermostatUI(this);
        name = "Office Thermostat";
    }

    /**
     * sends a message to warm the room.
     */
    public void increase_temp() {
        String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.INCREASE_TEMP));
        String a = sendMessage(json);
        ThermostatModel thermostat = new Gson().fromJson(a, ThermostatModel.class);
        System.out.println("Client Received " + json);

        if (thermostat.getAction() == ThermostatModel.Action.INCREASE_TEMP) {
            isWarming = thermostat.getValue();
            ui.updateArea(thermostat.getMessage());
        }
    }

    //decrease temp message
    public void decrease_temp() {
        String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.DECREASE_TEMP));
        String a = sendMessage(json);
        ThermostatModel thermostat = new Gson().fromJson(a, ThermostatModel.class);
        System.out.println("Client Received " + json);

        if (thermostat.getAction() == ThermostatModel.Action.DECREASE_TEMP) {
            isWarming = thermostat.getValue();
            ui.updateArea(thermostat.getMessage());
        }
    }

    //reset temp message
    public void reset_temp() {
        String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.RESET));
        String a = sendMessage(json);
        ThermostatModel thermostat = new Gson().fromJson(a, ThermostatModel.class);
        System.out.println("Client Received " + json);

        if (thermostat.getAction() == ThermostatModel.Action.RESET) {
            isWarming = thermostat.getValue();
            ui.updateArea(thermostat.getMessage());
        }
    }

    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Room is 100% warmed.")) {
            isWarming = false;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new ThermostatUI(this);
        isWarming = false;
    }
}
