package models;

import org.junit.Test;

import play.data.validation.Validation;
import play.data.validation.Validation.ValidationResult;
import play.test.UnitTest;

public class UserTest extends UnitTest {

	@Test
	public void shouldProduceAnErrorMessageForANonWordUsername() {
		User user = givenAUserWithAnInvalidName();

		ValidationResult validationResult = Validation.current().valid(user);

		assertFalse(validationResult.ok);
		assertNotNull(Validation.errors(".username"));
		assertEquals("Not a valid username",Validation.errors(".username").get(0).message());
	}
	
	@Test 
	public void shouldProduceAnErrorMessageForAUsernameLongerThan15Chars(){
		User user = givenAUserWithALongCredentials();

		ValidationResult validationResult = Validation.current().valid(user);

		assertFalse(validationResult.ok);
		assertNotNull(Validation.errors(".username"));
		assertEquals("Maximum size is 15",Validation.errors(".username").get(0).message());
	}
	
	@Test 
	public void shouldProduceAnErrorMessageForAPasswordLongerThan15Chars(){
		User user = givenAUserWithALongCredentials();

		ValidationResult validationResult = Validation.current().valid(user);

		assertFalse(validationResult.ok);
		assertNotNull(Validation.errors(".password"));
		assertEquals("Maximum size is 15",Validation.errors(".password").get(0).message());
	}

	@Test 
	public void shouldProduceAnErrorMessageForAUsernameLessThan4Chars(){
		User user = givenAUserWithShortCredentials();

		ValidationResult validationResult = Validation.current().valid(user);

		assertFalse(validationResult.ok);
		assertNotNull(Validation.errors(".username"));
		assertEquals("Minimum size is 4",Validation.errors(".username").get(0).message());
	}
	
	@Test 
	public void shouldProduceAnErrorMessageForAPasswordLessThan5Chars(){
		User user = givenAUserWithShortCredentials();

		ValidationResult validationResult = Validation.current().valid(user);

		assertFalse(validationResult.ok);
		assertNotNull(Validation.errors(".password"));
		assertEquals("Minimum size is 5",Validation.errors(".password").get(0).message());
	}

	@Test
	public void shouldDetermineThatAUserIsAnAdmin(){ 
		User admin = givenAnAdminUser(); 
		assertTrue(admin.isAdmin());
	}
	
	@Test
	public void shouldDetermineThatAUserIsNotAnAdmin() {
		User nonAdmin = givenANonAdminUser();
		assertFalse(nonAdmin.isAdmin());
	}
	
	private User givenAUserWithShortCredentials() {
		return new User("name", "oop", "oop"); 
	}
	
	private User givenAUserWithALongCredentials() {
		return new User("name", "thisPasswordIsWayWayTooooooLong", "thisNameIsWayWayTooooooLong"); 
	}

	private User givenANonAdminUser() {
		return new User("name", "password", "non@admin.username"); 
	}

	private User givenAnAdminUser() {
		return new User("name", "password", "admin@acmecorp.com");
	}

	private User givenAUserWithAnInvalidName() {
		return new User("name", "password", "  123");
	}
}
