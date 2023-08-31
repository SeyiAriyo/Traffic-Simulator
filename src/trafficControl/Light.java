/*
Oluwaseyi Ariyo
*/
package src.trafficControl;

import src.timer.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Light extends SwingWorker<Void, Void> {
    static int count = 1;
    Random random;

    Signal signalX = Signal.GO;
    Signal signalY = Signal.STOP;

    VerticalStreet streetY;
    HorizontalStreet streetX;
    
    private Time timer;
    int id, hLightX, hLightY, vLightX, vLightY;
    
    Color colourX = Color.GREEN;
    Color colourY = Color.RED;

    public Light(VerticalStreet y, HorizontalStreet x) {
        random = new Random();
//revisit if breaks
        this.streetX = x;
        this.streetY = y;
        this.hLightY = x.right - 20;
        this.vLightY = x.right;
        this.hLightX = y.left - 10;
        this.vLightX = y.right;
        this.id = count;

        count += 1;

        System.out.println("Light #" + id + " created");
    }

    @Override
    protected Void doInBackground() throws Exception {
        while (!(timer.stopped())) {
            if (!(timer.paused())) {
                Color colour = colourX;
                Signal signal = signalX;

                signalX = signalY;
                colourX = colourY;

                signalY = signal;
                colourY = colour;
            }
            Thread.sleep(1000 * random.nextInt(10));
        }
        return null;
    }

    public void draw(Graphics g) {
        g.setColor(colourX);
        g.fillOval(hLightX, hLightY, 10, 10);
        g.setColor(colourY);
        g.fillOval(vLightX, vLightY, 10, 10);
    }

    public void passTime(Time timer) {
        this.timer = timer;
    }

    @Override 
    protected void done() {
        System.out.println("Light #" + id + " has finished");
    }
}
