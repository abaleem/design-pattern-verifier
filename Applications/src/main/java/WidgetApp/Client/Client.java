package WidgetApp.Client;

import Annotations.ClientAnnotation;
import WidgetApp.Buttons.Button;
import WidgetApp.Checkboxes.Checkbox;
import WidgetApp.Factories.GUIFactory;

/**
 * Factory users don't care which concrete factory they use.
 * They work with factories and products through abstract interfaces.
 */
@ClientAnnotation
public class Client {
    private Button button;
    private Checkbox checkbox;

    public Client(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paint() {
        button.paint();
        checkbox.paint();
    }
}