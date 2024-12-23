package ecommercespringlabs.lab1.config;

import ecommercespringlabs.lab1.repository.impl.NaturalIdRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        basePackages = "ecommercespringlabs.lab1.repository",
        repositoryBaseClass = NaturalIdRepositoryImpl.class)
public class JpaRepositoryConfiguration {
}
