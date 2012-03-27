package controllers;

import models.User;
import play.data.validation.Valid;

public class Users extends Application{
    
	public static void index(){
        if(connectedUser() != null) {
            Products.index();
        }
        render();
	}
	
	public static void register() {
        render();
    }
    
    public static void saveUser(@Valid User user, String verifyPassword) {
        validation.required(verifyPassword);
        validation.equals(verifyPassword, user.password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@register", user, verifyPassword);
        }
        user.create();
        saveUserDetailsInSession(user);
        flash.success("Welcome, " + user.name);
        Products.index();
    }
    
    public static void login(String username, String password) {
        User user = User.find("byUsernameAndPassword", username, password).first();
        if(user != null) {
            saveUserDetailsInSession(user);
            flash.success(flashMessage(user));
            Products.index();
        }
        flash.put("username", username);
        flash.error("Login failed");
        index();
    }
    private static String flashMessage(User user) {
        StringBuffer flashMessage = new StringBuffer("Welcome, ");
        flashMessage.append(user.name);
        flashMessage.append(user.isAdmin()? ". You have logged in as Admin." : "");
        return flashMessage.toString();
    }
    public static void logout() {
        session.clear();
        index();
    }

    
    private static void saveUserDetailsInSession(User user){
        session.put("user", user.username);
        session.put("logged", user.id);
    }

}
