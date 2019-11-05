package AnnotationProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.*;




public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        // logger
        final Logger LOG_TAG = LoggerFactory.getLogger(AnnotationProcessor.class);

        if(!roundEnv.processingOver()){
            List<Element> absFactories = new ArrayList<>(roundEnv.getElementsAnnotatedWith(Annotations.AbstractFactory.class));
            List<Element> concFactories = new ArrayList<>(roundEnv.getElementsAnnotatedWith(Annotations.ConcreteFactory.class));
            List<Element> absProducts = new ArrayList<>(roundEnv.getElementsAnnotatedWith(Annotations.AbstractProduct.class));
            List<Element> concProducts = new ArrayList<>(roundEnv.getElementsAnnotatedWith(Annotations.ConcreteProduct.class));
            List<Element> clients = new ArrayList<>(roundEnv.getElementsAnnotatedWith(Annotations.ClientAnnotation.class));


            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Starting Annotation Processor");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Checking if all essential elements available");
            LOG_TAG.info("Starting Annotation Processor");
            LOG_TAG.info("Checking if all essential elements available");

            // Checking to see elements of abstract factory design pattern are created and annotated.
            if(absFactories.size() == 0){
                LOG_TAG.error("No Abstract Factory found. Make sure you annotated your Abstract Factory");
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Abstract factory not found");
            }
            if(concFactories.size() == 0){

                LOG_TAG.error("No Concrete Factories found. Make sure you annotated your Concrete Factories");
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete factories not found");
            }
            if(absProducts.size() == 0){
                LOG_TAG.error("No Abstract Products found. Make sure you annotated your Abstract Products");
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Abstract products not found");
            }
            if(concProducts.size() == 0){
                LOG_TAG.error("No Concrete Products found. Make sure you annotated your Concrete Products");
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete products not found");
            }
            if(clients.size() == 0){
                LOG_TAG.warn("No Client found");
                //processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "No Concrete Products found.");
            }


            // Clients Checks
            for(Element client:  clients){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Checking client: " + client);
                LOG_TAG.info("Checking client: " + client);

                // Creating a list of all fields, constructor of client
                List<VariableElement> clientFields = new ArrayList<>();
                List<ExecutableElement> clientConstructors = new ArrayList<>();

                for (Element clientElement: client.getEnclosedElements()){
                    if(clientElement.getKind().equals(ElementKind.FIELD)){
                        clientFields.add((VariableElement) clientElement);
                    }
                    else if (clientElement.getKind().equals(ElementKind.CONSTRUCTOR)){
                        clientConstructors.add((ExecutableElement) clientElement);
                    }
                }

                // Checking that no variable type is a concrete product or factory
                for(VariableElement clientField:  clientFields){
                    if(Arrays.toString(concProducts.toArray()).contains(clientField.asType().toString())){
                        LOG_TAG.error("Client: " + client + "'s field " + clientField.getSimpleName() + " has concrete product type");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Client fields have concrete element");
                    }
                    if(Arrays.toString(concFactories.toArray()).contains(clientField.asType().toString())){
                        LOG_TAG.error("Client: " + client + "'s field " + clientField.getSimpleName() + " has concrete factory type");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Client fields have concrete element");
                    }
                }

                // Checking to make sure constructor doesnt take any concrete factory or product as argument
                for(ExecutableElement clientConstructor: clientConstructors){
                    for(Element constructorParam: clientConstructor.getParameters()){
                        if(Arrays.toString(concProducts.toArray()).contains(constructorParam.asType().toString())){
                            LOG_TAG.error("Client: " + client + "'s constructor " + clientConstructor.getSimpleName() + " parameter has concrete product type");
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Client constructor has concrete elements");
                        }
                        if(Arrays.toString(concFactories.toArray()).contains(constructorParam.asType().toString())){
                            LOG_TAG.error("Client: " + client + "'s constructor " + clientConstructor.getSimpleName() + " parameter has concrete factory type");
                            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Client constructor has concrete elements");
                        }
                    }
                }
            }


            // Abstract Factory Checks
            for (Element absFactory: absFactories){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Checking abstract factory: " + absFactory);
                LOG_TAG.info("Checking abstract factory: " + absFactory);

                // Checking if the factory is implemented as interface.
                if(!absFactory.getKind().isInterface()) {
                    LOG_TAG.error("Abstract Factory: " + absFactory + " not implemented as interface");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Abstract factory not implemented as interface");
                }

                // Creating a list of all methods in the abstract factory
                List<ExecutableElement> absFactoryMethods = new ArrayList<>();
                for (Element absFactoryElement: absFactory.getEnclosedElements()){
                    if(absFactoryElement.getKind().equals(ElementKind.METHOD))
                        absFactoryMethods.add((ExecutableElement) absFactoryElement);
                }

                // Checking to make sure no method returns a concrete product
                for (ExecutableElement absFactoryMethod: absFactoryMethods){
                    if(Arrays.toString(concProducts.toArray()).contains(absFactoryMethod.getReturnType().toString())){
                        LOG_TAG.error("Abstract Factory: " + absFactory + "'s method " + absFactoryMethod.getSimpleName() + " returns concrete element");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Abstract factory method returns concrete element");
                    }
                }
            }


            // Concrete Factory Checks
            for (Element concFactory: concFactories){
                LOG_TAG.info("Checking concrete factory: " + concFactory);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Checking concrete factory: " + concFactory);


                // Checks if concrete factories are in fact class.
                if(!concFactory.getKind().isClass()){
                    LOG_TAG.error("Concrete factory " + concFactory + " is not a class");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete factory is not a class");
                }

                // Checks that concrete factories are not abstract
                if(concFactory.getModifiers().contains(Modifier.ABSTRACT)){
                    LOG_TAG.error("Concrete factory " + concFactory + " is abstract");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete factory is abstract");
                }

                // Checks to see in concrete factory implement any interface
                if(((TypeElement) concFactory).getInterfaces().size()==0){
                    LOG_TAG.error("Concrete factory " + concFactory.toString() + " doesn't implement any interface");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete factory does not implement any interface");
                }

                // Checks if concrete factory implements abstract factory interface
                for (TypeMirror interface0: ((TypeElement) concFactory).getInterfaces() ){
                    if(!Arrays.toString(absFactories.toArray()).contains(interface0.toString())){
                        LOG_TAG.error("Concrete factory " + concFactory.toString() + " doesn't implement abstract interfaces");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete factory does not implement abstract interfaces");
                    }
                }

                // creates a list of all methods in the concrete factory
                List<ExecutableElement> concFactoryMethods = new ArrayList<>();
                for (Element concFactoryElement: concFactory.getEnclosedElements()){
                    if(concFactoryElement.getKind().equals(ElementKind.METHOD)){
                        concFactoryMethods.add((ExecutableElement) concFactoryElement);
                    }
                }

                // Checking to make sure no method returns a concrete product
                for (ExecutableElement concFactoryMethod: concFactoryMethods){
                    if(Arrays.toString(concProducts.toArray()).contains(concFactoryMethod.getReturnType().toString())){
                        LOG_TAG.error("Concrete Factory: " + concFactory + "'s method " + concFactoryMethod.getSimpleName() + " returns concrete class");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete factory's method returns concrete class");
                    }
                }
            }


            // Abstract Product Checks
            for(Element absProduct: absProducts){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Checking abstract product: " + absProduct);
                LOG_TAG.info("Checking abstract product: " + absProduct);

                // Checking everything labeled as abstract product is an interface
                if(!absProduct.getKind().isInterface()) {
                    LOG_TAG.error("Abstract product " + absProduct + " is not an interface");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Abstract product is not an interface");
                }

                // Getting concrete implementations of abstract products
                List<Element> concImplementations = new ArrayList<>();
                for (Element concProduct: concProducts){
                    List<? extends TypeMirror> interfaces  = ((TypeElement)concProduct).getInterfaces();
                    if(interfaces.toString().contains(absProduct.toString())){
                        concImplementations.add(concProduct);
                    }
                }

                // Checking if each product is implemented in all given variants. Must be more than no. of factories
                int totalVariants = concFactories.size();
                if(concImplementations.size() < totalVariants){
                    LOG_TAG.error("Concrete Implementations of " + absProduct + " are less than factory variants");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, "Concrete product implementations are less than factory variants");
                }
            }


            // Concrete Product Checks
            for(Element concProduct: concProducts){
                LOG_TAG.info("Checking concrete product: " + concProduct);
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Checking concrete product: " + concProduct);

                // Checks if concrete products are in fact class.
                if(!concProduct.getKind().isClass()){
                    LOG_TAG.error("Concrete Product " + concProduct + " is not a class");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete product is not a class");
                }

                // Checks that concrete products are not abstract
                if(concProduct.getModifiers().contains(Modifier.ABSTRACT)){
                    LOG_TAG.error("Concrete Product " + concProduct + " is abstract");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete product is abstract");
                }

                // Checks to see in it implements any interface
                if(((TypeElement) concProduct).getInterfaces().size()==0){
                    LOG_TAG.error("Concrete product " + concProduct + " doesn't implement any interface");
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete product does not implement any interface");
                }

                // Checks to see if concrete product implement abstract products interface
                for (TypeMirror interface0: ((TypeElement) concProduct).getInterfaces() ){
                    if(!Arrays.toString(absProducts.toArray()).contains(interface0.toString())){
                        LOG_TAG.error("Concrete product " + concProduct + " doesn't implement abstract interfaces");
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Concrete product does not implement any abstract interfaces");
                    }
                }
            }
            LOG_TAG.info("If you dont see any error above, your abstract factory design pattern is correct!");
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "If you dont see any error above, your abstract factory design pattern is correct!");

        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // defining all the annotations that this processor can process
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Annotations.AbstractFactory.class.getCanonicalName());
        annotations.add(Annotations.ConcreteFactory.class.getCanonicalName());
        annotations.add(Annotations.AbstractProduct.class.getCanonicalName());
        annotations.add(Annotations.ConcreteProduct.class.getCanonicalName());
        annotations.add(Annotations.ClientAnnotation.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
