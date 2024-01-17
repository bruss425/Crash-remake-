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
import javafx.beans.binding.Bindings;

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
        crashNum = 100;
    
        // Create a Timeline for the animation
        Timeline timeline = new Timeline();
    
        double increment = 0.01; // Adjust the increment as needed
        for (double value = 1.00; value <= crashNum; value += increment) {
            final double finalValue = value;
            // Use the equation y = (1/100)x^2 + 1 for the duration parabolic motion where:
            // X is our current value on the screen adn y is the time that has elasped in the animation 
            double x = Math.sqrt((value - 1) * 100);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(x),
                event2 -> {
                    // This code will be executed when the KeyFrame is reached
                    multiplierLabel.setText(String.format("%.2f", finalValue) + "x");
                    startCrashBtn.setText("Playing...");
                    startCrashBtn.setStyle("-fx-background-color: GREEN");
                },
                new KeyValue(multiplierLabel.textProperty(), String.format("%.2f", value) + "x")
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
        // figure this out eventually 
        // upon initilization have the balance be 0 
        // then set balance and let go 
        // if i want to be robust i can set a maximum balance as well as make sure they cannot bet more than they can lose
        // also could mess around and make the deposit screen pretty good with a few options and a text box 
    }


    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        this.multiplierLabel.setText("");
    }

}