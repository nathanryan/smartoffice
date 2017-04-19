/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.LightsUI;
import com.google.gson.Gson;
import models.LightsModel;

/**
 *
 * @author Karl
 */
public class LightsClient extends Client {

    private final String BRIGHTEN_LIGHTS = "BRIGHTEN_LIGHTS";
    private final String DIM_LIGHTS = "DIM_LIGHTS";
    private final String TURN_OFF_LIGHTS = "TURN_OFF_LIGHTS";
    private final String TURN_ON_LIGHTS = "TURN_ON_LIGHTS";
    private boolean isChanging = false;

    /**
     * Bed Client Constructor.
     */
    public LightsClient() {
        super();
        serviceType = "_lights._udp.local.";
        ui = new LightsUI(this);
        name = "Office Lights";
    }

    /**
     * sends a message to brighten the room
     */
    public void brighten_lights() {
        String json = new Gson().toJson(new LightsModel(LightsModel.Action.BRIGHTEN_LIGHTS));
        String a = sendMessage(json);
        LightsModel lights = new Gson().fromJson(a, LightsModel.class);
        System.out.println("Client Received " + json);

        if (lights.getAction() == LightsModel.Action.BRIGHTEN_LIGHTS) {
            isChanging = lights.getValue();
            ui.updateArea(lights.getMessage());
        }
    }

    //send a message to dim lights
    public void dim_lights() {
        String json = new Gson().toJson(new LightsModel(LightsModel.Action.DIM_LIGHTS));
        String a = sendMessage(json);
        LightsModel lights = new Gson().fromJson(a, LightsModel.class);
        System.out.println("Client Received " + json);

        if (lights.getAction() == LightsModel.Action.DIM_LIGHTS) {
            isChanging = lights.getValue();
            ui.updateArea(lights.getMessage());
        }
    }

    //turn off lights message
    public void turn_off() {
        String json = new Gson().toJson(new LightsModel(LightsModel.Action.TURN_OFF_LIGHTS));
        String a = sendMessage(json);
        LightsModel lights = new Gson().fromJson(a, LightsModel.class);
        System.out.println("Client Received " + json);

        if (lights.getAction() == LightsModel.Action.TURN_OFF_LIGHTS) {
            isChanging = lights.getValue();
            ui.updateArea(lights.getMessage());
        }
    }

    //turn on lights message
    public void turn_on() {
        String json = new Gson().toJson(new LightsModel(LightsModel.Action.TURN_ON_LIGHTS));
        String a = sendMessage(json);
        LightsModel lights = new Gson().fromJson(a, LightsModel.class);
        System.out.println("Client Received " + json);

        if (lights.getAction() == LightsModel.Action.TURN_ON_LIGHTS) {
            isChanging = lights.getValue();
            ui.updateArea(lights.getMessage());
        }
    }

    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Lights are fully bright.")) {
            isChanging = false;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new LightsUI(this);
        isChanging = false;
    }
}
