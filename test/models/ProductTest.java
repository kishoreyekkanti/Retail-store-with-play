package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.math.BigDecimal;


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

}
