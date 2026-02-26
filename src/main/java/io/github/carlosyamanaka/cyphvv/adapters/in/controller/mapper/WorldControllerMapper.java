package io.github.carlosyamanaka.cyphvv.adapters.in.controller.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.in.controller.response.WorldResponse;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import org.springframework.stereotype.Component;

@Component
public class WorldControllerMapper {

    public WorldResponse toResponse(World world) {
        return new WorldResponse(
                world.getId(),
                world.getUserId(),
                world.getCreatedAt(),
                world.getWorldName()
        );
    }
}