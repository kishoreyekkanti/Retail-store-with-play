package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.math.BigDecimal;
import java.util.List;


public class ProductTest extends UnitTest{

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
}
