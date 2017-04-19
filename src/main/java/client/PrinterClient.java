/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.PrinterUI;
import com.google.gson.Gson;
import models.PrinterModel;
/**
 *
 * @author nathan
 */
public class PrinterClient extends Client {   
    
    public PrinterClient() {
        super();
        serviceType = "_printer._udp.local.";
        ui = new PrinterUI(this);
        name = "Office Printer";
    }

    public void checkQueue() {
        String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.CHECK_QUEUE));
        String a = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(a, PrinterModel.class);
        System.out.println("Client Received "+json);
        if (printer.getAction() == PrinterModel.Action.CHECK_QUEUE) {
           ui.updateArea(printer.getMessage());
        }
    }
    
        public void addQueue() {
        String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.ADD_QUEUE));
        String a = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(a, PrinterModel.class);
        System.out.println("Client Received "+json);
        if (printer.getAction() == PrinterModel.Action.ADD_QUEUE) {
           ui.updateArea(printer.getMessage());
        }
    }
        
    public void removeQueue() {
        String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.REMOVE_QUEUE));
        String a = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(a, PrinterModel.class);
        System.out.println("Client Received "+json);
        if (printer.getAction() == PrinterModel.Action.REMOVE_QUEUE) {
           ui.updateArea(printer.getMessage());
        }
    }
    
    public void Print() {
        String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.PRINT_QUEUE));
        String a = sendMessage(json);
        PrinterModel printer = new Gson().fromJson(a, PrinterModel.class);
        System.out.println("Client Received "+json);
        if (printer.getAction() == PrinterModel.Action.PRINT_QUEUE) {
           ui.updateArea(printer.getMessage());
        }
    }

    @Override
    public void updatePoll(String msg) {
        if (msg.equals("Checking Print Queue...")) {
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new PrinterUI(this);
    }
}
