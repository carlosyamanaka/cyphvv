package io.github.carlosyamanaka.cyphvv.application.ports.in;

public interface DeleteWorldUseCase {
    void execute(String userId, Long worldId);
}
