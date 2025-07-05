
public class NormalProduct extends Product {

    public NormalProduct(String name, double price, int quantity) {
        super(name, price, quantity);
    }

    @Override
    public String toString() {
        return "NormalProduct{"
                + "name='" + name + '\''
                + ", price=" + getPrice()
                + ", quantity=" + getQuantity()
                + '}';
    }
}
