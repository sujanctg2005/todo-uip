/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uip.todoapp;

/**
 *
 * @author Rampradeep
 */
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
 
public class Abc
{
    public static void main(String[] args) throws BackingStoreException
    {
        Preferences _preferencias =
            Preferences.userNodeForPackage(Abc.class);
        final String CHAVE = "zwx";
        _preferencias.put(CHAVE, "abc");
        System.out.println(_preferencias.get(CHAVE, ""));
        
       // _preferencias.flush();
    }
}