/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// JDOM Implementation
// @author ekta
package com.uip.todoapp.utility;

import java.io.IOException;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;


import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 *
 *
 */
public class XMLParser {

    /**
     * This class is to parse xml
     * and store data to xml format
     */
    org.jdom.Document doc;
    static XMLParser parser;
    String xmlFilePath = "data\\data.xml";

    private XMLParser() {


        initDoc();

    }
    /*  This method to access document object
     *  @return  instance of Document
     */

    public Document getDoc() {
        return doc;
    }
    /*
     *  initialize document object
     */

    public Document initDoc() {
        java.io.FileReader reader = null;
        try {

            reader = new java.io.FileReader(xmlFilePath);
            SAXBuilder builder = new SAXBuilder();
            doc = builder.build(reader);
        } catch (Exception ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return doc;

    }
    /*
     *  save changes data to xml
     */

    public void saveAllData() {

        try {
            org.jdom.output.XMLOutputter out = new XMLOutputter();
            Format f = Format.getCompactFormat();
            f.setIndent("  ");
            f.setLineSeparator("\n");
            f.setTextMode(Format.TextMode.TRIM);
            out.setFormat(f);

            java.io.FileWriter writer = new java.io.FileWriter(xmlFilePath);
            out.output(doc, writer);
            writer.flush();
            writer.close();
            writer = null;
            out = null;
            doc = null;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     *  create parser instance
     */
    static public XMLParser getInstance() {
        if (parser == null) {
            try {
                return new XMLParser();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return parser;

    }
}
