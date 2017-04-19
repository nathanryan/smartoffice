/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import models.ThermostatModel;

import serviceui.ServiceUI;

/*
 * ThermostatService.java
 *
 * @reference Dominic Carr https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 * @author Nathan Ryan, x13448212
 */

public class ThermostatService extends Service {

    private int maxRoomTemp;
    private int minRoomTemp;
    private int resetRoomTemp;
    private int roomTemp;
    private static boolean isIncreasing, isDecreasing, isResetting;

    public ThermostatService(String name) {
        super(name, "_thermostat._udp.local.");
        maxRoomTemp = 40; //maximum temp room can go to
        minRoomTemp = 0; //lowest temp room can drop to
        roomTemp = 20; //defaault standard room temp
        isIncreasing = false;
        isDecreasing = false;
        isResetting = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        ThermostatModel thermostat = new Gson().fromJson(a, ThermostatModel.class);

        if (thermostat.getAction() == ThermostatModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.STATUS, msg));
            sendBack(json);
        } //INCREASE ROOM TEMP
        else if (thermostat.getAction() == ThermostatModel.Action.INCREASE_TEMP) {
            increase_temp();
            String msg = (isIncreasing) ? "The Room is Warming Up by 5c!" : "Sorry you cannot increase the Temp, Room is at Max Temp";
            String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.INCREASE_TEMP, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isIncreasing) ? "The Room is Warming Up by 5c!" : "Sorry you cannot increase the Temp, Room is at Max Temp";
            ui.updateArea(serviceMessage);
        } //DECREASE ROOM TEMP
        else if (thermostat.getAction() == ThermostatModel.Action.DECREASE_TEMP) {
            decrease_temp();
            String msg = (isDecreasing) ? "The Room is Cooling Down by 5c!" : "Sorry you cannot decrease the Temp, Room is a Min Temp";
            String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.DECREASE_TEMP, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isDecreasing) ? "The Room is Cooling Down by 5c!" : "Sorry you cannot decrease the Temp, Room is a Min Temp";
            ui.updateArea(serviceMessage);
        } //RESET ROOM TEMP
        else if (thermostat.getAction() == ThermostatModel.Action.RESET) {
            reset_temp();
            String msg = (isResetting) ? "The Room temp has been reset" : "Sorry Room already reset";
            String json = new Gson().toJson(new ThermostatModel(ThermostatModel.Action.RESET, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isResetting) ? "The Room temp has been reset" : "Sorry you cannot reset the room again";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    public void increase_temp() {
        if (roomTemp != maxRoomTemp) {
            isIncreasing = true;
            roomTemp += 5;
        } else {
            isIncreasing = false;
        }
    }

    public void decrease_temp() {
        if (roomTemp != minRoomTemp) {
            isDecreasing = true;
            roomTemp -= 5;
        } else {
            isDecreasing = false;
        }
    }

    public void reset_temp() {
        if (roomTemp != 20) {
            isResetting = true;
            roomTemp = 20;
        } else {
            isResetting = false;
        }
    }

    @Override
    public String getStatus() {
        return "Room is " + roomTemp + " degrees celsius";
    }

    public static void main(String[] args) {
        new ThermostatService("Thermostat");
    }
}
