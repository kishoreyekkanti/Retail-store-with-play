package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

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
}
