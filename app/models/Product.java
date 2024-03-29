package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Product extends Model {

	@Required
	@MaxSize(50)
	private String name;

	@Column(precision=6, scale=2)
	private BigDecimal price;

	public String toString() {
		return "Product(" + name + "," + price + ")";
	}

	@Deprecated
	public Product(String name, BigDecimal price) {
		this.name = name;
		this.price = price;
	}

	public String getDescription() {
		return name +", " + price;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public static List<Product> list(String search, Integer size, Integer page) {
		List<Product> products = null;
		page = page != null ? page : 1;
		if(search.trim().length() == 0) {
			products = Product.all().fetch(page, size);
		} else {
			search = search.toLowerCase();
			products = Product.find("lower(name) like ? ", "%" + search + "%").fetch(page, size);
		}
		return products;
	}

}
