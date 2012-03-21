package models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class ProductOrder extends Model {
    
    @Required
    @ManyToOne
    private User user;
    
    @Required
    @ManyToOne
    private Product product;
    
    @Required(message="Credit card number is required")
    @Match(value="^\\d{16}$", message="Credit card number must be numeric and 16 digits long")
    private String creditCard;
    
    @Required(message="Credit card name is required")
    @MinSize(value=3, message="Credit card name is required")
    @MaxSize(value=70, message="Credit card name is required")
    private String creditCardName;
    
    private int creditCardExpiryMonth;
    
    private int creditCardExpiryYear;

    @Deprecated
    public ProductOrder(Product product, User user, String creditCard, String creditCardName) {
        this.product = product;
        this.user = user;
        this.creditCard = creditCard;
		this.creditCardName = creditCardName;
    }
   
    public String getDescription() {
        return (product != null) ? product.getDescription() : "" ;
    }

    public String toString() {
        return "ProductOrder(" + user + ","+ product + ")";
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getCreditCardName() {
		return creditCardName;
	}

	public void setCreditCardName(String creditCardName) {
		this.creditCardName = creditCardName;
	}

	public int getCreditCardExpiryMonth() {
		return creditCardExpiryMonth;
	}

	public void setCreditCardExpiryMonth(int creditCardExpiryMonth) {
		this.creditCardExpiryMonth = creditCardExpiryMonth;
	}

	public int getCreditCardExpiryYear() {
		return creditCardExpiryYear;
	}

	public void setCreditCardExpiryYear(int creditCardExpiryYear) {
		this.creditCardExpiryYear = creditCardExpiryYear;
	}
}
