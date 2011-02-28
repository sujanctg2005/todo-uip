/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uip.todoapp.service;

import com.uip.todoapp.service.impl.ToDoServiceImpl;
import com.uip.todoapp.utility.XMLParser;

/**
 *
 * @author sujan
 */

/**
 * this is factory class for instantiate all task service
 *
 */
public class ServiceFactory{
   
  public static ToDoService getToDoService(){
  
   return new ToDoServiceImpl(XMLParser.getInstance());

  }
}
