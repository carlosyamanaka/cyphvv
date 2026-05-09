package io.github.carlosyamanaka.cyphvv.adapters.in.controller.request;

import java.util.List;

public record SaveCardSectionsRequest(List<SectionItem> sections) {

    public record SectionItem(String type, String content) {}
}
