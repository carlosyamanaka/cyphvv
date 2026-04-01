package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.Card;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardRepositoryAdapter implements CardRepositoryPort {

    private final CardJpaRepository cardJpaRepository;
    private final CardRepositoryMapper mapper;

    public CardRepositoryAdapter(CardJpaRepository cardJpaRepository, CardRepositoryMapper mapper) {
        this.cardJpaRepository = cardJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Card save(Card card) {
        return mapper.toDomain(cardJpaRepository.save(mapper.toEntity(card)));
    }

    @Override
    public List<Card> findByWorldId(Long worldId) {
        return cardJpaRepository.findByWorldIdAndNotDeleted(worldId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
