package io.github.carlosyamanaka.cyphvv.config;

import io.github.carlosyamanaka.cyphvv.application.core.usecases.*;
import io.github.carlosyamanaka.cyphvv.application.ports.in.*;
import io.github.carlosyamanaka.cyphvv.application.ports.out.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorldUseCaseConfig {

    @Bean
    public CreateWorldUseCase createWorldUseCase(WorldRepositoryPort worldRepositoryPort, CardTypeRepositoryPort cardTypeRepositoryPort) {
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
    public DeleteWorldUseCase deleteWorldUseCase(WorldRepositoryPort worldRepositoryPort,
            CardRepositoryPort cardRepositoryPort,
            CardTypeRepositoryPort cardTypeRepositoryPort,
            CardSectionRepositoryPort cardSectionRepositoryPort,
            CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        return new DeleteWorldUseCaseImpl(worldRepositoryPort, cardRepositoryPort, cardTypeRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Bean
    public ListWorldsUseCase listWorldsUseCase(WorldRepositoryPort worldRepositoryPort) {
        return new ListWorldsUseCaseImpl(worldRepositoryPort);
    }

    @Bean
    public ListCardsByWorldUseCase listCardsByWorldUseCase(CardRepositoryPort cardRepositoryPort) {
        return new ListCardsByWorldUseCaseImpl(cardRepositoryPort);
    }

    @Bean
    public AddCardAliasUseCase addCardAliasUseCase(CardRepositoryPort cardRepositoryPort) {
        return new AddCardAliasUseCaseImpl(cardRepositoryPort);
    }

    @Bean
    public RemoveCardAliasUseCase removeCardAliasUseCase(CardRepositoryPort cardRepositoryPort) {
        return new RemoveCardAliasUseCaseImpl(cardRepositoryPort);
    }

    @Bean
    public UpdateCardNameUseCase updateCardNameUseCase(CardRepositoryPort cardRepositoryPort) {
        return new UpdateCardNameUseCaseImpl(cardRepositoryPort);
    }

    @Bean
    public SaveCardSectionsUseCase saveCardSectionsUseCase(CardRepositoryPort cardRepositoryPort,
            CardSectionRepositoryPort cardSectionRepositoryPort) {
        return new SaveCardSectionsUseCaseImpl(cardRepositoryPort, cardSectionRepositoryPort);
    }

    @Bean
    public SaveCardRelationshipsUseCase saveCardRelationshipsUseCase(CardRepositoryPort cardRepositoryPort,
            CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        return new SaveCardRelationshipsUseCaseImpl(cardRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Bean
    public DeleteCardUseCase deleteCardUseCase(CardRepositoryPort cardRepositoryPort,
            CardSectionRepositoryPort cardSectionRepositoryPort,
            CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        return new DeleteCardUseCaseImpl(cardRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }
}