/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.ThermostatClient;

/**
 *
 * @author nathan
 */
public class ThermostatUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton increase;
    private JButton decrease;
    private final ThermostatClient parent;

    public ThermostatUI(ThermostatClient thermostatClient) {
        super(thermostatClient);
        parent = thermostatClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        increase = new JButton("Increase");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{increase});

        decrease = new JButton("Decrease");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{decrease});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increase) {
            parent.increase_temp();
        } else if (e.getSource() == decrease) {
            parent.decrease_temp();
        }
    }
}
