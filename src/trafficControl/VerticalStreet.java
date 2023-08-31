/*
Oluwaseyi Ariyo
*/ 

package src.trafficControl;

import java.awt.*;
import java.util.ArrayList;

public class VerticalStreet extends Street{
    final int height, middle, right, left;

    public VerticalStreet(int middle, int height) {
        this.height = height;
        this.middle = middle;
        this.left = middle - 5;
        this.right = middle + 5;
        this.length = height;
        this.lights = new ArrayList<>();
        this.cars = new ArrayList<>();
        
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(left, 0, left, height);
        g.drawLine(right, 0, right, height);

        for (Car car : this.cars) {
            car.draw(g);
        }
    }
}
