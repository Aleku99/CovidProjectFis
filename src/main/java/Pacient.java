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
import sun.security.krb5.internal.crypto.Aes128;

import javax.xml.soap.Text;

public class Pacient {
    public static Scene pacientStartScene(String name)
    {

        Label titleLabel = new Label("Welcome " + name);
        Button logoutButton = new Button();
        Button writeSymptomsButton = new Button();
        Button checkTreatmentButton = new Button();

        logoutButton.setText("Logout");
        writeSymptomsButton.setText("Write symptoms");
        checkTreatmentButton.setText("Check treatment");

        logoutButton.setOnAction(e->{Main.window.setScene(User.login());});
        writeSymptomsButton.setOnAction(e->{});
        checkTreatmentButton.setOnAction(e->{});

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

}
