package atri;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Atri atri;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.jpg"));
    private Image atriImage = new Image(this.getClass().getResourceAsStream("/images/atri.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Atri instance */
    public void setAtri(Atri d) {
        atri = d;
        String response = atri.getWelcome();
        dialogContainer.getChildren().addAll(
                DialogBox.getAtriDialog(response, atriImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Atri's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = atri.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getAtriDialog(response, atriImage)
        );
        userInput.clear();
    }
}
