BEGIN;

ALTER TABLE public.card
    ADD COLUMN IF NOT EXISTS description text;

UPDATE public.card
SET card_name = ''
WHERE card_name IS NULL;

ALTER TABLE public.card
    ALTER COLUMN card_name SET NOT NULL;

COMMIT;
