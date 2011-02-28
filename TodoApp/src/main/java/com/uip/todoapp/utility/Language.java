/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp.utility;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author sujan
 *
 * handle language resource
 */
public class Language {

    private static ResourceBundle resources;


/*
 *  get language resource bundle
 *  @param local1 define current local type
 *  @return instance of ResourceBundle
 */
    public static ResourceBundle getResourceBundle(Locale locale1) {
        try {
            
            resources = ResourceBundle.getBundle("com.uip.todoapp.resources.language",
                    locale1);
        } catch (MissingResourceException mre) {
            System.err.println("com.uip.todoapp.resources.language not found");
            System.exit(1);
        }
        return resources;
    }
}
