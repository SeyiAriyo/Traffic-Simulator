/*
Oluwaseyi Ariyo

*/
package src.timer;

import javax.swing.*;

import src.gui.TrafficCanvas;
import java.util.List;

public class Time extends SwingWorker<Void, Integer>{
    
    private final JTextField text;
    private final TrafficCanvas canvas;
    private boolean pause, stop;

    public Time(JTextField a, TrafficCanvas b) {
        this.text = a;
        this.canvas = b;
        this.stop = this.pause = false;
    }

    @Override
    protected Void doInBackground() throws Exception {
        int seconds = 0;
        while (!stop) {
            if (!pause) {
                seconds++;
                publish(seconds);
            }
            Thread.sleep(1000);
        }
        return null;
    }

    @Override
    protected void process(List<Integer> pieces) {
        Integer s = pieces.get(pieces.size() - 1);
        String timer = String.format("%02d:%02d", s / 60, s % 60);
        
        text.setText(timer);
        canvas.repaint();
    }

    @Override
    protected void done() {
        System.out.println("Timer done");
    }

    public void play() {
        this.pause = false;
    }

    public void pause() {
        this.pause = true;
    }
    
    public boolean paused() {
        return pause;
    }

    public void stop() {
        this.stop = true;
    }

    public boolean stopped() {
        return stop;
    }

    
    
}
