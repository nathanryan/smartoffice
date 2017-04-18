/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import java.util.Timer;
import java.util.TimerTask;
import models.KettleModel;

import serviceui.ServiceUI;
/**
 *
 * @author karl
 */
public class KettleService extends Service{
     private Timer timer;
     private int boilPercent;
     private static boolean isBoiling, isRunning;

    public KettleService(String name) {
        super(name, "_kettle._udp.local.");
        timer = new Timer();
        boilPercent = -1; // the initial boil percent
        isBoiling = false;
        isRunning = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " +a);
        KettleModel kettle = new Gson().fromJson(a, KettleModel.class);
        if (kettle.getAction() == KettleModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.STATUS, msg));
            sendBack(json);
        } else if (kettle.getAction() == KettleModel.Action.BOIL) {
            timer = new Timer();
            timer.schedule(new RemindTask(), 100, 1000);
            String msg = (isBoiling)? "Kettle is already boiling.." : "The Kettle is Boiling";
            String json = new Gson().toJson(new KettleModel(KettleModel.Action.BOIL,msg , isBoiling));
            System.out.println(json);
            sendBack(json);
            String serviceMessage = (isBoiling)?  "Kettle is already boiling.." : "Water boiling..";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    class RemindTask extends TimerTask {
       
        @Override
        public void run() {
            isRunning = true;
            if (boilPercent < 100) {
                isBoiling = true;
                boilPercent += 10;
            }
            else{
                isBoiling = false;
            }
        }

    }

    @Override
    public String getStatus() {
        String msg = "";
//        return "Water is " + boilPercent + " degrees celsius";
        if(boilPercent == -1){
            msg = "Kettle is ready to Boil!";
        }else if (boilPercent > -1 && boilPercent < 100){
            msg = "Water is " + boilPercent + " degrees celsius";
        }else if(boilPercent >= 100){
            msg = "Boiling is complete";
            boilPercent = -1;
            if (isRunning){
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
