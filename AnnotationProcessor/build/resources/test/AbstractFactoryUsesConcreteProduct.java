import Annotations.AbstractFactory;
import Annotations.AbstractProduct;
import Annotations.ConcreteFactory;
import Annotations.ConcreteProduct;

@AbstractFactory
interface abstractFactory{
    product1 someFunc();
}

@ConcreteFactory
class factory1 implements abstractFactory{
    @Override
    public product1 someFunc() {
        return new product1();
    }
}

@AbstractProduct
interface abstractProduct{
}

@ConcreteProduct
class product1 implements abstractProduct{
}
