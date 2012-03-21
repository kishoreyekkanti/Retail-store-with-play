package models;

import javax.persistence.Entity;
import javax.persistence.Table;

import play.Play;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
@Table(name="Customer")
public class User extends Model {
    
    @Required
    @MaxSize(15)
    @MinSize(4)
    @Match(value="^\\w*$", message="Not a valid username")
    public String username;
    
    @Required
    @MaxSize(15)
    @MinSize(5)
    public String password;
    
    @Required
    @MaxSize(100)
    public String name;
   
    @Deprecated
    public User(String name, String password, String username) {
        this.name = name;
        this.password = password;
        this.username = username;
    }

    public String toString()  {
        return "User(" + username + ")";
    }

    public boolean isAdmin() {
        return username.equals(Play.configuration.getProperty("acmecorp.adminEmail", ""));
    }
}
