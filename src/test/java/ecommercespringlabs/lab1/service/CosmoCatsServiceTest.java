package ecommercespringlabs.lab1.service;

import ecommercespringlabs.lab1.domain.CosmoCat;
import ecommercespringlabs.lab1.service.impl.CosmoCatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CosmoCatServiceImpl.class)
public class CosmoCatsServiceTest {
    @Mock
    private CosmoCatsService cosmoCatsService;
    private CosmoCat cosmoCat;

    @BeforeEach
    public void init() {
        cosmoCat = CosmoCat.builder().id(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")).name("Starsik").breed("korat").gender("male").build();
    }

    @Test
    void getCosmoCats() {
        List<CosmoCat> mockCosmoCats = List.of(cosmoCat);
        Mockito.when(cosmoCatsService.getAllCosmoCats()).thenReturn(mockCosmoCats);
        assertNotNull(cosmoCatsService.getAllCosmoCats());
    }
}
