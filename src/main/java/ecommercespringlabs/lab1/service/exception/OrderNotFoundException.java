package ecommercespringlabs.lab1.service.exception;

public class OrderNotFoundException extends RuntimeException{
    public static final String ORDER_NOT_FOUND_MESSAGE = "Order with ID %s Not found";
    public OrderNotFoundException(String id) {
        super(String.format(ORDER_NOT_FOUND_MESSAGE, id));
    }
}
