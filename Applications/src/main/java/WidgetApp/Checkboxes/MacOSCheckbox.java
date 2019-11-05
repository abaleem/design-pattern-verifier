package WidgetApp.Checkboxes;

import Annotations.ConcreteProduct;

/**
 * All product families have the same varieties (MacOS/Windows). First variant of checkbox.
 */
@ConcreteProduct
public class MacOSCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("You have created a MacOS Checkbox");
    }
}
