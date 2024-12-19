package ecommercespringlabs.lab1.featureToggle.aspect;

import ecommercespringlabs.lab1.featureToggle.FeatureToggles;
import ecommercespringlabs.lab1.featureToggle.FeatureToggleService;
import ecommercespringlabs.lab1.featureToggle.annotation.FeatureToggle;
import ecommercespringlabs.lab1.featureToggle.exception.FeatureToggleNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.ProceedingJoinPoint;



@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Around(value = "@annotation(featureToggle)")
    public Object checkFeatureToggle(ProceedingJoinPoint joinPoint, FeatureToggle featureToggle) throws Throwable {
        FeatureToggles toggle = featureToggle.value();

        if (!featureToggleService.checkFeatureToggle(toggle.getName())) {
            throw new FeatureToggleNotEnabledException(toggle.getName());
        }
        return joinPoint.proceed();
    }
}
