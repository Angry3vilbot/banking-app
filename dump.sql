--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: jar; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.jar (
                            uid integer NOT NULL,
                            title text NOT NULL,
                            goal numeric(128,2),
                            balance numeric(128,2) DEFAULT 0 NOT NULL
);


--
-- Name: jar_uid_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.jar ALTER COLUMN uid ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.jar_uid_seq
    START WITH 10000000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: request; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.request (
                                id integer NOT NULL,
                                name text NOT NULL,
                                amount numeric(128,2) NOT NULL,
                                target numeric(16,0) NOT NULL
);


--
-- Name: request_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.request ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: transaction; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.transaction (
                                    id integer NOT NULL,
                                    value numeric(128,2) NOT NULL,
                                    title text NOT NULL,
                                    type text,
                                    destination text,
                                    sender text
);


--
-- Name: transaction_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.transaction ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.transaction_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
    );


--
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public."user" (
                               cardnumber numeric(16,0) NOT NULL,
                               name text,
                               balance numeric(128,2) DEFAULT 0,
                               jars numeric(16,0)[],
                               password text NOT NULL,
                               transactions numeric(16,0)[],
                               requests numeric(16,0)[]
);


--
-- Name: jar jar_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.jar
    ADD CONSTRAINT jar_pkey PRIMARY KEY (uid);


--
-- Name: request request_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.request
    ADD CONSTRAINT request_pkey PRIMARY KEY (id);


--
-- Name: transaction transaction_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.transaction
    ADD CONSTRAINT transaction_pkey PRIMARY KEY (id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (cardnumber);


--
-- PostgreSQL database dump complete
--
