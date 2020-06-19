import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Doctor {
    public static String doctorName="";
    public static Scene doctorStartScene(String name)
    {
        doctorName = name;
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
        //setStatusButton.setOnAction(e->{Main.window.setScene(setStatusScene());});
        //callAmbulanceButton.setOnAction(e->{callAmbulance();});
        //seeSymptomsButton.setOnAction(e->{seeSymptoms();});


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
}
