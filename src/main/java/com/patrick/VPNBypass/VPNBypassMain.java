/**
 *  ========================================
    Class : VPNBypassMain
    Project: VPNBypass
    Version: 1.0-SNAPSHOT
    Date: 24/1/18
    Author: Patrick McGranaghan
    Description: This is the applications main class.
    It creates the GUI and contains all the methods
    to add static network routing rules
    ========================================
 */

package com.patrick.VPNBypass;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

//Utils to get DefaultGateway
import java.io.*;
import java.util.*;
import com.google.gson.*;

import com.patrick.VPNBypass.Domains.*;

/**
 * VPNBypass Main class
 *
 * @author Patrick McGranaghan
 * @version 1.0-SNAPSHOT
 */
public class VPNBypassMain extends Application{

    Stage window;
    /** This is the gateway object. When the application
     * starts it will store the deafult gateway address.
     * This is done so that it we do not have to call
     * a function every time we add a new rule.
     */
    Gateway gatewayAddress = new Gateway(getGateway());

    /**
     * The ArrayList will contain a list of domain objects.
     * This stores all domains added during the lifetime of
     * the application, including their IP's.
     * This has been added as I plan to add a list of added
     * routes and functionality to remove the rules.
     */
    static ArrayList<Domain> Domains = new ArrayList<Domain>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        primaryStage.getIcons().add(new Image("https://cdn.stuf.io/74749Qvdj9mg1C.png"));
        window.setTitle("VPNBypass");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10 ,10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Name lbl
        Label domainLbl = new Label("Domain");
        GridPane.setConstraints(domainLbl, 0, 0);
        //Domain Input
        TextField domainInput = new TextField();
        domainInput.setPromptText("stuf.io");
        GridPane.setConstraints(domainInput, 1, 0);
        //Action is triggered when Enter key is pressed.
        domainInput.setOnAction(e -> addDomain(domainInput, domainInput.getText()));

        //Add button
        Button whitelistBtn = new Button("Whitelist");
        GridPane.setConstraints(whitelistBtn, 1, 1);
        whitelistBtn.setOnAction(e -> addDomain(domainInput, domainInput.getText()));

        //Adds elements to grid
        grid.getChildren().addAll(domainLbl, domainInput, whitelistBtn);

        Scene scene = new Scene(grid, 300, 80);

        window.setScene(scene);
        window.show();
        //Focuses the textfield
        grid.requestFocus();
    }


    /**
     *  Method that will add the domain to the "whitelist'
     * @param input - TextField Object
     * @param message - String
     */
    public void addDomain(TextField input, String message){
        //If the text field contins data
        if (message.length() > 0){
            //If the domain is valid, then the routing rule will be added
            if(isDomain(input, message)){
                addRoute();
            }else{
                //Displays an error message, using JavaFX8u40 (JDK 8u40 is the minimum JDK needed to run this)
                Alertbox.display("Invalid domain", "The domain you have entered is invalid.\nPlease try again.");
            }
        }
    }

    /**
     * Method which validates the domain entered. Uses a separate API project which I have made.
     * If the domain is not valid, ie. has an invalid domain extension,
     * then false is returned.
     * @param input - TextField Object
     * @param message - String
     * @return true/false - boolean
     */
    private static boolean isDomain(TextField input, String message){
        try {
            //Validate input against start domain restrictions
            if(message.length() < 4 || message.length() > 63 || !message.contains(".")){
                return false;
            }else{
                JsonObject apiResult = getAPIQuery("domainValidator", message);
                if(apiResult.get("success").getAsBoolean()){
                    //In some cases, domains have multiple addresses, the arraylist
                    //allows for multiple addresses to be added to the 'whitelist'
                    ArrayList<String> addresses = new ArrayList<String>();
                    JsonArray resultArray = apiResult.getAsJsonArray("result");
                    //Iterates through each record
                    for (JsonElement ra: resultArray){
                        //.getAsString is used to eliminate the double quotes
                        addresses.add(ra.getAsString());
                    }
                    //Creates a new domain object and adds the object to the domain array list
                    Domains.add(new Domain(message, addresses));
                    return true;
                }else{
                    return false;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Method that will call my API and return the JSON Data.
     * @param function - String (API Function being used)
     * @param parameter - String (Parameter required for the function)
     * @return - JsonObject
     */
    private static JsonObject getAPIQuery(String function, String parameter){
        StringBuilder content = new StringBuilder();
        JsonParser parser = new JsonParser();
        String tmpUrl = "http://apiv2.beta.stuf.io/index.php?query=api&function=" + function + "&domain=" + parameter;
        try {
            //Create URL Object
            URL url = new URL(tmpUrl);
            //Creates URLConnection Object
            URLConnection urlConnection = url.openConnection();
            //Wraps connection in bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            //read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(IOException e){
            e.printStackTrace();
        }
        return (JsonObject) parser.parse(content.toString());
    }

    /**
     * Method that will get the users default gateway address.
     * Simply runs the netstat -rn command and then reads from the response,
     * Credit to Alnitak @ Stackoverflow - https://stackoverflow.com/a/247216/5191574
     * @return default gateway address - String
     */
    private static String getGateway(){
        String defaultGateway = "";
        try{
            Process result = Runtime.getRuntime().exec("netstat -rn");

            BufferedReader output = new BufferedReader(new InputStreamReader(result.getInputStream()));
            String line = output.readLine();
            while (line != null) {
                if (line.contains("0.0.0.0")) {

                    StringTokenizer stringTokenizer = new StringTokenizer(line);
                    stringTokenizer.nextElement(); // first element is 0.0.0.0
                    stringTokenizer.nextElement(); // second element is 0.0.0.0
                    defaultGateway = (String) stringTokenizer.nextElement();
                    break;
                }
                line = output.readLine();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return defaultGateway;
    }

    /**
     * Method that will add a routing rule.
     * This requires elevated permisson levels on windows by default.
     * Within the README I have explained how I have got around this.
     * The method will get the last domain added, iterate through its addresses
     * and add a new rule for each IP.
     */
    private void addRoute(){
        int arrayIndex = (Domains.size() - 1);
        try{
            for (int i = 0; i < Domains.get(arrayIndex).getAddresses().size(); i++){
                String tmpCommand = "route add " + Domains.get(arrayIndex).getAddresses().get(i) + " " + gatewayAddress.getGatewayaddr();
                Process result = Runtime.getRuntime().exec(tmpCommand);
                /* Used for debugging
                InputStream in = result.getInputStream();
                int c;
                while((c = in.read()) != -1){
                    System.out.print((char) c);
                }
                */
            }
            Alertbox.display("VPNBypass", "Success", "Domain has been added");
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}