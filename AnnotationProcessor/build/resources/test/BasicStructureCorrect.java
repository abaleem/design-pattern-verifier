import Annotations.AbstractFactory;
import Annotations.AbstractProduct;
import Annotations.ConcreteFactory;
import Annotations.ConcreteProduct;

@AbstractFactory
interface abstractFactory{
}

@ConcreteFactory
class factory1 implements abstractFactory{

}

@AbstractProduct
interface abstractProduct{

}

@ConcreteProduct
class product1 implements abstractProduct{

}
