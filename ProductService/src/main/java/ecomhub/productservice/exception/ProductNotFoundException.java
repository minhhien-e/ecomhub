package ecomhub.productservice.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String resourceName) {
        super(resourceName + " not found");
    }
}