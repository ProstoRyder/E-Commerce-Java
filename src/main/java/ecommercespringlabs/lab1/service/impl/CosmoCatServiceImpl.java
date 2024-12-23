package ecommercespringlabs.lab1.service.impl;

import ecommercespringlabs.lab1.domain.CosmoCat;
import ecommercespringlabs.lab1.service.CosmoCatsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CosmoCatServiceImpl implements CosmoCatsService {
    List<CosmoCat> cosmoCats = new ArrayList<>();

    public CosmoCatServiceImpl() {
        cosmoCats.add(CosmoCat.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).name("Starsik").breed("korat").gender("male").build());
        cosmoCats.add(CosmoCat.builder().id(UUID.randomUUID()).name("Luna").breed("maine coon").gender("female").build());
    }

    @Override
    public List<CosmoCat> getAllCosmoCats() {
        return cosmoCats;
    }
}