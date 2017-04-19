/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import models.LightsModel;
import clientui.LightsUI;

import serviceui.ServiceUI;

/**
 *
 * @author Karl
 */
public class LightsService extends Service {

    private int maxLightBright;
    private int minLightBright;
    private int roomBright;
    private static boolean isBrightening, isDimming, isLightsOff, isLightsOn;

    public LightsService(String name) {
        super(name, "_lights._udp.local.");
        maxLightBright = 100; //maximum brightness of the room
        minLightBright = 30; //lowest dimming settings
        roomBright = 0; //defaault standard room tbrightness with lights
        isBrightening = false;
        isDimming = false;
        isLightsOff = true;
        isLightsOn = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        LightsModel lights = new Gson().fromJson(a, LightsModel.class);

        if (lights.getAction() == LightsModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new LightsModel(LightsModel.Action.STATUS, msg));
            sendBack(json);
        } //BRIGHTEN ROOM
        else if (lights.getAction() == LightsModel.Action.BRIGHTEN_LIGHTS) {
            brighten_lights();
            String msg = (isBrightening) ? "The Room is brightening by 10%" : "The room cant get any brighter";
            String json = new Gson().toJson(new LightsModel(LightsModel.Action.BRIGHTEN_LIGHTS, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isBrightening) ? "The lights brightened!" : "The lights cant get any brighter..";
            ui.updateArea(serviceMessage);
        } //DIM ROOM
        else if (lights.getAction() == LightsModel.Action.DIM_LIGHTS) {
            dim_lights();
            String msg = (isDimming) ? "The Room is dimming by 10%" : "The lights cant dim any lower";
            String json = new Gson().toJson(new LightsModel(LightsModel.Action.DIM_LIGHTS, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDimming) ? "The Room is dimming!" : "Sorry the room cant dim any more..";
            ui.updateArea(serviceMessage);
        } //TURN OFF LIGHTS
        else if (lights.getAction() == LightsModel.Action.TURN_OFF_LIGHTS) {
            turn_off_lights();
            String msg = (isLightsOff) ? "The Lights have been turned off" : "Lights are already off";
            String json = new Gson().toJson(new LightsModel(LightsModel.Action.TURN_OFF_LIGHTS, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isLightsOff) ? "Lights turned off" : "Lights are off";
            ui.updateArea(serviceMessage);
        } //TURN ON LIGHTS
        else if (lights.getAction() == LightsModel.Action.TURN_ON_LIGHTS) {
            turn_on_lights();
            String msg = (isLightsOn) ? "The Lights have been turned on" : "Lights have been turned on";
            String json = new Gson().toJson(new LightsModel(LightsModel.Action.TURN_ON_LIGHTS, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isLightsOn) ? "Lights turned on" : "Lights are on";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    public void turn_off_lights() {
        if (roomBright >= 0) {
            roomBright = 0;
            System.out.println("Lights Turned off");
        }
    }

    public void turn_on_lights() {
        if (roomBright <= 0) {
            roomBright += 100;
            System.out.println("Room is" + roomBright + "% bright");
        }
    }

    public void brighten_lights() {
        if (roomBright != maxLightBright) {
            isBrightening = true;
            roomBright += 10;
        } else {
            isBrightening = false;
        }
    }

    public void dim_lights() {
        if (roomBright != minLightBright) {
            isDimming = true;
            roomBright -= 10;
        } else {
            isDimming = false;
        }
    }

    @Override
    public String getStatus() {
        return "Room is " + roomBright + "% bright";
    }

    public static void main(String[] args) {
        new LightsService("Light");
    }
}
