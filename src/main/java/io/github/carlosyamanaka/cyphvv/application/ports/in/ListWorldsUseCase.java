package io.github.carlosyamanaka.cyphvv.application.ports.in;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;

import java.util.List;

public interface ListWorldsUseCase {
    List<World> execute(String userId);
}
