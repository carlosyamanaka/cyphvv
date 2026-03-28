package io.github.carlosyamanaka.cyphvv.adapters.in.controller;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.WorldControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.CardControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateWorldRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.CardResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListWorldsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListCardsByWorldUseCase;
import io.github.carlosyamanaka.cyphvv.security.FirebaseUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worlds")
public class WorldController {

    private final CreateWorldUseCase createWorldUseCase;
    private final ListWorldsUseCase listWorldsUseCase;
    private final ListCardsByWorldUseCase listCardsByWorldUseCase;
    private final WorldControllerMapper worldMapper;
    private final CardControllerMapper cardMapper;

    // dependency injection by spring
    public WorldController(CreateWorldUseCase createWorldUseCase,
                          ListWorldsUseCase listWorldsUseCase,
                          ListCardsByWorldUseCase listCardsByWorldUseCase,
                          WorldControllerMapper worldMapper,
                          CardControllerMapper cardMapper) {
        this.createWorldUseCase = createWorldUseCase;
        this.listWorldsUseCase = listWorldsUseCase;
        this.listCardsByWorldUseCase = listCardsByWorldUseCase;
        this.worldMapper = worldMapper;
        this.cardMapper = cardMapper;
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

        System.out.println("🌍 Creating world for user: " + user.getEmail() + " (UID: " + userId + ")");

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
}