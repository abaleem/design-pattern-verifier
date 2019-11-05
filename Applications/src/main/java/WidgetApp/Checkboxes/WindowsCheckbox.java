package WidgetApp.Checkboxes;

import Annotations.ConcreteProduct;

/**
 * Second variant of button.
 */
@ConcreteProduct
public class WindowsCheckbox implements Checkbox{
    @Override
    public void paint() {
        System.out.println("You have created a Windows Checkbox");
    }
}
