
/*
Oluwaseyi Ariyo

*/
package src.gui;

import javax.swing.*;
import java.awt.*;

public class Landing extends JPanel {
        JTextField rows = new JTextField("3", 2);
        JTextField cols = new JTextField("3", 2);
        JTextField cars = new JTextField("3", 2);
        JButton button = new JButton("Start");


    public Landing(GUI gui) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel select = new JLabel("Please select options for the traffic simulation");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 0, 10);
        this.add(select, gbc);

        JLabel label1 = new JLabel("Number of Rows:");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(label1, gbc);

        rows.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        this.add(rows, gbc);

        JLabel label2 = new JLabel("Number of Columns:");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(label2, gbc);

        cols.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 10);
        this.add(cols, gbc);

        JLabel label3 = new JLabel("Initial Car Count:");
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 10, 0, 0);
        this.add(label3, gbc);

        cars.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 10, 5, 10);
        this.add(cars, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 10, 10, 5);
        this.add(button, gbc);
        button.addActionListener(e -> {
            int rows = Integer.valueOf(this.rows.getText());
            int cols = Integer.valueOf(this.cols.getText());
            int cars = Integer.valueOf(this.cars.getText());

            TrafficCanvas canvas = new TrafficCanvas(rows, cols, cars);
            ControlCentre control = new ControlCentre(canvas);

            gui.remove(this);
            gui.setLayout(new BorderLayout());
            gui.add(control, BorderLayout.NORTH);
            gui.add(canvas, BorderLayout.CENTER);
            gui.revalidate();
            gui.pack();
        });
    }
    
   
}
