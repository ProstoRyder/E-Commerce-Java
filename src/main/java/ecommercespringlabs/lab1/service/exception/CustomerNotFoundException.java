package ecommercespringlabs.lab1.service.exception;

public class CustomerNotFoundException extends RuntimeException{
    public static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer with ID %s Not found";
    public CustomerNotFoundException(String id) {
        super(String.format(CUSTOMER_NOT_FOUND_MESSAGE, id));
    }
}
