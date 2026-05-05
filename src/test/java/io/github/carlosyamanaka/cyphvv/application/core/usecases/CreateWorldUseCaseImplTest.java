package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.WorldRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateWorldUseCaseImpl Tests")
class CreateWorldUseCaseImplTest {

    @Mock
    private WorldRepositoryPort worldRepositoryPort;

    private CreateWorldUseCase createWorldUseCase;

    @BeforeEach
    void setUp() {
        createWorldUseCase = new CreateWorldUseCaseImpl(worldRepositoryPort);
    }

    @Test
    @DisplayName("Should create a world successfully with valid userId and worldName")
    void testExecute_Success() {
        // Arrange
        String userId = "user-123";
        String worldName = "My World";
        World savedWorld = new World(1L, userId, OffsetDateTime.now(), false, null, worldName);

        when(worldRepositoryPort.save(any(World.class))).thenReturn(savedWorld);

        // Act
        World result = createWorldUseCase.execute(userId, worldName);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(worldName, result.getWorldName());
        assertFalse(result.getDeleted());
        assertNull(result.getDeletedAt());
        verify(worldRepositoryPort, times(1)).save(any(World.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is null")
    void testExecute_UserIdNull() {
        // Arrange
        String worldName = "My World";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createWorldUseCase.execute(null, worldName);
        });

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(worldRepositoryPort, never()).save(any(World.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is empty")
    void testExecute_UserIdEmpty() {
        // Arrange
        String worldName = "My World";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createWorldUseCase.execute("", worldName);
        });

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(worldRepositoryPort, never()).save(any(World.class));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is only whitespace")
    void testExecute_UserIdWhitespace() {
        // Arrange
        String worldName = "My World";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            createWorldUseCase.execute("   ", worldName);
        });

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(worldRepositoryPort, never()).save(any(World.class));
    }

    @Test
    @DisplayName("Should create world with any worldName value (including null)")
    void testExecute_AnyWorldName() {
        // Arrange
        String userId = "user-123";
        String worldName = null;
        World savedWorld = new World(1L, userId, OffsetDateTime.now(), false, null, worldName);

        when(worldRepositoryPort.save(any(World.class))).thenReturn(savedWorld);

        // Act
        World result = createWorldUseCase.execute(userId, worldName);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(worldRepositoryPort, times(1)).save(any(World.class));
    }

    @Test
    @DisplayName("Should set deleted to false when creating new world")
    void testExecute_DeletedFalseByDefault() {
        // Arrange
        String userId = "user-123";
        String worldName = "My World";
        World savedWorld = new World(1L, userId, OffsetDateTime.now(), false, null, worldName);

        when(worldRepositoryPort.save(any(World.class))).thenReturn(savedWorld);

        // Act
        World result = createWorldUseCase.execute(userId, worldName);

        // Assert
        assertFalse(result.getDeleted());
        assertNull(result.getDeletedAt());
    }

    @Test
    @DisplayName("Should call repository save method exactly once")
    void testExecute_RepositoryCalled() {
        // Arrange
        String userId = "user-123";
        String worldName = "My World";
        World savedWorld = new World(1L, userId, OffsetDateTime.now(), false, null, worldName);

        when(worldRepositoryPort.save(any(World.class))).thenReturn(savedWorld);

        // Act
        createWorldUseCase.execute(userId, worldName);

        // Assert
        verify(worldRepositoryPort, times(1)).save(any(World.class));
    }
}
