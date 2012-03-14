package controllers;

import play.mvc.*;
import play.data.validation.*;

import models.*;

public class Application extends Controller {
    
    @Before
    static void addUser() {
        User user = connected();
        if(user != null) {
            renderArgs.put("user", user);
        }
    }
    @Before
    static void checkSecure() {
        Secure secure = getActionAnnotation(Secure.class);
        if (secure != null) {
            if (connectedUser() == null || (secure.admin() && !connectedUser().isAdmin())) {
                forbidden();
            }
        }
    }
    static User connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String username = session.get("user");
        if(username != null) {
            return User.find("byUsername", username).first();
        } 
        return null;
    }
    
    // ~~

    public static void index() {
        if(connected() != null) {
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
        // Oops
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

    static void saveUserDetailsInSession(User user){
        session.put("user", user.username);
        session.put("logged", user.id);
    }
    public static void logout() {
        session.clear();
        index();
    }
    static User connectedUser() {
        String userId = session.get("logged");
        return userId == null ? null : (User) User.findById(Long.parseLong(userId));
    }

}