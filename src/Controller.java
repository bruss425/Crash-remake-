/**
 * Sample Skeleton for 'mainScene.fxml' Controller Class
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Controller {

    private Stage mainWindow;

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label multiplierLabel;

    @FXML
    private Button startCrashBtn;

    @FXML
    void exit(ActionEvent event) {
        if (mainWindow != null) {
            // Close the main stage
            mainWindow.close();
        }
    }

    @FXML
void runCrash(ActionEvent event) {
    double crashNum = crashGame.generateRandomMultiplier();

    // Create a Timeline for the animation
    Timeline timeline = new Timeline();

    // Add KeyFrames to gradually increase the multiplierLabel value
    double durationInSeconds = 2.0;
    double increment = 0.01;
    for (double value = 1.00; value <= crashNum; value += increment) {
        final double finalValue = value;
        KeyFrame keyFrame = new KeyFrame(
            Duration.seconds((finalValue - 1.00) * durationInSeconds / (crashNum - 1)),
            event2 -> {
                // This code will be executed when the KeyFrame is reached
                multiplierLabel.setText(String.format("%.2f", finalValue));
                startCrashBtn.setText("Playing...");
                startCrashBtn.setStyle("-fx-background-color: GREEN");
            },
            new KeyValue(multiplierLabel.textProperty(), String.format("%.2f", finalValue))
        );
        timeline.getKeyFrames().add(keyFrame);
    }

    // Set an action for when the animation finishes
    timeline.setOnFinished(event2 -> {
        startCrashBtn.setText("Start Crash");
        // Reset the background color of startCrashBtn
        startCrashBtn.setStyle("-fx-background-color: #f09609;");
    });

    // Play the Timeline
    timeline.play();
}

    
    @FXML
    void depositScreen(ActionEvent event) {

    }


    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        this.multiplierLabel.setText("");
    }

}