/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uip.todoapp.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.prefs.BackingStoreException;
/**
 *
 * @author sujan
 *
 * 
 *
 *
 */
public class Utility {

   static Preferences prefs =
            Preferences.userNodeForPackage(Utility.class);
    static String TASK_MAX_ID = "taskID";
    static String TAG_MAX_ID = "tagID";
    private static String dateFormat = "yyyy-MM-dd";

    public static Object[] listToArry(List data) {
        Object[] obj = new Object[data.size()];
        int i = 0;
        for (Object d : data) {
            obj[i] = d;
            i++;
        }
        return obj;

    }

    public static Date parseDate(String dateValue) {
        try {
            DateFormat df = new SimpleDateFormat(dateFormat);
            
            return df.parse(dateValue);
        } catch (Exception ex) {
            // System.out.println("data is null");
        }
        return null;
    }

    public static String formatDateShort(Date date) {
        try {
           
            DateFormat df = new SimpleDateFormat(dateFormat);
            return df.format(date);

        } catch (Exception ex) {
          // System.out.println("data is null");
        }
        return "";
    }

    
    public static Integer getTaskMaxId() {
        int id = prefs.getInt(TASK_MAX_ID, 10) + 1;
        prefs.putInt(TASK_MAX_ID, id);
        return id++;
    }
    public static Integer getTagMaxId() {
        int id = prefs.getInt(TAG_MAX_ID, 10) + 1;
        prefs.putInt(TAG_MAX_ID, id);
        return id++;
    }

    
   
}
