package linghe.rotbot.service;

import java.awt.*;

public class WindowService {

    public static void windowNotification(String message, TrayIcon.MessageType messageType) {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();
        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));
        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("机器人通知");
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        trayIcon.displayMessage(message, message, messageType);
    }

    public static void windowInfo(String message) {
        windowNotification(message, TrayIcon.MessageType.INFO);
    }

    public static void windowWarning(String message) {
        windowNotification(message, TrayIcon.MessageType.WARNING);
    }

    public static void windowError(String message) {
        windowNotification(message, TrayIcon.MessageType.ERROR);
    }
}
