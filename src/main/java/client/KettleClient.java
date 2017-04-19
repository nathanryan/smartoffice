/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.KettleUI;
import com.google.gson.Gson;
import models.KettleModel;
import models.LightsModel;

/**
 *
 * @author Karl
 */
public class KettleClient extends Client {

    private final String BOIL = "Boiled";
    private boolean isWarming = false;
    private final String EMPTY_KETTLE = "EMPTY_KETTLE";
    private final String FILL_KETTLE = "FILL_KETTLE";
    private final String TURN_OFF = "TURN_OFF";
    private final String TURN_ON = "TURN_ON";

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

    //fill the kettle message
    public void fill_kettle() {
        String json = new Gson().toJson(new KettleModel(KettleModel.Action.FILL_KETTLE));
        String a = sendMessage(json);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        System.out.println("Client Received " + json);

        if (kettle.getAction() == KettleModel.Action.FILL_KETTLE) {
            isWarming = kettle.getValue();
            ui.updateArea(kettle.getMessage());
        }
    }

    //empty kettle message
    public void empty_kettle() {
        String json = new Gson().toJson(new KettleModel(KettleModel.Action.EMPTY_KETTLE));
        String a = sendMessage(json);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        System.out.println("Client Received " + json);

        if (kettle.getAction() == KettleModel.Action.EMPTY_KETTLE) {
            isWarming = kettle.getValue();
            ui.updateArea(kettle.getMessage());
        }
    }

    //turn off power message
    public void power_off() {
        String json = new Gson().toJson(new KettleModel(KettleModel.Action.TURN_OFF));
        String a = sendMessage(json);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        System.out.println("Client Received " + json);

        if (kettle.getAction() == KettleModel.Action.TURN_OFF) {
            isWarming = kettle.getValue();
            ui.updateArea(kettle.getMessage());
        }
    }
    
    //turn on power message
    public void power_on() {
        String json = new Gson().toJson(new KettleModel(KettleModel.Action.TURN_ON));
        String a = sendMessage(json);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        System.out.println("Client Received " + json);

        if (kettle.getAction() == KettleModel.Action.TURN_ON) {
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
