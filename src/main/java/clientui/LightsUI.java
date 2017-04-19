/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.LightsClient;

/**
 *
 * @author Karl
 */
public class LightsUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton brighten;
    private JButton dim;
    private JButton turnOff;
    private JButton turnOn;
    private final LightsClient parent;

    public LightsUI(LightsClient lightsClient) {
        super(lightsClient);
        parent = lightsClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        brighten = new JButton("Brighten");
        brighten.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{brighten});

        dim = new JButton("Dim");
        dim.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{dim});

        turnOff = new JButton("Turn off Lights");
        turnOff.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnOff});

        turnOn = new JButton("Turn on Lights");
        turnOn.setEnabled(true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnOn});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == brighten) {
            parent.brighten_lights();
        } else if (e.getSource() == dim) {
            parent.dim_lights();
        } else if (e.getSource() == turnOff) {
            parent.turn_off();
            brighten.setEnabled(false);
            dim.setEnabled(false);
            turnOff.setEnabled(false);
            turnOn.setEnabled(true);
        } else if (e.getSource() == turnOn) {
            parent.turn_on();
            brighten.setEnabled(true);
            dim.setEnabled(true);
            turnOff.setEnabled(true);
            turnOn.setEnabled(false);
        }
    }
}
