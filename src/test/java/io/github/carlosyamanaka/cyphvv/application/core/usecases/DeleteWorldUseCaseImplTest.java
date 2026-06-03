package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.*;
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
@DisplayName("DeleteWorldUseCaseImpl Tests")
class DeleteWorldUseCaseImplTest {

    @Mock
    private WorldRepositoryPort worldRepositoryPort;

    @Mock
    private CardRepositoryPort cardRepositoryPort;

    @Mock
    private CardTypeRepositoryPort cardTypeRepositoryPort;

    @Mock
    private CardSectionRepositoryPort cardSectionRepositoryPort;

    @Mock
    private CardRelationshipRepositoryPort cardRelationshipRepositoryPort;

    private DeleteWorldUseCase deleteWorldUseCase;

    @BeforeEach
    void setUp() {
        deleteWorldUseCase = new DeleteWorldUseCaseImpl(
                worldRepositoryPort,
                cardRepositoryPort,
                cardTypeRepositoryPort,
                cardSectionRepositoryPort,
                cardRelationshipRepositoryPort
        );
    }

    @Test
    @DisplayName("Should delete world successfully when owner and world exist")
    void testExecute_Success() {
        // Arrange
        String userId = "user-123";
        Long worldId = 1L;
        World existingWorld = new World(worldId, userId, OffsetDateTime.now(), false, null, "Fantasy World");

        when(worldRepositoryPort.findById(worldId)).thenReturn(existingWorld);

        // Act
        deleteWorldUseCase.execute(userId, worldId);

        // Assert
        assertTrue(existingWorld.getDeleted());
        assertNotNull(existingWorld.getDeletedAt());
        verify(worldRepositoryPort, times(1)).save(existingWorld);
        verify(cardTypeRepositoryPort, times(1)).softDeleteByWorldId(worldId);
        verify(cardRepositoryPort, times(1)).softDeleteByWorldId(worldId);
        verify(cardSectionRepositoryPort, times(1)).softDeleteByWorldId(worldId);
        verify(cardRelationshipRepositoryPort, times(1)).softDeleteByWorldId(worldId);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when world not found")
    void testExecute_WorldNotFound() {
        // Arrange
        String userId = "user-123";
        Long worldId = 1L;

        when(worldRepositoryPort.findById(worldId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteWorldUseCase.execute(userId, worldId);
        });

        assertEquals("World not found", exception.getMessage());
        verify(worldRepositoryPort, never()).save(any());
        verifyNoInteractions(cardRepositoryPort, cardTypeRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when world belongs to another user")
    void testExecute_WorldDoesNotBelongToUser() {
        // Arrange
        String userId = "user-123";
        Long worldId = 1L;
        World existingWorld = new World(worldId, "other-user", OffsetDateTime.now(), false, null, "Fantasy World");

        when(worldRepositoryPort.findById(worldId)).thenReturn(existingWorld);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteWorldUseCase.execute(userId, worldId);
        });

        assertEquals("World does not belong to the user", exception.getMessage());
        verify(worldRepositoryPort, never()).save(any());
        verifyNoInteractions(cardRepositoryPort, cardTypeRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when userId is null")
    void testExecute_UserIdNull() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteWorldUseCase.execute(null, 1L);
        });

        assertEquals("User ID must be valid", exception.getMessage());
        verifyNoInteractions(worldRepositoryPort, cardRepositoryPort, cardTypeRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when worldId is invalid")
    void testExecute_WorldIdInvalid() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            deleteWorldUseCase.execute("user-123", -1L);
        });

        assertEquals("World ID must be valid", exception.getMessage());
        verifyNoInteractions(worldRepositoryPort, cardRepositoryPort, cardTypeRepositoryPort, cardSectionRepositoryPort, cardRelationshipRepositoryPort);
    }
}
