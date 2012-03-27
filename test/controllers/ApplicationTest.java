package controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;

public class ApplicationTest {
    @Before
    public void setUp() throws Exception {
        Fixtures.loadModels("data.yml");
    }

    @After
    public void tearDown() throws Exception {
        Fixtures.deleteAllModels();
    }

    @Test

    public void shouldTestIfTheUserIsNotConnected(){
        Application.checkSecure();

    }
}
