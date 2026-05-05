package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.WorldRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.WorldEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorldRepositoryAdapter Tests")
class WorldRepositoryAdapterTest {

    @Mock
    private WorldJpaRepository worldJpaRepository;

    @Mock
    private WorldRepositoryMapper worldRepositoryMapper;

    private WorldRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new WorldRepositoryAdapter(worldJpaRepository, worldRepositoryMapper);
    }

    @Test
    @DisplayName("Should save world successfully")
    void testSave_Success() {
        // Arrange
        String userId = "user-123";
        String worldName = "My World";
        World world = new World(null, userId, OffsetDateTime.now(), false, null, worldName);

        WorldEntity entity = new WorldEntity();
        entity.setId(1L);
        entity.setUserId(userId);
        entity.setWorldName(worldName);

        WorldEntity savedEntity = new WorldEntity();
        savedEntity.setId(1L);
        savedEntity.setUserId(userId);
        savedEntity.setWorldName(worldName);

        World savedWorld = new World(1L, userId, OffsetDateTime.now(), false, null, worldName);

        when(worldRepositoryMapper.toEntity(world)).thenReturn(entity);
        when(worldJpaRepository.save(entity)).thenReturn(savedEntity);
        when(worldRepositoryMapper.toDomain(savedEntity)).thenReturn(savedWorld);

        // Act
        World result = adapter.save(world);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(worldName, result.getWorldName());
        verify(worldRepositoryMapper, times(1)).toEntity(world);
        verify(worldJpaRepository, times(1)).save(entity);
        verify(worldRepositoryMapper, times(1)).toDomain(savedEntity);
    }

    @Test
    @DisplayName("Should find worlds by userId")
    void testFindByUserId_Success() {
        // Arrange
        String userId = "user-123";
        WorldEntity entity1 = new WorldEntity();
        entity1.setId(1L);
        entity1.setUserId(userId);

        WorldEntity entity2 = new WorldEntity();
        entity2.setId(2L);
        entity2.setUserId(userId);

        List<WorldEntity> entities = List.of(entity1, entity2);

        World world1 = new World(1L, userId, OffsetDateTime.now(), false, null, "World 1");
        World world2 = new World(2L, userId, OffsetDateTime.now(), false, null, "World 2");

        when(worldJpaRepository.findByUserIdAndNotDeleted(userId)).thenReturn(entities);
        when(worldRepositoryMapper.toDomain(entity1)).thenReturn(world1);
        when(worldRepositoryMapper.toDomain(entity2)).thenReturn(world2);

        // Act
        List<World> result = adapter.findByUserId(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(world1, result.get(0));
        assertEquals(world2, result.get(1));
        verify(worldJpaRepository, times(1)).findByUserIdAndNotDeleted(userId);
    }

    @Test
    @DisplayName("Should return empty list when no worlds found for userId")
    void testFindByUserId_EmptyList() {
        // Arrange
        String userId = "user-123";
        when(worldJpaRepository.findByUserIdAndNotDeleted(userId)).thenReturn(new ArrayList<>());

        // Act
        List<World> result = adapter.findByUserId(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(worldJpaRepository, times(1)).findByUserIdAndNotDeleted(userId);
    }

    @Test
    @DisplayName("Should map entity to domain in save method")
    void testSave_MappingCalled() {
        // Arrange
        World world = new World(null, "user-123", OffsetDateTime.now(), false, null, "World");
        WorldEntity entity = new WorldEntity();
        WorldEntity savedEntity = new WorldEntity();
        savedEntity.setId(1L);
        World savedWorld = new World(1L, "user-123", OffsetDateTime.now(), false, null, "World");

        when(worldRepositoryMapper.toEntity(world)).thenReturn(entity);
        when(worldJpaRepository.save(entity)).thenReturn(savedEntity);
        when(worldRepositoryMapper.toDomain(savedEntity)).thenReturn(savedWorld);

        // Act
        adapter.save(world);

        // Assert
        verify(worldRepositoryMapper, times(1)).toEntity(world);
        verify(worldRepositoryMapper, times(1)).toDomain(savedEntity);
    }

    @Test
    @DisplayName("Should call JPA repository save exactly once")
    void testSave_RepositoryCalled() {
        // Arrange
        World world = new World(null, "user-123", OffsetDateTime.now(), false, null, "World");
        WorldEntity entity = new WorldEntity();
        WorldEntity savedEntity = new WorldEntity();
        savedEntity.setId(1L);
        World savedWorld = new World(1L, "user-123", OffsetDateTime.now(), false, null, "World");

        when(worldRepositoryMapper.toEntity(world)).thenReturn(entity);
        when(worldJpaRepository.save(entity)).thenReturn(savedEntity);
        when(worldRepositoryMapper.toDomain(savedEntity)).thenReturn(savedWorld);

        // Act
        adapter.save(world);

        // Assert
        verify(worldJpaRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Should call JPA repository findByUserIdAndNotDeleted exactly once")
    void testFindByUserId_RepositoryCalled() {
        // Arrange
        String userId = "user-123";
        when(worldJpaRepository.findByUserIdAndNotDeleted(userId)).thenReturn(new ArrayList<>());

        // Act
        adapter.findByUserId(userId);

        // Assert
        verify(worldJpaRepository, times(1)).findByUserIdAndNotDeleted(userId);
    }

    @Test
    @DisplayName("Should preserve world data through mapping layers")
    void testSave_DataPreservation() {
        // Arrange
        String userId = "test-user-xyz";
        String worldName = "Test World Name";
        OffsetDateTime createdAt = OffsetDateTime.now();
        World world = new World(null, userId, createdAt, false, null, worldName);

        WorldEntity entity = new WorldEntity();
        WorldEntity savedEntity = new WorldEntity();
        savedEntity.setId(99L);

        World savedWorld = new World(99L, userId, createdAt, false, null, worldName);

        when(worldRepositoryMapper.toEntity(world)).thenReturn(entity);
        when(worldJpaRepository.save(entity)).thenReturn(savedEntity);
        when(worldRepositoryMapper.toDomain(savedEntity)).thenReturn(savedWorld);

        // Act
        World result = adapter.save(world);

        // Assert
        assertEquals(userId, result.getUserId());
        assertEquals(worldName, result.getWorldName());
        assertFalse(result.getDeleted());
    }
}
