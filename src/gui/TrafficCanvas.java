
/*
Oluwaseyi Ariyo
*/
package src.gui;

import src.trafficControl.*;
import src.timer.*;

import javax.swing.*;
import java.awt.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.Random;

public class TrafficCanvas extends JPanel{
    ExecutorService service;
    private final int rows, cols, height, width;
    private int numCar;
    Time timer;
    ArrayList<Light> lights = new ArrayList<>();
    ArrayList<VerticalStreet> yStreets = new ArrayList<>();
    ArrayList<HorizontalStreet> xStreets = new ArrayList<>();
    ArrayList<Street> streets = new ArrayList<>();
    protected ArrayList<Car> cars = new ArrayList<>();

    TrafficCanvas(int rows, int cols, int numCar) {
        this.cols = cols;
        this.rows = rows;
        this.numCar = numCar;
        int extra = 10;
        this.width = 1000;
        this.height = 1000;
        this.service = Executors.newFixedThreadPool((rows * cols) + numCar + extra + 1);

        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setBackground(Color.WHITE);
        start();
    }

    public int getRows() {
        return this.rows;
    }

    public int getNumCar() {
        return numCar;
    }

    public int getCols() {
        return cols;
    }

    public Car addCar() {
        Street s = streets.get((new Random()).nextInt(streets.size()));
        Car c = new Car(s);
        s.addCar(c);
        cars.add(c);
        this.revalidate();

        return c;
    }
    
    public Car addCar(int x) {
        Street s = streets.get((new Random()).nextInt(streets.size()));
        Car c = new Car(s);
        s.addCar(c);
        cars.add(c);
        numCar += 1;
        this.revalidate();

        return c;
    }

    public void makeCars() {
        for (int i = 0; i < numCar; i++) {
            addCar();
        }
    }


    public void start() {
        System.out.println("Starting Canvas");

        int x = width / (cols + 1);
        int y = height / (rows + 1);

        for (int i = 1; i <= this.cols; i++) {
            int currX = x * i;
            VerticalStreet street = new VerticalStreet(currX, height);

            streets.add(street);
            yStreets.add(street);
        }

        for (int i = 1; i <= this.rows; i++) {
            int currY = y * i;
            HorizontalStreet street = new HorizontalStreet(currY, width);

            streets.add(street);
            xStreets.add(street);
        }

        for (VerticalStreet yStreet : yStreets) {
            for (HorizontalStreet xStreet : xStreets) {
                Light light = new Light(yStreet, xStreet);
                
                yStreet.addLight(light);
                xStreet.addLight(light);
                lights.add(light);

            }
        }
        makeCars();
    }

    public void passTime(Time timer) {
        this.timer = timer;

        for (Light light : lights) {
            light.passTime(timer);
        }

        for (Street street : streets) {
            ArrayList<Car> cars = street.getCars();

            for (Car car : cars) {
                car.passTime(timer);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Street s : streets) {
            s.draw(g);
        }

        for (Light l : lights) {
            l.draw(g);
        }
    }

    public void startService() {
        for (Street s : streets) {
            ArrayList<Car> cars = s.getCars();
            
            for (Car c : cars) {
                service.submit(c);
            }
        }

        for (Light l : lights) {
            service.submit(l);
        }
    }
}
