/*
Oluwaseyi Ariyo

*/
package src.trafficControl;

import javax.swing.*;

import src.timer.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class Car extends SwingWorker<Void, Integer>{
    private final int id;
    private int xPos;
    private int yPos;
    private int speed = 0;
    private int currSpeed = 0;
    private boolean still = false;
    private Street street;
    private Time time;
    private Random random;
    private final Color colour = new Color((int )(Math.random() * 0x100000));
    private static int count = 0;
    private final JTextField text = new JTextField(5);

    
    public Car(Street x) {
        this.id = count += 1;
        this.random = new Random();
        this.street = x;
        this.speed = random.nextInt(60) + 20;
        System.out.println("Creating car #" + this.id);

        if (street instanceof VerticalStreet) {
            this.xPos = ((VerticalStreet)street).left;
            this.yPos = street.length - 10;
        }

        if (street instanceof HorizontalStreet) {
            this.yPos = ((HorizontalStreet)street).left;
            this.xPos = 0;
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!time.stopped()) {

            if (!time.paused()) {

                publish(getSpeed());

                if (this.street instanceof VerticalStreet) {

                    verticalMove();

                } else {

                    horizontalMove();
                }
            }
            Thread.sleep(1000);
        }
        return null;
    }

    @Override
    protected void process(List<Integer> pieces) {
        Integer s = pieces.get(pieces.size() - 1);
        String speed = String.valueOf(s).concat(" mph");

        text.setText(speed);
    }

    //If the car can make a turn and the light is green, chooses to make a turn or continue at random. Stops if red.
    public void verticalMove() {
        ArrayList<Light> lights = street.getLights();
        ArrayList<Light> lightsAhead = new ArrayList<>();
        ArrayList<Car> cars = street.getCars();
        ArrayList<Car> carsAhead = new ArrayList<>();
        int dest = this.yPos - speed;
        int stop;

        for(Car car : cars) {
            if ((car.yPos >= dest) && (car.yPos < this.yPos)) {
                carsAhead.add(car);
            }
        }

        if (carsAhead.size() > 0) {
            slowDown(carsAhead);
        }

        //look for possible turn
        for (Light light : lights) {
            if ((light.vLightY >= dest) && (light.vLightY < this.yPos)) {
                lightsAhead.add(light);
            }
        }

        //go on green, stop on red
        if(lightsAhead.size() > 0) {
            Light light = lightsAhead.get(0);
            
            if (light.signalY == Signal.STOP) {
                if (carsAhead.size() > 0) {
                    stop = carsAhead.size();
                } else {
                    stop = 1;
                }

                verticalMove(this.yPos - light.vLightY - (10 * stop));
                this.still = true;
                System.out.println(currSpeed);

            } else if (light.signalY == Signal.GO) {
                System.out.println(this.speed);
                this.still = false;

                if(random.nextBoolean()) {
                    verticalMove(this.speed);
                } else {
                    horizontalTurn(light);
                }

            }
        } else {
            verticalMove(this.speed);
        }
    }

    //Adjusts car speed depending on speed of car in front of it
    private void slowDown(ArrayList<Car> carsAhead) {
        final int[] low = {this.speed};
        for (Car car : carsAhead) {
            if(car.speed < low[0]) {
                low[0] = car.speed;
            }
        }
        this.speed = low[0];
    }

    public void verticalMove(int length) {
        this.yPos -= length;

        if (this.yPos <= 0) {
            this.yPos = this.street.length - 10;
        }
    }

    public void verticalTurn(Light light) {
        int distance = light.hLightX - this.xPos;
        int difference = this.speed - distance;

        horizontalMove(distance);
        light.streetY.addCar(this);
        this.street = light.streetY;
        this.xPos = light.streetY.left;
        verticalMove(difference);
    }

    public void horizontalMove() {
        int dest = this.xPos + speed;
        int stop;
        ArrayList<Car> carsAhead = new ArrayList<>();
        ArrayList<Light> lightsAhead = new ArrayList<>();
        ArrayList<Light> lights = street.getLights();
        ArrayList<Car> cars = street.getCars();

        for (Car car : cars) {
            if ((car.xPos <= dest) && (car.xPos > this.xPos)) {
                carsAhead.add(car);
            }
        }

        if (carsAhead.size() > 0) {
            slowDown(carsAhead);
        }

        for (Light light : lights) {
            if ((light.hLightX <= dest) && (light.hLightX > this.xPos)) {
                lightsAhead.add(light);
            }
        }

        if (lightsAhead.size() > 0) {
            Light light = lightsAhead.get(0);

            if(light.signalX == Signal.STOP) {
                if (carsAhead.size() > 0) {
                    stop = carsAhead.size();
                } else {
                    stop = 1;
                }

                horizontalMove(light.hLightX - this.xPos - (10 * stop));
                this.still = true;
                System.out.println(currSpeed);
            }
            else if (light.signalX == Signal.GO) {
                System.out.println(speed);
                this.still = false;

                if (random.nextBoolean()) {
                    horizontalMove(this.speed);
                } else {
                    verticalTurn(light);
                }
            }
        } else {
            horizontalMove(this.speed);
        }
    }

    public void horizontalMove(int length) {
        this.xPos += length;

        if (this.xPos >= this.street.length) {
            this.xPos = 0;
        }
    }

    public void horizontalTurn(Light light) {
        int distance = this.yPos - light.vLightY;
        int difference = this.speed - distance;

        verticalMove(distance);
        light.streetX.addCar(this);
        this.street = light.streetX;
        this.yPos = light.streetX.left;
        horizontalMove(difference);
    }

    @Override
    protected void done() {
        System.out.println("Car #" + this.id + " is done");
    }

    public void passTime(Time timer) {
        this.time = timer;
    }

    public void draw (Graphics g) {
        g.setColor(this.colour);
        g.fillOval(this.xPos, this.yPos, 10, 10);
    }

    public int getSpeed() {
        if (this.still == false) {
            return this.speed;
        }
        return 0;
    }

    public JTextField showField() {
        return this.text;
    }

    
    // public int getSpeed() {
    //     if (isRunning.get()) {
    //         if(atLight.get()) {
    //             speed = 0;
    //         } else {
    //             speed = 180;
    //         }
    //     } else {
    //         speed = 0;
    //     }
    //     return speed;
    // }
}
