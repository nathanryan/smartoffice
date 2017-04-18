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
public class PrinterUI extends ClientUI{
    
    private static final long serialVersionUID = -5318589393275157185L;
    private JButton check;
    private JButton print;
    private final PrinterClient parent;

    public PrinterUI(PrinterClient printerClient) {
        super(printerClient);
        parent = printerClient;
        init();
    }

      @Override
    public void init() {
        super.init();
        check = new JButton("Check Queue");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{check});
        
        check = new JButton("Start Printing Queuein");
        scroll.setBounds(5, 40, UIConstants.COMPONENTWIDTH, 300);
        add(new JButton[]{check});
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == check) {
            parent.check();
        } 
        
        else if(e.getSource() == print){
            parent.print();
        }
       
    }
}
