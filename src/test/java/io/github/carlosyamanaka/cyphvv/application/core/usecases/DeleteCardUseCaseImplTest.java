package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardSectionRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteCardUseCaseImpl Tests")
class DeleteCardUseCaseImplTest {

    @Mock
    private CardRepositoryPort cardRepositoryPort;

    @Mock
    private CardSectionRepositoryPort cardSectionRepositoryPort;

    @Mock
    private CardRelationshipRepositoryPort cardRelationshipRepositoryPort;

    private DeleteCardUseCase deleteCardUseCase;

    @BeforeEach
    void setUp() {
        deleteCardUseCase = new DeleteCardUseCaseImpl(
                cardRepositoryPort,
                cardSectionRepositoryPort,
                cardRelationshipRepositoryPort
        );
    }

    private Card buildCard(Long id, Long worldId, List<String> aliases) {
        return new Card(id, worldId, 1L, "Test Card", "http://example.com/image.jpg",
                aliases, Collections.emptyList(), Collections.emptyList(),
                OffsetDateTime.now(), false, null);
    }

    @Test
    @DisplayName("Should delete card successfully and clear its aliases, sections, and relationships")
    void testExecute_Success() {
        Long worldId = 1L;
        Long cardId = 2L;
        List<String> aliases = new ArrayList<>(List.of("alias1", "alias2"));
        Card card = buildCard(cardId, worldId, aliases);

        when(cardRepositoryPort.findById(worldId, cardId)).thenReturn(card);

        deleteCardUseCase.execute(worldId, cardId);

        // Verify the card's deleted status and aliases were updated
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepositoryPort, times(1)).save(cardCaptor.capture());
        
        Card savedCard = cardCaptor.getValue();
        assertTrue(savedCard.getDeleted());
        assertNotNull(savedCard.getDeletedAt());
        assertTrue(savedCard.getAliases().isEmpty());

        // Verify sections were soft-deleted
        verify(cardSectionRepositoryPort, times(1)).softDeleteByCardId(cardId);

        // Verify relationships were soft-deleted
        verify(cardRelationshipRepositoryPort, times(1)).saveAll(cardId, Collections.emptyList());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is null")
    void testExecute_WorldIdNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(null, 1L));
        assertEquals("World ID must be valid", exception.getMessage());
        verifyNoInteractions(cardRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is invalid (0 or negative)")
    void testExecute_WorldIdInvalid() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(0L, 1L));
        assertEquals("World ID must be valid", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(-1L, 1L));
        assertEquals("World ID must be valid", exception2.getMessage());
        
        verifyNoInteractions(cardRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardId is null")
    void testExecute_CardIdNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(1L, null));
        assertEquals("Card ID must be valid", exception.getMessage());
        verifyNoInteractions(cardRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardId is invalid (0 or negative)")
    void testExecute_CardIdInvalid() {
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(1L, 0L));
        assertEquals("Card ID must be valid", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(1L, -1L));
        assertEquals("Card ID must be valid", exception2.getMessage());

        verifyNoInteractions(cardRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when card is not found")
    void testExecute_CardNotFound() {
        Long worldId = 1L;
        Long cardId = 2L;

        when(cardRepositoryPort.findById(worldId, cardId)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                deleteCardUseCase.execute(worldId, cardId));
        assertEquals("Card not found", exception.getMessage());

        verify(cardRepositoryPort, times(1)).findById(worldId, cardId);
        verify(cardRepositoryPort, never()).save(any());
        verifyNoInteractions(cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }
}
