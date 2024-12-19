package ecommercespringlabs.lab1.featureToggle.annotation;

import ecommercespringlabs.lab1.featureToggle.FeatureToggles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface FeatureToggle {
    FeatureToggles value();
}
