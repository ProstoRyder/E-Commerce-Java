package ecommercespringlabs.lab1.featureToggle;


import ecommercespringlabs.lab1.featureToggle.config.FeatureToggleProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class FeatureToggleService {
    private final ConcurrentHashMap<String, Boolean> featureToggles;

    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        this.featureToggles = new ConcurrentHashMap<>(featureToggleProperties.getToggles());
    }

    public boolean checkFeatureToggle(String featureName) {
        return featureToggles.getOrDefault(featureName, false);
    }

    public void enable(String featureName) {
        featureToggles.put(featureName, true);
    }

    public void disable(String featureName) {
        featureToggles.put(featureName, false);
    }
}
