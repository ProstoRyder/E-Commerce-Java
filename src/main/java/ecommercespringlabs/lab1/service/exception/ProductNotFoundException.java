package ecommercespringlabs.lab1.service.exception;

public class ProductNotFoundException extends RuntimeException{
    public static final String PRODUCT_NOT_FOUND_MESSAGE = "Product with ID %s Not found";
    public ProductNotFoundException(String id) {
        super(String.format(PRODUCT_NOT_FOUND_MESSAGE, id));
    }
}
