package io.github.carlosyamanaka.cyphvv.adapters.in.controller;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper.WorldControllerMapper;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.request.CreateWorldRequest;
import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.CreateWorldUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worlds")
public class WorldController {

    private final CreateWorldUseCase createWorldUseCase;
    private final WorldControllerMapper mapper;

    //dependency injection by spring
    public WorldController(CreateWorldUseCase createWorldUseCase, WorldControllerMapper mapper) {
        this.createWorldUseCase = createWorldUseCase;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<WorldResponse> createWorld(@RequestBody CreateWorldRequest request) {
        // create world
        World createdWorld = createWorldUseCase.execute(request.userId(), request.worldName());

        // map result to response
        WorldResponse response = mapper.toResponse(createdWorld);

        // output response with status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}