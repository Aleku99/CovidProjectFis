import com.sun.java.swing.plaf.windows.resources.windows;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import java.awt.*;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.security.krb5.internal.crypto.Aes128;

import javax.xml.soap.Text;

public class Pacient {

    public static String pacientName="";
    public static Scene pacientStartScene(String name)
    {
        pacientName = name;
        Label titleLabel = new Label("Welcome " + name);
        Button logoutButton = new Button();
        Button writeSymptomsButton = new Button();
        Button checkTreatmentButton = new Button();

        logoutButton.setText("Logout");
        writeSymptomsButton.setText("Write symptoms");
        checkTreatmentButton.setText("Check treatment");

        logoutButton.setOnAction(e->{Main.window.setScene(User.login());});
        writeSymptomsButton.setOnAction(e->{Main.window.setScene(writeSymptomsScene());});
        checkTreatmentButton.setOnAction(e->{checkTreatment();});

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(titleLabel, 1,0);
        GridPane.setConstraints(writeSymptomsButton, 1,1);
        GridPane.setConstraints(checkTreatmentButton, 1,2);
        GridPane.setConstraints(logoutButton, 1,3);
        grid.getChildren().addAll(titleLabel, writeSymptomsButton,checkTreatmentButton,logoutButton);

        Scene pacientStartScene = new Scene(grid, 400, 600);
        return pacientStartScene;
    }

    public static Scene writeSymptomsScene()
    {
        Label writeSymptomsLabel = new Label("Write your symtpoms in the box below");
        TextField writeSymtpomsTextField = new TextField();
        Button backButton = new Button();
        Button submitButton = new Button();

        submitButton.setText("Submit");
        backButton.setText("Back");
        submitButton.setOnAction(e->{
            updateDb("symptoms", writeSymtpomsTextField.getText());
            Main.window.setScene(writeSymptomsScene());
        });
        backButton.setOnAction(e->Main.window.setScene(pacientStartScene(pacientName)));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        GridPane.setConstraints(writeSymptomsLabel, 1,0);
        GridPane.setConstraints(writeSymtpomsTextField, 1,1);
        GridPane.setConstraints(submitButton, 1,2);
        GridPane.setConstraints(backButton, 1,3);

        grid.getChildren().addAll(writeSymptomsLabel, writeSymtpomsTextField, submitButton, backButton);
        Scene writeSymptomsScene = new Scene(grid, 400, 600);
        return writeSymptomsScene;
    }
    public static void checkTreatment()
    {
        JSONParser jsonParser = new JSONParser();

        try{
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("pacientdb.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");

            for(Object o: jsonArray)
            {
                if(((JSONObject)o).get("name").equals(pacientName))
                {
                    if(((JSONObject)o).get("treatment").equals(""))
                    {
                        AlertBox.display("Inca nu exista tratament pentru dumneavoastra");
                    }
                    else
                    {
                        AlertBox.display(((JSONObject)o).get("treatment").toString());
                    }
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void updateDb(String key, String value)
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
                    ((JSONObject)o).put("symptoms", value);
                    System.out.println(value);
                    System.out.println(((JSONObject)o).get("user"));
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
