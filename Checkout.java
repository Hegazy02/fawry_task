
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Checkout {

    private final Customer customer;
    private final Cart cart;
    private final Date checkoutTime;
    private double subTotal;
    private double shippingFees;
    private double totalAmount;
    private final ShippingService shippingService;

    public Checkout(Customer customer, Cart cart) {
        validateInput(customer, cart);
        this.customer = customer;
        this.cart = cart;
        this.checkoutTime = new Date();

        this.subTotal = calculateSubTotal();
        this.shippingFees = calculateShippingFees();
        this.totalAmount = calculateTotalAmount();
        this.shippingService = new ShippingService();

        validateCustomerBalance();
        validateProductExpiry();
        updateCustomerBalance();
        updateInventory();
        processShipping();
    }

    private void validateInput(Customer customer, Cart cart) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null");
        }
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart cannot be empty");
        }
    }

    private void validateCustomerBalance() {
        if (customer.getBalance() < totalAmount) {
            throw new IllegalArgumentException(
                    String.format("Insufficient balance. Required: %.2f, Available: %.2f",
                            totalAmount, customer.getBalance())
            );
        }
    }

    private void validateProductExpiry() {
        for (CartItem item : cart.getItems().values()) {
            if (item.product instanceof Expirable) {
                Date expiryDate = ((Expirable) item.product).getExpiryDate();
                if (expiryDate.before(checkoutTime)) {
                    throw new IllegalArgumentException(
                            String.format("Product %s expired on %s",
                                    item.product.name, expiryDate)
                    );
                }
            }
        }
    }

    private double calculateSubTotal() {
        return cart.getItems().values().stream()
                .mapToDouble(item -> item.product.getPrice() * item.getQuantity())
                .sum();
    }

    private double calculateShippingFees() {
        return cart.getItems().values().stream()
                .filter(item -> item.product instanceof Shippable)
                .mapToDouble(item -> ((Shippable) item.product).getWeight() * 0.1)
                .sum();
    }

    private double calculateTotalAmount() {
        return subTotal + shippingFees;
    }

    private void updateCustomerBalance() {
        customer.setBalance(customer.getBalance() - totalAmount);
    }

    private void updateInventory() {
        cart.getItems().values().forEach(item -> {
            Product product = item.product;
            product.setQuantity(product.getQuantity() - item.getQuantity());
        });
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getShippingFees() {
        return shippingFees;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getCheckoutTime() {
        return checkoutTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Product, CartItem> getCartItems() {
        return Map.copyOf(cart.getItems());
    }

    private void processShipping() {
        List<ShippableItem> shippableItems = new ArrayList<>();

        for (CartItem item : cart.getItems().values()) {
            if (item.product instanceof Shippable) {
                Shippable shippableProduct = (Shippable) item.product;
                shippableItems.add(new ShippableItem() {
                    @Override
                    public String getName() {
                        return item.product.name;
                    }

                    @Override
                    public double getWeight() {
                        return shippableProduct.getWeight() * item.getQuantity();
                    }
                });
            }
        }

        if (!shippableItems.isEmpty()) {
            shippingService.shipItems(shippableItems);
        }
    }

    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();

        receipt.append("================================\n");
        receipt.append(String.format("         ORDER RECEIPT         %n"));
        receipt.append(String.format("Date: %s%n", checkoutTime.toString()));
        receipt.append("================================\n\n");

        receipt.append("CUSTOMER INFORMATION:\n");
        receipt.append(String.format("Name: %s%n",
                customer.getName() != null ? customer.getName() : "Not Provided"));

        receipt.append("\n");

        receipt.append("ORDER ITEMS:\n");
        receipt.append("------------------------------------------------\n");
        receipt.append(String.format("%-30s %6s %10s %10s%n",
                "Item", "Qty", "Unit Price", "Subtotal"));
        receipt.append("------------------------------------------------\n");

        cart.getItems().values().forEach(item -> {
            Product product = item.product;
            receipt.append(String.format("%-30s %6d %10.2f %10.2f%n",
                    product.name,
                    item.getQuantity(),
                    product.getPrice(),
                    product.getPrice() * item.getQuantity()));
        });

        receipt.append("------------------------------------------------\n");
        receipt.append(String.format("%48s %10.2f%n", "Subtotal:", subTotal));
        receipt.append(String.format("%48s %10.2f%n", "Shipping:", shippingFees));
        receipt.append(String.format("%48s %10.2f%n", "Tax:", 0.00));
        receipt.append("------------------------------------------------\n");
        receipt.append(String.format("%48s %10.2f%n", "TOTAL:", totalAmount));
        receipt.append("\n");

        receipt.append("Thank you for your purchase!\n");
        receipt.append("================================\n");

        return receipt.toString();
    }
}
