-- Adiciona a coluna icon_type na tabela card_type se ela ainda não existir
ALTER TABLE card_type ADD COLUMN IF NOT EXISTS icon_type VARCHAR(255);
