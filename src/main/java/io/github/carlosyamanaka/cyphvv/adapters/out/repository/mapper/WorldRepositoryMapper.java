package io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.WorldEntity;
import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import org.springframework.stereotype.Component;

@Component
public class WorldRepositoryMapper {

    // translate domain to entity (required for saving to the database)
    public WorldEntity toEntity(World world) {
        WorldEntity entity = new WorldEntity();
        entity.setId(world.getId());
        entity.setUserId(world.getUserId());
        entity.setCreatedAt(world.getCreatedAt());
        entity.setDeleted(world.getDeleted());
        entity.setDeletedAt(world.getDeletedAt());
        return entity;
    }

    // translate entity to domain (required for returning from the database)
    public World toDomain(WorldEntity entity) {
        return new World(
                entity.getId(),
                entity.getUserId(),
                entity.getCreatedAt(),
                entity.getDeleted(),
                entity.getDeletedAt()
        );
    }
}