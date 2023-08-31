
/*
Oluwaseyi Ariyo

*/
package src.gui;
import javax.swing.*;

public class GUI extends JFrame{
    public GUI() {

        super("Traffic Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setResizable(false);
        
        Landing landing = new Landing(this);
        add(landing);
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new GUI();
    }
}
