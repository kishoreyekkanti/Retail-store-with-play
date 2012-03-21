package models;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.data.validation.*;
import play.data.validation.Error;
import play.test.Fixtures;
import play.test.UnitTest;

import static play.data.validation.Validation.ValidationResult;

public class ProductOrderTest extends UnitTest {
    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("/data.yml");
    }
    @After
    public void tearDown(){
        Fixtures.deleteAllModels();
        Fixtures.deleteDatabase();

    }
    
    @Test
    public void shouldRaiseAnErrorIfCreditCardNumberIsBlank(){
        User user = User.find("username ='demo'").first();
        Product product = ProductOrder.find("product.name= 'Red Widget'").first();
        ProductOrder productOrder = new ProductOrder(product,user);
        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCard"));
        Error error = Validation.errors(".creditCard").get(0);
        assertEquals("Credit card number is required",error.message());
    }
}
