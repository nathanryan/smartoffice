/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Timer;
import java.util.TimerTask;
import serviceui.ServiceUI;
import models.PrinterModel;
import com.google.gson.Gson;

/**
 *
 * @author nathan
 */
public class PrinterService extends Service {

    private Timer timer;
    private int queueLength;
    private static boolean docsInQueue, isAdding, isRemoving, isPrinting, isRunning;

    public PrinterService(String name) {
        super(name, "_printer._udp.local.");
        timer = new Timer();
        queueLength = 0; // the initial queue length
        docsInQueue = false;
        isAdding = false;
        isRemoving = false;
        isPrinting = false;
        isRunning = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        System.out.println("recieved: " + a);
        PrinterModel kettle = new Gson().fromJson(a, PrinterModel.class);
        if (kettle.getAction() == PrinterModel.Action.STATUS) {
            String msg = getStatus();
            String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.STATUS, msg));
            sendBack(json);
        } //CHECK QUEUE
        else if (kettle.getAction() == PrinterModel.Action.CHECK_QUEUE) {
            check_queue();
            String msg = "There are " + queueLength + " documents queued for printing";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.CHECK_QUEUE, msg, docsInQueue));
            System.out.println(json);
            sendBack(json);
            String serviceMessage = "There are " + queueLength + " documents queued for printing";
            ui.updateArea(serviceMessage);
        } //ADD DOC TO QUEUE
        else if (kettle.getAction() == PrinterModel.Action.ADD_QUEUE) {
            add_queue();
            String msg = (isAdding) ? "Added one new document to the Print Queue" : "Cannot add document to Queue, please print documents or remove one from Queue";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.ADD_QUEUE, msg, isAdding));
            System.out.println(json);
            sendBack(json);
            String serviceMessage = (isAdding) ? "Added one new document to the Print Queue" : "Cannot add document to Queue, please print documents or remove one from Queue";
            ui.updateArea(serviceMessage);
        } //REMOVE DOC FROM QUEUE
        else if (kettle.getAction() == PrinterModel.Action.REMOVE_QUEUE) {
            remove_queue();
            String msg = (isRemoving) ? "Removing one document from the Print Queue" : "There are no documents in Queue to remove";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.REMOVE_QUEUE, msg, isAdding));
            System.out.println(json);
            sendBack(json);
            String serviceMessage = (isRemoving) ? "Removing one document from the Print Queue" : "There are no documents in Queue to remove";
            ui.updateArea(serviceMessage);
        } //PRINT QUEUE
        else if (kettle.getAction() == PrinterModel.Action.PRINT_QUEUE) {
            timer = new Timer();
            timer.schedule(new RemindTask(), 100, 1000);
            String msg = (isPrinting) ? "There are no documents in Queue to Print" : "Printing";
            String json = new Gson().toJson(new PrinterModel(PrinterModel.Action.PRINT_QUEUE, msg, isPrinting));
            System.out.println(json);
            sendBack(json);
            String serviceMessage = (isPrinting) ? "There are no documents in Queue to Print" : "Printing";
            ui.updateArea(serviceMessage);
        } else {
            sendBack(BAD_COMMAND + " - " + a);
        }
    }

    public void check_queue() {
        if (queueLength < 1) {
            docsInQueue = false;
        } else if (queueLength > 1) {
            docsInQueue = true;
        }
    }

    public void add_queue() {
        if (queueLength <= 10) {
            isAdding = true;
            queueLength += 1;
        } else if (queueLength >= 10) {
            isAdding = false;
            ui.updateArea("Please remove documents or print documents to add to Queue");
        }
    }

    public void remove_queue() {
        if (queueLength > 0) {
            isRemoving = true;
            queueLength -= 1;
        } else if (queueLength == 0) {
            isRemoving = false;
        }
    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            isRunning = true;
            if (queueLength > 0) {
                isPrinting = true;
                queueLength -= 1;
            } else {
                isPrinting = false;
            }
        }
    }

    @Override
    public String getStatus() {
        if (queueLength == 0) {
            if (isRunning) {
                timer.cancel();
                isRunning = false;
            }
        }

        return "There are " + queueLength + " documents in the queue";

    }

    public static void main(String[] args) {
        new PrinterService("Printer");
    }

}
