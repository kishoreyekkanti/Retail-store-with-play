import models.Product;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
    
    public void doJob() {
        // Load default data if the database is empty
        if(Product.count() == 0) {
            Fixtures.load("data.yml");
        }
    }
    
}