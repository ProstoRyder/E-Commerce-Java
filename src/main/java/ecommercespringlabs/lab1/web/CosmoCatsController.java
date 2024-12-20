package ecommercespringlabs.lab1.web;

import ecommercespringlabs.lab1.domain.CosmoCat;
import ecommercespringlabs.lab1.featureToggle.FeatureToggles;
import ecommercespringlabs.lab1.featureToggle.annotation.FeatureToggle;
import ecommercespringlabs.lab1.service.CosmoCatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cosmo-cat")
public class CosmoCatsController {
    private final CosmoCatsService cosmoCatsService;

    public CosmoCatsController(CosmoCatsService cosmoCatsService) {
        this.cosmoCatsService = cosmoCatsService;
    }

    @GetMapping
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public ResponseEntity<List<CosmoCat>> getCosmoCats() {
        return ResponseEntity.ok(cosmoCatsService.getAllCosmoCats());
    }
}
