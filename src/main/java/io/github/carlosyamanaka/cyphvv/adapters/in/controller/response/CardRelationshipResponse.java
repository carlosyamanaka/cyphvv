package io.github.carlosyamanaka.cyphvv.adapters.in.controller.response;

import java.util.List;

public record CardRelationshipResponse(Long id, String name, List<CardRelationshipTargetResponse> targets) {
    public record CardRelationshipTargetResponse(Long targetCardId) {}
}
