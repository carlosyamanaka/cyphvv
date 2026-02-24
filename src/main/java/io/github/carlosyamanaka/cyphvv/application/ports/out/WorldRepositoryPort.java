package io.github.carlosyamanaka.cyphvv.application.ports.out;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;

public interface WorldRepositoryPort {
    World save(World world);
}