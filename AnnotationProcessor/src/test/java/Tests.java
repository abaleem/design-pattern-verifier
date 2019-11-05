import AnnotationProcessor.AnnotationProcessor;
import com.google.testing.compile.JavaFileObjects;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.ls.LSOutput;

import javax.tools.JavaFileObject;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;
import static org.truth0.Truth.ASSERT;

/**
 * Please refer to files in resources with similar name to see implementation.
 */
public class Tests {

    final Logger LOG_TAG = LoggerFactory.getLogger(AnnotationProcessor.class);

    // Checks if you wrongly annotate or define core elements.
    @Test
    public void AbstractFactoryNotInterface()
    {
        LOG_TAG.info("Running Test: AbstractFactoryNotInterface");
        JavaFileObject o = JavaFileObjects.forResource("AbstractFactoryNotInterface.java");
        ASSERT.about(javaSource())
                .that(o)
                .processedWith(new AnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("Abstract factory not implemented as interface");
    }


    // Complies with error "Concrete factories not found" as none defined in its resource file
    @Test
    public void ConcreteImplementationMissing()
    {
        LOG_TAG.info("Running Test: ConcreteImplementationMissing");
        JavaFileObject o = JavaFileObjects.forResource("ConcreteImplementationMissing.java");
        ASSERT.about(javaSource())
                .that(o)
                .processedWith(new AnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("Concrete factories not found");
    }



    // Concrete elements are created but doesn't extend abstract interfaces
    @Test
    public void ConcreteDoesNotExtendAbstract()
    {
        LOG_TAG.info("Running Test: ConcreteDoesNotExtendAbstract");
        JavaFileObject o = JavaFileObjects.forResource("ConcreteDoesNotExtendAbstract.java");
        ASSERT.about(javaSource())
                .that(o)
                .processedWith(new AnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("Concrete factory does not implement any interface");
    }


    // Abstract factories are not allowed to use concrete products. I defined a function return type to be concrete in resource file.
    // Hence it complies with that error.
    @Test
    public void AbstractFactoryUsesConcreteProduct()
    {
        LOG_TAG.info("Running Test: AbstractFactoryUsesConcreteProduct");
        JavaFileObject o = JavaFileObjects.forResource("AbstractFactoryUsesConcreteProduct.java");
        ASSERT.about(javaSource())
                .that(o)
                .processedWith(new AnnotationProcessor())
                .failsToCompile()
                .withErrorContaining("Abstract factory method returns concrete element");
    }

    // All interfaces and classes correctly defined. Hence should compile without error!
    @Test
    public void BasicStructureCorrect()
    {
        LOG_TAG.info("Running Test: BasicStructureCorrect");
        JavaFileObject o = JavaFileObjects.forResource("BasicStructureCorrect.java");
        ASSERT.about(javaSource())
                .that(o)
                .processedWith(new AnnotationProcessor())
                .compilesWithoutError();
    }

    // Simple implementation with core structure and some functionality correctly implemented.
    @Test
    public void SimpleImplementation()
    {
        LOG_TAG.info("Running Test: SimpleImplementation");
        JavaFileObject o = JavaFileObjects.forResource("SimpleImplementation.java");
        ASSERT.about(javaSource())
                .that(o)
                .processedWith(new AnnotationProcessor())
                .compilesWithoutError();
    }

}
