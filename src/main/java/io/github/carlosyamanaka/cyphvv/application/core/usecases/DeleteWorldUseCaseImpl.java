package io.github.carlosyamanaka.cyphvv.application.core.usecases;

import io.github.carlosyamanaka.cyphvv.application.core.domain.World;
import io.github.carlosyamanaka.cyphvv.application.ports.in.DeleteWorldUseCase;
import io.github.carlosyamanaka.cyphvv.application.ports.out.WorldRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardSectionRepositoryPort;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRelationshipRepositoryPort;

public class DeleteWorldUseCaseImpl implements DeleteWorldUseCase {

    private final WorldRepositoryPort worldRepositoryPort;
    private final CardRepositoryPort cardRepositoryPort;
    private final CardTypeRepositoryPort cardTypeRepositoryPort;
    private final CardSectionRepositoryPort cardSectionRepositoryPort;
    private final CardRelationshipRepositoryPort cardRelationshipRepositoryPort;

    public DeleteWorldUseCaseImpl(WorldRepositoryPort worldRepositoryPort,
                                  CardRepositoryPort cardRepositoryPort,
                                  CardTypeRepositoryPort cardTypeRepositoryPort,
                                  CardSectionRepositoryPort cardSectionRepositoryPort,
                                  CardRelationshipRepositoryPort cardRelationshipRepositoryPort) {
        this.worldRepositoryPort = worldRepositoryPort;
        this.cardRepositoryPort = cardRepositoryPort;
        this.cardTypeRepositoryPort = cardTypeRepositoryPort;
        this.cardSectionRepositoryPort = cardSectionRepositoryPort;
        this.cardRelationshipRepositoryPort = cardRelationshipRepositoryPort;
    }

    @Override
    public void execute(String userId, Long worldId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID must be valid");
        }

        if (worldId == null || worldId <= 0) {
            throw new IllegalArgumentException("World ID must be valid");
        }

        World world = worldRepositoryPort.findById(worldId);
        if (world == null) {
            throw new IllegalArgumentException("World not found");
        }

        if (!world.getUserId().equals(userId)) {
            throw new IllegalArgumentException("World does not belong to the user");
        }

        // Soft-delete the world itself
        world.delete();
        worldRepositoryPort.save(world);

        // Soft-delete all card types belonging to this world
        cardTypeRepositoryPort.softDeleteByWorldId(worldId);

        // Soft-delete all cards belonging to this world
        cardRepositoryPort.softDeleteByWorldId(worldId);

        // Soft-delete all sections for cards belonging to this world
        cardSectionRepositoryPort.softDeleteByWorldId(worldId);

        // Soft-delete all relationships and targets belonging to cards of this world
        cardRelationshipRepositoryPort.softDeleteByWorldId(worldId);
    }
}
