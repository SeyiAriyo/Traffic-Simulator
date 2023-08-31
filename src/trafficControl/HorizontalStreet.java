/*
Oluwaseyi Ariyo

*/
package src.trafficControl;

import java.awt.*;
import java.util.ArrayList;

public class HorizontalStreet extends Street{
    final int width, middle, left, right;

    public HorizontalStreet(int middle, int width) {
        this.width = width;
        this.middle = middle;
        this.left = middle - 5;
        this.right = middle + 5;
        this.lights = new ArrayList<>();
        this.cars = new ArrayList<>();
        this.length = width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.drawLine(0, left, width, left);
        g.drawLine(0, right, width, right);

        for (Car car : cars) {
            car.draw(g);
        }
    }
}
