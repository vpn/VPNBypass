/**
 *  ========================================
 *   Class : Alertbox
 *   Project: VPNBypass
 *   Version: 1.0.0
 *   Date: 24/1/18
 *   Author: Patrick McGranaghan
 ========================================
 */
package com.patrick.VPNBypass;

import javafx.scene.control.Alert;

/**
 * Displays an alert to the user. The type of alert
 * is determined based on parameters.
 *
 * @author Patrick McGranaghan
 * @version 1.0-SNAPSHOT
 */
public class Alertbox {

    /**
     * Method to display an ERROR alert
     * @param header - String
     * @param message - String
     */
    public static void display(String header, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Method that will display an INFO alert.
     * @param title - String
     * @param header - String
     * @param message - String
     */
    public static void display(String title, String header, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
