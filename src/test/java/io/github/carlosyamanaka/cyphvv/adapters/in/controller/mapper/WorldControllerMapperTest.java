package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorldControllerMapper Tests")
class WorldControllerMapperTest {

    private WorldControllerMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new WorldControllerMapper();
    }

    @Test
    @DisplayName("Should map World domain object to WorldResponse")
    void testToResponse_Success() {
        // Arrange
        Long id = 1L;
        String userId = "user-123";
        OffsetDateTime createdAt = OffsetDateTime.now();
        String worldName = "My World";
        World world = new World(id, userId, createdAt, false, null, worldName);

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals(userId, response.userId());
        assertEquals(createdAt, response.createdAt());
        assertEquals(worldName, response.worldName());
    }

    @Test
    @DisplayName("Should map World with null worldName")
    void testToResponse_NullWorldName() {
        // Arrange
        Long id = 1L;
        String userId = "user-123";
        OffsetDateTime createdAt = OffsetDateTime.now();
        World world = new World(id, userId, createdAt, false, null, null);

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertNotNull(response);
        assertNull(response.worldName());
    }

    @Test
    @DisplayName("Should map World with all fields populated")
    void testToResponse_AllFieldsPopulated() {
        // Arrange
        Long id = 123L;
        String userId = "firebase-user-id-xyz";
        OffsetDateTime createdAt = OffsetDateTime.parse("2024-01-15T10:30:00+00:00");
        String worldName = "Fantasy Kingdom";
        World world = new World(id, userId, createdAt, false, null, worldName);

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertEquals(123L, response.id());
        assertEquals("firebase-user-id-xyz", response.userId());
        assertEquals(createdAt, response.createdAt());
        assertEquals("Fantasy Kingdom", response.worldName());
    }

    @Test
    @DisplayName("Should return WorldResponse record (immutable)")
    void testToResponse_ReturnsRecord() {
        // Arrange
        World world = new World(1L, "user-123", OffsetDateTime.now(), false, null, "World");

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertNotNull(response);
        assertTrue(response instanceof WorldResponse);
    }

    @Test
    @DisplayName("Should preserve createdAt timestamp exactly")
    void testToResponse_PreservesTimestamp() {
        // Arrange
        OffsetDateTime timestamp = OffsetDateTime.parse("2025-05-04T15:45:30.123456+05:30");
        World world = new World(1L, "user-123", timestamp, false, null, "World");

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertEquals(timestamp, response.createdAt());
    }

    @Test
    @DisplayName("Should map World with empty string worldName")
    void testToResponse_EmptyWorldName() {
        // Arrange
        World world = new World(1L, "user-123", OffsetDateTime.now(), false, null, "");

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertEquals("", response.worldName());
    }

    @Test
    @DisplayName("Should map World with special characters in worldName")
    void testToResponse_SpecialCharacters() {
        // Arrange
        String worldName = "World @#$%^&*() 中文 日本語";
        World world = new World(1L, "user-123", OffsetDateTime.now(), false, null, worldName);

        // Act
        WorldResponse response = mapper.toResponse(world);

        // Assert
        assertEquals(worldName, response.worldName());
    }

    @Test
    @DisplayName("Should handle multiple consecutive mappings")
    void testToResponse_MultipleCalls() {
        // Arrange
        World world1 = new World(1L, "user-1", OffsetDateTime.now(), false, null, "World 1");
        World world2 = new World(2L, "user-2", OffsetDateTime.now(), false, null, "World 2");

        // Act
        WorldResponse response1 = mapper.toResponse(world1);
        WorldResponse response2 = mapper.toResponse(world2);

        // Assert
        assertNotEquals(response1.id(), response2.id());
        assertNotEquals(response1.userId(), response2.userId());
        assertNotEquals(response1.worldName(), response2.worldName());
    }
}
