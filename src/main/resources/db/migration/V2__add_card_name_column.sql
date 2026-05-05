BEGIN;

    ALTER TABLE public.card
    ADD COLUMN IF NOT EXISTS card_name character varying;

COMMIT;
