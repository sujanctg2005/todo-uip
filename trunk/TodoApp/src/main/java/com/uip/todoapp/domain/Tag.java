/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.uip.todoapp.domain;

/**
 *
 * @author sujan
 */
public class Tag {
private Integer tagID;
private String tagName;

    /**
     * @return the tagID
     */
    public Integer getTagID() {
        return tagID;
    }

    /**
     * @param tagID the tagID to set
     */
    public void setTagID(Integer tagID) {
        this.tagID = tagID;
    }

    /**
     * @return the tagName
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * @param tagName the tagName to set
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;


    }
public boolean equals(Object obj){

    if(obj instanceof Tag){
        return this.tagName.toLowerCase().equals( ((Tag)obj).tagName.toLowerCase());

        
    }
    
        return false;
}

   public String toString(){
   return tagName;
      // return " Tag : [ID : ]+"+tagID+" , tagName: "+tagName+"";
   }

}
