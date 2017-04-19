package clientui;

import java.awt.event.ActionEvent;
import javax.swing.JButton;
import client.KettleClient;

/*
 * KettleUI.java
 *
 * @reference Dominic Carr https://moodle.ncirl.ie/mod/resource/view.php?id=54977													/example.java
 *
 * @author Karl King, x13565467
 */

public class KettleUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton boil;
    private JButton empty;
    private JButton fill;
    private JButton turnOffKettle;
    private JButton turnOnKettle;
    private final KettleClient parent;

    public KettleUI(KettleClient kettleClient) {
        super(kettleClient);
        parent = kettleClient;
        init();
    }
    
    //buttons for kettle
    @Override
    public void init() {
        super.init();
        boil = new JButton("Boil Water");
        boil.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{boil});

        empty = new JButton("Empty Kettle");
        empty.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{empty});

        fill = new JButton("Fill Kettle");
        fill.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{fill});

        turnOffKettle = new JButton("Turn off Power");
        turnOffKettle.setEnabled(false);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnOffKettle});

        turnOnKettle = new JButton("Turn on Power");
        turnOnKettle.setEnabled(true);
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{turnOnKettle});
    }
    
    //setting button views
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == turnOnKettle) {
            parent.power_on();
            boil.setEnabled(false);
            empty.setEnabled(false);
            fill.setEnabled(true);
            turnOffKettle.setEnabled(true);
            turnOnKettle.setEnabled(false);
        } else if (e.getSource() == turnOffKettle) {
            parent.power_off();
            boil.setEnabled(false);
            empty.setEnabled(false);
            fill.setEnabled(false);
            turnOnKettle.setEnabled(true);
            turnOffKettle.setEnabled(false);

        } else if (e.getSource() == boil) {
            parent.boilWater();
            boil.setEnabled(true);
            fill.setEnabled(false);
            empty.setEnabled(true);
        } else if (e.getSource() == empty) {
            parent.empty_kettle();
            boil.setEnabled(false);
            fill.setEnabled(true);
            empty.setEnabled(false);
        } else if (e.getSource() == fill) {
            parent.fill_kettle();
            boil.setEnabled(true);
            empty.setEnabled(true);
            fill.setEnabled(false);
        }
    }
}
