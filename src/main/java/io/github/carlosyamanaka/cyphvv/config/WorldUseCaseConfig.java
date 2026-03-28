package io.github.carlosyamanaka.cyphvv.config;

import io.github.carlosyamanaka.cyphvv.application.core.usecases.CreateWorldUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.ListWorldsUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.ListCardsByWorldUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListWorldsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardsByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.WorldRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorldUseCaseConfig {

    @Bean
    public CreateWorldUseCase createWorldUseCase(WorldRepositoryPort worldRepositoryPort) {
        //telling spring to inject the WorldRepositoryPort implementation when creating the CreateWorldUseCase bean
        return new CreateWorldUseCaseImpl(worldRepositoryPort);
    }

    @Bean
    public ListWorldsUseCase listWorldsUseCase(WorldRepositoryPort worldRepositoryPort) {
        return new ListWorldsUseCaseImpl(worldRepositoryPort);
    }

    @Bean
    public ListCardsByWorldUseCase listCardsByWorldUseCase(CardRepositoryPort cardRepositoryPort) {
        return new ListCardsByWorldUseCaseImpl(cardRepositoryPort);
    }
}