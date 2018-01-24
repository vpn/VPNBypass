/**
 *  ========================================
 *   Class : Domain
 *   Project: VPNBypass
 *   Version: 1.0.0
 *   Date: 24/1/18
 *   Author: Patrick McGranaghan
 ========================================
 */
package com.patrick.VPNBypass.Domains;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Creates Domain object to store domain name and addresses
 *
 * @author Patrick McGranaghan
 * @version 1.0-SNAPSHOT
 */

public class Domain {

    private String name;

    private ArrayList<String> addresses = new ArrayList<>();
    private static int numDomains = 0;

    /**
     * Creates Object
     * @param name - String
     * @param addresses - ArrayList<String>
     */
    public Domain(String name, ArrayList<String> addresses){
        this.name = name;
        this.addresses = addresses;
        numDomains++;
    }

    /**
     * Get the domain name
     * @return - String
     */
    public String getName(){
        return name;
    }

    /**
     * Get associated addresses
     * @return - ArrayList<String>
     */
    public ArrayList<String> getAddresses(){
        return addresses;
    }

    /**
     * Sets Domain name
     * @param name - String
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets domain addresses
     * @param addresses - ArrayList<String>
     */
    public void setAddresses(ArrayList<String> addresses){
        this.addresses = addresses;
    }

}
