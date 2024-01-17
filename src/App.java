import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage primaryStage;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            // Create an FXMLLoader instance to load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));

            // Load the FXML file and get the root element of the scene graph
            Parent root = fxmlLoader.load();

            // Get the controller instance associated with the FXML file
            Controller controller = fxmlLoader.getController();

            // Set the main stage for the controller to manage
            controller.setMainWindow(primaryStage);

            // Create a new scene with the loaded root element
            Scene scene = new Scene(root);
           // scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            // Set the title of the main stage
            primaryStage.setTitle("Crash");

            // Set the scene for the main stage
            primaryStage.setScene(scene);

            // Display the main stage
            primaryStage.show();
        } catch (Exception e) {
            // Print the stack trace if an exception occurs during the process
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}

