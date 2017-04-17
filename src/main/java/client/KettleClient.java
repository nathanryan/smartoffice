/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.KettleUI;
/**
 *
 * @author Karl
 */
public class KettleClient extends Client {
    private final String BOIL = "Boiled";
    private boolean isWarming = false;
    
    
    public KettleClient() {
        super();
        serviceType = "_kettle._udp.local.";
        ui = new KettleUI(this);
        name = "Kitchen Kettle";
    }

    /**
     * sends input for boil kettle.
     */
    public void boilWater() {
        if (!isWarming) {
            String a = sendMessage(BOIL);
            if (a.equals(OK)) {
                isWarming = true;
                ui.updateArea("Kettle is boiling..");
            }
        } else {
            ui.updateArea("Kettle is already boiling..");
        }
    }

    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Kettle is boiled.")) {
            isWarming = false;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new KettleUI(this);
        isWarming = false;
    }
}
