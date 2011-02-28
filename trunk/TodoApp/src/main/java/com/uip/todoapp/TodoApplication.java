/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp;

import java.util.Locale;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class TodoApplication extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override
    protected void startup() {
        
        
        show(new TodoForm(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override
    protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of TodoApplication
     */
    public static TodoApplication getApplication() {
        return Application.getInstance(TodoApplication.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        
        

        String vers = System.getProperty("java.version");
        if (vers.compareTo("1.1.2") < 0) {
            System.out.println("!!!WARNING: Swing must be run with a "
                    + "1.1.2 or higher version VM!!!");
        }


        launch(TodoApplication.class, args);
    }
}
