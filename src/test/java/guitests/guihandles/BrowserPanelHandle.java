package guitests.guihandles;

import org.teamstbf.yats.TestApp;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handler for the BrowserPanel of the UI
 */
public class BrowserPanelHandle extends GuiHandle {

    private static final String BROWSER_ID = "#browser";

    public BrowserPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Clicks on the WebView.
     */
    public void clickOnWebView() {
        guiRobot.clickOn(BROWSER_ID);
    }

}
