package ecommercespringlabs.lab1.service.featuretoggle;

import ecommercespringlabs.lab1.featureToggle.FeatureToggleService;
import ecommercespringlabs.lab1.service.featuretoggle.annotation.DisabledFeatureToggle;
import ecommercespringlabs.lab1.service.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


public class FeatureToggleExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {

        context.getTestMethod().ifPresent(method -> {

            FeatureToggleService featureToggleService = getFeatureToggleService(context);

            if (method.isAnnotationPresent(EnabledFeatureToggle.class)) {
                EnabledFeatureToggle enabledFeatureToggleAnnotation = method.getAnnotation(EnabledFeatureToggle.class);
                featureToggleService.enable(enabledFeatureToggleAnnotation.value().getName());
            } else if (method.isAnnotationPresent(DisabledFeatureToggle.class)) {
                DisabledFeatureToggle disabledFeatureToggleAnnotation = method.getAnnotation(DisabledFeatureToggle.class);
                featureToggleService.disable(disabledFeatureToggleAnnotation.value().getName());
            }
        });
    }

    private FeatureToggleService getFeatureToggleService(ExtensionContext context) {
        return SpringExtension.getApplicationContext(context).getBean(FeatureToggleService.class);
    }
}
