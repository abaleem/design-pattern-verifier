package WidgetApp.Factories;

import Annotations.AbstractFactory;
import WidgetApp.Buttons.Button;
import WidgetApp.Checkboxes.Checkbox;

/**
 *  Abstract factory knows about all abstract products types
 */
@AbstractFactory
public interface GUIFactory {

    Button createButton();
    Checkbox createCheckbox();
}
