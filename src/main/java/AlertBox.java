import com.sun.java.swing.plaf.windows.resources.windows;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class AlertBox {
    public static void display(String message)
    {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        window.setTitle("Message");

        Label label = new Label(message);
        Button button = new Button("Close the window");
        button.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
}
