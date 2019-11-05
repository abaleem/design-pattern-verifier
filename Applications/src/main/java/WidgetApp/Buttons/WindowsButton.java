package WidgetApp.Buttons;

import Annotations.ConcreteProduct;

/**
 * Second variant of button.
 */
@ConcreteProduct
public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("You have created a Windows Button");
    }
}
