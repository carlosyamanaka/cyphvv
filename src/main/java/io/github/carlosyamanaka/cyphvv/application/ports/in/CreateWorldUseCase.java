package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;

public interface CreateWorldUseCase {
    World execute(String userId);
}