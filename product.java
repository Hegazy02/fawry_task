
import java.util.Date;

abstract class Product {

    public String name;
    private double price;
    private int quantity;
    

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        this.quantity = quantity;
    }
}

interface Shippable {

    double getWeight();

    void setWeight(double weight);
}

interface Expirable {

    Date getExpiryDate();

    void setExpiryDate(Date expiryDate);

}

