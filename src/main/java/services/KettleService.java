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
 * @author karl
 */
public class KettleService extends Service{
     private final Timer timer;
     private int boilPercent;

    public KettleService(String name) {
        super(name, "_kettle._udp.local.");
        timer = new Timer();
        boilPercent = 0; // the initial boil percent
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        if (a.equals("get_status")) {
            sendBack(getStatus());
        } else if (a.equals("Boiled")) {
            timer.schedule(new RemindTask(), 0, 3000);
            sendBack("OK");
            ui.updateArea("Water boiling..");
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            if (boilPercent < 100) {
                boilPercent += 10;
            }
        }
    }

    @Override
    public String getStatus() {
        return "Water is " + boilPercent + " degrees celsius";
    }

    public static void main(String[] args) {
        new KettleService("Kettle");
    }
}
