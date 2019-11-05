package WidgetApp.Buttons;

import Annotations.ConcreteProduct;

/**
 * All product families have the same varieties (MacOS/Windows). First variant of button.
 */
@ConcreteProduct
public class MacOSButton implements Button {
    @Override
    public void paint() {
        System.out.println("You have created a MacOS Button");
    }
}
