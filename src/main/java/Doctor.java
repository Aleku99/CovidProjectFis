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
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.security.krb5.internal.crypto.Aes128;

import javax.mail.*;
import javax.mail.internet.*;
import javax.xml.soap.Text;
import java.util.Properties;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;

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
        addPacientButton.setOnAction(e->{Main.window.setScene(addPacientScene());});
        setStatusButton.setOnAction(e->{Main.window.setScene(setStatusScene());});
        callAmbulanceButton.setOnAction(e->{
            try {
                callAmbulance("aleku99@yahoo.com");
            }
            catch(Exception e1){
                e1.printStackTrace();
            }
        });
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
                    System.out.println("Aici pica");
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
        Label treatmentLabel = new Label("Treatment");
        TextField treatmentField = new TextField();
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
                            updateDbDoc("status",statusField.getValue().toString(),nameField.getText(),"name");
                            updateDbDoc("treatment",treatmentField.getText(),nameField.getText(),"name");
                            AlertBox.display("Status set successfully!");
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
        GridPane.setConstraints(treatmentLabel,1,2);
        GridPane.setConstraints(treatmentField,2,2);
        GridPane.setConstraints(setStatusButton,1,3);
        GridPane.setConstraints(backButton,1,4);
        grid.getChildren().addAll(pacientNameLabel,nameField,statusLabel,statusField, backButton,setStatusButton,treatmentField,treatmentLabel);


        Scene setStatusScene = new Scene(grid, 400, 600);
        return setStatusScene;
    }
    public static Scene addPacientScene()
    {
        Label pacientNameLabel = new Label("Pacient's username");
        TextField nameField = new TextField();

        Button backButton = new Button();
        Button addPacientButton = new Button();
        backButton.setText("Back");
        addPacientButton.setText("Add pacient");

        backButton.setOnAction(e->{Main.window.setScene(doctorStartScene(doctorName,doctorId));});
        addPacientButton.setOnAction(e->{
            if(userValidation(nameField.getText())==true) {
                JSONParser jsonParser = new JSONParser();
                try {
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("pacientdb.json"));
                    JSONArray jsonArray = (JSONArray) jsonObject.get("users");
                    int gasit=0;
                    for (Object o : jsonArray) {

                        if (((JSONObject) o).get("user").equals((String) nameField.getText())) {
                            gasit=1;
                            updateDbDoc("id",doctorId,nameField.getText(),"user");
                            AlertBox.display("Pacient added succesfully!");
                        }

                    }
                    if(gasit==0)
                    {AlertBox.display("Pacient doesn't exist!");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            Main.window.setScene(addPacientScene());
        });
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(pacientNameLabel,1,0);
        GridPane.setConstraints(nameField,2,0);
        GridPane.setConstraints(backButton,1,3);
        GridPane.setConstraints(addPacientButton,1,2);
        grid.getChildren().addAll(pacientNameLabel,nameField,addPacientButton, backButton);

        Scene addPacientScene = new Scene(grid, 400, 600);
        return addPacientScene;
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

    public static boolean userValidation(String nume)
    {

        if (nume.equals(null) || nume.equals(""))
        {
            AlertBox.display("You must enter pacient's username!");
            return false;
        }

        return true;
    }
    public static void updateDbDoc(String key, String value,String pacientName,String compareKey)
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

                if(((JSONObject)o).get(compareKey).equals(pacientName))
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
    public static void callAmbulance(String recepient) throws Exception
    {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        String myAccountEmail = "loghinalex19@gmail.com";
        String password = "alexandru19111999";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail,recepient);
        Transport.send(message);
        System.out.println("Message sent succesfully");
    }
    private static Message prepareMessage(Session session, String myAccountEmail,String recepient)
    {
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("My first mail from java");
            message.setText("Hello");
            return message;
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
        return null;
    }


}
