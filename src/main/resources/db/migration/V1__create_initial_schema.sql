BEGIN;

CREATE TABLE public.world (
                              id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
                              created_at timestamp with time zone NOT NULL DEFAULT now(),
                              user_id text,
                              deleted boolean DEFAULT false,
                              deleted_at timestamp with time zone,
                              world_name text,
                              CONSTRAINT world_pkey PRIMARY KEY (id)
);

CREATE TABLE public.card_type (
                                  id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
                                  created_at timestamp with time zone NOT NULL DEFAULT now(),
                                  card_type_name character varying,
                                  world_id bigint NOT NULL,
                                  deleted_at timestamp with time zone,
                                  deleted boolean DEFAULT false,
                                  CONSTRAINT card_type_pkey PRIMARY KEY (id),
                                  CONSTRAINT card_type_world_id_fkey FOREIGN KEY (world_id) REFERENCES public.world(id)
);

CREATE TABLE public.card (
                             id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
                             created_at timestamp with time zone NOT NULL DEFAULT now(),
                             card_type_id bigint NOT NULL,
                             aliases text[],
                             img_url text,
                             world_id bigint NOT NULL,
                             deleted boolean DEFAULT false,
                             deleted_at timestamp with time zone,
                             CONSTRAINT card_pkey PRIMARY KEY (id),
                             CONSTRAINT card_card_type_id_fkey FOREIGN KEY (card_type_id) REFERENCES public.card_type(id),
                             CONSTRAINT card_world_id_fkey FOREIGN KEY (world_id) REFERENCES public.world(id)
);

CREATE TABLE public.card_sections (
                                      id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
                                      created_at timestamp with time zone NOT NULL DEFAULT now(),
                                      type text,
                                      content text,
                                      card_id bigint NOT NULL,
                                      deleted boolean DEFAULT false,
                                      CONSTRAINT card_sections_pkey PRIMARY KEY (id),
                                      CONSTRAINT card_sections_card_id_fkey FOREIGN KEY (card_id) REFERENCES public.card(id)
);

CREATE TABLE public.card_relationship (
                                          id bigint GENERATED ALWAYS AS IDENTITY NOT NULL,
                                          name text NOT NULL,
                                          origin_card_id bigint NOT NULL,
                                          deleted boolean DEFAULT false,
                                          CONSTRAINT card_relationship_pkey PRIMARY KEY (id),
                                          CONSTRAINT card_relationship_origin_card_id_fkey FOREIGN KEY (origin_card_id) REFERENCES public.card(id)
);

CREATE TABLE public.card_relationship_target (
                                                 card_relationship_id bigint NOT NULL,
                                                 target_card_id bigint NOT NULL,
                                                 deleted boolean DEFAULT false,
                                                 CONSTRAINT card_relationship_target_pkey PRIMARY KEY (card_relationship_id, target_card_id),
                                                 CONSTRAINT card_relationship_target_target_card_id_fkey FOREIGN KEY (target_card_id) REFERENCES public.card(id),
                                                 CONSTRAINT card_relationship_target_card_relationship_id_fkey FOREIGN KEY (card_relationship_id) REFERENCES public.card_relationship(id)
);

COMMIT;