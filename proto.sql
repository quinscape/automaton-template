--
-- PostgreSQL database dump
--

-- Dumped from database version 10.19 (Ubuntu 10.19-2.pgdg20.04+1)
-- Dumped by pg_dump version 10.19 (Ubuntu 10.19-2.pgdg20.04+1)

BEGIN;

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

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: app; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app (
    id character varying(36) NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    owner_id character varying(36) NOT NULL,
    flag boolean NOT NULL
);


ALTER TABLE public.app OWNER TO proto;

--
-- Name: app_attachment; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_attachment (
    id character varying(36) NOT NULL,
    description text,
    type character varying(64) NOT NULL,
    url character varying(255)
);


ALTER TABLE public.app_attachment OWNER TO proto;

--
-- Name: app_attachment_data; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_attachment_data (
    id character varying(36) NOT NULL,
    attachment_id character varying(36) NOT NULL,
    data bytea NOT NULL
);


ALTER TABLE public.app_attachment_data OWNER TO proto;

--
-- Name: app_config; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_config (
    name character varying(64) NOT NULL,
    scope jsonb NOT NULL
);


ALTER TABLE public.app_config OWNER TO proto;

--
-- Name: app_login; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_login (
    username character varying(64) NOT NULL,
    series character varying(64) NOT NULL,
    token character varying(64) NOT NULL,
    last_used timestamp without time zone NOT NULL
);


ALTER TABLE public.app_login OWNER TO proto;

--
-- Name: app_translation; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_translation (
    id character varying(36) NOT NULL,
    locale character varying(64) NOT NULL,
    tag character varying(255) NOT NULL,
    process_name character varying(64) NOT NULL,
    translation text NOT NULL,
    created timestamp without time zone NOT NULL,
    modified timestamp without time zone NOT NULL
);


ALTER TABLE public.app_translation OWNER TO proto;

--
-- Name: app_user; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_user (
    id character varying(36) NOT NULL,
    login character varying(64) NOT NULL,
    password character varying(255) NOT NULL,
    disabled boolean,
    created timestamp without time zone NOT NULL,
    last_login timestamp without time zone,
    roles character varying(255) NOT NULL
);


ALTER TABLE public.app_user OWNER TO proto;

--
-- Name: app_user_config; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_user_config (
    login character varying(64) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.app_user_config OWNER TO proto;

--
-- Name: app_version; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.app_version (
    id character varying(36) NOT NULL,
    field_mask numeric(39,0) NOT NULL,
    owner_id character varying(36) NOT NULL,
    created timestamp without time zone NOT NULL,
    entity_type character varying(100) NOT NULL,
    entity_id character varying(36) NOT NULL,
    prev character varying(36)
);


ALTER TABLE public.app_version OWNER TO proto;

--
-- Name: domain_field; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.domain_field (
    id character varying(36) NOT NULL,
    name character varying(100) NOT NULL,
    type character varying(100) NOT NULL,
    description text,
    domain_type_id character varying(36) NOT NULL
);


ALTER TABLE public.domain_field OWNER TO proto;

--
-- Name: domain_field_meta; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.domain_field_meta (
    id character varying(36) NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(100) NOT NULL,
    domain_field_id character varying(36) NOT NULL
);


ALTER TABLE public.domain_field_meta OWNER TO proto;

--
-- Name: domain_type; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.domain_type (
    id character varying(36) NOT NULL,
    name character varying(100) NOT NULL,
    description text,
    app_id character varying(36) NOT NULL
);


ALTER TABLE public.domain_type OWNER TO proto;

--
-- Name: domain_type_meta; Type: TABLE; Schema: public; Owner: proto
--

CREATE TABLE public.domain_type_meta (
    id character varying(36) NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(100) NOT NULL,
    domain_type_id character varying(36) NOT NULL
);


ALTER TABLE public.domain_type_meta OWNER TO proto;

--
-- Data for Name: app; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app (id, name, description, owner_id, flag) FROM stdin;
\.


--
-- Data for Name: app_attachment; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_attachment (id, description, type, url) FROM stdin;
\.


--
-- Data for Name: app_attachment_data; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_attachment_data (id, attachment_id, data) FROM stdin;
\.


--
-- Data for Name: app_config; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_config (name, scope) FROM stdin;
shipping	{"value": "oiwpoir"}
\.


--
-- Data for Name: app_login; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_login (username, series, token, last_used) FROM stdin;
admin	gtkQV29TIFt8UaGAJrdn9A==	aD5edWn7qYK3KwGr5OUYgw==	2019-06-24 21:20:24.477
admin	l1Ypn+bTJVa20FmNHgATmQ==	4xVIu3Nz/eklwVKEK3CfhQ==	2019-06-26 13:46:02.276
admin	pcdsLmi4B4jdQKB3efIbkw==	IKqNRX1I96oNbJuBfTdWFg==	2019-06-28 02:51:54.441
admin	3turHnrT57VO3oGM1OXvOg==	BU0Rzuf7ADxHuSIC/67wtg==	2019-07-09 21:32:05.126
admin	1ZGFuqUgxArAL+QA1IJTxQ==	GkBF10LxnQEiVzsSM7JYng==	2019-07-11 14:04:04.059
admin	TRq6EGofSICS79Mer/CgZA==	e+U+ozsFrO/eGFbSTEPOlg==	2019-08-13 11:03:42.03
admin	hSUCEeeA3mdwF4s48kmM4w==	vwDUiQ8UA3Sw3h8jjmrSlw==	2019-08-13 12:37:15.527
admin	0NfkS/TXLCBxvPtCETfe5g==	qaPsjE7jwkaCavVh+ElDFg==	2019-08-13 14:06:55.168
admin	cuKGVMBP+Z5n6gxmPr3adA==	VuMtckqAqbGUyMlZQB9ylA==	2019-08-14 00:55:29.099
admin	AVQTJWGZrKYwzzrsMwvhVg==	YiQmSnSHu65RoKkhd0zm9A==	2019-08-14 01:46:56.39
admin	gpv4RrulHjsx5HS/KUbRzg==	e5VKu9WZdI/KpvzAA+xZnQ==	2019-08-14 01:48:17.271
admin	9oMJKAaZC27pO8u8IPHnSw==	B8l+Uvd3fRoqhs+Dlw9TBQ==	2019-08-14 01:59:10.673
admin	XTzZscFjh3c2RFVQ7d/orQ==	Puw0MOoEtEWwiFCOnWGTmg==	2019-08-14 02:02:44.644
admin	Mmm8/fMQ5vi4cfR0KXYHZg==	4rpPn3W7HISNn9flosgC5Q==	2019-08-14 10:59:56.098
admin	JMzv/httFQJUVLDxHq5SGA==	qgcLC3ksrvC3cBZ77jm3lA==	2019-08-14 14:27:34.717
admin	HNxtEMpHHklUYj5qcctCEQ==	xJ/G1vdlLRtz732YsET3MQ==	2019-08-14 15:07:20.833
admin	/X0EiGjFWJcwH9KX5D3Vqg==	ocBgL9u2Iyvg0eUZ0wMdcg==	2019-09-06 20:09:47.791
admin	HeN6Kwzcc7jZ1L67DF10jg==	CrkMjzLG2Ik/c/Z3yUXJuQ==	2019-11-13 14:03:49.476
admin	g3buVAhWvlA+5RzaO9oJCA==	y/VzN/XR40bXxVPZzTmLbg==	2019-12-05 19:05:53.126
admin	rH4EBLvfNaUT6kO/Wm58AQ==	t4wk1W+enWgG22yUuYg/Vg==	2020-06-04 18:17:20.516
admin	lOCLXQs/qfxEz5JoE+wK+g==	tkXYyMu2mGGUkIinv9eUBw==	2020-06-05 12:23:34.621
admin	a/Z77rHLKL9UvwUvpvnlog==	+HH3Pcc9rSwgadzJc5b5gg==	2020-06-05 13:41:00.983
admin	nbet+r5vA25eevUcb2ah2g==	1QnRImXrkJNBm5X8fCrg4A==	2020-06-06 17:15:05.396
admin	xpZcB3Eel2xYnnjj4Gwzzw==	yKOqGY4O2g7h1L7OyDmOeg==	2021-04-09 23:40:21.864
admin	nCPf1d0TB7dU5+a7ap/3aQ==	MZZ5OkgOod3Wa8RYFJn6fg==	2021-04-09 23:52:23.944
admin	TyASBXVPqIxa9xbgnddzLw==	B2NoBt/cCU7SOjrW8jrMJg==	2022-01-30 04:25:18.4
admin	sd4RU0pruDsKAGEpg4u2/A==	iuJIt96XUNogABXen+5P4g==	2022-01-30 04:27:37.272
admin	+gU6rPR4Bz85MSOEEFqNng==	xOTNnFebvDYQ6o9gtFocZA==	2022-01-30 04:28:02.279
admin	07Va1P/uCLpBzYS1MQfljA==	lZoFMpfjp9cRNp8aD5TBIA==	2022-01-30 04:28:58.689
admin	uFIttI0FGY+OKKnLaYnxOg==	U39Wlgs41hAoFBFdK1SA3w==	2022-01-30 06:32:59.717
\.


--
-- Data for Name: app_translation; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_translation (id, locale, tag, process_name, translation, created, modified) FROM stdin;
1015097c-1d83-4690-a697-3dfce1572494	de-DE	Process		Prozess	2018-10-17 01:20:04.388419	2018-10-17 01:20:04.388419
08d18dbb-e54b-4920-b236-41b53b2a9b01	en-US	Process		Process	2018-10-17 01:21:43.247707	2018-10-17 01:21:43.247707
6730f08e-a4ac-488c-ac15-17b6883a12d4	de-DE	Process	shipping/customer	Prozess2	2018-10-17 01:20:25.746852	2018-10-17 01:20:25.746852
9bab054e-591a-4b49-80e1-6224ee64202f	en-US	Process	shipping/customer	Process2	2018-10-17 01:21:31.881317	2018-10-17 01:21:31.881317
\.


--
-- Data for Name: app_user; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_user (id, login, password, disabled, created, last_login, roles) FROM stdin;
d7df0f2c-9aa8-4845-b2bf-1d02abd3666e	admin	$2a$10$RcNKc79dFTvjLls02vA6ge/k6C.DFYi0IjLRKr0xKY6.0L3MwPtJW	f	2018-06-13 20:34:21.674191	2018-06-13 20:34:21.674191	ROLE_ADMIN, ROLE_USER, ROLE_TEST
af432487-a1b1-4f99-96d4-3b8e9796c95a	anonymous		t	2018-11-30 14:38:35.12544	\N	ROLE_ANONYMOUS
\.


--
-- Data for Name: app_user_config; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_user_config (login, user_id) FROM stdin;
\.


--
-- Data for Name: app_version; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.app_version (id, field_mask, owner_id, created, entity_type, entity_id, prev) FROM stdin;
\.


--
-- Data for Name: domain_field; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.domain_field (id, name, type, description, domain_type_id) FROM stdin;
\.


--
-- Data for Name: domain_field_meta; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.domain_field_meta (id, name, value, domain_field_id) FROM stdin;
\.


--
-- Data for Name: domain_type; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.domain_type (id, name, description, app_id) FROM stdin;
\.


--
-- Data for Name: domain_type_meta; Type: TABLE DATA; Schema: public; Owner: proto
--

COPY public.domain_type_meta (id, name, value, domain_type_id) FROM stdin;
\.


--
-- Name: app pk_app; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app
    ADD CONSTRAINT pk_app PRIMARY KEY (id);


--
-- Name: app_attachment pk_app_attachment; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_attachment
    ADD CONSTRAINT pk_app_attachment PRIMARY KEY (id);


--
-- Name: app_attachment_data pk_app_attachment_data; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_attachment_data
    ADD CONSTRAINT pk_app_attachment_data PRIMARY KEY (id);


--
-- Name: app_config pk_app_config; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_config
    ADD CONSTRAINT pk_app_config PRIMARY KEY (name);


--
-- Name: app_login pk_app_login; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_login
    ADD CONSTRAINT pk_app_login PRIMARY KEY (series);


--
-- Name: app_translation pk_app_translation; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_translation
    ADD CONSTRAINT pk_app_translation PRIMARY KEY (id);


--
-- Name: app_user pk_app_user; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT pk_app_user PRIMARY KEY (id);


--
-- Name: app_user_config pk_app_user_config; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_user_config
    ADD CONSTRAINT pk_app_user_config PRIMARY KEY (login);


--
-- Name: app_version pk_app_version; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_version
    ADD CONSTRAINT pk_app_version PRIMARY KEY (id);


--
-- Name: domain_field pk_domain_field; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_field
    ADD CONSTRAINT pk_domain_field PRIMARY KEY (id);


--
-- Name: domain_field_meta pk_domain_field_meta; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_field_meta
    ADD CONSTRAINT pk_domain_field_meta PRIMARY KEY (id);


--
-- Name: domain_type pk_domain_type; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_type
    ADD CONSTRAINT pk_domain_type PRIMARY KEY (id);


--
-- Name: domain_type_meta pk_domain_type_meta; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_type_meta
    ADD CONSTRAINT pk_domain_type_meta PRIMARY KEY (id);


--
-- Name: app_attachment_data uc_app_attachment_data_attachment_id; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_attachment_data
    ADD CONSTRAINT uc_app_attachment_data_attachment_id UNIQUE (attachment_id);


--
-- Name: app_translation uc_app_translation; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_translation
    ADD CONSTRAINT uc_app_translation UNIQUE (locale, tag, process_name);


--
-- Name: app_user uc_app_user_login; Type: CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_user
    ADD CONSTRAINT uc_app_user_login UNIQUE (login);


--
-- Name: app_attachment_data fk_app_attachment_data_attachment_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_attachment_data
    ADD CONSTRAINT fk_app_attachment_data_attachment_id FOREIGN KEY (attachment_id) REFERENCES public.app_attachment(id);


--
-- Name: app_user_config fk_app_user_config_user_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_user_config
    ADD CONSTRAINT fk_app_user_config_user_id FOREIGN KEY (user_id) REFERENCES public.app_user(id) ON DELETE CASCADE;


--
-- Name: app_version fk_app_version_owner_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.app_version
    ADD CONSTRAINT fk_app_version_owner_id FOREIGN KEY (owner_id) REFERENCES public.app_user(id);


--
-- Name: domain_field_meta fk_domain_field_meta_field_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_field_meta
    ADD CONSTRAINT fk_domain_field_meta_field_id FOREIGN KEY (domain_field_id) REFERENCES public.domain_field(id);


--
-- Name: domain_field fk_domain_field_type_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_field
    ADD CONSTRAINT fk_domain_field_type_id FOREIGN KEY (domain_type_id) REFERENCES public.domain_type(id);


--
-- Name: domain_type fk_domain_type_app_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_type
    ADD CONSTRAINT fk_domain_type_app_id FOREIGN KEY (app_id) REFERENCES public.app(id);


--
-- Name: domain_type_meta fk_domain_type_meta_type_id; Type: FK CONSTRAINT; Schema: public; Owner: proto
--

ALTER TABLE ONLY public.domain_type_meta
    ADD CONSTRAINT fk_domain_type_meta_type_id FOREIGN KEY (domain_type_id) REFERENCES public.domain_type(id);


COMMIT;

--
-- PostgreSQL database dump complete
--

