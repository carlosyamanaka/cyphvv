package io.github.carlosyamanaka.cyphvv.adapters.out.repository;

import io.github.carlosyamanaka.cyphvv.adapters.out.repository.entity.CardTypeEntity;
import io.github.carlosyamanaka.cyphvv.adapters.out.repository.mapper.CardTypeRepositoryMapper;
import io.github.carlosyamanaka.cyphvv.application.core.domain.CardType;
import io.github.carlosyamanaka.cyphvv.application.ports.out.CardTypeRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardTypeRepositoryAdapter implements CardTypeRepositoryPort {

    private final CardTypeJpaRepository cardTypeJpaRepository;
    private final CardTypeRepositoryMapper mapper;

    public CardTypeRepositoryAdapter(CardTypeJpaRepository cardTypeJpaRepository, CardTypeRepositoryMapper mapper) {
        this.cardTypeJpaRepository = cardTypeJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CardType save(CardType cardType) {
        return mapper.toDomain(cardTypeJpaRepository.save(mapper.toEntity(cardType)));
    }

    @Override
    public List<CardType> findByWorldId(Long worldId) {
        return cardTypeJpaRepository.findByWorldIdAndNotDeleted(worldId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public CardType findByIdAndWorldId(Long id, Long worldId) {
        CardTypeEntity entity = cardTypeJpaRepository.findByIdAndWorldIdAndNotDeleted(id, worldId);
        if (entity == null) {
            return null;
        }

        return mapper.toDomain(entity);
    }

    @Override
    public void softDeleteByWorldId(Long worldId) {
        cardTypeJpaRepository.softDeleteByWorldId(worldId, java.time.OffsetDateTime.now());
    }
}
