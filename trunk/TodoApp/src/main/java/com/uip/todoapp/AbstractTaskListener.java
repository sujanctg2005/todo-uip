/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uip.todoapp;

/**
 *
 * @author sujan
 */
public interface  AbstractTaskListener {
    public  void updateTaskEvent(int index);
    public  void removeTaskEvent(int index);
    public  void newTaskEvent(int index);

}
