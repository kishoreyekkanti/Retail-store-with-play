package controllers;

import java.util.List;

import models.Product;
import models.ProductOrder;
import models.User;
import play.mvc.Before;

public class Products extends Application {
    
    @Before
    static void checkUser() {
        if(connected() == null) {
            flash.error("Please log in first");
            Users.index();
        }
    }
    
    public static void index() {
        List<ProductOrder> productOrders = ProductOrder.find("byUser", connected()).fetch();
        List<Product> products =  Product.all().fetch();

        render(products, productOrders);
    }

    public static void list(String search, Integer size, Integer page) {
    	List<Product> products = Product.list(search, size, page);
        render(products, search, size, page);
    }
    
    public static void show(Long id) {
        Product product = Product.findById(id);
        render(product);
    }
    
    public static void productOrder(Long id) {
        Product product = Product.findById(id);
        render(product);
    }
    
    public static void confirmOrder(Long id, ProductOrder productOrder) {
        Product product = Product.findById(id);
        //TODO good grief this stinks
        productOrder.setProduct(product);
        productOrder.setUser(connected());
        validation.valid(productOrder);
        
        // Errors or revise
        if(validation.hasErrors() || params.get("revise") != null) {
            render("@productOrder", product, productOrder);
        }
        
        // Confirm
        if(params.get("confirm") != null) {
            productOrder.create();
            flash.success("Thank you, %s, your confimation number for %s is %s", connected().name, product.getName(), productOrder.id);
            index();
        }
       
        
        // Display productOrder
        render(product, productOrder);
    }
    
    public static void cancelOrder(Long id) {
        ProductOrder productOrder = ProductOrder.findById(id);
        productOrder.delete();
        flash.success("ProductOrder cancelled for confirmation number %s", productOrder.id);
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

