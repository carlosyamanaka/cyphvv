package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateCardUseCaseImpl Tests")
class CreateCardUseCaseImplTest {

    @Mock
    private CardRepositoryPort cardRepositoryPort;

    private CreateCardUseCase createCardUseCase;

    @BeforeEach
    void setUp() {
        createCardUseCase = new CreateCardUseCaseImpl(cardRepositoryPort);
    }

    private Card buildSavedCard(Long id, Long worldId, Long cardTypeId, String imageUrl) {
        return new Card(id, worldId, cardTypeId, "Sem nome", imageUrl,
                Collections.<String>emptyList(), Collections.<CardSection>emptyList(),
                OffsetDateTime.now(), false, null);
    }

    @Test
    @DisplayName("Should create a card successfully with valid parameters")
    void testExecute_Success() {
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = "http://example.com/card.jpg";

        Card savedCard = buildSavedCard(1L, worldId, cardTypeId, imageUrl);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        Card result = createCardUseCase.execute(worldId, cardTypeId, "Sem nome", imageUrl);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(worldId, result.getWorldId());
        assertEquals(cardTypeId, result.getCardTypeId());
        assertEquals(imageUrl, result.getImageUrl());
        assertFalse(result.getDeleted());
        assertNull(result.getDeletedAt());
        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is null")
    void testExecute_WorldIdNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCardUseCase.execute(null, 1L, "Sem nome", "http://example.com/card.jpg"));
        assertEquals("World ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is zero")
    void testExecute_WorldIdZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCardUseCase.execute(0L, 1L, "Sem nome", "http://example.com/card.jpg"));
        assertEquals("World ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is negative")
    void testExecute_WorldIdNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCardUseCase.execute(-1L, 1L, "Sem nome", "http://example.com/card.jpg"));
        assertEquals("World ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardTypeId is null")
    void testExecute_CardTypeIdNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCardUseCase.execute(1L, null, "Sem nome", "http://example.com/card.jpg"));
        assertEquals("Card type ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardTypeId is zero")
    void testExecute_CardTypeIdZero() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCardUseCase.execute(1L, 0L, "Sem nome", "http://example.com/card.jpg"));
        assertEquals("Card type ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardTypeId is negative")
    void testExecute_CardTypeIdNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                createCardUseCase.execute(1L, -1L, "Sem nome", "http://example.com/card.jpg"));
        assertEquals("Card type ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should create card with null imageUrl")
    void testExecute_NullImageUrl() {
        Long worldId = 1L;
        Long cardTypeId = 1L;

        Card savedCard = buildSavedCard(1L, worldId, cardTypeId, null);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        Card result = createCardUseCase.execute(worldId, cardTypeId, "Sem nome", (String) null);

        assertNotNull(result);
        assertNull(result.getImageUrl());
        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should call repository save method exactly once")
    void testExecute_RepositoryCalled() {
        Long worldId = 1L;
        Long cardTypeId = 1L;

        when(cardRepositoryPort.save(any(Card.class))).thenReturn(buildSavedCard(1L, worldId, cardTypeId, null));

        createCardUseCase.execute(worldId, cardTypeId, "Sem nome", (String) null);

        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should initialize sections as empty list")
    void testExecute_EmptyAliasesList() {
        Long worldId = 1L;
        Long cardTypeId = 1L;

        Card savedCard = buildSavedCard(1L, worldId, cardTypeId, "http://example.com/card.jpg");
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        Card result = createCardUseCase.execute(worldId, cardTypeId, "Sem nome", "http://example.com/card.jpg");

        assertNotNull(result.getSections());
        assertTrue(result.getSections().isEmpty());
        assertNotNull(result.getAliases());
        assertTrue(result.getAliases().isEmpty());
    }

    @Test
    @DisplayName("Should create card with large worldId and cardTypeId values")
    void testExecute_LargeIds() {
        Long worldId = Long.MAX_VALUE;
        Long cardTypeId = Long.MAX_VALUE;

        when(cardRepositoryPort.save(any(Card.class))).thenReturn(buildSavedCard(1L, worldId, cardTypeId, null));

        Card result = createCardUseCase.execute(worldId, cardTypeId, "Sem nome", (String) null);

        assertNotNull(result);
        assertEquals(worldId, result.getWorldId());
        assertEquals(cardTypeId, result.getCardTypeId());
        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }
}
