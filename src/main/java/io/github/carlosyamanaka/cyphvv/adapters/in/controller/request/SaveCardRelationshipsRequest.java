package io.github.carlosyamanaka.cyphvv.adapters.in.controller.request;

import java.util.List;

public record SaveCardRelationshipsRequest(List<CardRelationshipItemRequest> relationships) {
    public record CardRelationshipItemRequest(String name, List<CardRelationshipTargetItemRequest> targets) {
        public record CardRelationshipTargetItemRequest(Long targetCardId) {}
    }
}
