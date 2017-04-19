/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientui;

import client.PrinterClient;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

/**
 *
 * @author nathan
 */
public class PrinterUI extends ClientUI {

    private static final long serialVersionUID = -5318589393275157185L;
    private JButton check_queue;
    private JButton add_queue;
    private JButton remove_queue;
    private JButton print_queue;

    private final PrinterClient parent;

    public PrinterUI(PrinterClient kettleClient) {
        super(kettleClient);
        parent = kettleClient;
        init();
    }

    @Override
    public void init() {
        super.init();
        check_queue = new JButton("Check Queue");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{check_queue});

        print_queue = new JButton("Print");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{print_queue});

        add_queue = new JButton("Add Document to Queue");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{add_queue});

        remove_queue = new JButton("Remove Document from Queue");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{remove_queue});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == check_queue) {
            parent.checkQueue();
        }

        if (e.getSource() == add_queue) {
            parent.addQueue();
        }

        if (e.getSource() == remove_queue) {
            parent.removeQueue();
        }

        if (e.getSource() == print_queue) {
            parent.Print();
        }
    }
}
