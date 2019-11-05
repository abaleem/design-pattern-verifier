package WidgetApp.Buttons;


import Annotations.AbstractProduct;

/**
 * Abstract factory assumes you have several families of products structured into separate class hierarchies (Button/Checkbox).
 * All products of the same family have the common interface.
 */
@AbstractProduct()
public interface Button {
    void paint();
}
