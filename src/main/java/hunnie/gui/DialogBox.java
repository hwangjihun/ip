package hunnie.gui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * A dialog box consisting of an ImageView to represent the speaker and a label containing text.
 */
public class DialogBox extends HBox {
    private static final double AVATAR_SIZE = 50;
    private static final String DIALOG_ROW_STYLE_CLASS = "dialog-row";
    private static final String USER_ROW_STYLE_CLASS = "user-row";
    private static final String BOT_ROW_STYLE_CLASS = "bot-row";
    private static final String AVATAR_STYLE_CLASS = "avatar-image";

    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        FXMLLoader fxmlLoader = new FXMLLoader(DialogBox.class.getResource("/view/DialogBox.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        applyAvatarStyle();
        dialog.setMaxWidth(280);
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        this.getStyleClass().add(DIALOG_ROW_STYLE_CLASS);
        dialog.getStyleClass().add("dialog-text");
        displayPicture.getStyleClass().add(AVATAR_STYLE_CLASS);
    }

    /**
     * Creates a dialog box for the user's input.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox box = new DialogBox(text, img);
        box.dialog.getStyleClass().add("user-dialog");
        box.getStyleClass().add(USER_ROW_STYLE_CLASS);
        box.setAlignment(Pos.TOP_RIGHT);
        return box;
    }

    /**
     * Creates a dialog box for the bot's response.
     */
    public static DialogBox getHunnieDialog(String text, Image img) {
        DialogBox box = new DialogBox(text, img);
        box.dialog.getStyleClass().add("bot-dialog");
        box.getStyleClass().add(BOT_ROW_STYLE_CLASS);
        box.flip();
        return box;
    }

    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(children);
        this.getChildren().setAll(children);
        this.setAlignment(Pos.TOP_LEFT);
    }

    private void applyAvatarStyle() {
        displayPicture.setFitWidth(AVATAR_SIZE);
        displayPicture.setFitHeight(AVATAR_SIZE);
        displayPicture.setPreserveRatio(false);

        Image image = displayPicture.getImage();
        if (image != null && image.getWidth() > 0 && image.getHeight() > 0) {
            double size = Math.min(image.getWidth(), image.getHeight());
            double x = (image.getWidth() - size) / 2;
            double y = (image.getHeight() - size) / 2;
            displayPicture.setViewport(new Rectangle2D(x, y, size, size));
        }

        Circle clip = new Circle(AVATAR_SIZE / 2, AVATAR_SIZE / 2, AVATAR_SIZE / 2);
        displayPicture.setClip(clip);
    }
}
