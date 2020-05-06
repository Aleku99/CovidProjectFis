import com.sun.java.swing.plaf.windows.resources.windows;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class User {

    public static JSONArray docUsersArray = new JSONArray();

    public static Scene roleSelect()
    {
        Label select = new Label("Select role: ");
        Button doctorButton = new Button();
        Button pacientButton = new Button();
        Button backButton = new Button();
        pacientButton.setText("Pacient");
        doctorButton.setText("Doctor");
        backButton.setText("Back");
        doctorButton.setOnAction(e->Main.window.setScene(registerDoctor()));
        pacientButton.setOnAction(e->Main.window.setScene(registerPacient()));
        backButton.setOnAction(e->Main.window.setScene(Main.appOpeningScene));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(select, 1,0);
        GridPane.setConstraints(doctorButton, 1,1);
        GridPane.setConstraints(pacientButton, 1,2);
        GridPane.setConstraints(backButton, 1,3);
        grid.getChildren().addAll(select, doctorButton, pacientButton,backButton);

        Scene roleSelectScene = new Scene(grid, 400, 600);
        return roleSelectScene;
    }
    public static Scene registerDoctor()
    {
        Label docRegFormLabel = new Label("Registration Form");
        Label nameLabel = new Label("Name");
        Label addressLabel = new Label("Address");
        Label phoneNumberLabel = new Label("Phone Number");
        Label hospitalLabel = new Label("Hospital");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        TextField fullName = new TextField();
        TextField address = new TextField();
        TextField phoneNumber = new TextField();
        TextField hospital = new TextField();
        TextField username = new TextField();
        TextField password = new TextField();
        fullName.setPromptText("First name and last name");
        address.setPromptText("Your address");
        phoneNumber.setPromptText("Your personal phone number");
        hospital.setPromptText("Hospital you're working in");
        username.setPromptText("Desired username");
        password.setPromptText("desired password");

        Button back = new Button();
        Button submit = new Button();
        back.setText("Back");
        submit.setText("Submit");
        back.setOnAction(e->Main.window.setScene(User.roleSelect()));
        submit.setOnAction(e->{

            while(true) {
                if(validateDocRegistration(username.getText(), password.getText(), fullName.getText(), phoneNumber.getText(), hospital.getText(), address.getText())) {
                    JSONObject docUsersObj = new JSONObject();
                    JSONObject docObj = new JSONObject();

                    docObj.put("user", username.getText());
                    docObj.put("password", Encryption.encrypt_password(password.getText()));
                    docObj.put("name", fullName.getText());
                    docObj.put("address", address.getText());
                    docObj.put("phoneNumber", phoneNumber.getText());
                    docObj.put("hospital", hospital.getText());
                    docObj.put("randomCode", docUniqueCode(5));

                    JSONParser jsonParser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("doctordb.json"));
                        JSONArray jsonArray = (JSONArray) jsonObject.get("users");
                        docUsersArray = jsonArray;
                    } catch (FileNotFoundException err) {
                        err.printStackTrace();
                    } catch (IOException err) {
                        err.printStackTrace();
                    } catch (ParseException err) {
                        err.printStackTrace();
                    } catch (Exception err) {
                        err.printStackTrace();
                    }


                    docUsersArray.add(docObj);
                    docUsersObj.put("users", docUsersArray);

                    try (FileWriter file = new FileWriter("doctordb.json")) {
                        file.write(docUsersObj.toJSONString());
                        file.flush();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                    AlertBox.display("Succesfully registered!");
                    Main.window.setScene(Main.appOpeningScene);
                }
                break;
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(docRegFormLabel,1,0);
        GridPane.setConstraints(nameLabel, 0,1);
        GridPane.setConstraints(addressLabel,0,2);
        GridPane.setConstraints(phoneNumberLabel, 0,3);
        GridPane.setConstraints(hospitalLabel, 0,4);
        GridPane.setConstraints(usernameLabel, 0, 5);
        GridPane.setConstraints(passwordLabel, 0, 6);
        GridPane.setConstraints(fullName,1,1);
        GridPane.setConstraints(address,1,2);
        GridPane.setConstraints(phoneNumber, 1,3);
        GridPane.setConstraints(hospital, 1,4);
        GridPane.setConstraints(username, 1,5);
        GridPane.setConstraints(password, 1,6);
        GridPane.setConstraints(submit, 1,7);
        GridPane.setConstraints(back, 1,8);
        grid.getChildren().addAll(fullName,address,phoneNumber,hospital,submit,back,
                                    docRegFormLabel,nameLabel,addressLabel,phoneNumberLabel,
                                    hospitalLabel, usernameLabel, passwordLabel, username, password);

        Scene registerDoctorScene = new Scene(grid, 400, 600);
        return registerDoctorScene;
    }
    public static Scene registerPacient()
    {
        Label docRegFormLabel = new Label("Registration Form");
        Label nameLabel = new Label("Name");
        Label addressLabel = new Label("Address");
        Label phoneNumberLabel = new Label("Phone Number");
        Label idLabel = new Label("ID");
        Label usernameLabel = new Label("Username");
        Label passwordLabel = new Label("Password");

        TextField fullName = new TextField();
        TextField address = new TextField();
        TextField phoneNumber = new TextField();
        TextField id = new TextField();
        TextField username = new TextField();
        TextField password = new TextField();
        fullName.setPromptText("First name and last name");
        address.setPromptText("Your address");
        phoneNumber.setPromptText("Your personal phone number");
        id.setPromptText("Your ID");
        username.setPromptText("Desired username");
        password.setPromptText("desired password");

        Button back = new Button();
        Button submit = new Button();
        back.setText("Back");
        submit.setText("Submit");
        back.setOnAction(e->Main.window.setScene(User.roleSelect()));
        back.setOnAction(e->Main.window.setScene(User.roleSelect()));
        submit.setOnAction(e->{

            while(true) {
                if(validatePacientRegistration(username.getText(), password.getText(), fullName.getText(), phoneNumber.getText(), id.getText(), address.getText())) {
                    JSONObject docUsersObj = new JSONObject();
                    JSONObject docObj = new JSONObject();

                    docObj.put("user", username.getText());
                    docObj.put("password", Encryption.encrypt_password(password.getText()));
                    docObj.put("name", fullName.getText());
                    docObj.put("address", address.getText());
                    docObj.put("phoneNumber", phoneNumber.getText());
                    docObj.put("id", id.getText());

                    JSONParser jsonParser = new JSONParser();
                    try {
                        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("pacientdb.json"));
                        JSONArray jsonArray = (JSONArray) jsonObject.get("users");
                        docUsersArray = jsonArray;
                    } catch (FileNotFoundException err) {
                        err.printStackTrace();
                    } catch (IOException err) {
                        err.printStackTrace();
                    } catch (ParseException err) {
                        err.printStackTrace();
                    } catch (Exception err) {
                        err.printStackTrace();
                    }


                    docUsersArray.add(docObj);
                    docUsersObj.put("users", docUsersArray);

                    try (FileWriter file = new FileWriter("pacientdb.json")) {
                        file.write(docUsersObj.toJSONString());
                        file.flush();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                    AlertBox.display("Succesfully registered!");
                    Main.window.setScene(Main.appOpeningScene);
                }
                break;
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(docRegFormLabel,1,0);
        GridPane.setConstraints(nameLabel, 0,1);
        GridPane.setConstraints(addressLabel,0,2);
        GridPane.setConstraints(phoneNumberLabel, 0,3);
        GridPane.setConstraints(idLabel, 0,4);
        GridPane.setConstraints(usernameLabel, 0, 5);
        GridPane.setConstraints(passwordLabel, 0, 6);
        GridPane.setConstraints(fullName,1,1);
        GridPane.setConstraints(address,1,2);
        GridPane.setConstraints(phoneNumber, 1,3);
        GridPane.setConstraints(id, 1,4);
        GridPane.setConstraints(username, 1,5);
        GridPane.setConstraints(password, 1,6);
        GridPane.setConstraints(submit, 1,7);
        GridPane.setConstraints(back, 1,8);
        grid.getChildren().addAll(fullName,address,phoneNumber,idLabel,submit,back,
                docRegFormLabel,nameLabel,addressLabel,phoneNumberLabel,id, username, usernameLabel,
                passwordLabel, password);

        Scene registerPacientScene = new Scene(grid, 400, 600);
        return registerPacientScene;
    }
    public static boolean validateDocRegistration(String username, String pass, String nume, String telefon, String hospital, String address)
    {
        //validare nume
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
        //validare numar telefon
        if(telefon.equals(null) || telefon.equals(""))
        {
            AlertBox.display("You must enter your number!");
            return false;
        }
        else
        {
            int contorNumere = 0;
            for(char c:telefon.toCharArray())
            {
                contorNumere++;
                if(Character.isDigit(c)==false)
                {
                    AlertBox.display("Your number can't have letters!");
                    return false;
                }
            }
            if(contorNumere!=10)
            {
                AlertBox.display("Your number must have 10 digits!");
                return false;
            }
        }
        //validare nume spital
        if(hospital.equals(null) || hospital.equals(""))
        {
            AlertBox.display("You must enter hospital's name!");
            return false;
        }
        else
        {
            for(char c:hospital.toCharArray())
            {
                if(Character.isDigit(c))
                {
                    AlertBox.display("Hospital's name can't have digits!");
                    return false;
                }
            }
        }
        //validare adresa
        if(address.equals(null) || address.equals(""))
        {
            AlertBox.display("You must enter your address!");
            return false;
        }
        //validare username
        JSONParser jsonParser = new JSONParser();
        try
        {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("doctordb.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while(iterator.hasNext())
            {
                if(iterator.next().get("user").equals(username))
                {
                    AlertBox.display("Username already exists!");
                    return false;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //validare pass
        if(pass.equals(null) || pass.equals(""))
        {
            AlertBox.display("You must enter a password!");
            return false;
        }

        return true;
    }
    public static boolean validatePacientRegistration(String username, String pass, String nume, String telefon, String id, String address)
    {
        boolean ok=false;
        //validare nume
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
        //validare numar telefon
        if(telefon.equals(null) || telefon.equals(""))
        {
            AlertBox.display("You must enter your number!");
            return false;
        }
        else
        {
            int contorNumere = 0;
            for(char c:telefon.toCharArray())
            {
                contorNumere++;
                if(Character.isDigit(c)==false)
                {
                    AlertBox.display("Your number can't have letters!");
                    return false;
                }
            }
            if(contorNumere!=10)
            {
                AlertBox.display("Your number must have 10 digits!");
                return false;
            }
        }
        //validare id
        if(id.equals(null) || id.equals(""))
        {
            AlertBox.display("You must enter the id!");
            return false;
        }
        else
        {
            for(char c:id.toCharArray())
            {
                if(!Character.isDigit(c))
                {
                    AlertBox.display("ID can't have letters in it!");
                    return false;
                }
            }
            JSONParser jsonParser = new JSONParser();
            try
            {
                JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("doctordb.json"));
                JSONArray jsonArray = (JSONArray) jsonObject.get("users");
                Iterator<JSONObject> iterator = jsonArray.iterator();
                while(iterator.hasNext())
                {
                    if(!iterator.next().get("randomCode").equals(id))
                    {
                        ok=true;
                    }
                }

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(ok==false)
            {
                AlertBox.display("Your ID doesn't match with any doctor's ID!");
                return false;
            }
        }
        //validare adresa
        if(address.equals(null) || address.equals(""))
        {
            AlertBox.display("You must enter your address!");
            return false;
        }
        //validare username
        JSONParser jsonParser = new JSONParser();
        try
        {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("doctordb.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            Iterator<JSONObject> iterator = jsonArray.iterator();
            while(iterator.hasNext())
            {
                if(iterator.next().get("user").equals(username))
                {
                    AlertBox.display("Username already exists!");
                    return false;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //validare pass
        if(pass.equals(null) || pass.equals(""))
        {
            AlertBox.display("You must enter a password!");
            return false;
        }

        return true;
    }
    public static int docUniqueCode(int n)
    {
        int m = (int) Math.pow(10, n - 1);
        return m + new Random().nextInt(9 * m);
    }
}
