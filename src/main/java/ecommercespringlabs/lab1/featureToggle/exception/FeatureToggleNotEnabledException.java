package ecommercespringlabs.lab1.featureToggle.exception;

public class FeatureToggleNotEnabledException extends RuntimeException {

    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category %s not found";

    public FeatureToggleNotEnabledException(String message) {
        super(String.format(CATEGORY_NOT_FOUND_MESSAGE, message));
    }
}
