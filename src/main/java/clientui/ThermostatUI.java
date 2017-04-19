package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.ThermostatClient;

/*
 * ThermostatUI.java
 *
 * @reference Dominic Carr https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 * @author Nathan Ryan, x13448212
 */

public class ThermostatUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton increase;
    private JButton decrease;
    private JButton reset;
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

        reset = new JButton("Reset");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{reset});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increase) {
            parent.increase_temp();
        } else if (e.getSource() == decrease) {
            parent.decrease_temp();
        } else if (e.getSource() == reset) {
            parent.reset_temp();
        }
    }
}
