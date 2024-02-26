public class PayPalPayment implements PaymentStrategy {
    private String email;
    private String password;

    public PayPalPayment(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void pay(Product product) {
        System.out.println("***IMPLEMENTING OF PAYMENT FOR GIVEN PAYPAL ACCOUNT***");
    }
}