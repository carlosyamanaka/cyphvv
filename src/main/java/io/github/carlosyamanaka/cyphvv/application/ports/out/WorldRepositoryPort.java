package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;

import java.util.List;

public interface WorldRepositoryPort {
    World save(World world);
    List<World> findByUserId(String userId);
    World findById(Long id);
}