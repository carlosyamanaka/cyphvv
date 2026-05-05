package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;

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

    @Test
    @DisplayName("Should create a card successfully with valid parameters")
    void testExecute_Success() {
        // Arrange
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = "http://example.com/card.jpg";

        Card savedCard = new Card(1L, worldId, cardTypeId, imageUrl, new ArrayList<>(), OffsetDateTime.now(), false,
                null);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        // Act
        Card result = createCardUseCase.execute(worldId, cardTypeId, imageUrl);

        // Assert
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
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(null, 1L, "http://example.com/card.jpg");
        });

        assertEquals("World ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is zero")
    void testExecute_WorldIdZero() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(0L, 1L, "http://example.com/card.jpg");
        });

        assertEquals("World ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is negative")
    void testExecute_WorldIdNegative() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(-1L, 1L, "http://example.com/card.jpg");
        });

        assertEquals("World ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardTypeId is null")
    void testExecute_CardTypeIdNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(1L, null, "http://example.com/card.jpg");
        });

        assertEquals("Card type ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardTypeId is zero")
    void testExecute_CardTypeIdZero() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(1L, 0L, "http://example.com/card.jpg");
        });

        assertEquals("Card type ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when cardTypeId is negative")
    void testExecute_CardTypeIdNegative() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(1L, -1L, "http://example.com/card.jpg");
        });

        assertEquals("Card type ID must be valid", exception.getMessage());
        verify(cardRepositoryPort, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should create card with null imageUrl")
    void testExecute_NullImageUrl() {
        // Arrange
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = null;

        Card savedCard = new Card(1L, worldId, cardTypeId, imageUrl, new ArrayList<>(), OffsetDateTime.now(), false,
                null);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        // Act
        Card result = createCardUseCase.execute(worldId, cardTypeId, imageUrl);

        // Assert
        assertNotNull(result);
        assertNull(result.getImageUrl());
        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should call repository save method exactly once")
    void testExecute_RepositoryCalled() {
        // Arrange
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = "http://example.com/card.jpg";

        Card savedCard = new Card(1L, worldId, cardTypeId, imageUrl, new ArrayList<>(), OffsetDateTime.now(), false,
                null);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        // Act
        createCardUseCase.execute(worldId, cardTypeId, imageUrl);

        // Assert
        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should initialize aliases as empty list")
    void testExecute_EmptyAliasesList() {
        // Arrange
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = "http://example.com/card.jpg";

        Card savedCard = new Card(1L, worldId, cardTypeId, imageUrl, new ArrayList<>(), OffsetDateTime.now(), false,
                null);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        // Act
        Card result = createCardUseCase.execute(worldId, cardTypeId, imageUrl);

        // Assert
        assertNotNull(result.getAliases());
        assertTrue(result.getAliases().isEmpty());
    }

    @Test
    @DisplayName("Should create card with large worldId and cardTypeId values")
    void testExecute_LargeIds() {
        // Arrange
        Long worldId = Long.MAX_VALUE;
        Long cardTypeId = Long.MAX_VALUE;
        String imageUrl = "http://example.com/card.jpg";

        Card savedCard = new Card(1L, worldId, cardTypeId, imageUrl, new ArrayList<>(), OffsetDateTime.now(), false,
                null);
        when(cardRepositoryPort.save(any(Card.class))).thenReturn(savedCard);

        // Act
        Card result = createCardUseCase.execute(worldId, cardTypeId, imageUrl);

        // Assert
        assertNotNull(result);
        assertEquals(worldId, result.getWorldId());
        assertEquals(cardTypeId, result.getCardTypeId());
        verify(cardRepositoryPort, times(1)).save(any(Card.class));
    }
}
