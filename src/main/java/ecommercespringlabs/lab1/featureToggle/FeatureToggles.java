package ecommercespringlabs.lab1.featureToggle;

import lombok.Getter;

@Getter
public enum FeatureToggles {
    COSMO_CATS("cosmo-cats");

    private final String name;

    FeatureToggles(String name) {
        this.name = name;
    }
}
