package io.github.carlosyamanaka.cyphvv.adapters.in.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.CardControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.CardTypeControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.WorldControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateCardRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateCardTypeRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateWorldRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.UpdateCardTypeRequest;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.*;
import io.github.carlosyamanaka.cyphvv.security.FirebaseUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayName("WorldController Tests")
class WorldControllerTest {

        @Mock
        private CreateWorldUseCase createWorldUseCase;

        @Mock
        private CreateCardUseCase createCardUseCase;

        @Mock
        private CreateCardTypeUseCase createCardTypeUseCase;

        @Mock
        private ListCardTypesByWorldUseCase listCardTypesByWorldUseCase;

        @Mock
        private GetCardTypeByIdUseCase getCardTypeByIdUseCase;

        @Mock
        private UpdateCardTypeUseCase updateCardTypeUseCase;

        @Mock
        private DeleteCardTypeUseCase deleteCardTypeUseCase;

        @Mock
        private ListWorldsUseCase listWorldsUseCase;

        @Mock
        private ListCardsByWorldUseCase listCardsByWorldUseCase;

        @Mock
        private WorldControllerMapper worldMapper;

        @Mock
        private CardControllerMapper cardMapper;

        @Mock
        private CardTypeControllerMapper cardTypeMapper;

        private WorldController controller;
        private FirebaseUserDetails mockUser;
        private static final String USER_ID = "test-user-123";
        private static final String USER_EMAIL = "test@example.com";

        @BeforeEach
        void setUp() {
                controller = new WorldController(
                                createWorldUseCase, createCardUseCase, createCardTypeUseCase,
                                listCardTypesByWorldUseCase, getCardTypeByIdUseCase, updateCardTypeUseCase,
                                deleteCardTypeUseCase, listWorldsUseCase, listCardsByWorldUseCase,
                                worldMapper, cardMapper, cardTypeMapper);

                mockUser = new FirebaseUserDetails(USER_ID, USER_EMAIL, "John Doe", new java.util.HashMap<>());
        }

        @Test
        @DisplayName("Should list all worlds for authenticated user")
        void testListWorlds_Success() {
                // Arrange
                World world1 = new World(1L, USER_ID, OffsetDateTime.now(), false, null, "World 1");
                World world2 = new World(2L, USER_ID, OffsetDateTime.now(), false, null, "World 2");
                List<World> worlds = List.of(world1, world2);

                when(listWorldsUseCase.execute(USER_ID)).thenReturn(worlds);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse response1 = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse(
                                1L, USER_ID, OffsetDateTime.now(), "World 1");
                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse response2 = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse(
                                2L, USER_ID, OffsetDateTime.now(), "World 2");

                when(worldMapper.toResponse(world1)).thenReturn(response1);
                when(worldMapper.toResponse(world2)).thenReturn(response2);

                // Act
                ResponseEntity<List<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse>> result = controller
                                .listWorlds(mockUser);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals(2, result.getBody().size());
                verify(listWorldsUseCase, times(1)).execute(USER_ID);
        }

        @Test
        @DisplayName("Should return empty list when user has no worlds")
        void testListWorlds_EmptyList() {
                // Arrange
                when(listWorldsUseCase.execute(USER_ID)).thenReturn(new ArrayList<>());

                // Act
                ResponseEntity<List<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse>> result = controller
                                .listWorlds(mockUser);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals(0, result.getBody().size());
        }

        @Test
        @DisplayName("Should create a new world successfully")
        void testCreateWorld_Success() {
                // Arrange
                String worldName = "New World";
                CreateWorldRequest request = new CreateWorldRequest(worldName);

                World createdWorld = new World(1L, USER_ID, OffsetDateTime.now(), false, null, worldName);
                when(createWorldUseCase.execute(USER_ID, worldName)).thenReturn(createdWorld);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse response = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse(
                                1L, USER_ID, OffsetDateTime.now(), worldName);
                when(worldMapper.toResponse(createdWorld)).thenReturn(response);

                // Act
                ResponseEntity<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse> result = controller
                                .createWorld(mockUser, request);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.CREATED, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals(worldName, result.getBody().worldName());
                verify(createWorldUseCase, times(1)).execute(USER_ID, worldName);
        }

        @Test
        @DisplayName("Should list cards by world ID")
        void testListCardsByWorld_Success() {
                // Arrange
                Long worldId = 1L;
                Card card1 = new Card(1L, worldId, 1L, "Card 1", "Desc 1", "http://example.com/card1.jpg",
                                new ArrayList<>(), OffsetDateTime.now(),
                                false, null);
                Card card2 = new Card(2L, worldId, 2L, "Card 2", "Desc 2", "http://example.com/card2.jpg",
                                new ArrayList<>(), OffsetDateTime.now(),
                                false, null);
                List<Card> cards = List.of(card1, card2);

                when(listCardsByWorldUseCase.execute(worldId)).thenReturn(cards);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse cardResponse1 = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse(
                                1L, worldId, 1L, "Card 1", "Desc 1", "http://example.com/card1.jpg", new ArrayList<>(),
                                OffsetDateTime.now());
                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse cardResponse2 = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse(
                                2L, worldId, 2L, "Card 2", "Desc 2", "http://example.com/card2.jpg", new ArrayList<>(),
                                OffsetDateTime.now());

                when(cardMapper.toResponse(card1)).thenReturn(cardResponse1);
                when(cardMapper.toResponse(card2)).thenReturn(cardResponse2);

                // Act
                ResponseEntity<List<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse>> result = controller
                                .listCardsByWorld(mockUser, worldId);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals(2, result.getBody().size());
                verify(listCardsByWorldUseCase, times(1)).execute(worldId);
        }

        @Test
        @DisplayName("Should create a new card successfully")
        void testCreateCard_Success() {
                // Arrange
                Long worldId = 1L;
                Long cardTypeId = 1L;
                String cardName = "Card teste";
                String description = "Descricao";
                String imageUrl = "http://example.com/card.jpg";
                CreateCardRequest request = new CreateCardRequest(cardName, description, cardTypeId, imageUrl);

                Card createdCard = new Card(1L, worldId, cardTypeId, cardName, description, imageUrl, new ArrayList<>(),
                                OffsetDateTime.now(), false,
                                null);
                when(createCardUseCase.execute(worldId, cardTypeId, cardName, description, imageUrl))
                                .thenReturn(createdCard);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse response = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse(
                                1L, worldId, cardTypeId, cardName, description, imageUrl, new ArrayList<>(),
                                OffsetDateTime.now());
                when(cardMapper.toResponse(createdCard)).thenReturn(response);

                // Act
                ResponseEntity<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse> result = controller
                                .createCard(mockUser, worldId, request);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.CREATED, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals(cardTypeId, result.getBody().cardTypeId());
                verify(createCardUseCase, times(1)).execute(worldId, cardTypeId, cardName, description, imageUrl);
        }

        @Test
        @DisplayName("Should list card types by world ID")
        void testListCardTypesByWorld_Success() {
                // Arrange
                Long worldId = 1L;
                CardType cardType1 = new CardType(1L, worldId, "Type 1", OffsetDateTime.now(), false, null);
                CardType cardType2 = new CardType(2L, worldId, "Type 2", OffsetDateTime.now(), false, null);
                List<CardType> cardTypes = List.of(cardType1, cardType2);

                when(listCardTypesByWorldUseCase.execute(worldId)).thenReturn(cardTypes);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse cardTypeResponse1 = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse(
                                1L, worldId, "Type 1", OffsetDateTime.now());
                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse cardTypeResponse2 = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse(
                                2L, worldId, "Type 2", OffsetDateTime.now());

                when(cardTypeMapper.toResponse(cardType1)).thenReturn(cardTypeResponse1);
                when(cardTypeMapper.toResponse(cardType2)).thenReturn(cardTypeResponse2);

                // Act
                ResponseEntity<List<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse>> result = controller
                                .listCardTypesByWorld(mockUser, worldId);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertNotNull(result.getBody());
                assertEquals(2, result.getBody().size());
        }

        @Test
        @DisplayName("Should get card type by ID")
        void testGetCardTypeById_Success() {
                // Arrange
                Long worldId = 1L;
                Long cardTypeId = 1L;
                CardType cardType = new CardType(cardTypeId, worldId, "Type 1", OffsetDateTime.now(), false, null);

                when(getCardTypeByIdUseCase.execute(worldId, cardTypeId)).thenReturn(cardType);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse response = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse(
                                cardTypeId, worldId, "Type 1", OffsetDateTime.now());
                when(cardTypeMapper.toResponse(cardType)).thenReturn(response);

                // Act
                ResponseEntity<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse> result = controller
                                .getCardTypeById(mockUser, worldId, cardTypeId);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertEquals(cardTypeId, result.getBody().id());
        }

        @Test
        @DisplayName("Should create a new card type successfully")
        void testCreateCardType_Success() {
                // Arrange
                Long worldId = 1L;
                String cardTypeName = "New Type";
                CreateCardTypeRequest request = new CreateCardTypeRequest(cardTypeName);

                CardType createdCardType = new CardType(1L, worldId, cardTypeName, OffsetDateTime.now(), false, null);
                when(createCardTypeUseCase.execute(worldId, cardTypeName)).thenReturn(createdCardType);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse response = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse(
                                1L, worldId, cardTypeName, OffsetDateTime.now());
                when(cardTypeMapper.toResponse(createdCardType)).thenReturn(response);

                // Act
                ResponseEntity<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse> result = controller
                                .createCardType(mockUser, worldId, request);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.CREATED, result.getStatusCode());
                assertEquals(cardTypeName, result.getBody().cardTypeName());
        }

        @Test
        @DisplayName("Should update card type successfully")
        void testUpdateCardType_Success() {
                // Arrange
                Long worldId = 1L;
                Long cardTypeId = 1L;
                String newCardTypeName = "Updated Type";
                UpdateCardTypeRequest request = new UpdateCardTypeRequest(newCardTypeName);

                CardType updatedCardType = new CardType(cardTypeId, worldId, newCardTypeName, OffsetDateTime.now(),
                                false,
                                null);
                when(updateCardTypeUseCase.execute(worldId, cardTypeId, newCardTypeName)).thenReturn(updatedCardType);

                io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse response = new io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse(
                                cardTypeId, worldId, newCardTypeName, OffsetDateTime.now());
                when(cardTypeMapper.toResponse(updatedCardType)).thenReturn(response);

                // Act
                ResponseEntity<io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse> result = controller
                                .updateCardType(mockUser, worldId, cardTypeId, request);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.OK, result.getStatusCode());
                assertEquals(newCardTypeName, result.getBody().cardTypeName());
        }

        @Test
        @DisplayName("Should delete card type successfully")
        void testDeleteCardType_Success() {
                // Arrange
                Long worldId = 1L;
                Long cardTypeId = 1L;
                doNothing().when(deleteCardTypeUseCase).execute(worldId, cardTypeId);

                // Act
                ResponseEntity<Void> result = controller.deleteCardType(mockUser, worldId, cardTypeId);

                // Assert
                assertNotNull(result);
                assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
                verify(deleteCardTypeUseCase, times(1)).execute(worldId, cardTypeId);
        }
}
