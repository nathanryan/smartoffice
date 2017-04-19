/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.KettleUI;
import com.google.gson.Gson;
import models.KettleModel;

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
        String json = new Gson().toJson(new KettleModel(KettleModel.Action.BOIL));
        String a = sendMessage(json);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        System.out.println("Client Received " + json);
        if (kettle.getAction() == KettleModel.Action.BOIL) {
            isWarming = kettle.getValue();
            ui.updateArea(kettle.getMessage());
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
