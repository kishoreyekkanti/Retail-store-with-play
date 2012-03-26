package models;

import java.math.BigDecimal;
import java.util.List;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.data.validation.Validation;
import play.data.validation.Validation.ValidationResult;
import play.test.Fixtures;
import play.test.UnitTest;


public class ProductTest extends UnitTest{
	
    @Before
    public void setUp() {
        Fixtures.deleteAllModels();
        Fixtures.loadModels("/data.yml");
        Validation.clear();
    }
    
    @After
    public void tearDown(){
        Fixtures.deleteAllModels();
        Fixtures.deleteDatabase();

    }
	
	@Test
	public void shouldHaveADescription() {
		Product product = new Product("name", new BigDecimal("23.555"));
		Assert.assertEquals("name, 23.555", product.getDescription());
	}
	
    @Test
    public void shouldShowAnErrorMessageIfThereIsNoName() {
   	    Product product = givenAProductWithNoName();
        
        ValidationResult validationResult = Validation.current().valid(product);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".name"));
        assertEquals("Required",Validation.errors(".name").get(0).message());
    }
    
    @Test
    public void shouldShowAnErrorIfTheNameIsTooLong() {
   	    Product product = givenAProductWithALongName();
        
        ValidationResult validationResult = Validation.current().valid(product);
        assertFalse(validationResult.ok);
        assertNotNull(Validation.errors(".name"));
        assertEquals("Maximum size is 50",Validation.errors(".name").get(0).message());
    }

    @Test
    public void shouldReturnListOfProductsForTheGivenSizeAndPage(){
    	List<Product> products = Product.list("", 3, 2);
    	assertEquals(3, products.size());
    	assertEquals("Oldspice after shave", products.get(0).getName());
    }
    
    @Test
    public void shouldFetchResultsFromPageToOneIfPageNumberIsNotSet(){
    	List<Product> products = Product.list("", 3, null);
    	assertEquals(3, products.size());
    	assertEquals("Red Widget", products.get(0).getName());
    }
    
    @Test
    public void shouldSearchForTheGivenTerm(){
    	List<Product> products = Product.list("iphone", 3, 1);
    	assertEquals(1, products.size());
    	assertEquals("iphone 4S", products.get(0).getName());
    	
    }
    
	private Product givenAProductWithALongName() {
		return new Product("This is surely longer than 50 chars, deee dah dum dee dah dum", new BigDecimal("23.555"));
	}

	private Product givenAProductWithNoName() {
		return new Product("", new BigDecimal("23.555"));
	}
    
}
