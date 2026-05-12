package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CardControllerMapper Tests")
class CardControllerMapperTest {

    private CardControllerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CardControllerMapper();
    }

    private Card buildCard(Long id, Long worldId, Long cardTypeId, String imageUrl,
            List<String> aliases, List<CardSection> sections, OffsetDateTime createdAt) {
        return new Card(id, worldId, cardTypeId, "Nome", imageUrl, aliases, sections, Collections.emptyList(), createdAt, false, null);
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
        Card card = buildCard(id, worldId, cardTypeId, imageUrl, aliases, Collections.emptyList(), createdAt);

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
        assertNotNull(response.sections());
        assertTrue(response.sections().isEmpty());
        assertEquals(createdAt, response.createdAt());
    }

    @Test
    @DisplayName("Should map Card with null imageUrl")
    void testToResponse_NullImageUrl() {
        // Arrange
        Card card = buildCard(1L, 1L, 1L, null, new ArrayList<>(), Collections.emptyList(), OffsetDateTime.now());

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
        Card card = buildCard(1L, 1L, 1L, "http://example.com/card.jpg", new ArrayList<>(), Collections.emptyList(), OffsetDateTime.now());

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
        Card card = buildCard(1L, 1L, 1L, "http://example.com/card.jpg", aliases, Collections.emptyList(), OffsetDateTime.now());

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(4, response.aliases().size());
        assertEquals(aliases, response.aliases());
    }

    @Test
    @DisplayName("Should map Card with sections")
    void testToResponse_WithSections() {
        // Arrange
        List<CardSection> sections = List.of(
                new CardSection(1L, 1L, "description", "Conteudo inicial", OffsetDateTime.now(), false),
                new CardSection(2L, 1L, "text", "Mais texto", OffsetDateTime.now(), false));
        Card card = buildCard(1L, 1L, 1L, null, Collections.emptyList(), sections, OffsetDateTime.now());

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(2, response.sections().size());
        assertEquals("description", response.sections().get(0).type());
        assertEquals("Conteudo inicial", response.sections().get(0).content());
        assertEquals("text", response.sections().get(1).type());
    }

    @Test
    @DisplayName("Should return CardResponse record (immutable)")
    void testToResponse_ReturnsRecord() {
        // Arrange
        Card card = buildCard(1L, 1L, 1L, "http://example.com/card.jpg", new ArrayList<>(), Collections.emptyList(), OffsetDateTime.now());

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertNotNull(response);
        assertInstanceOf(CardResponse.class, response);
    }

    @Test
    @DisplayName("Should preserve createdAt timestamp exactly")
    void testToResponse_PreservesTimestamp() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2025-05-04T15:45:30.123456+05:30");
        Card card = buildCard(1L, 1L, 1L, "http://example.com/card.jpg", new ArrayList<>(), Collections.emptyList(), timestamp);

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
        Card card = buildCard(1L, 1L, 1L, imageUrl, new ArrayList<>(), Collections.emptyList(), OffsetDateTime.now());

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
        Card card = buildCard(1L, 1L, 1L, "http://example.com/card.jpg", aliases, Collections.emptyList(), OffsetDateTime.now());

        // Act
        CardResponse response = mapper.toResponse(card);

        // Assert
        assertEquals(aliases, response.aliases());
    }

    @Test
    @DisplayName("Should map Card with large IDs")
    void testToResponse_LargeIds() {
        // Arrange
        Card card = buildCard(Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, "http://example.com/card.jpg",
                new ArrayList<>(), Collections.emptyList(), OffsetDateTime.now());

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
        Card card1 = buildCard(1L, 1L, 1L, "http://example.com/card1.jpg", new ArrayList<>(), Collections.emptyList(), OffsetDateTime.now());
        Card card2 = buildCard(2L, 2L, 2L, "http://example.com/card2.jpg", List.of("Alias"), Collections.emptyList(), OffsetDateTime.now());

        // Act
        CardResponse response1 = mapper.toResponse(card1);
        CardResponse response2 = mapper.toResponse(card2);

        // Assert
        assertNotEquals(response1.id(), response2.id());
        assertNotEquals(response1.worldId(), response2.worldId());
        assertNotEquals(response1.cardTypeId(), response2.cardTypeId());
    }
}
