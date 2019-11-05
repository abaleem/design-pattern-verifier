package WidgetApp.Factories;

import Annotations.ClientAnnotation;
import Annotations.ConcreteFactory;
import Annotations.ConcreteProduct;
import WidgetApp.Buttons.Button;
import WidgetApp.Buttons.MacOSButton;
import WidgetApp.Checkboxes.Checkbox;
import WidgetApp.Checkboxes.MacOSCheckbox;
import WidgetApp.Checkboxes.WindowsCheckbox;


/**
 * Each concrete factory implements basic factory and is responsible to create products of a single variety
 */
@ConcreteFactory
public class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}
