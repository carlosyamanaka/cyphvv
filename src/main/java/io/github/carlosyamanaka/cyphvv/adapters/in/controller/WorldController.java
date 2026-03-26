package io.github.carlosyamanaka.cyphvv.adapters.in.controller;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.WorldControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateWorldRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import io.github.carlosyamanaka.cyphvv.security.FirebaseUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worlds")
public class WorldController {

    private final CreateWorldUseCase createWorldUseCase;
    private final WorldControllerMapper mapper;

    // dependency injection by spring
    public WorldController(CreateWorldUseCase createWorldUseCase, WorldControllerMapper mapper) {
        this.createWorldUseCase = createWorldUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<WorldResponse> createWorld(
            @AuthenticationPrincipal FirebaseUserDetails user,
            @RequestBody CreateWorldRequest request) {

        String userId = user.getUid();

        // TODO: tirar o print
        System.out.println("🌍 Creating world for user: " + user.getEmail() + " (UID: " + userId + ")");

        World createdWorld = createWorldUseCase.execute(userId, request.worldName());

        WorldResponse response = mapper.toResponse(createdWorld);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}