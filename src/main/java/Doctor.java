import com.sun.java.swing.plaf.windows.resources.windows;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import java.awt.*;

import java.io.*;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.security.krb5.internal.crypto.Aes128;

import javax.xml.soap.Text;

public class Doctor {
    public static String doctorName="";
    public static String doctorId="";
    public static Scene doctorStartScene(String name, String id)
    {
        doctorName = name;
        doctorId = id;
        Label titleLabel = new Label("Welcome " + name);
        Button logoutButton = new Button();
        Button addPacientButton = new Button();
        Button setStatusButton = new Button();
        Button callAmbulanceButton = new Button();
        Button seeSymptomsButton = new Button();


        logoutButton.setText("Logout");
        addPacientButton.setText("Add pacient");
        setStatusButton.setText("Set pacient's status");
        callAmbulanceButton.setText("Call an ambulance");
        seeSymptomsButton.setText("See symptoms");

        logoutButton.setOnAction(e->{Main.window.setScene(User.login());});
        //addPacientButton.setOnAction(e->{Main.window.setScene(addPacientScene());});
        setStatusButton.setOnAction(e->{Main.window.setScene(setStatusScene());});
        //callAmbulanceButton.setOnAction(e->{callAmbulance();});
        seeSymptomsButton.setOnAction(e->{Main.window.setScene(seeSymptomsScene());});


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(titleLabel, 1,0);
        GridPane.setConstraints(addPacientButton, 1,1);
        GridPane.setConstraints(setStatusButton, 1,2);
        GridPane.setConstraints(callAmbulanceButton, 1,3);
        GridPane.setConstraints(seeSymptomsButton, 1,4);
        GridPane.setConstraints(logoutButton, 1,5);
        grid.getChildren().addAll(titleLabel, addPacientButton,setStatusButton,callAmbulanceButton,seeSymptomsButton,logoutButton);

        Scene doctorStartScene = new Scene(grid, 400, 600);
        return doctorStartScene;
    }
    public static Scene seeSymptomsScene()
    {
        Label pacientNameLabel = new Label("Pacient's name");
        TextField nameField = new TextField();


        Button backButton = new Button();
        Button searchPacientButton = new Button();
        backButton.setText("Back");
        searchPacientButton.setText("Seach pacient");
        backButton.setOnAction(e->{Main.window.setScene(doctorStartScene(doctorName,doctorId));});
        searchPacientButton.setOnAction(e->{
            if(nameValidation(nameField.getText())==true) {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("pacientdb.json"));
                    JSONArray jsonArray = (JSONArray) jsonObject.get("users");
                    int gasit=0;
                    for (Object o : jsonArray) {

                        if (((JSONObject) o).get("name").equals((String) nameField.getText()) && ((JSONObject) o).get("id").equals(doctorId)) {
                            gasit=1;
                            if (((JSONObject) o).get("symptoms").equals("")) {
                                AlertBox.display("The pacient doesn't have any symptoms!");
                            } else {
                                AlertBox.display(((JSONObject) o).get("symptoms").toString());
                            }
                        }

                    }
                    if(gasit==0)
                    {AlertBox.display("Pacient doesn't exist or it isn't your pacient.");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(pacientNameLabel,1,0);
        GridPane.setConstraints(nameField,2,0);
        GridPane.setConstraints(searchPacientButton,1,2);
        GridPane.setConstraints(backButton,1,3);
        grid.getChildren().addAll(pacientNameLabel,nameField,backButton,searchPacientButton);

        Scene seeSymptomsScene = new Scene(grid, 400, 600);
        return seeSymptomsScene ;

    }
    public static Scene setStatusScene()
    {
        Label pacientNameLabel = new Label("Pacient's name");
        TextField nameField = new TextField();
        Label statusLabel = new Label("Pacient's status");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "healthy",
                        "infected",
                        "dead"
                );
        ComboBox statusField = new ComboBox(options);

        Button backButton = new Button();
        Button setStatusButton = new Button();
        backButton.setText("Back");
        setStatusButton.setText("Set status");
        backButton.setOnAction(e->{Main.window.setScene(doctorStartScene(doctorName,doctorId));});
        setStatusButton.setOnAction(e->{
            if(nameValidation(nameField.getText())==true) {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("pacientdb.json"));
                    JSONArray jsonArray = (JSONArray) jsonObject.get("users");
                    int gasit=0;
                    for (Object o : jsonArray) {

                        if (((JSONObject) o).get("name").equals((String) nameField.getText()) && ((JSONObject) o).get("id").equals(doctorId)) {
                            gasit=1;
                           //code for setting status
                            updateDbDoc("status",statusField.getValue().toString(),nameField.getText());
                        }

                    }
                    if(gasit==0)
                    {AlertBox.display("Pacient doesn't exist or it isn't your pacient.");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            Main.window.setScene(setStatusScene());
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(pacientNameLabel,1,0);
        GridPane.setConstraints(nameField,2,0);
        GridPane.setConstraints(statusLabel, 1,1);
        GridPane.setConstraints(statusField,2,1);
        GridPane.setConstraints(setStatusButton,1,2);
        GridPane.setConstraints(backButton,1,3);
        grid.getChildren().addAll(pacientNameLabel,nameField,statusLabel,statusField, backButton,setStatusButton);


        Scene setStatusScene = new Scene(grid, 400, 600);
        return setStatusScene;
    }
    public static boolean nameValidation(String nume)
    {

        if (nume.equals(null) || nume.equals(""))
        {
            AlertBox.display("You must enter your name!");
            return false;
        }
        else
        {
            for(char c:nume.toCharArray())
            {
                if(Character.isDigit(c))
                {
                    AlertBox.display("You can't have numbers in your name!");
                    return false;
                }

            }
        }
        return true;
    }
    public static void updateDbDoc(String key, String value,String pacientName)
    {
        JSONObject pacientUsersObj = new JSONObject();
        JSONArray pacientUsersArray = new JSONArray();

        JSONParser jsonParser = new JSONParser();
        try
        {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("pacientdb.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");


            for(Object o:jsonArray)
            {

                if(((JSONObject)o).get("name").equals(pacientName))
                {
                    ((JSONObject)o).put(key, value);
                    pacientUsersArray.add(o);

                }
                else
                {
                    pacientUsersArray.add(o);
                }
            }
        }
        catch(Exception e2)
        {
            e2.printStackTrace();
        }
        pacientUsersObj.put("users", pacientUsersArray);


        try (FileWriter file = new FileWriter("pacientdb.json")) {
            file.write(pacientUsersObj.toJSONString());
            file.flush();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

}
