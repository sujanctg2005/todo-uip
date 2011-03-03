package com.uip.todoapp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 *
 * @author Valjean Clark
 */
public class CustomSliderUI extends BasicSliderUI {

    public CustomSliderUI(CustomSlider b) {
        super(b);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.black);
        g.drawRect(trackRect.x, trackRect.y, trackRect.width, trackRect.height);

        if(thumbRect.x - trackRect.x <= 0.25*trackRect.width) {
            g2d.setPaint(Color.red);
            g.fillRect(trackRect.x + 1, trackRect.y + 1, thumbRect.x - thumbRect.width/2, trackRect.height - 1);
        }
        else if(thumbRect.x - trackRect.x <= 0.5*trackRect.width) {
            g2d.setPaint(Color.orange);
            g.fillRect(trackRect.x + 1, trackRect.y + 1, thumbRect.x - thumbRect.width/2, trackRect.height - 1);
        }
        else if(thumbRect.x - trackRect.x <= 0.75*trackRect.width) {
            g2d.setPaint(Color.yellow);
            g.fillRect(trackRect.x + 1, trackRect.y + 1, thumbRect.x - thumbRect.width/2, trackRect.height - 1);
        }
        else {
            g2d.setPaint(Color.green);
            g.fillRect(trackRect.x + 1, trackRect.y + 1, thumbRect.x - thumbRect.width/2, trackRect.height - 1);
        }
    }

    @Override
    public void paintThumb(Graphics g) {
        int x = thumbRect.x;
        int y = thumbRect.y;
        int w = thumbRect.width;
        int h = thumbRect.height;      
        
        // Create graphics copy.
        Graphics2D g2d = (Graphics2D) g.create();

        // Create default thumb shape.
        Shape thumbShape = new RoundRectangle2D.Double(x, y-2, w, h+4, 5, 5);

        // Draw thumb.
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.gray);
        g2d.fill(thumbShape);

        g2d.setColor(Color.black);
        g2d.draw(thumbShape);
        
        // Dispose graphics.
        g2d.dispose();

    }
}
