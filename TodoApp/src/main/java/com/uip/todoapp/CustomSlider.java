package com.uip.todoapp;

import javax.swing.JSlider;

/**
 *
 * @author Valjean Clark
 */
public class CustomSlider extends JSlider {

    public CustomSlider(int min, int max, int value) {
        super(min, max, value);
        //super();

        //overrides default UI and uses custom one
        setUI(new CustomSliderUI(this));

        //set the value of the slider like this
        setValue(0);

        //hard codes attributes for completion 25% at a time
        setMajorTickSpacing(25);
        setPaintTicks(true);
        setPaintLabels(true);
        setSnapToTicks(true);
    }
}
