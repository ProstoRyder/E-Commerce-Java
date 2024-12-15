package ecommercespringlabs.lab1.service.exception;

public class CategoryNotFoundException extends RuntimeException{
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category with ID %s Not found";
    public CategoryNotFoundException(String id) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, id));
    }
}
