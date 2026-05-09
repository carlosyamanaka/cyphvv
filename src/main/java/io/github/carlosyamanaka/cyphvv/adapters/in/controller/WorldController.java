package io.github.carlosyamanaka.cyphvv.adapters.in.controller;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.WorldControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.CardControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.CardTypeControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.AddCardAliasRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateCardRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateCardTypeRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateWorldRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.UpdateCardTypeRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.UpdateCardNameRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.SaveCardSectionsRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardTypeResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardSection;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.GetCardTypeByIdUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardTypesByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListWorldsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardsByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.UpdateCardTypeUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.AddCardAliasUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.RemoveCardAliasUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.UpdateCardNameUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.SaveCardSectionsUseCase;
import io.github.carlosyamanaka.cyphvv.security.FirebaseUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/worlds")
public class WorldController {

    private final CreateWorldUseCase createWorldUseCase;
    private final CreateCardUseCase createCardUseCase;
    private final CreateCardTypeUseCase createCardTypeUseCase;
    private final ListCardTypesByWorldUseCase listCardTypesByWorldUseCase;
    private final GetCardTypeByIdUseCase getCardTypeByIdUseCase;
    private final UpdateCardTypeUseCase updateCardTypeUseCase;
    private final DeleteCardTypeUseCase deleteCardTypeUseCase;
    private final ListWorldsUseCase listWorldsUseCase;
    private final ListCardsByWorldUseCase listCardsByWorldUseCase;
    private final AddCardAliasUseCase addCardAliasUseCase;
    private final RemoveCardAliasUseCase removeCardAliasUseCase;
    private final UpdateCardNameUseCase updateCardNameUseCase;
    private final SaveCardSectionsUseCase saveCardSectionsUseCase;
    private final WorldControllerMapper worldMapper;
    private final CardControllerMapper cardMapper;
    private final CardTypeControllerMapper cardTypeMapper;

    public WorldController(CreateWorldUseCase createWorldUseCase,
            CreateCardUseCase createCardUseCase,
            CreateCardTypeUseCase createCardTypeUseCase,
            ListCardTypesByWorldUseCase listCardTypesByWorldUseCase,
            GetCardTypeByIdUseCase getCardTypeByIdUseCase,
            UpdateCardTypeUseCase updateCardTypeUseCase,
            DeleteCardTypeUseCase deleteCardTypeUseCase,
            ListWorldsUseCase listWorldsUseCase,
            ListCardsByWorldUseCase listCardsByWorldUseCase,
            AddCardAliasUseCase addCardAliasUseCase,
            RemoveCardAliasUseCase removeCardAliasUseCase,
            UpdateCardNameUseCase updateCardNameUseCase,
            SaveCardSectionsUseCase saveCardSectionsUseCase,
            WorldControllerMapper worldMapper,
            CardControllerMapper cardMapper,
            CardTypeControllerMapper cardTypeMapper) {
        this.createWorldUseCase = createWorldUseCase;
        this.createCardUseCase = createCardUseCase;
        this.createCardTypeUseCase = createCardTypeUseCase;
        this.listCardTypesByWorldUseCase = listCardTypesByWorldUseCase;
        this.getCardTypeByIdUseCase = getCardTypeByIdUseCase;
        this.updateCardTypeUseCase = updateCardTypeUseCase;
        this.deleteCardTypeUseCase = deleteCardTypeUseCase;
        this.listWorldsUseCase = listWorldsUseCase;
        this.listCardsByWorldUseCase = listCardsByWorldUseCase;
        this.addCardAliasUseCase = addCardAliasUseCase;
        this.removeCardAliasUseCase = removeCardAliasUseCase;
        this.updateCardNameUseCase = updateCardNameUseCase;
        this.saveCardSectionsUseCase = saveCardSectionsUseCase;
        this.worldMapper = worldMapper;
        this.cardMapper = cardMapper;
        this.cardTypeMapper = cardTypeMapper;
    }

    @GetMapping
    public ResponseEntity<List<WorldResponse>> listWorlds(
            @AuthenticationPrincipal FirebaseUserDetails user) {

        String userId = user.getUid();
        List<World> worlds = listWorldsUseCase.execute(userId);
        List<WorldResponse> responses = worlds.stream()
                .map(worldMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping
    public ResponseEntity<WorldResponse> createWorld(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @RequestBody CreateWorldRequest request) {

        String userId = user.getUid();
        World createdWorld = createWorldUseCase.execute(userId, request.worldName());
        WorldResponse response = worldMapper.toResponse(createdWorld);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{worldId}/cards")
    public ResponseEntity<List<CardResponse>> listCardsByWorld(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId) {

        List<Card> cards = listCardsByWorldUseCase.execute(worldId);
        List<CardResponse> responses = cards.stream()
                .map(cardMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{worldId}/cards")
    public ResponseEntity<CardResponse> createCard(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @RequestBody CreateCardRequest request) {

        Card createdCard = createCardUseCase.execute(
                worldId,
                request.cardTypeId(),
                request.cardName(),
                request.imageUrl());
        CardResponse response = cardMapper.toResponse(createdCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{worldId}/card-types")
    public ResponseEntity<List<CardTypeResponse>> listCardTypesByWorld(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId) {

        List<CardType> cardTypes = listCardTypesByWorldUseCase.execute(worldId);
        List<CardTypeResponse> responses = cardTypes.stream()
                .map(cardTypeMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{worldId}/card-types/{cardTypeId}")
    public ResponseEntity<CardTypeResponse> getCardTypeById(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardTypeId) {

        CardType cardType = getCardTypeByIdUseCase.execute(worldId, cardTypeId);
        CardTypeResponse response = cardTypeMapper.toResponse(cardType);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{worldId}/card-types")
    public ResponseEntity<CardTypeResponse> createCardType(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @RequestBody CreateCardTypeRequest request) {

        CardType cardType = createCardTypeUseCase.execute(worldId, request.cardTypeName(), request.iconType());
        CardTypeResponse response = cardTypeMapper.toResponse(cardType);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{worldId}/card-types/{cardTypeId}")
    public ResponseEntity<CardTypeResponse> updateCardType(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardTypeId,
            @RequestBody UpdateCardTypeRequest request) {

        CardType updatedCardType = updateCardTypeUseCase.execute(worldId, cardTypeId, request.cardTypeName(), request.iconType());
        CardTypeResponse response = cardTypeMapper.toResponse(updatedCardType);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{worldId}/card-types/{cardTypeId}")
    public ResponseEntity<Void> deleteCardType(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardTypeId) {

        deleteCardTypeUseCase.execute(worldId, cardTypeId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{worldId}/cards/{cardId}/aliases")
    public ResponseEntity<CardResponse> addCardAlias(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardId,
            @RequestBody AddCardAliasRequest request) {

        Card updatedCard = addCardAliasUseCase.execute(worldId, cardId, request.alias());
        CardResponse response = cardMapper.toResponse(updatedCard);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{worldId}/cards/{cardId}/aliases/{alias}")
    public ResponseEntity<CardResponse> removeCardAlias(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardId,
            @PathVariable String alias) {

        Card updatedCard = removeCardAliasUseCase.execute(worldId, cardId, alias);
        CardResponse response = cardMapper.toResponse(updatedCard);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{worldId}/cards/{cardId}/name")
    public ResponseEntity<CardResponse> updateCardName(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardId,
            @RequestBody UpdateCardNameRequest request) {

        Card updatedCard = updateCardNameUseCase.execute(worldId, cardId, request.cardName());
        CardResponse response = cardMapper.toResponse(updatedCard);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{worldId}/cards/{cardId}/sections")
    public ResponseEntity<CardResponse> saveCardSections(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @PathVariable Long worldId,
            @PathVariable Long cardId,
            @RequestBody SaveCardSectionsRequest request) {

        List<CardSection> sections = request.sections() == null
                ? Collections.emptyList()
                : request.sections().stream()
                        .map(item -> new CardSection(null, cardId, item.type(), item.content(), null, false))
                        .toList();

        Card updatedCard = saveCardSectionsUseCase.execute(worldId, cardId, sections);
        CardResponse response = cardMapper.toResponse(updatedCard);
        return ResponseEntity.ok(response);
    }
}