package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListWorldsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.WorldRepositoryPort;
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
@DisplayName("ListWorldsUseCaseImpl Tests")
class ListWorldsUseCaseImplTest {

    @Mock
    private WorldRepositoryPort worldRepositoryPort;

    private ListWorldsUseCase listWorldsUseCase;

    @BeforeEach
    void setUp() {
        listWorldsUseCase = new ListWorldsUseCaseImpl(worldRepositoryPort);
    }

    @Test
    @DisplayName("Should return list of worlds for valid userId")
    void testExecute_Success() {
        // Arrange
        String userId = "user-123";
        World world1 = new World(1L, userId, OffsetDateTime.now(), false, null, "World 1");
        World world2 = new World(2L, userId, OffsetDateTime.now(), false, null, "World 2");
        List<World> expectedWorlds = List.of(world1, world2);

        when(worldRepositoryPort.findByUserId(userId)).thenReturn(expectedWorlds);

        // Act
        List<World> result = listWorldsUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedWorlds, result);
        verify(worldRepositoryPort, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return empty list when user has no worlds")
    void testExecute_EmptyList() {
        // Arrange
        String userId = "user-123";
        when(worldRepositoryPort.findByUserId(userId)).thenReturn(new ArrayList<>());

        // Act
        List<World> result = listWorldsUseCase.execute(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        verify(worldRepositoryPort, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is null")
    void testExecute_UserIdNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            listWorldsUseCase.execute(null);
        });

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(worldRepositoryPort, never()).findByUserId(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is empty")
    void testExecute_UserIdEmpty() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            listWorldsUseCase.execute("");
        });

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(worldRepositoryPort, never()).findByUserId(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is only whitespace")
    void testExecute_UserIdWhitespace() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            listWorldsUseCase.execute("   ");
        });

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(worldRepositoryPort, never()).findByUserId(any());
    }

    @Test
    @DisplayName("Should call repository findByUserId method exactly once")
    void testExecute_RepositoryCalled() {
        // Arrange
        String userId = "user-123";
        when(worldRepositoryPort.findByUserId(userId)).thenReturn(new ArrayList<>());

        // Act
        listWorldsUseCase.execute(userId);

        // Assert
        verify(worldRepositoryPort, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("Should return worlds in the same order as repository")
    void testExecute_OrderPreserved() {
        // Arrange
        String userId = "user-123";
        World world3 = new World(3L, userId, OffsetDateTime.now(), false, null, "World 3");
        World world1 = new World(1L, userId, OffsetDateTime.now(), false, null, "World 1");
        World world2 = new World(2L, userId, OffsetDateTime.now(), false, null, "World 2");
        List<World> expectedWorlds = List.of(world3, world1, world2);

        when(worldRepositoryPort.findByUserId(userId)).thenReturn(expectedWorlds);

        // Act
        List<World> result = listWorldsUseCase.execute(userId);

        // Assert
        assertEquals(expectedWorlds, result);
        assertEquals("World 3", result.get(0).getWorldName());
        assertEquals("World 1", result.get(1).getWorldName());
        assertEquals("World 2", result.get(2).getWorldName());
    }
}
