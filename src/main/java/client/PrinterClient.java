/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientui.PrinterUI;

/**
 *
 * @author nathan
 */
public class PrinterClient extends Client {
    private final String CHECK = "CHECK";
    private final String PRINT = "PRINT";
    private boolean isOn = false;
    private boolean printing = false;
    
      public PrinterClient() {
        super();
        serviceType = "_printer._udp.local.";
        ui = new PrinterUI(this);
        name = "Office Printer";
    }
      
    public void check(){
        if(!isOn){
            String a = sendMessage(CHECK);
            if(a.equals(OK)){
                isOn = true;
                ui.updateArea("Printer is on!");
            }
        }
        else{
            ui.updateArea("Printer is already on!");
        }
    }
    
    public void print(){
        if(!printing){
            String a = sendMessage(PRINT);
            if(a.equals(OK)){
                printing = true;
                ui.updateArea("Printing documents");
            }
        }
        else
        {
            ui.updateArea("Not Printing");
        }
        
    }
    
    @Override
    public void updatePoll(String msg){
        if(msg.equals("Printer is on!")){
            isOn = true;
        }
        else if(msg.equals("Printing documents")){
            printing = true;
        }
    }
    
    @Override
    public void disable(){
        super.disable();
        ui = new PrinterUI(this);
        isOn = false;
    }
}
