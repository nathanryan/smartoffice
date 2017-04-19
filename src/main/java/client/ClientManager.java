/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.IOException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

import clientui.ClientManagerUI;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author nathan and karl
 */
public class ClientManager implements ServiceListener {
    
    private final ClientManagerUI ui;
    private JmDNS jmdns;
    private final ThermostatClient client = new ThermostatClient();
    private final KettleClient kclient = new KettleClient();
    private final PrinterClient pclient = new PrinterClient();

    public ClientManager() {
        try {
            jmdns = JmDNS.create(InetAddress.getLocalHost());
            jmdns.addServiceListener(client.getServiceType(), this);
            jmdns.addServiceListener(kclient.getServiceType(), this);
            jmdns.addServiceListener(pclient.getServiceType(), this);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ui = new ClientManagerUI(this);
    }

    public void end() {
        try {
            jmdns.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void serviceAdded(ServiceEvent arg0) {
        System.out.println(arg0);
        arg0.getDNS().requestServiceInfo(arg0.getType(), arg0.getName(), 0);
    }

    public void serviceRemoved(ServiceEvent arg0) {
        System.out.println(arg0);
        String type = arg0.getType();
        String name = arg0.getName();
        ServiceInfo newService = null;
        //TRY CHANGE TO SWITCH 
        if (client.getServiceType().equals(type) && client.hasMultiple()) {
            if (client.isCurrent(name)) {
                ServiceInfo[] a = jmdns.list(type);
                for (ServiceInfo in : a) {
                    if (!in.getName().equals(name)) {
                        newService = in;
                    }
                }
                client.switchService(newService);
            }
            client.remove(name);
        }
        
        //Run Thermostat Client
        else if (client.getServiceType().equals(type)) {
            ui.removePanel(client.returnUI());
            client.disable();
            client.initialized = false;
        }
        
        //Run Kettle Client
        else if (kclient.getServiceType().equals(type) && kclient.hasMultiple()) {
            if (kclient.isCurrent(name)) {
                ServiceInfo[] a = jmdns.list(type);
                for (ServiceInfo in : a) {
                    if (!in.getName().equals(name)) {
                        newService = in;
                    }
                }
                kclient.switchService(newService);
            }
            kclient.remove(name);
        } 
        
        //Run Printer Client
        else if (pclient.getServiceType().equals(type) && pclient.hasMultiple()) {
            if (pclient.isCurrent(name)) {
                ServiceInfo[] a = jmdns.list(type);
                for (ServiceInfo in : a) {
                    if (!in.getName().equals(name)) {
                        newService = in;
                    }
                }
                pclient.switchService(newService);
            }
            pclient.remove(name);
        } 
        
        else if (kclient.getServiceType().equals(type)) {
            ui.removePanel(kclient.returnUI());
            kclient.disable();
            kclient.initialized = false;
        }
        
    }

    public void serviceResolved(ServiceEvent arg0) {
        System.out.println(arg0);
        String address = arg0.getInfo().getHostAddress();
        int port = arg0.getInfo().getPort();
        String type = arg0.getInfo().getType();

        //Thermostat Client
        if (client.getServiceType().equals(type) && !client.isInitialized()) {
            client.setUp(address, port);
            ui.addPanel(client.returnUI(), client.getName());
            client.setCurrent(arg0.getInfo());
            client.addChoice(arg0.getInfo());
        } else if (client.getServiceType().equals(type)
                && client.isInitialized()) {
            client.addChoice(arg0.getInfo());
        }
        
        //Kettle Client
        else if (kclient.getServiceType().equals(type) && !kclient.isInitialized()) {
            kclient.setUp(address, port);
            ui.addPanel(kclient.returnUI(), kclient.getName());
            kclient.setCurrent(arg0.getInfo());
            kclient.addChoice(arg0.getInfo());
        } else if (kclient.getServiceType().equals(type)
                && kclient.isInitialized()) {
            kclient.addChoice(arg0.getInfo());
        }
        
        //Printer Client
        else if (pclient.getServiceType().equals(type) && !pclient.isInitialized()) {
            pclient.setUp(address, port);
            ui.addPanel(pclient.returnUI(), pclient.getName());
            pclient.setCurrent(arg0.getInfo());
            pclient.addChoice(arg0.getInfo());
        } else if (pclient.getServiceType().equals(type)
                && pclient.isInitialized()) {
            pclient.addChoice(arg0.getInfo());
        }
    }

    public static void main(String[] args) {
        new ClientManager();

    }
}
