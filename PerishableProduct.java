
import java.util.Date;

public class PerishableProduct extends Product implements Shippable, Expirable {

    private Date expiryDate;
    private double weight;

    public PerishableProduct(String name, double price, int quantity, Date expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public void setWeight(double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        this.weight = weight;
    }

    @Override
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
