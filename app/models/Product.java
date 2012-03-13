package models;

import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.math.*;

@Entity
public class Product extends Model {
    
    @Required
    @MaxSize(50)
    public String name;
    
    @Column(precision=6, scale=2)
    public BigDecimal price;

    public String toString() {
        return "Product(" + name + "," + price + ")";
    }
    
}
