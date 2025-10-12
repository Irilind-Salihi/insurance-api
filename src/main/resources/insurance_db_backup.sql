--
-- PostgreSQL database dump
--

-- Dumped from database version 15.14 (Debian 15.14-1.pgdg13+1)
-- Dumped by pg_dump version 15.14 (Debian 15.14-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: clients; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.clients (
    id bigint NOT NULL,
    phone character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    email character varying(255) NOT NULL
);


ALTER TABLE public.clients OWNER TO testuser;

--
-- Name: clients_seq; Type: SEQUENCE; Schema: public; Owner: testuser
--

CREATE SEQUENCE public.clients_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clients_seq OWNER TO testuser;

--
-- Name: companies; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.companies (
    company_identifier character varying(255) NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.companies OWNER TO testuser;

--
-- Name: contracts; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.contracts (
    contract_id bigint NOT NULL,
    cost_amount numeric(38,2) NOT NULL,
    end_date date,
    start_date date NOT NULL,
    update_date date NOT NULL,
    client_id bigint
);


ALTER TABLE public.contracts OWNER TO testuser;

--
-- Name: contracts_seq; Type: SEQUENCE; Schema: public; Owner: testuser
--

CREATE SEQUENCE public.contracts_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contracts_seq OWNER TO testuser;

--
-- Name: persons; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.persons (
    birthdate date NOT NULL,
    id bigint NOT NULL
);


ALTER TABLE public.persons OWNER TO testuser;

--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: testuser
--

INSERT INTO public.clients (id, phone, name, email) VALUES
(1, 'jason@gmail.com', 'Jason Smith', '+336995599'),
(2, 'rh@gvolvo.com', 'Volvo', '+416995599'),
(3, 'Charlesc@Volvitrus.com', 'Volvitrus Consulting', '+449529469'),
(4, 'bane-michael@yahoo.com', 'Michael Bane', '+4198449165');


--
-- Data for Name: companies; Type: TABLE DATA; Schema: public; Owner: testuser
--

INSERT INTO public.companies (company_identifier, id) VALUES
('VOL-1', 2),
('VOL-2', 3);

--
-- Data for Name: contracts; Type: TABLE DATA; Schema: public; Owner: testuser
--

DO $$
DECLARE
    i INT;
    contract_amount NUMERIC;
    start_dt DATE;
    update_dt DATE;
    end_dt DATE;
BEGIN
    -- ===============================
    -- CLIENT_ID = 2 → 20000 rows, 90% ended, 10% mixed
    -- ===============================
    FOR i IN 1..20000 LOOP
        contract_amount := 1000 + random() * 5000; -- random cost between 1000 and 6000
        start_dt := CURRENT_DATE - (365 + (random() * 365))::INT; -- start date 1-2 years ago
        update_dt := start_dt + (random() * 30)::INT; -- within 30 days of start
        IF i <= 18000 THEN -- 90% ended
            end_dt := CURRENT_DATE - (random() * 120)::INT; -- ended up to 4 months ago
        ELSE -- 10% mixed
            IF random() < 0.5 THEN
                end_dt := NULL;
            ELSE
                end_dt := CURRENT_DATE + (random() * 365)::INT; -- future date
            END IF;
        END IF;

        INSERT INTO public.contracts(contract_id, cost_amount, start_date, update_date, end_date, client_id)
        VALUES (i, contract_amount, start_dt, update_dt, end_dt, 2);
    END LOOP;

    -- ===============================
    -- CLIENT_ID = 3 → 15 rows, 30% ended, 70% mixed
    -- ===============================
    FOR i IN 20001..20015 LOOP
        contract_amount := 1000 + random() * 5000;
        start_dt := CURRENT_DATE - (365 + (random() * 365))::INT;
        update_dt := start_dt + (random() * 30)::INT;

        IF i <= 20005 THEN -- 30% ended
            end_dt := CURRENT_DATE - (random() * 120)::INT;
        ELSE -- 70% mixed
            IF random() < 0.5 THEN
                end_dt := NULL;
            ELSE
                end_dt := CURRENT_DATE + (random() * 365)::INT;
            END IF;
        END IF;

        INSERT INTO public.contracts(contract_id, cost_amount, start_date, update_date, end_date, client_id)
        VALUES (i, contract_amount, start_dt, update_dt, end_dt, 3);
    END LOOP;

    -- ===============================
    -- CLIENT_ID = 4 → 40 rows, 70% ended, 30% mixed
    -- ===============================
    FOR i IN 20016..20055 LOOP
        contract_amount := 1000 + random() * 5000;
        start_dt := CURRENT_DATE - (365 + (random() * 365))::INT;
        update_dt := start_dt + (random() * 30)::INT;

        IF i <= 20044 THEN -- 70% ended
            end_dt := CURRENT_DATE - (random() * 120)::INT;
        ELSE -- 30% mixed
            IF random() < 0.5 THEN
                end_dt := NULL;
            ELSE
                end_dt := CURRENT_DATE + (random() * 365)::INT;
            END IF;
        END IF;

        INSERT INTO public.contracts(contract_id, cost_amount, start_date, update_date, end_date, client_id)
        VALUES (i, contract_amount, start_dt, update_dt, end_dt, 4);
    END LOOP;
END $$;


--
-- Data for Name: persons; Type: TABLE DATA; Schema: public; Owner: testuser
--

INSERT INTO public.persons (birthdate, id) VALUES
('2000-10-10', 1),
('1994-10-10', 4);

--
-- Name: clients_seq; Type: SEQUENCE SET; Schema: public; Owner: testuser
--

SELECT pg_catalog.setval('public.clients_seq', 51, true);


--
-- Name: contracts_seq; Type: SEQUENCE SET; Schema: public; Owner: testuser
--

SELECT pg_catalog.setval('public.contracts_seq', 51, true);


--
-- Name: clients clients_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: companies companies_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- Name: contracts contracts_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT contracts_pkey PRIMARY KEY (contract_id);


--
-- Name: persons persons_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT persons_pkey PRIMARY KEY (id);


--
-- Name: idx_contract_client_end_date; Type: INDEX; Schema: public; Owner: testuser
--

CREATE INDEX idx_contract_client_end_date ON public.contracts USING btree (client_id, end_date);


--
-- Name: idx_contract_client_id; Type: INDEX; Schema: public; Owner: testuser
--

CREATE INDEX idx_contract_client_id ON public.contracts USING btree (client_id);


--
-- Name: idx_contract_end_date; Type: INDEX; Schema: public; Owner: testuser
--

CREATE INDEX idx_contract_end_date ON public.contracts USING btree (end_date);


--
-- Name: companies fk268eoae5idwy775v5hcrs5d6t; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT fk268eoae5idwy775v5hcrs5d6t FOREIGN KEY (id) REFERENCES public.clients(id);


--
-- Name: persons fkmf0qfvutfttelwj4rj3ku81t6; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.persons
    ADD CONSTRAINT fkmf0qfvutfttelwj4rj3ku81t6 FOREIGN KEY (id) REFERENCES public.clients(id);


--
-- Name: contracts fkrqssit79jdlx2ch8ubajt6w4y; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.contracts
    ADD CONSTRAINT fkrqssit79jdlx2ch8ubajt6w4y FOREIGN KEY (client_id) REFERENCES public.clients(id);


--
-- PostgreSQL database dump complete
--
