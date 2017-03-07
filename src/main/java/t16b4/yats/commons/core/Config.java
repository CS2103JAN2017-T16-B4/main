package t16b4.yats.commons.core;

import java.util.Objects;
import java.util.logging.Level;

/**
 * Config values used by the app
 */
public class Config {

    public static final String DEFAULT_CONFIG_FILE = "config.json";

    // Config values customizable through config file
    private String appTitle = "Yet Another Task Scheduler";
    private Level logLevel = Level.INFO;
    private String userPrefsFilePath = "preferences.json";
    private String itemListFilePath = "data/itemlist.xml";
    private String itemListName = "MyItemList";


    public String getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(String appTitle) {
        this.appTitle = appTitle;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public String getUserPrefsFilePath() {
        return userPrefsFilePath;
    }

    public void setUserPrefsFilePath(String userPrefsFilePath) {
        this.userPrefsFilePath = userPrefsFilePath;
    }

    public String getTaskListFilePath() {
        return itemListFilePath;
    }

    public void setTaskListFilePath(String addressBookFilePath) {
        this.itemListFilePath = addressBookFilePath;
    }

    public String getTaskListName() {
        return itemListName;
    }

    public void setTaskListName(String addressBookName) {
        this.itemListName = addressBookName;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Config)) { //this handles null as well.
            return false;
        }

        Config o = (Config) other;

        return Objects.equals(appTitle, o.appTitle)
                && Objects.equals(logLevel, o.logLevel)
                && Objects.equals(userPrefsFilePath, o.userPrefsFilePath)
                && Objects.equals(itemListFilePath, o.itemListFilePath)
                && Objects.equals(itemListName, o.itemListName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appTitle, logLevel, userPrefsFilePath, itemListFilePath, itemListName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("App title : " + appTitle);
        sb.append("\nCurrent log level : " + logLevel);
        sb.append("\nPreference file Location : " + userPrefsFilePath);
        sb.append("\nLocal data file location : " + itemListFilePath);
        sb.append("\nItemList name : " + itemListName);
        return sb.toString();
    }

}