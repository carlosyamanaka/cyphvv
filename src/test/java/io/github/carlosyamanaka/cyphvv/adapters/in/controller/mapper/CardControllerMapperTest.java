package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CardControllerMapper Tests")
class CardControllerMapperTest {

    private CardControllerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CardControllerMapper();
    }

    @Test
    @DisplayName("Should map Card domain object to CardResponse")
    void testToResponse_Success() {
        // Arrange
        Long id = 1L;
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = "http://example.com/card.jpg";
        List<String> aliases = List.of("Alias 1", "Alias 2");
        OffsetDateTime createdAt = OffsetDateTime.now();
        Card card = new Card(id, worldId, cardTypeId, "Nome", "Descricao", imageUrl, aliases, createdAt, false, null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(worldId, response.worldId());
        assertEquals(cardTypeId, response.cardTypeId());
        assertEquals(imageUrl, response.imageUrl());
        assertEquals(aliases, response.aliases());
        assertEquals("Nome", response.cardName());
        assertEquals("Descricao", response.description());
        assertEquals(createdAt, response.createdAt());
    }

    @Test
    @DisplayName("Should map Card with null imageUrl")
    void testToResponse_NullImageUrl() {
        // Arrange
        Card card = new Card(1L, 1L, 1L, "Nome", "Descricao", null, new ArrayList<>(), OffsetDateTime.now(), false,
                null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertNotNull(response);
        assertNull(response.imageUrl());
    }

    @Test
    @DisplayName("Should map Card with empty aliases list")
    void testToResponse_EmptyAliases() {
        // Arrange
        Card card = new Card(1L, 1L, 1L, "Nome", "Descricao", "http://example.com/card.jpg", new ArrayList<>(),
                OffsetDateTime.now(), false,
                null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertNotNull(response.aliases());
        assertTrue(response.aliases().isEmpty());
    }

    @Test
    @DisplayName("Should map Card with multiple aliases")
    void testToResponse_MultipleAliases() {
        // Arrange
        List<String> aliases = List.of("Alias 1", "Alias 2", "Alias 3", "Alias 4");
        Card card = new Card(1L, 1L, 1L, "Nome", "Descricao", "http://example.com/card.jpg", aliases,
                OffsetDateTime.now(), false, null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(4, response.aliases().size());
        assertEquals(aliases, response.aliases());
    }

    @Test
    @DisplayName("Should return CardResponse record (immutable)")
    void testToResponse_ReturnsRecord() {
        // Arrange
        Card card = new Card(1L, 1L, 1L, "Nome", "Descricao", "http://example.com/card.jpg", new ArrayList<>(),
                OffsetDateTime.now(), false,
                null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertNotNull(response);
        assertTrue(response instanceof CardResponse);
    }

    @Test
    @DisplayName("Should preserve createdAt timestamp exactly")
    void testToResponse_PreservesTimestamp() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2025-05-04T15:45:30.123456+05:30");
        Card card = new Card(1L, 1L, 1L, "http://example.com/card.jpg", new ArrayList<>(), timestamp, false, null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(timestamp, response.createdAt());
    }

    @Test
    @DisplayName("Should map Card with special characters in imageUrl")
    void testToResponse_SpecialCharactersInUrl() {
        // Arrange
        String imageUrl = "http://example.com/card@#$%^&().jpg";
        Card card = new Card(1L, 1L, 1L, imageUrl, new ArrayList<>(), OffsetDateTime.now(), false, null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(imageUrl, response.imageUrl());
    }

    @Test
    @DisplayName("Should map Card with special characters in aliases")
    void testToResponse_SpecialCharactersInAliases() {
        // Arrange
        List<String> aliases = List.of("Alias @#$", "Alias 中文", "Alias 日本語");
        Card card = new Card(1L, 1L, 1L, "http://example.com/card.jpg", aliases, OffsetDateTime.now(), false, null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(aliases, response.aliases());
    }

    @Test
    @DisplayName("Should map Card with large IDs")
    void testToResponse_LargeIds() {
        // Arrange
        Card card = new Card(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, "Nome", "Descricao",
                "http://example.com/card.jpg",
                new ArrayList<>(), OffsetDateTime.now(), false, null);

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(Long.MAX_VALUE, response.id());
        assertEquals(Long.MAX_VALUE, response.worldId());
        assertEquals(Long.MAX_VALUE, response.cardTypeId());
    }

    @Test
    @DisplayName("Should handle multiple consecutive mappings")
    void testToResponse_MultipleCalls() {
        // Arrange
        Card card1 = new Card(1L, 1L, 1L, "Nome", "Descricao", "http://example.com/card1.jpg", new ArrayList<>(),
                OffsetDateTime.now(),
                false, null);
        Card card2 = new Card(2L, 2L, 2L, "Nome2", "Descricao2", "http://example.com/card2.jpg", List.of("Alias"),
                OffsetDateTime.now(), false,
                null);

        // Act
        CardResponse response1 = mapper.toResponse(card1);
        CardResponse response2 = mapper.toResponse(card2);

        // Assert
        assertNotEquals(response1.id(), response2.id());
        assertNotEquals(response1.worldId(), response2.worldId());
        assertNotEquals(response1.cardTypeId(), response2.cardTypeId());
    }
}
