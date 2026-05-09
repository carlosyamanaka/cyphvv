package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardSectionRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CardRepositoryAdapter Tests")
class CardRepositoryAdapterTest {

    @Mock
    private CardJpaRepository cardJpaRepository;

    @Mock
    private CardSectionJpaRepository cardSectionJpaRepository;

    @Mock
    private CardRepositoryMapper cardRepositoryMapper;

    @Mock
    private CardSectionRepositoryMapper cardSectionRepositoryMapper;

    private CardRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new CardRepositoryAdapter(cardJpaRepository, cardSectionJpaRepository, cardRepositoryMapper, cardSectionRepositoryMapper);
    }

    @Test
    @DisplayName("Should save card successfully")
    void testSave_Success() {
        // Arrange
        Long worldId = 1L;
        Long cardTypeId = 1L;
        String imageUrl = "http://example.com/card.jpg";
        Card card = new Card(null, worldId, cardTypeId, "Sem nome", imageUrl, new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(), false, null);

        CardEntity entity = new CardEntity();
        entity.setId(1L);
        entity.setWorldId(worldId);
        entity.setCardTypeId(cardTypeId);
        entity.setImageUrl(imageUrl);

        Card savedCard = new Card(1L, worldId, cardTypeId, "Sem nome", imageUrl, new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(), false,
                null);

        when(cardRepositoryMapper.toEntity(card)).thenReturn(entity);
        when(cardJpaRepository.save(entity)).thenReturn(entity);
        when(cardRepositoryMapper.toDomain(entity)).thenReturn(savedCard);

        // Act
        Card result = adapter.save(card);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(worldId, result.getWorldId());
        assertEquals(cardTypeId, result.getCardTypeId());
        assertEquals(imageUrl, result.getImageUrl());
        verify(cardRepositoryMapper, times(1)).toEntity(card);
        verify(cardJpaRepository, times(1)).save(entity);
        verify(cardRepositoryMapper, times(1)).toDomain(entity);
    }

    @Test
    @DisplayName("Should find cards by worldId")
    void testFindByWorldId_Success() {
        // Arrange
        Long worldId = 1L;
        CardEntity entity1 = new CardEntity();
        entity1.setId(1L);
        entity1.setWorldId(worldId);

        CardEntity entity2 = new CardEntity();
        entity2.setId(2L);
        entity2.setWorldId(worldId);

        List<CardEntity> entities = List.of(entity1, entity2);

        Card card1 = new Card(1L, worldId, 1L, "Sem nome", "http://example.com/card1.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);
        Card card2 = new Card(2L, worldId, 2L, "Sem nome", "http://example.com/card2.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);

        when(cardJpaRepository.findByWorldIdAndNotDeleted(worldId)).thenReturn(entities);
        when(cardSectionJpaRepository.findByCardIdAndNotDeleted(1L)).thenReturn(new ArrayList<>());
        when(cardSectionJpaRepository.findByCardIdAndNotDeleted(2L)).thenReturn(new ArrayList<>());
        when(cardRepositoryMapper.toDomainWithSections(eq(entity1), anyList())).thenReturn(card1);
        when(cardRepositoryMapper.toDomainWithSections(eq(entity2), anyList())).thenReturn(card2);

        // Act
        List<Card> result = adapter.findByWorldId(worldId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(card1, result.get(0));
        assertEquals(card2, result.get(1));
        verify(cardJpaRepository, times(1)).findByWorldIdAndNotDeleted(worldId);
    }

    @Test
    @DisplayName("Should return empty list when no cards found for worldId")
    void testFindByWorldId_EmptyList() {
        // Arrange
        Long worldId = 1L;
        when(cardJpaRepository.findByWorldIdAndNotDeleted(worldId)).thenReturn(new ArrayList<>());

        // Act
        List<Card> result = adapter.findByWorldId(worldId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(cardJpaRepository, times(1)).findByWorldIdAndNotDeleted(worldId);
    }

    @Test
    @DisplayName("Should map entity to domain in save method")
    void testSave_MappingCalled() {
        // Arrange
        Card card = new Card(null, 1L, 1L, "Sem nome", "http://example.com/card.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);
        CardEntity entity = new CardEntity();
        Card savedCard = new Card(1L, 1L, 1L, "Sem nome", "http://example.com/card.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);

        when(cardRepositoryMapper.toEntity(card)).thenReturn(entity);
        when(cardJpaRepository.save(entity)).thenReturn(entity);
        when(cardRepositoryMapper.toDomain(entity)).thenReturn(savedCard);

        // Act
        adapter.save(card);

        // Assert
        verify(cardRepositoryMapper, times(1)).toEntity(card);
        verify(cardRepositoryMapper, times(1)).toDomain(entity);
    }

    @Test
    @DisplayName("Should call JPA repository save exactly once")
    void testSave_RepositoryCalled() {
        // Arrange
        Card card = new Card(null, 1L, 1L, "Sem nome", "http://example.com/card.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);
        CardEntity entity = new CardEntity();
        Card savedCard = new Card(1L, 1L, 1L, "Sem nome", "http://example.com/card.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);

        when(cardRepositoryMapper.toEntity(card)).thenReturn(entity);
        when(cardJpaRepository.save(entity)).thenReturn(entity);
        when(cardRepositoryMapper.toDomain(entity)).thenReturn(savedCard);

        // Act
        adapter.save(card);

        // Assert
        verify(cardJpaRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Should call JPA repository findByWorldIdAndNotDeleted exactly once")
    void testFindByWorldId_RepositoryCalled() {
        // Arrange
        Long worldId = 1L;
        when(cardJpaRepository.findByWorldIdAndNotDeleted(worldId)).thenReturn(new ArrayList<>());

        // Act
        adapter.findByWorldId(worldId);

        // Assert
        verify(cardJpaRepository, times(1)).findByWorldIdAndNotDeleted(worldId);
    }

    @Test
    @DisplayName("Should preserve card data through mapping layers")
    void testSave_DataPreservation() {
        // Arrange
        Long worldId = 1L;
        Long cardTypeId = 2L;
        String imageUrl = "http://example.com/test-card.jpg";
        Card card = new Card(null, worldId, cardTypeId, "Sem nome", imageUrl, new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(), false, null);

        CardEntity entity = new CardEntity();
        Card savedCard = new Card(99L, worldId, cardTypeId, "Sem nome", imageUrl, new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(), false,
                null);

        when(cardRepositoryMapper.toEntity(card)).thenReturn(entity);
        when(cardJpaRepository.save(entity)).thenReturn(entity);
        when(cardRepositoryMapper.toDomain(entity)).thenReturn(savedCard);

        // Act
        Card result = adapter.save(card);

        // Assert
        assertEquals(worldId, result.getWorldId());
        assertEquals(cardTypeId, result.getCardTypeId());
        assertEquals(imageUrl, result.getImageUrl());
        assertFalse(result.getDeleted());
    }

    @Test
    @DisplayName("Should handle cards with empty aliases list")
    void testSave_EmptyAliases() {
        // Arrange
        Card card = new Card(null, 1L, 1L, "Sem nome", "http://example.com/card.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);
        CardEntity entity = new CardEntity();
        Card savedCard = new Card(1L, 1L, 1L, "Sem nome", "http://example.com/card.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);

        when(cardRepositoryMapper.toEntity(card)).thenReturn(entity);
        when(cardJpaRepository.save(entity)).thenReturn(entity);
        when(cardRepositoryMapper.toDomain(entity)).thenReturn(savedCard);

        // Act
        Card result = adapter.save(card);

        // Assert
        assertNotNull(result.getAliases());
        assertTrue(result.getAliases().isEmpty());
    }

    @Test
    @DisplayName("Should handle multiple saves in sequence")
    void testSave_MultipleCalls() {
        // Arrange
        Card card1 = new Card(null, 1L, 1L, "Sem nome", "http://example.com/card1.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);
        Card card2 = new Card(null, 2L, 2L, "Sem nome", "http://example.com/card2.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);

        CardEntity entity1 = new CardEntity();
        CardEntity entity2 = new CardEntity();

        Card savedCard1 = new Card(1L, 1L, 1L, "Sem nome", "http://example.com/card1.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);
        Card savedCard2 = new Card(2L, 2L, 2L, "Sem nome", "http://example.com/card2.jpg", new ArrayList<>(), new ArrayList<>(), OffsetDateTime.now(),
                false, null);

        when(cardRepositoryMapper.toEntity(card1)).thenReturn(entity1);
        when(cardJpaRepository.save(entity1)).thenReturn(entity1);
        when(cardRepositoryMapper.toDomain(entity1)).thenReturn(savedCard1);

        when(cardRepositoryMapper.toEntity(card2)).thenReturn(entity2);
        when(cardJpaRepository.save(entity2)).thenReturn(entity2);
        when(cardRepositoryMapper.toDomain(entity2)).thenReturn(savedCard2);

        // Act
        Card result1 = adapter.save(card1);
        Card result2 = adapter.save(card2);

        // Assert
        assertNotEquals(result1.getWorldId(), result2.getWorldId());
        assertNotEquals(result1.getCardTypeId(), result2.getCardTypeId());
        verify(cardJpaRepository, times(2)).save(any());
    }
}
