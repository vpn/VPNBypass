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

/**
 * Creates Gateway object to store default gateway address
 *
 * @author Patrick McGranaghan
 * @version 1.0-SNAPSHOT
 */
public class Gateway {

    private String gatewayaddr;

    /**
     * Creates the object
     * @param address - String
     */
    public Gateway(String address){
        this.gatewayaddr = address;
    }

    /**
     * Gets default gateway address
     * @return - String
     */
    public String getGatewayaddr() {
        return gatewayaddr;
    }

    /**
     * Sets the default gateway address
     * @param gatewayaddr - String
     */
    public void setGatewayaddr(String gatewayaddr) {
        this.gatewayaddr = gatewayaddr;
    }



}
