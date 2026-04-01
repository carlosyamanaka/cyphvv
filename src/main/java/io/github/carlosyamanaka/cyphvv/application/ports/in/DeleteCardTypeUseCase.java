package io.github.carlosyamanaka.cyphvv.application.ports.in;

public interface DeleteCardTypeUseCase {
    void execute(Long worldId, Long cardTypeId);
}
