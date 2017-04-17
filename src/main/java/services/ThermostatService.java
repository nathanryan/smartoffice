/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;
/**
 *
 * @author nathan
 */
public class ThermostatService extends Service{
     private final Timer timer;
     private int maxRoomTemp;
     private int minRoomTemp;
     private int roomTemp;

    public ThermostatService(String name) {
        super(name, "_thermostat._udp.local.");
        timer = new Timer();
        maxRoomTemp = 40; //maxium temp room can go to
        minRoomTemp = 0; //lowest temp room can drop to
        roomTemp = 20; //defaault standard room temp
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        if (a.equals("get_status")) {
            sendBack(getStatus());
        } else if (a.equals("INCREASE_TEMP")) {
            //timer.schedule(new RemindTask(), 0, 2000);
            increase_temp();
            sendBack("OK");
            ui.updateArea("Warming Room");
        } else if (a.equals("DECREASE_TEMP")) {
            //timer.schedule(new RemindTask(), 0, 2000);
            decrease_temp();
            sendBack("OK");
            ui.updateArea("Cooling Room");
        } 
        else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }
    
     public void increase_temp() {
            if (roomTemp != maxRoomTemp) {
                roomTemp += 1;
            }
        }
     
     public void decrease_temp() {
            if (roomTemp != minRoomTemp) {
                roomTemp -= 1;
            }
        }

   /* class RemindTask extends TimerTask {

        @Override
        public void run() {
            if (roomTemp != maxRoomTemp) {
                roomTemp += 1;
            }
        }
    } */

    @Override
    public String getStatus() {
        return "Room is " + roomTemp + " degrees celsius";
    }

    public static void main(String[] args) {
        new ThermostatService("Thermostat");
    }
}
