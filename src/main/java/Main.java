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
import jdk.nashorn.internal.runtime.JSONFunctions;


public class Main extends Application {


    static Stage window;
    static Scene appOpeningScene;

    public static void main(String args[])
    {
       launch(args);
    }
    public void start(Stage primaryStage) throws Exception
    {

        window = primaryStage;
        window.setTitle("Covid-19 Pacients' Management");

        Label title = new Label("Covid-19 Pacients' Management");

        Button registerButton = new Button();
        Button loginButton = new Button();
        registerButton.setText("Register");
        loginButton.setText("Log in");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20,20,20,20));
        grid.setVgap(10);
        grid.setAlignment(Pos.TOP_CENTER);
        GridPane.setConstraints(title, 1,0);
        GridPane.setConstraints(registerButton, 1,1);
        GridPane.setConstraints(loginButton, 1,2);
        grid.getChildren().addAll(title, registerButton, loginButton);

        appOpeningScene = new Scene(grid, 400, 600);
        window.setScene(appOpeningScene);

        registerButton.setOnAction(e->window.setScene(User.roleSelect()));
        window.show();
    }
}
