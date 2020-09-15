package util;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

public class BreakoutApplicationTest extends ApplicationTest {

    protected void click (Scene scene, int x, int y) {
        scene.getOnMouseClicked().handle(new MouseEvent(MouseEvent.MOUSE_CLICKED, x, y, x, y, MouseButton.PRIMARY, 1,
                false, false, false, false, true, false,
                false, true, false, false, null));
    }

    protected void press (Scene scene, KeyCode key) {
        javafxRun(() -> scene.getOnKeyPressed().handle(new KeyEvent(KeyEvent.KEY_PRESSED, key.getChar(), key.getName(), key,
                false, false, false, false)));
        // other possibilities:
        //scene.processKeyEvent(new KeyEvent(KeyEvent.KEY_PRESSED, key.getChar(), key.getName(), key, false, false, false, false));
        //simulateAction(n, () -> Event.fireEvent(scene, new KeyEvent(KeyEvent.KEY_PRESSED, key.getChar(), key.getName(), key, false, false, false, false)));
    }

    private void javafxRun (Runnable action) {
        // fire event using given action on the given node
        Platform.runLater(action);
        // make it "later" so the requested event has time to run
        WaitForAsyncUtils.waitForFxEvents();
    }

}
