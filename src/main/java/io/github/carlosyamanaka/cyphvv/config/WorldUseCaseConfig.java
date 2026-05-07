package io.github.carlosyamanaka.cyphvv.config;

import io.github.carlosyamanaka.cyphvv.application.core.usecases.CreateWorldUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.CreateCardUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.CreateCardTypeUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.ListCardTypesByWorldUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.GetCardTypeByIdUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.UpdateCardTypeUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.DeleteCardTypeUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.ListWorldsUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.core.usecases.ListCardsByWorldUseCaseImpl;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardTypesByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.GetCardTypeByIdUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.UpdateCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListWorldsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardsByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.WorldRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorldUseCaseConfig {

    @Bean
    public CreateWorldUseCase createWorldUseCase(WorldRepositoryPort worldRepositoryPort, CardTypeRepositoryPort cardTypeRepositoryPort) {
        // telling spring to inject the WorldRepositoryPort and CardTypeRepositoryPort implementations when creating
        // the CreateWorldUseCase bean
        return new CreateWorldUseCaseImpl(worldRepositoryPort, cardTypeRepositoryPort);
    }

    @Bean
    public CreateCardUseCase createCardUseCase(CardRepositoryPort cardRepositoryPort) {
        return new CreateCardUseCaseImpl(cardRepositoryPort);
    }

    @Bean
    public CreateCardTypeUseCase createCardTypeUseCase(CardTypeRepositoryPort cardTypeRepositoryPort) {
        return new CreateCardTypeUseCaseImpl(cardTypeRepositoryPort);
    }

    @Bean
    public ListCardTypesByWorldUseCase listCardTypesByWorldUseCase(CardTypeRepositoryPort cardTypeRepositoryPort) {
        return new ListCardTypesByWorldUseCaseImpl(cardTypeRepositoryPort);
    }

    @Bean
    public GetCardTypeByIdUseCase getCardTypeByIdUseCase(CardTypeRepositoryPort cardTypeRepositoryPort) {
        return new GetCardTypeByIdUseCaseImpl(cardTypeRepositoryPort);
    }

    @Bean
    public UpdateCardTypeUseCase updateCardTypeUseCase(CardTypeRepositoryPort cardTypeRepositoryPort) {
        return new UpdateCardTypeUseCaseImpl(cardTypeRepositoryPort);
    }

    @Bean
    public DeleteCardTypeUseCase deleteCardTypeUseCase(CardTypeRepositoryPort cardTypeRepositoryPort) {
        return new DeleteCardTypeUseCaseImpl(cardTypeRepositoryPort);
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