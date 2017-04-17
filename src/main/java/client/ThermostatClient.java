/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.ThermostatUI;
/**
 *
 * @author nathan
 */
public class ThermostatClient extends Client {
    private final String INCREASE_TEMP = "INCREASE_TEMP";
    private final String DECREASE_TEMP = "DECREASE_TEMP";
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
     * sends a message to warm the bed.
     */
    public void increase_temp() {
        if (!isWarming) {
            String a = sendMessage(INCREASE_TEMP);
            if (a.equals(OK)) {
                //isWarming = true;
                ui.updateArea("Increasing temperature by 1c");
            }
        } else {
            ui.updateArea("Temperature already increasing");
        }
    }
    
    public void decrease_temp() {
        if (!isWarming) {
            String a = sendMessage(DECREASE_TEMP);
            if (a.equals(OK)) {
                
                ui.updateArea("Decreasing temperature by 1c");
            }
        } else {
            ui.updateArea("Temperature already decreasing");
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
