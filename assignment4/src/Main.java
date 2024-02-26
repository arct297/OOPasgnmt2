import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void showProductsRange(ArrayList<Product> productsList){
        System.out.println("\nOur range of products:");
        for (Product product : productsList){
            System.out.print("\n" + product.toString() + "\n");
        }
    }

    public static int requestAction(ArrayList<String> actionsList, Scanner scanner){
        System.out.println("Action list:");
        for (String action: actionsList){
            System.out.println(action);
        }
        System.out.print("Choose the action`s number: ");
        return scanner.nextInt();
    }

    public static void main(String[] args) {
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product("Smartphone Samsung A50 5G", 599.99f, 0.05f, "A high-end smartphone with advanced features."));
        productList.add(new Product("Laptop Asus ROG F4549858", 899.99f, 0.1f, "A powerful laptop for productivity and gaming."));
        productList.add(new Product("Headphones HyperX S-500", 149.99f, 0.0f, "Wireless headphones with noise-cancellation technology."));
        productList.add(new Product("Smartwatch Apple Watch A8", 249.99f, 0.05f, "A stylish smartwatch with fitness tracking capabilities."));
        productList.add(new Product("Camera Canon C457H793", 499.99f, 0.15f, "A professional-grade camera for photography enthusiasts."));

        ArrayList<String> actionsList = new ArrayList<>();
        actionsList.add("1. Show range of products");
        actionsList.add("2. Buy product");
        actionsList.add("3. Exit");

        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------------------");
        System.out.println("Welcome to our electronics store!\n");

        while (true){
            int actionId = requestAction(actionsList, scanner);
            switch (actionId){
                case 1:
                    showProductsRange(productList);
                    break;
                case 2:
                    System.out.print("Please, input an ID of product which you want to buy: ");
                    int productId = scanner.nextInt();
                    boolean isProductFound = false;
                    Product currentProduct = null;
                    for (Product product: productList){
                        if (product.getId() == productId){
                            isProductFound = true;
                            currentProduct = product;
                            break;
                        }
                    }
                    if (!isProductFound){
                        System.out.println("Product with ID " + productId + "does not exist in our range.");
                        break;
                    }

                    // Agreement
                    boolean isCorrectChoise = false;
                    int answer = 2;
                    System.out.print("You are going to buy a product\n" + currentProduct.toString() + "\nAre you sure?\n1. Yes\n2. No\nInput your answer: ");
                    while (!isCorrectChoise) {
                        answer = scanner.nextInt();
                        if (!(answer == 1 || answer == 2)){
                            System.out.print("Please, choose action from given list\nAre you sure?\n1. Yes\n2. No\nInput your answer: ");
                            continue;
                        }
                        isCorrectChoise = true;
                    }
                    if (answer == 2){
                        System.out.println("You refused buying this product.");
                        break;
                    }

                    // Shipping method
                    isCorrectChoise = false;
                    int shippingMethod = 0;
                    while (!isCorrectChoise) {
                        System.out.println("Available shipping methods:");
                        System.out.println("1. Standard Shipping");
                        System.out.println("2. Express Shipping");
                        System.out.print("\nInput number of shipping method: ");
                        shippingMethod = scanner.nextInt();
                        if (!(shippingMethod == 1 || shippingMethod == 2)){
                            System.out.print("Please, choose method from given list");
                            continue;
                        }
                        isCorrectChoise = true;
                    }
                    ShippingStrategy shippingStrategy = null;
                    switch (shippingMethod) {
                        case 1:
                            shippingStrategy = new StandardShipping();
                            break;
                        case 2:
                            shippingStrategy = new ExpressShipping();
                            break;
                    }

                    // Payment method.
                    isCorrectChoise = false;
                    int paymentMethod = 0;
                    while (!isCorrectChoise) {
                        System.out.println("Available payment methods:");
                        System.out.println("1. Credit Card");
                        System.out.println("2. PayPal");

                        System.out.print("\nInput number of payment method: ");
                        paymentMethod = scanner.nextInt();
                        if (!(paymentMethod == 1 || paymentMethod == 2)){
                            System.out.print("Please, choose method from given list");
                            continue;
                        }
                        isCorrectChoise = true;
                    }
                    PaymentStrategy paymentStrategy = null;
                    switch (paymentMethod) {
                        case 1:
                            paymentStrategy = getCreditCardPayment(scanner);
                            break;
                        case 2:
                            paymentStrategy = getPayPalPayment(scanner);
                            break;
                    }

                    shippingStrategy.ship(currentProduct);
                    paymentStrategy.pay(currentProduct);
                    System.out.println("\nThank you very much! Your purchasing was successfully implemented!\n");
                    break;
                case 3:
                    System.out.println("Goodbye! See you later!");
                    System.exit(0);
                default:
                    System.out.println("Please, choose action from given list.");
            }
        }


    }
    private static PaymentStrategy getCreditCardPayment(Scanner scanner) {
        System.out.println("Enter credit card details:");
        boolean isCorrectFormat;

        // Card number.
        isCorrectFormat = false;
        String cardNumber = null;
        while (!isCorrectFormat){
            System.out.print("Card Number (16 digits): ");
            cardNumber = scanner.next();
            if (!isValidCardNumber(cardNumber)) {
                System.out.println("Invalid card number format. Please enter 16 digits.");
                continue;
            }
            isCorrectFormat = true;
        }

        // Date.
        isCorrectFormat = false;
        String expiryDate = null;
        while (!isCorrectFormat){
            System.out.print("Expiry Date (MM/YY): ");
            expiryDate = scanner.next();
            if (!isValidDate(expiryDate)) {
                System.out.println("Invalid date format. Please enter MM/YY date.");
                continue;
            }
            isCorrectFormat = true;
        }

        //CVV
        isCorrectFormat = false;
        String cvv = null;
        while (!isCorrectFormat){
            System.out.print("CVV (3 digits): ");
            cvv = scanner.next();
            if (!isValidCVV(cvv)) {
                System.out.println("Invalid CVV format. Please enter 3 digits.");
                continue;
            }
            isCorrectFormat = true;
        }
        return new CreditCardPayment(cardNumber, expiryDate, cvv);
    }

    private static PaymentStrategy getPayPalPayment(Scanner scanner) {
        System.out.println("Enter PayPal credentials:");
        System.out.print("Email: ");
        String email = scanner.next();

        System.out.print("Password: ");
        String password = scanner.next();

        return new PayPalPayment(email, password);
    }

    private static boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() == 16;
    }

    private static boolean isValidDate(String date) {
        return date.length() == 5;
    }

    private static boolean isValidCVV(String cvv) {
        return cvv.length() == 3;
    }
}


