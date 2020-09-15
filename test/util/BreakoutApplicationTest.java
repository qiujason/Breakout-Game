package util;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ApplicationTest extends ApplicationTest {

    protected void click (Scene scene, int x, int y) {
        scene.getOnMouseClicked().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, MouseButton.PRIMARY, 1,
                false, false, false, false, true, false,
                false, true, false, false, null));
    }

}
