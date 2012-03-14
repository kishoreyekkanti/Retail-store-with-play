package controllers;

import play.mvc.*;

import java.util.*;

import models.*;

public class Products extends Application {
    
    @Before
    static void checkUser() {
        if(connected() == null) {
            flash.error("Please log in first");
            Application.index();
        }
    }
    
    // ~~~
    
    public static void index() {
        List<Booking> bookings = Booking.find("byUser", connected()).fetch();
        render(bookings);
    }

    public static void list(String search, Integer size, Integer page) {
        List<Product> products = null;
        page = page != null ? page : 1;
        if(search.trim().length() == 0) {
            products = Product.all().fetch(page, size);
        } else {
            search = search.toLowerCase();
            products = Product.find("lower(name) like ? ", "%" + search + "%").fetch(page, size);
        }
        render(products, search, size, page);
    }
    
    public static void show(Long id) {
        Product product = Product.findById(id);
        render(product);
    }
    
    public static void book(Long id) {
        Product hotel = Product.findById(id);
        render(hotel);
    }
    
    public static void confirmBooking(Long id, Booking booking) {
        Product hotel = Product.findById(id);
        booking.hotel = hotel;
        booking.user = connected();
        validation.valid(booking);
        
        // Errors or revise
        if(validation.hasErrors() || params.get("revise") != null) {
            render("@book", hotel, booking);
        }
        
        // Confirm
        if(params.get("confirm") != null) {
            booking.create();
            flash.success("Thank you, %s, your confimation number for %s is %s", connected().name, hotel.name, booking.id);
            index();
        }
        
        // Display booking
        render(hotel, booking);
    }
    
    public static void cancelBooking(Long id) {
        Booking booking = Booking.findById(id);
        booking.delete();
        flash.success("Booking cancelled for confirmation number %s", booking.id);
        index();
    }
    
    public static void settings() {
        render();
    }
    
    public static void saveSettings(String password, String verifyPassword) {
        User connected = connected();
        connected.password = password;
        validation.valid(connected);
        validation.required(verifyPassword);
        validation.equals(verifyPassword, password).message("Your password doesn't match");
        if(validation.hasErrors()) {
            render("@settings", connected, verifyPassword);
        }
        connected.save();
        flash.success("Password updated");
        index();
    }
    
}

