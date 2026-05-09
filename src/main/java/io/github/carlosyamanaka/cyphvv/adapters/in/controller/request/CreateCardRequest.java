package io.github.carlosyamanaka.cyphvv.adapters.in.controller.request;

public record CreateCardRequest(String cardName, Long cardTypeId, String imageUrl) {

    public CreateCardRequest(Long cardTypeId, String imageUrl) {
        this(null, cardTypeId, imageUrl);
    }
}
