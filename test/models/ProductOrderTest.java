package models;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import play.data.validation.Validation;
import play.data.validation.Validation.ValidationResult;
import play.test.UnitTest;

public class ProductOrderTest extends UnitTest {
	
    private User user;
	private Product product;

	@Before
    public void setUp() {
        user = new User("name", "password", "username");
        product = new Product("Thing", new BigDecimal("19.95"));
        Validation.clear();
    }
    
    @Test
    public void shouldShowAnErrorMessageIfTheCreditCardNumberIsBlank(){
    	ProductOrder productOrder = givenAProductOrderWithoutACreditCard();

        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCard"));
        assertEquals("Credit card number is required",Validation.errors(".creditCard").get(0).message());
    }
    
    @Test
    public void shouldShowAnErrorMessageIfCreditCardNumberIsNotOnlyDigits() {
   	    ProductOrder productOrder = givenAProductOrderWithAnInvalidCreditCard();
        
        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCard"));
        assertEquals("Credit card number must be numeric and 16 digits long",Validation.errors(".creditCard").get(0).message());
    }
    
    @Test
    public void shouldShowAnErrorMessageIfCreditCardNumberIsNot16Digits() {
   	    ProductOrder productOrder = givenAProductOrderWithCreditCardWithAShortNumber();
        
        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCard"));
        assertEquals("Credit card number must be numeric and 16 digits long",Validation.errors(".creditCard").get(0).message());
    }
    
    @Test
    public void shouldShowAnErrorMessageIfTheCardNameDoesNotExist() {
   	    ProductOrder productOrder = givenAProductOrderWithoutACardName();
        
        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCardName"));
        assertEquals("Credit card name is required",Validation.errors(".creditCardName").get(0).message());
    }
    
    @Test
    public void shouldShowAnErrorMessageIfTheCardNameIsTooShort() {
   	    ProductOrder productOrder = givenAProductOrderWithAShortCardName();
        
        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCardName"));
        assertEquals("Credit card name is required",Validation.errors(".creditCardName").get(0).message());
    }
    
    @Test
    public void shouldShowAnErrorMessageIfTheCardNameIsTooLong() {
   	    ProductOrder productOrder = givenAProductOrderWithALongCardName();
        
        ValidationResult validationResult = Validation.current().valid(productOrder);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".creditCardName"));
        assertEquals("Credit card name is required",Validation.errors(".creditCardName").get(0).message());
    }
    
    private ProductOrder givenAProductOrderWithALongCardName() {
    	return new ProductOrder(product,user, "0123456789123456", "This name is really quite a long name,"+
    															  " will be more than 70 chars, la de dah de dah");
	}

	private ProductOrder givenAProductOrderWithAShortCardName() {
    	return new ProductOrder(product,user, "0123456789123456", "a");
	}

	private ProductOrder givenAProductOrderWithoutACardName() {
    	return new ProductOrder(product,user, "0123456789123456", "");
	}

	private ProductOrder givenAProductOrderWithCreditCardWithAShortNumber() {
    	return new ProductOrder(product,user, "012345", "card name");
	}

	private ProductOrder givenAProductOrderWithAnInvalidCreditCard() {
        return new ProductOrder(product,user, "012345abcd12345abcd", "card name");
	}

	private ProductOrder givenAProductOrderWithoutACreditCard(){
        return new ProductOrder(product,user, "", "card name");
    }
}
