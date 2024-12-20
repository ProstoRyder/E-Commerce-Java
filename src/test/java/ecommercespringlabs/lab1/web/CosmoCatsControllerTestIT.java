package ecommercespringlabs.lab1.web;



import ecommercespringlabs.lab1.featureToggle.FeatureToggles;
import ecommercespringlabs.lab1.service.featuretoggle.FeatureToggleExtension;
import ecommercespringlabs.lab1.service.featuretoggle.annotation.DisabledFeatureToggle;
import ecommercespringlabs.lab1.service.featuretoggle.annotation.EnabledFeatureToggle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(FeatureToggleExtension.class)
public class CosmoCatsControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void get404FeatureDisabled() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cat")).andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void get200FeatureEnabled() throws Exception {
        mockMvc.perform(get("/api/v1/cosmo-cat")).andExpect(status().isOk());
    }
}
