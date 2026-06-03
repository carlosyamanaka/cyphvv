package io.github.carlosyamanaka.cyphvv.application.ports.in;

public interface DeleteCardUseCase {
    void execute(Long worldId, Long cardId);
}
