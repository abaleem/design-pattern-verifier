import Annotations.AbstractFactory;
import Annotations.AbstractProduct;
import Annotations.ConcreteFactory;
import Annotations.ConcreteProduct;

@AbstractFactory
interface abstractFactory{
    abstractProduct doSomething();
}

@ConcreteFactory
class factory1 implements abstractFactory{
    @Override
    public abstractProduct doSomething() {
        return new product1();
    }
}

@AbstractProduct
interface abstractProduct{
    void doSomthingProduct();
}

@ConcreteProduct
class product1 implements abstractProduct{
    @Override
    public void doSomthingProduct() {
        System.out.println("doing something");
    }
}
