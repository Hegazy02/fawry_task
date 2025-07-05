
public class CartItem {

    public Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "CartItem{"
                + "product=" + product
                + ", quantity=" + quantity
                + '}';
    }
}
