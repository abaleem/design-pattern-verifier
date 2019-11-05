package WidgetApp.Factories;

import Annotations.ConcreteFactory;
import WidgetApp.Buttons.Button;
import WidgetApp.Buttons.WindowsButton;
import WidgetApp.Checkboxes.Checkbox;
import WidgetApp.Checkboxes.WindowsCheckbox;

/**
 * Each concrete factory implements basic factory and is responsible to create products of a single variety
 */
@ConcreteFactory
public class WindowsFactory implements GUIFactory {

    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}
