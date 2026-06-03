# agent.md - Padrões do Projeto Cyphvv (Backend)

## Objetivo

API Spring Boot em Java utilizando Arquitetura Hexagonal/Portas e Adaptadores para suportar a plataforma de worldbuilding.

## Stack e Arquitetura

- **Framework:** Spring Boot, Spring Data JPA, Spring Security
- **Estrutura:** Arquitetura Hexagonal:
  - `adapters.in.controller` (Camada Web, REST Controllers)
  - `application.ports.in` e `application.ports.out` (Interfaces e Portas)
  - `application.core.usecases` (Casos de uso/Regras de negócio, livres de dependências externas)
  - `adapters.out.repository` (Adaptadores de banco de dados, JpaRepositories, Entidades JPA)

## Padrão de Soft Delete (Itens Deletados)

- **Responsabilidade de Filtragem:** A filtragem de registros deletados deve ocorrer estritamente na camada de repositório/banco de dados do backend.
- **Consultas JPA:**
  - Todas as listagens públicas e de usuário devem incluir filtro por `deleted = false`.
  - Use consultas customizadas com `@Query` ou convenções de nomenclatura JPA que declarem explicitamente a verificação de exclusão (ex: `findByUserIdAndNotDeleted`), garantindo que dados marcados como deletados nunca sejam entregues por endpoints comuns de listagem.
- **Caso de Uso de Deleção (`DeleteCardUseCase`, etc.):**
  - A deleção de dados é lógica (Soft Delete).
  - Define `deleted = true` e preenche `deletedAt = OffsetDateTime.now()`.
  - Limpa relacionamentos e dados dependentes de forma lógica no mesmo fluxo.

## Diretrizes Gerais para IA (Backend)

- **Entidades e Domínio:** Mantenha a separação entre entidades de banco de dados (`*Entity`) e objetos de domínio core. Faça o mapeamento correto na camada de adaptadores.
- **Segurança:** Não remova filtros de autenticação nos controllers (`@AuthenticationPrincipal FirebaseUserDetails user`).
- **Modificações de Query:** Ao expor novos endpoints de listagem, sempre garanta que registros marcados com `deleted = true` estejam filtrados da busca por padrão.
