-- Criação da tabela de seções (caso não tenha sido criada manualmente)
CREATE TABLE IF NOT EXISTS public.card_sections (
  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
  created_at timestamp with time zone NOT NULL DEFAULT now(),
  type text NULL,
  content text NULL,
  card_id bigint NOT NULL,
  deleted boolean NULL DEFAULT false,
  CONSTRAINT card_sections_pkey PRIMARY KEY (id),
  CONSTRAINT card_sections_card_id_fkey FOREIGN KEY (card_id) REFERENCES card (id)
) TABLESPACE pg_default;

-- Migração dos dados antigos: Transfere as descrições existentes para a nova tabela
INSERT INTO public.card_sections (type, content, card_id, created_at)
SELECT 'description', description, id, created_at
FROM public.card
WHERE description IS NOT NULL AND description <> '';

-- Remoção da coluna obsoleta
ALTER TABLE public.card DROP COLUMN description;
