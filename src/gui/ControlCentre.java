/*
Oluwaseyi Ariyo

*/

package src.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import src.trafficControl.*;
import src.timer.Time;
import java.util.StringTokenizer;

public class ControlCentre extends JPanel implements ActionListener {
    private JTextField currTime;
    private final int rows, cols, cars;
    private TrafficCanvas canvas;
    private Time timer;
    JButton start;
    JButton stop;
    JButton pause;
    JButton addCar;
    
    GridBagConstraints gbc = new GridBagConstraints();

    public ControlCentre(TrafficCanvas c) {
        this.canvas = c;
        this.cols = c.getCols();
        this.rows = c.getRows();
        this.cars = c.getNumCar();
        start = new JButton("Start");
        stop = new JButton("Stop");
        pause = new JButton("Pause");
        addCar = new JButton("Add car");

        this.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(start, gbc);
        start.addActionListener(this);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(stop, gbc);
        stop.addActionListener(this);

        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(pause, gbc);
        pause.addActionListener(this);

        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(addCar, gbc);
        addCar.addActionListener(this);

        currTime = new JTextField(4);
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.add(currTime, gbc);

        showSpeed();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            if (timer == null || timer.stopped()) {
                timer = new Time(this.currTime, this.canvas);
                this.canvas.service.submit(timer);
                this.canvas.passTime(timer);
                this.canvas.startService();
            } else {
                timer.play();
            }
        } else if (e.getSource() == stop) {
            currTime.setText("");
            timer.stop();
            this.getParent().remove(canvas);

            clearSpeed();
            canvas.cars.clear();
            
            this.canvas = new TrafficCanvas(rows, cols, cars);
            showSpeed();

            this.getParent().add(this.canvas, BorderLayout.CENTER);
            revalidate();
            repaint();

        } else if (e.getSource() == pause) {
            timer.pause();
            
        } else if ((e.getSource() == addCar) && ( this.canvas.getNumCar() <= 11) && !(currTime.getText().isEmpty())) {
           System.out.println(currTime.getText());
            Car c = this.canvas.addCar(3);
            c.passTime(timer);
            this.canvas.service.submit(c);
            showSpeed();
            this.canvas.repaint();
        }
    }

    private void showSpeed() {
        int track = 0;
        for (Car c : canvas.cars) {
            
            gbc = new GridBagConstraints();
            gbc.gridx = track;
            gbc.gridy = 1;
            gbc.insets = new Insets(10, 10, 10, 10);
            this.add(c.showField(), gbc);
            track += 1;
        }
    }

    private void clearSpeed() {
        for (Car c : canvas.cars) {
            this.remove(c.showField());
        }
    }
}
