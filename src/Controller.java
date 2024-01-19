/**
 * Sample Skeleton for 'mainScene.fxml' Controller Class
 */

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;
// import javafx.beans.binding.Bindings;

public class Controller {

    private Stage mainWindow;
    private double betAmount;
    private boolean cashedOut;
    private Double cashoutNum; 

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }


    @FXML // fx:id="roundOverLabel"
    private Label roundOverLabel; 

    @FXML // fx:id="payoutLabel"
    private Label payoutLabel; 

    @FXML
    private TextField amountTextField;
    @FXML 
    private Label userBalanceLabel;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // fx:id="betBtn"
    private Button betBtn; // Value injected by FXMLLoader

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private Label multiplierLabel;

    @FXML
    private Button startCrashBtn;

    @FXML 
    private Label betAmountLabel;// Value injected
   

    @FXML
    private Button depositBtn;

    @FXML
    private TextField depositTextField;


    @FXML
    void exit(ActionEvent event) {
        if (mainWindow != null) {
            // Close the main stage
            mainWindow.close();
        }
    }
 // the main method of this 
   @FXML
void runCrash(ActionEvent event) {
    // set pre crash stuff
    double crashNum = crashGame.generateRandomMultiplier();
    cashedOut = false;
    cashoutNum = 0.0;
    multiplierLabel.setStyle("-fx-text-fill: WHITE;");

    // set our text
    payoutLabel.setText(""); 
    multiplierLabel.setText("");
    amountTextField.setText("");
    roundOverLabel.setText("");

    // set the multiplier and label back 
    roundOverLabel.setText("Current Payout");
    payoutLabel.setStyle("-fx-text-fill: #73cd7a;");

    // Create a Timeline for the original animation
    Timeline originalTimeline = new Timeline();

     // update the labels with new bet values 
     crashGame.subtractBalance(betAmount);
     updateUI();
     // make the bet not clickable and box not typable 
     betBtn.setDisable(true);
     amountTextField.setEditable(false);

    double increment = 0.01; // Adjust the increment as needed
    for (double value = 1.00; value <= crashNum; value += increment) {
        final double currentNum = value;
        double x = Math.sqrt((value - 1) * 100);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(x),
                animationEvent -> {  // Rename the parameter to avoid conflict
                    // move the value of multiplier 
                    multiplierLabel.setText(String.format("%.2f", currentNum) + "x");
                    
                    // move value of potential payout
                    payoutLabel.setText("+ $" + String.format("%.2f", betAmount * currentNum));

                    startCrashBtn.setText("CRASH");
                    startCrashBtn.setStyle("-fx-background-color: #38FF00");

                    // the button for crashing 
                    startCrashBtn.setOnAction(crashEvent -> performCrash(currentNum));
                    if(cashedOut) {
                        // make button not clickable
                        startCrashBtn.setDisable(true);
                        roundOverLabel.setText("YOU WON!");
                        
                        payoutLabel.setText("+ $" + String.format("%.2f", cashoutNum));
                    }
                    else if(currentNum > crashNum - 0.02) {
                        // Make button not clickable
                        startCrashBtn.setDisable(true);

                        payoutLabel.setText("+ $0.00");
                        payoutLabel.setStyle("-fx-text-fill: #d03838;");
                        roundOverLabel.setText("Round Over");
                        multiplierLabel.setStyle("-fx-text-fill: #d03838;");
                    }
                    

                },
                new KeyValue(multiplierLabel.textProperty(), String.format("%.2f", value) + "x")
        );
        originalTimeline.getKeyFrames().add(keyFrame);
    }

    // Create a PauseTransition for the pause
    PauseTransition pause = new PauseTransition(Duration.millis(500));
    pause.setOnFinished(pauseEvent -> {  // Rename the parameter to avoid conflict
        // Code to execute after the pause

        // Create a new Timeline for the blinking effect
        
        Timeline blinkingTimeline = new Timeline();
        startCrashBtn.setDisable(true);


        // Add keyframes for blinking
        for (int i = 0; i < 6; i++) {
            // Toggle label visibility to simulate blinking
            KeyFrame blink = new KeyFrame(Duration.seconds(0.3 * i),
                    blinkEvent -> multiplierLabel.setVisible(!multiplierLabel.isVisible())
            );
            blinkingTimeline.getKeyFrames().add(blink);
        }

        // Set an action for when the blinking effect finishes
        blinkingTimeline.setOnFinished(blinkingFinishedEvent -> {
            // rest crash button text 
            startCrashBtn.setText("Start Crash");
            startCrashBtn.setStyle("-fx-background-color: #f09609;");
            startCrashBtn.setDisable(false);
            multiplierLabel.setVisible(true); // Ensure the label is visible after blinking
            //rest bet btn 
            betBtn.setDisable(false);
            amountTextField.setEditable(true);
            // after the round set it to round over and set multipler color to red
            startCrashBtn.setOnAction(crashEvent -> runCrash(null));
            
            
            // reset the bet ammount 
            betAmount = 0.0;
            updateUI();
        });

        // Play the blinking timeline
        blinkingTimeline.play();
    });

    // Play the original timeline and then the pause
    originalTimeline.setOnFinished(originalFinishedEvent -> pause.play());
    originalTimeline.play();
}

    // Function to perform crash
    private void performCrash(double current) {
        // Add your crash functionality here
        System.out.println("Performing Crash!");
        // set calculate how much they won 
        cashoutNum = current * betAmount;
        cashedOut = true; 
        userBalance += cashoutNum;
        updateUI();
    }

    @FXML
    void placeBet(ActionEvent event) {
         // Get the entered amount from the TextField
    String amountText = amountTextField.getText();

    // Validate that the entered amount is a valid double value
    try {
        betAmount = Double.parseDouble(amountText);

        // update our labels 
        updateUI(); 

        // Clear the TextField after placing the bet
        amountTextField.clear();
    } catch (NumberFormatException e) {
        // Handle the case where the entered amount is not a valid double
        System.out.println("Invalid amount entered. Please enter a valid number.");
    }
    }

    private void updateUI() {
        betAmountLabel.setText(String.format("Bet Amount: $%.2f", betAmount));
        userBalanceLabel.setText(String.format("Balance $%.2f", userBalance));
        // for reseting before the round 
        
    }

    
    
    @FXML
    void depositScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepositScreen.fxml"));
            DialogPane depositDialogPane = loader.load();

            Dialog<Void> depositDialog = new Dialog<>();
            depositDialog.setDialogPane(depositDialogPane);
            depositDialog.initOwner(((Stage) depositBtn.getScene().getWindow()));  // Set the owner stage

            depositDialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addFunds(ActionEvent event) {
        String fundsText = depositTextField.getText();

        // Validate that the entered amount is a valid double value
        try {
            userBalance += Double.parseDouble(fundsText);
            System.out.println(userBalance);
    
            // update our labels 
            //updateUI(); 
    
            // Clear the TextField after placing the bet
            depositTextField.clear();
        } catch (NumberFormatException e) {
            // Handle the case where the entered amount is not a valid double
            System.out.println("Invalid amount entered. Please enter a valid number.");
        }
    }



    @FXML // fx:id="exitBtn"
    private Button exitBtn; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {        
        userBalance = 0.0;
        betAmount = 0.0; 
    }
}