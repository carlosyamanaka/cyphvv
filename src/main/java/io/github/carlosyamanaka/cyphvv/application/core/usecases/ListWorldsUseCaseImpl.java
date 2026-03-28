package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.ListWorldsUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.WorldRepositoryPort;

import java.util.List;

public class ListWorldsUseCaseImpl implements ListWorldsUseCase {

    private final WorldRepositoryPort worldRepositoryPort;

    public ListWorldsUseCaseImpl(WorldRepositoryPort worldRepositoryPort) {
        this.worldRepositoryPort = worldRepositoryPort;
    }

    @Override
    public List<World> execute(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        return worldRepositoryPort.findByUserId(userId);
    }
}
