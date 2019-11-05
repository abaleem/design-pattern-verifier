package WidgetApp;

import WidgetApp.Client.Client;
import WidgetApp.Factories.GUIFactory;
import WidgetApp.Factories.MacOSFactory;
import WidgetApp.Factories.WindowsFactory;

public class WidgetApp {

    /**
     * Application creates the factory at run time depending on the configuration
     */
    private static Client configureApplication() {
        Client app;
        GUIFactory factory;
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("mac")) {
            factory = new MacOSFactory();
            app = new Client(factory);
        } else {
            factory = new WindowsFactory();
            app = new Client(factory);
        }
        return app;
    }

    public static void main(String[] args) {
        Client app = configureApplication();
        app.paint();
    }
}