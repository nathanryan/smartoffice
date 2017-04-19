package services;

import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;
import models.KettleModel;
import models.LightsModel;

import serviceui.ServiceUI;

/*
 * KettleService.java
 *
 * @reference Dominic Carr https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 * @author Karl King, x13565467
 */

public class KettleService extends Service {

    private Timer timer;
    private int boilPercent;
    private int waterLevel;
    private static boolean isBoiling, isRunning, isFilling, isEmpty, isOn, isOff;

    public KettleService(String name) {
        super(name, "_kettle._udp.local.");
        timer = new Timer();
        boilPercent = -1; // the initial boil percent
        waterLevel = 0;
        isBoiling = false;
        isFilling = false;
        isOn = false;
        isOff = true;
        isEmpty = true;
        isRunning = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        if (kettle.getAction() == KettleModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.STATUS, msg));
            sendBack(json);
            
            //BOIL KETTLE
        } else if (kettle.getAction() == KettleModel.Action.BOIL) {
            timer = new Timer();
            timer.schedule(new RemindTask(), 100, 1000);
            String msg = (isBoiling) ? "Kettle is already boiling.." : "The Kettle is Boiling";
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.BOIL, msg, isBoiling));
            System.out.println(json);
            sendBack(json);
            String serviceMessage = (isBoiling) ? "Kettle is already boiling.." : "Water boiling..";
            ui.updateArea(serviceMessage);
            
        } //FILLING KETTLE
        else if (kettle.getAction() == KettleModel.Action.FILL_KETTLE) {
            fill_kettle();
            String msg = (isFilling) ? "The kettle is being filled" : "The kettle is full";
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.FILL_KETTLE, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isFilling) ? "The kettle is being filled" : "The kettle is already full";
            ui.updateArea(serviceMessage);

        } //EMPTY KETTLE
        else if (kettle.getAction() == KettleModel.Action.EMPTY_KETTLE) {
            empty_kettle();
            String msg = (isEmpty) ? "The kettle is being emptyed" : "The kettle is empty";
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.EMPTY_KETTLE, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isEmpty) ? "The kettle is being emptyed" : "The kettle is empty";
            ui.updateArea(serviceMessage);
            
            //TURN ON KETTLE
        } else if (kettle.getAction() == KettleModel.Action.TURN_ON) {
            power_on();
            String msg = (isOn) ? "The Kettle is turned on" : "The Kettle is on";
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.TURN_ON, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOn) ? "The kettle is turned on" : "The kettle is on";
            ui.updateArea(serviceMessage);
            
            //TURN OFF KETTLE
        } else if (kettle.getAction() == KettleModel.Action.TURN_OFF) {
            power_off();
            String msg = (isOff) ? "The Kettle is turned off" : "The Kettle is off";
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.TURN_OFF, msg));
            System.out.println(json);
            sendBack(json);

            String serviceMessage = (isOff) ? "The kettle is turned off" : "The kettle is off";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }
    
    //setting conditions
    public void empty_kettle() {
        if (waterLevel >= 1) {
            isEmpty = true;
            waterLevel = 0;
        } else {
            isEmpty = false;
        }
    }
    
    public void fill_kettle() {
        if (waterLevel <= 0) {
            isFilling = true;
            waterLevel = 10;
        } else {
            isFilling = false;
        }

    }

    public void power_off() {
        System.out.println("Kettle is Off");
    }

    public void power_on() {
        System.out.println("Kettle is on");
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            isRunning = true;
            if (boilPercent < 100 && waterLevel > 1) {
                isBoiling = true;
                boilPercent += 10;
            } else {
                isBoiling = false;
            }
        }
    }

    @Override
    public String getStatus() {
        String msg = "";
//        return "Water is " + boilPercent + " degrees celsius";
        if (boilPercent == -1 && waterLevel > 1) {
            msg = "Kettle is ready to Boil!";
        } else if (boilPercent > -1 && boilPercent < 100) {
            msg = "Water is " + boilPercent + " degrees celsius";
        } else if (boilPercent >= 100) {
            msg = "Boiling is complete";
            boilPercent = -1;
            if (isRunning) {
                timer.cancel();
                isRunning = false;
            }
        }
        return msg;
    }

    public static void main(String[] args) {
        new KettleService("Kettle");
    }
}
