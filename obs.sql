PGDMP      6            
    {            obs    16.0    16.0 r    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    73778    obs    DATABASE     w   CREATE DATABASE obs WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Turkish_Turkey.1254';
    DROP DATABASE obs;
                postgres    false            `           1247    73780    durumlar    TYPE     �   CREATE TYPE public.durumlar AS ENUM (
    'Red Edildi',
    'Kabul Edildi',
    'Cevap Bekleniyor',
    'Başkası Onayladı',
    'Talep Gönderildi',
    'Talep Onaylandı',
    'Talep Beklemede'
);
    DROP TYPE public.durumlar;
       public          postgres    false            �            1259    73795    Anlasma    TABLE     �   CREATE TABLE public."Anlasma" (
    "anlasmaNo" integer NOT NULL,
    "ogrNo" integer NOT NULL,
    "sicilNo" integer NOT NULL,
    "dersID" integer NOT NULL,
    durum public.durumlar[] NOT NULL,
    gonderen boolean NOT NULL
);
    DROP TABLE public."Anlasma";
       public         heap    postgres    false    864            �            1259    73800    Anlasma_anlasmaNo_seq    SEQUENCE     �   CREATE SEQUENCE public."Anlasma_anlasmaNo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public."Anlasma_anlasmaNo_seq";
       public          postgres    false    215            �           0    0    Anlasma_anlasmaNo_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public."Anlasma_anlasmaNo_seq" OWNED BY public."Anlasma"."anlasmaNo";
          public          postgres    false    216            �            1259    73801    Belge    TABLE     �   CREATE TABLE public."Belge" (
    "ogrNo" integer NOT NULL,
    "dersID" integer NOT NULL,
    "hNot" character varying,
    index integer NOT NULL
);
    DROP TABLE public."Belge";
       public         heap    postgres    false            �            1259    73806    Belge_index_seq    SEQUENCE     �   CREATE SEQUENCE public."Belge_index_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."Belge_index_seq";
       public          postgres    false    217            �           0    0    Belge_index_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public."Belge_index_seq" OWNED BY public."Belge".index;
          public          postgres    false    218            �            1259    73807    Dersler    TABLE     �   CREATE TABLE public."Dersler" (
    "dersID" integer NOT NULL,
    "dersAd" character varying NOT NULL,
    "AKTS" double precision
);
    DROP TABLE public."Dersler";
       public         heap    postgres    false            �            1259    73812    Gecmis    TABLE     �   CREATE TABLE public."Gecmis" (
    "gecmisIndex" integer NOT NULL,
    "ogrNo" integer NOT NULL,
    "sicilNo" integer NOT NULL,
    "dersID" integer NOT NULL,
    durum public.durumlar[] NOT NULL,
    gonderen boolean NOT NULL
);
    DROP TABLE public."Gecmis";
       public         heap    postgres    false    864            �            1259    73817    Gecmis_gecmisIndex_seq    SEQUENCE     �   CREATE SEQUENCE public."Gecmis_gecmisIndex_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public."Gecmis_gecmisIndex_seq";
       public          postgres    false    220            �           0    0    Gecmis_gecmisIndex_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public."Gecmis_gecmisIndex_seq" OWNED BY public."Gecmis"."gecmisIndex";
          public          postgres    false    221            �            1259    73818    HFormul    TABLE        CREATE TABLE public."HFormul" (
    "sicilNo" integer NOT NULL,
    "dersID" integer NOT NULL,
    katsayi integer NOT NULL
);
    DROP TABLE public."HFormul";
       public         heap    postgres    false            �            1259    73821    HIlgi    TABLE     ~   CREATE TABLE public."HIlgi" (
    "hIndex" integer NOT NULL,
    "sicilNo" integer NOT NULL,
    "ilgiID" integer NOT NULL
);
    DROP TABLE public."HIlgi";
       public         heap    postgres    false            �            1259    73824    HIlgi_hIndex_seq    SEQUENCE     �   CREATE SEQUENCE public."HIlgi_hIndex_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public."HIlgi_hIndex_seq";
       public          postgres    false    223            �           0    0    HIlgi_hIndex_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public."HIlgi_hIndex_seq" OWNED BY public."HIlgi"."hIndex";
          public          postgres    false    224            �            1259    73825    Hocalar    TABLE     �   CREATE TABLE public."Hocalar" (
    "sicilNo" integer NOT NULL,
    "hAd" character varying(20) NOT NULL,
    "hSoyad" character varying(20) NOT NULL,
    "hSifre" character varying(20) NOT NULL,
    kontenjan integer
);
    DROP TABLE public."Hocalar";
       public         heap    postgres    false            �            1259    73828    Mesaj    TABLE     �   CREATE TABLE public."Mesaj" (
    "mesajNo" integer NOT NULL,
    kim integer NOT NULL,
    kime integer NOT NULL,
    mesaj character varying,
    "dersID" integer
);
    DROP TABLE public."Mesaj";
       public         heap    postgres    false            �            1259    73833    Mesaj_mesajNo_seq    SEQUENCE     �   CREATE SEQUENCE public."Mesaj_mesajNo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public."Mesaj_mesajNo_seq";
       public          postgres    false    226            �           0    0    Mesaj_mesajNo_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public."Mesaj_mesajNo_seq" OWNED BY public."Mesaj"."mesajNo";
          public          postgres    false    227            �            1259    73834    Notlar    TABLE     e   CREATE TABLE public."Notlar" (
    "hNot" character varying NOT NULL,
    "sNot" double precision
);
    DROP TABLE public."Notlar";
       public         heap    postgres    false            �            1259    73839    OIlgi    TABLE     ~   CREATE TABLE public."OIlgi" (
    "ogrIndex" integer NOT NULL,
    "ogrNo" integer NOT NULL,
    "ilgiID" integer NOT NULL
);
    DROP TABLE public."OIlgi";
       public         heap    postgres    false            �            1259    73842    OIlgi_ogrIndex_seq    SEQUENCE     �   CREATE SEQUENCE public."OIlgi_ogrIndex_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public."OIlgi_ogrIndex_seq";
       public          postgres    false    229            �           0    0    OIlgi_ogrIndex_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE public."OIlgi_ogrIndex_seq" OWNED BY public."OIlgi"."ogrIndex";
          public          postgres    false    230            �            1259    73843 
   Ogrenciler    TABLE       CREATE TABLE public."Ogrenciler" (
    "ogrNo" integer NOT NULL,
    "ogrAd" character varying NOT NULL,
    "ogrSoyad" character varying NOT NULL,
    "ogrSifre" character varying NOT NULL,
    "GNO" double precision,
    "toplamTalep" integer,
    "talepOnay" integer
);
     DROP TABLE public."Ogrenciler";
       public         heap    postgres    false            �            1259    73848    Ogrenciler_ogrNo_seq    SEQUENCE     �   CREATE SEQUENCE public."Ogrenciler_ogrNo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public."Ogrenciler_ogrNo_seq";
       public          postgres    false    231            �           0    0    Ogrenciler_ogrNo_seq    SEQUENCE OWNED BY     S   ALTER SEQUENCE public."Ogrenciler_ogrNo_seq" OWNED BY public."Ogrenciler"."ogrNo";
          public          postgres    false    232            �            1259    81963    Random    TABLE     �   CREATE TABLE public."Random" (
    "randomNo" integer NOT NULL,
    "ogrNo" integer NOT NULL,
    "sicilNo" integer NOT NULL,
    "dersID" integer NOT NULL
);
    DROP TABLE public."Random";
       public         heap    postgres    false            �            1259    81962    Random_randomNo_seq    SEQUENCE     �   CREATE SEQUENCE public."Random_randomNo_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE public."Random_randomNo_seq";
       public          postgres    false    239            �           0    0    Random_randomNo_seq    SEQUENCE OWNED BY     Q   ALTER SEQUENCE public."Random_randomNo_seq" OWNED BY public."Random"."randomNo";
          public          postgres    false    238            �            1259    73849    Sistem    TABLE     �   CREATE TABLE public."Sistem" (
    "karakterSiniri" integer,
    "aktifPasif" boolean,
    "sistemIndex" integer NOT NULL,
    "varsayilanTalep" integer,
    asama boolean NOT NULL
);
    DROP TABLE public."Sistem";
       public         heap    postgres    false            �            1259    73852    Yonetici    TABLE     b   CREATE TABLE public."Yonetici" (
    "yonID" integer NOT NULL,
    "yonSifre" integer NOT NULL
);
    DROP TABLE public."Yonetici";
       public         heap    postgres    false            �            1259    73855    dersHoca    TABLE     �   CREATE TABLE public."dersHoca" (
    "dersIndex" integer NOT NULL,
    "dersID" integer NOT NULL,
    "sicilNo" integer NOT NULL
);
    DROP TABLE public."dersHoca";
       public         heap    postgres    false            �            1259    73858    dersHoca_dersIndex_seq    SEQUENCE     �   CREATE SEQUENCE public."dersHoca_dersIndex_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public."dersHoca_dersIndex_seq";
       public          postgres    false    235            �           0    0    dersHoca_dersIndex_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE public."dersHoca_dersIndex_seq" OWNED BY public."dersHoca"."dersIndex";
          public          postgres    false    236            �            1259    73859    ilgiAlanlari    TABLE     o   CREATE TABLE public."ilgiAlanlari" (
    "ilgiID" integer NOT NULL,
    "ilgiAd" character varying NOT NULL
);
 "   DROP TABLE public."ilgiAlanlari";
       public         heap    postgres    false            �           2604    73864    Anlasma anlasmaNo    DEFAULT     |   ALTER TABLE ONLY public."Anlasma" ALTER COLUMN "anlasmaNo" SET DEFAULT nextval('public."Anlasma_anlasmaNo_seq"'::regclass);
 D   ALTER TABLE public."Anlasma" ALTER COLUMN "anlasmaNo" DROP DEFAULT;
       public          postgres    false    216    215            �           2604    73865    Belge index    DEFAULT     n   ALTER TABLE ONLY public."Belge" ALTER COLUMN index SET DEFAULT nextval('public."Belge_index_seq"'::regclass);
 <   ALTER TABLE public."Belge" ALTER COLUMN index DROP DEFAULT;
       public          postgres    false    218    217            �           2604    73866    Gecmis gecmisIndex    DEFAULT     ~   ALTER TABLE ONLY public."Gecmis" ALTER COLUMN "gecmisIndex" SET DEFAULT nextval('public."Gecmis_gecmisIndex_seq"'::regclass);
 E   ALTER TABLE public."Gecmis" ALTER COLUMN "gecmisIndex" DROP DEFAULT;
       public          postgres    false    221    220            �           2604    73867    HIlgi hIndex    DEFAULT     r   ALTER TABLE ONLY public."HIlgi" ALTER COLUMN "hIndex" SET DEFAULT nextval('public."HIlgi_hIndex_seq"'::regclass);
 ?   ALTER TABLE public."HIlgi" ALTER COLUMN "hIndex" DROP DEFAULT;
       public          postgres    false    224    223            �           2604    73868    Mesaj mesajNo    DEFAULT     t   ALTER TABLE ONLY public."Mesaj" ALTER COLUMN "mesajNo" SET DEFAULT nextval('public."Mesaj_mesajNo_seq"'::regclass);
 @   ALTER TABLE public."Mesaj" ALTER COLUMN "mesajNo" DROP DEFAULT;
       public          postgres    false    227    226            �           2604    73869    OIlgi ogrIndex    DEFAULT     v   ALTER TABLE ONLY public."OIlgi" ALTER COLUMN "ogrIndex" SET DEFAULT nextval('public."OIlgi_ogrIndex_seq"'::regclass);
 A   ALTER TABLE public."OIlgi" ALTER COLUMN "ogrIndex" DROP DEFAULT;
       public          postgres    false    230    229            �           2604    73870    Ogrenciler ogrNo    DEFAULT     z   ALTER TABLE ONLY public."Ogrenciler" ALTER COLUMN "ogrNo" SET DEFAULT nextval('public."Ogrenciler_ogrNo_seq"'::regclass);
 C   ALTER TABLE public."Ogrenciler" ALTER COLUMN "ogrNo" DROP DEFAULT;
       public          postgres    false    232    231            �           2604    81966    Random randomNo    DEFAULT     x   ALTER TABLE ONLY public."Random" ALTER COLUMN "randomNo" SET DEFAULT nextval('public."Random_randomNo_seq"'::regclass);
 B   ALTER TABLE public."Random" ALTER COLUMN "randomNo" DROP DEFAULT;
       public          postgres    false    239    238    239            �           2604    73871    dersHoca dersIndex    DEFAULT     ~   ALTER TABLE ONLY public."dersHoca" ALTER COLUMN "dersIndex" SET DEFAULT nextval('public."dersHoca_dersIndex_seq"'::regclass);
 E   ALTER TABLE public."dersHoca" ALTER COLUMN "dersIndex" DROP DEFAULT;
       public          postgres    false    236    235            h          0    73795    Anlasma 
   TABLE DATA           _   COPY public."Anlasma" ("anlasmaNo", "ogrNo", "sicilNo", "dersID", durum, gonderen) FROM stdin;
    public          postgres    false    215   �       j          0    73801    Belge 
   TABLE DATA           C   COPY public."Belge" ("ogrNo", "dersID", "hNot", index) FROM stdin;
    public          postgres    false    217   ,�       l          0    73807    Dersler 
   TABLE DATA           ?   COPY public."Dersler" ("dersID", "dersAd", "AKTS") FROM stdin;
    public          postgres    false    219   I�       m          0    73812    Gecmis 
   TABLE DATA           `   COPY public."Gecmis" ("gecmisIndex", "ogrNo", "sicilNo", "dersID", durum, gonderen) FROM stdin;
    public          postgres    false    220   �       o          0    73818    HFormul 
   TABLE DATA           A   COPY public."HFormul" ("sicilNo", "dersID", katsayi) FROM stdin;
    public          postgres    false    222   ,�       p          0    73821    HIlgi 
   TABLE DATA           @   COPY public."HIlgi" ("hIndex", "sicilNo", "ilgiID") FROM stdin;
    public          postgres    false    223   ��       r          0    73825    Hocalar 
   TABLE DATA           T   COPY public."Hocalar" ("sicilNo", "hAd", "hSoyad", "hSifre", kontenjan) FROM stdin;
    public          postgres    false    225   s�       s          0    73828    Mesaj 
   TABLE DATA           H   COPY public."Mesaj" ("mesajNo", kim, kime, mesaj, "dersID") FROM stdin;
    public          postgres    false    226   N�       u          0    73834    Notlar 
   TABLE DATA           2   COPY public."Notlar" ("hNot", "sNot") FROM stdin;
    public          postgres    false    228   k�       v          0    73839    OIlgi 
   TABLE DATA           @   COPY public."OIlgi" ("ogrIndex", "ogrNo", "ilgiID") FROM stdin;
    public          postgres    false    229   ��       x          0    73843 
   Ogrenciler 
   TABLE DATA           s   COPY public."Ogrenciler" ("ogrNo", "ogrAd", "ogrSoyad", "ogrSifre", "GNO", "toplamTalep", "talepOnay") FROM stdin;
    public          postgres    false    231   ։       �          0    81963    Random 
   TABLE DATA           L   COPY public."Random" ("randomNo", "ogrNo", "sicilNo", "dersID") FROM stdin;
    public          postgres    false    239   Ɗ       z          0    73849    Sistem 
   TABLE DATA           k   COPY public."Sistem" ("karakterSiniri", "aktifPasif", "sistemIndex", "varsayilanTalep", asama) FROM stdin;
    public          postgres    false    233   �       {          0    73852    Yonetici 
   TABLE DATA           9   COPY public."Yonetici" ("yonID", "yonSifre") FROM stdin;
    public          postgres    false    234   �       |          0    73855    dersHoca 
   TABLE DATA           F   COPY public."dersHoca" ("dersIndex", "dersID", "sicilNo") FROM stdin;
    public          postgres    false    235   2�       ~          0    73859    ilgiAlanlari 
   TABLE DATA           <   COPY public."ilgiAlanlari" ("ilgiID", "ilgiAd") FROM stdin;
    public          postgres    false    237   ��       �           0    0    Anlasma_anlasmaNo_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public."Anlasma_anlasmaNo_seq"', 1035, true);
          public          postgres    false    216            �           0    0    Belge_index_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public."Belge_index_seq"', 66479, true);
          public          postgres    false    218            �           0    0    Gecmis_gecmisIndex_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public."Gecmis_gecmisIndex_seq"', 350, true);
          public          postgres    false    221            �           0    0    HIlgi_hIndex_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public."HIlgi_hIndex_seq"', 206, true);
          public          postgres    false    224            �           0    0    Mesaj_mesajNo_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public."Mesaj_mesajNo_seq"', 185, true);
          public          postgres    false    227            �           0    0    OIlgi_ogrIndex_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('public."OIlgi_ogrIndex_seq"', 149, true);
          public          postgres    false    230            �           0    0    Ogrenciler_ogrNo_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public."Ogrenciler_ogrNo_seq"', 351, true);
          public          postgres    false    232            �           0    0    Random_randomNo_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public."Random_randomNo_seq"', 4693, true);
          public          postgres    false    238            �           0    0    dersHoca_dersIndex_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public."dersHoca_dersIndex_seq"', 141, true);
          public          postgres    false    236            �           2606    73873    Anlasma Anlasma_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public."Anlasma"
    ADD CONSTRAINT "Anlasma_pkey" PRIMARY KEY ("anlasmaNo");
 B   ALTER TABLE ONLY public."Anlasma" DROP CONSTRAINT "Anlasma_pkey";
       public            postgres    false    215            �           2606    73875    Belge Belge_pkey 
   CONSTRAINT     U   ALTER TABLE ONLY public."Belge"
    ADD CONSTRAINT "Belge_pkey" PRIMARY KEY (index);
 >   ALTER TABLE ONLY public."Belge" DROP CONSTRAINT "Belge_pkey";
       public            postgres    false    217            �           2606    73877    Dersler Dersler_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public."Dersler"
    ADD CONSTRAINT "Dersler_pkey" PRIMARY KEY ("dersID");
 B   ALTER TABLE ONLY public."Dersler" DROP CONSTRAINT "Dersler_pkey";
       public            postgres    false    219            �           2606    73879    Gecmis Gecmis_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public."Gecmis"
    ADD CONSTRAINT "Gecmis_pkey" PRIMARY KEY ("gecmisIndex");
 @   ALTER TABLE ONLY public."Gecmis" DROP CONSTRAINT "Gecmis_pkey";
       public            postgres    false    220            �           2606    73881    HIlgi HIlgi_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public."HIlgi"
    ADD CONSTRAINT "HIlgi_pkey" PRIMARY KEY ("hIndex");
 >   ALTER TABLE ONLY public."HIlgi" DROP CONSTRAINT "HIlgi_pkey";
       public            postgres    false    223            �           2606    73883    Hocalar Hocalar_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public."Hocalar"
    ADD CONSTRAINT "Hocalar_pkey" PRIMARY KEY ("sicilNo");
 B   ALTER TABLE ONLY public."Hocalar" DROP CONSTRAINT "Hocalar_pkey";
       public            postgres    false    225            �           2606    73885    Mesaj Mesaj_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public."Mesaj"
    ADD CONSTRAINT "Mesaj_pkey" PRIMARY KEY ("mesajNo");
 >   ALTER TABLE ONLY public."Mesaj" DROP CONSTRAINT "Mesaj_pkey";
       public            postgres    false    226            �           2606    73887    Notlar Notlar_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public."Notlar"
    ADD CONSTRAINT "Notlar_pkey" PRIMARY KEY ("hNot");
 @   ALTER TABLE ONLY public."Notlar" DROP CONSTRAINT "Notlar_pkey";
       public            postgres    false    228            �           2606    73889    OIlgi OIlgi_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public."OIlgi"
    ADD CONSTRAINT "OIlgi_pkey" PRIMARY KEY ("ogrIndex");
 >   ALTER TABLE ONLY public."OIlgi" DROP CONSTRAINT "OIlgi_pkey";
       public            postgres    false    229            �           2606    73891    Ogrenciler Ogrenciler_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public."Ogrenciler"
    ADD CONSTRAINT "Ogrenciler_pkey" PRIMARY KEY ("ogrNo");
 H   ALTER TABLE ONLY public."Ogrenciler" DROP CONSTRAINT "Ogrenciler_pkey";
       public            postgres    false    231            �           2606    81968    Random Random_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public."Random"
    ADD CONSTRAINT "Random_pkey" PRIMARY KEY ("randomNo");
 @   ALTER TABLE ONLY public."Random" DROP CONSTRAINT "Random_pkey";
       public            postgres    false    239            �           2606    73893    Sistem Sistem_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public."Sistem"
    ADD CONSTRAINT "Sistem_pkey" PRIMARY KEY ("sistemIndex");
 @   ALTER TABLE ONLY public."Sistem" DROP CONSTRAINT "Sistem_pkey";
       public            postgres    false    233            �           2606    73895    Yonetici Yonetici_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public."Yonetici"
    ADD CONSTRAINT "Yonetici_pkey" PRIMARY KEY ("yonID");
 D   ALTER TABLE ONLY public."Yonetici" DROP CONSTRAINT "Yonetici_pkey";
       public            postgres    false    234            �           2606    73897    dersHoca dersHoca_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public."dersHoca"
    ADD CONSTRAINT "dersHoca_pkey" PRIMARY KEY ("dersIndex");
 D   ALTER TABLE ONLY public."dersHoca" DROP CONSTRAINT "dersHoca_pkey";
       public            postgres    false    235            �           2606    73899    ilgiAlanlari ilgiAlanlari_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public."ilgiAlanlari"
    ADD CONSTRAINT "ilgiAlanlari_pkey" PRIMARY KEY ("ilgiID");
 L   ALTER TABLE ONLY public."ilgiAlanlari" DROP CONSTRAINT "ilgiAlanlari_pkey";
       public            postgres    false    237            �           1259    73900    fki_dersID_fkey    INDEX     I   CREATE INDEX "fki_dersID_fkey" ON public."Belge" USING btree ("dersID");
 %   DROP INDEX public."fki_dersID_fkey";
       public            postgres    false    217            �           1259    73901    fki_hNot_fkey    INDEX     E   CREATE INDEX "fki_hNot_fkey" ON public."Belge" USING btree ("hNot");
 #   DROP INDEX public."fki_hNot_fkey";
       public            postgres    false    217            �           1259    73902    fki_ilgiID_fkey    INDEX     I   CREATE INDEX "fki_ilgiID_fkey" ON public."OIlgi" USING btree ("ilgiID");
 %   DROP INDEX public."fki_ilgiID_fkey";
       public            postgres    false    229            �           1259    73903    fki_ogrNo_fkey    INDEX     G   CREATE INDEX "fki_ogrNo_fkey" ON public."Belge" USING btree ("ogrNo");
 $   DROP INDEX public."fki_ogrNo_fkey";
       public            postgres    false    217            �           1259    73904    fki_sicilNo_fkey    INDEX     N   CREATE INDEX "fki_sicilNo_fkey" ON public."dersHoca" USING btree ("sicilNo");
 &   DROP INDEX public."fki_sicilNo_fkey";
       public            postgres    false    235            �           2606    73905    Belge dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Belge"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 ?   ALTER TABLE ONLY public."Belge" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    217    4777    219            �           2606    73910    HFormul dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."HFormul"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 A   ALTER TABLE ONLY public."HFormul" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    219    222    4777            �           2606    73915    dersHoca dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."dersHoca"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 B   ALTER TABLE ONLY public."dersHoca" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    235    4777    219            �           2606    73920    Anlasma dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Anlasma"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 A   ALTER TABLE ONLY public."Anlasma" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    215    4777    219            �           2606    73925    Mesaj dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Mesaj"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 ?   ALTER TABLE ONLY public."Mesaj" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    226    4777    219            �           2606    73930    Gecmis dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Gecmis"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 @   ALTER TABLE ONLY public."Gecmis" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    219    220    4777            �           2606    81979    Random dersID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Random"
    ADD CONSTRAINT "dersID_fkey" FOREIGN KEY ("dersID") REFERENCES public."Dersler"("dersID") NOT VALID;
 @   ALTER TABLE ONLY public."Random" DROP CONSTRAINT "dersID_fkey";
       public          postgres    false    239    219    4777            �           2606    73935    Belge hNot_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Belge"
    ADD CONSTRAINT "hNot_fkey" FOREIGN KEY ("hNot") REFERENCES public."Notlar"("hNot") NOT VALID;
 =   ALTER TABLE ONLY public."Belge" DROP CONSTRAINT "hNot_fkey";
       public          postgres    false    217    4787    228            �           2606    73940    HIlgi ilgiID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."HIlgi"
    ADD CONSTRAINT "ilgiID_fkey" FOREIGN KEY ("ilgiID") REFERENCES public."ilgiAlanlari"("ilgiID") NOT VALID;
 ?   ALTER TABLE ONLY public."HIlgi" DROP CONSTRAINT "ilgiID_fkey";
       public          postgres    false    223    4801    237            �           2606    73945    OIlgi ilgiID_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."OIlgi"
    ADD CONSTRAINT "ilgiID_fkey" FOREIGN KEY ("ilgiID") REFERENCES public."ilgiAlanlari"("ilgiID") NOT VALID;
 ?   ALTER TABLE ONLY public."OIlgi" DROP CONSTRAINT "ilgiID_fkey";
       public          postgres    false    229    4801    237            �           2606    73950    Belge ogrNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Belge"
    ADD CONSTRAINT "ogrNo_fkey" FOREIGN KEY ("ogrNo") REFERENCES public."Ogrenciler"("ogrNo") NOT VALID;
 >   ALTER TABLE ONLY public."Belge" DROP CONSTRAINT "ogrNo_fkey";
       public          postgres    false    217    4792    231            �           2606    73955    OIlgi ogrNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."OIlgi"
    ADD CONSTRAINT "ogrNo_fkey" FOREIGN KEY ("ogrNo") REFERENCES public."Ogrenciler"("ogrNo") NOT VALID;
 >   ALTER TABLE ONLY public."OIlgi" DROP CONSTRAINT "ogrNo_fkey";
       public          postgres    false    229    4792    231            �           2606    73960    Anlasma ogrNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Anlasma"
    ADD CONSTRAINT "ogrNo_fkey" FOREIGN KEY ("ogrNo") REFERENCES public."Ogrenciler"("ogrNo") NOT VALID;
 @   ALTER TABLE ONLY public."Anlasma" DROP CONSTRAINT "ogrNo_fkey";
       public          postgres    false    215    4792    231            �           2606    73965    Gecmis ogrNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Gecmis"
    ADD CONSTRAINT "ogrNo_fkey" FOREIGN KEY ("ogrNo") REFERENCES public."Ogrenciler"("ogrNo") NOT VALID;
 ?   ALTER TABLE ONLY public."Gecmis" DROP CONSTRAINT "ogrNo_fkey";
       public          postgres    false    220    4792    231            �           2606    81969    Random ogrNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Random"
    ADD CONSTRAINT "ogrNo_fkey" FOREIGN KEY ("ogrNo") REFERENCES public."Ogrenciler"("ogrNo") NOT VALID;
 ?   ALTER TABLE ONLY public."Random" DROP CONSTRAINT "ogrNo_fkey";
       public          postgres    false    231    239    4792            �           2606    73970    HFormul sicilNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."HFormul"
    ADD CONSTRAINT "sicilNo_fkey" FOREIGN KEY ("sicilNo") REFERENCES public."Hocalar"("sicilNo") NOT VALID;
 B   ALTER TABLE ONLY public."HFormul" DROP CONSTRAINT "sicilNo_fkey";
       public          postgres    false    225    222    4783            �           2606    73975    HIlgi sicilNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."HIlgi"
    ADD CONSTRAINT "sicilNo_fkey" FOREIGN KEY ("sicilNo") REFERENCES public."Hocalar"("sicilNo") NOT VALID;
 @   ALTER TABLE ONLY public."HIlgi" DROP CONSTRAINT "sicilNo_fkey";
       public          postgres    false    4783    225    223            �           2606    73980    dersHoca sicilNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."dersHoca"
    ADD CONSTRAINT "sicilNo_fkey" FOREIGN KEY ("sicilNo") REFERENCES public."Hocalar"("sicilNo") NOT VALID;
 C   ALTER TABLE ONLY public."dersHoca" DROP CONSTRAINT "sicilNo_fkey";
       public          postgres    false    235    225    4783            �           2606    73985    Anlasma sicilNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Anlasma"
    ADD CONSTRAINT "sicilNo_fkey" FOREIGN KEY ("sicilNo") REFERENCES public."Hocalar"("sicilNo") NOT VALID;
 B   ALTER TABLE ONLY public."Anlasma" DROP CONSTRAINT "sicilNo_fkey";
       public          postgres    false    225    4783    215            �           2606    73990    Gecmis sicilNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Gecmis"
    ADD CONSTRAINT "sicilNo_fkey" FOREIGN KEY ("sicilNo") REFERENCES public."Hocalar"("sicilNo") NOT VALID;
 A   ALTER TABLE ONLY public."Gecmis" DROP CONSTRAINT "sicilNo_fkey";
       public          postgres    false    225    220    4783            �           2606    81974    Random sicilNo_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public."Random"
    ADD CONSTRAINT "sicilNo_fkey" FOREIGN KEY ("sicilNo") REFERENCES public."Hocalar"("sicilNo") NOT VALID;
 A   ALTER TABLE ONLY public."Random" DROP CONSTRAINT "sicilNo_fkey";
       public          postgres    false    225    239    4783            h      x������ � �      j      x������ � �      l   �  x��SMo�@=���C+��HJE	MT�JH��L�^��A2�?�c��7��ՙb��Qo�|��{�/��tb!Ր�<��0y�J2��2��*�s%o��#Wi�PaWs�E=m�x�y."�o��y��*�T1�U��E)9��̠ +��A���i��h�<C���<?#XT+ͣ/a<��?�y.��=?�w��A$z9���&�z։r���$�aF�,>�<�G��h`�oG��ߩ(�1<*_CFb&�� ;E�!�����j��r�6É�c��y�k����%$��wҜ�ݪr3"ͅ����Li��A�:�������$Z
rL���N3�"]�.M�:������LP��N���� <�qR��@6�b.��{e�dX(-��$zKT셝-�\ϕq�b�g9�r�GCU>�J��yL�g�-׆�/��MU�Wc]��cqB.��'r瓺"䊨%z	�L�OX�D��=�%�[b�����>�� �hS%��@�l�)d�BA[\�Q����YQ(N�Jr;5D�_.���-.H��\�ގC����a,�4�ь�^��v�abq�L�O^������_�����v8ڻ�h���[%o�W�7��=��=��sAK�8Ҵ�s��p��aB�W���ZM�\�	\@VL�#o5���ʐ+#�Ue��༭	GS��BD�Ϡ\g��{����;�y?>y���^�      m      x������ � �      o   J   x��λ	�0���4L���.����K��C��P(,���05?.�޺&F���_�-�'�Y�Q��Ѱ�����M      p   �   x��ɕ1���`�yŐ��?��|RQ��6�m��o��5�銉 m�٦�gA�m�q��+W�H��F
RG�ƈ�Йm�Zo����W��D�v\������څ� �n�-4�ր	/�!�)7�y��:��ϽrYkl	y4��Pt�Mhf����9ZVr�Z�M�k�Y:Y�p�h�Q�Щ�o���D�O�����?I��G`�����~����nH�      r   �   x�=�1n�@E��Sp��Bi�PX��
�f�,�j�Z� q�phr��{1k���G�?��q�yPޮ+:RwF�>���)�A�Ga+v��� ��S�����Xz���Y�wIŜ`�\TJ�M*�;V���h�sOI�P�FE�t� j{�?·��F��ڤ��k�,+3U�`�38U�Q6>ңdq�x�*�C��[�ew��U/      s      x������ � �      u   >   x�ƹ�0��Ɛ�d =PJ/�����ñ��lf`�X��ԫ0�ӻa���$m�
u      v      x������ � �      x   �   x�E�=n�0�����߱h����E��T���F��:�c�dɔM�ʨ6����(�x6��`g��y4=R(Y�Hh�*����g�.W-�	�ɷ���8�5Q�,��ht�a���hY�/�����`��Lր�S���1��y%�W���v�ɞy��|�jl����_t�-(�j�n��_�}�S��p�'���#��#dBa묧��FZ2y�B�_L�_�      �      x������ � �      z      x�34�,�4�4�,����� ��      {      x�3426�4162����� �P      |   _   x����@���b$j�\����@�UOד#�d]����`lc�c4��"5����ҒEZm��4Hk���:i]�t��w���"�t'      ~   �   x�U�;NC1E��*f���-E�@�"!�hF0���9&�����*w���(����ފ���!�V9���w��	��w�+�R�8a�-�9,ߕ���v�V�V=>pw		qaY)ZqW�H{�=�kX�P��a9r�7pe�v�{7�C���z�b����%�s�t+�l%�����Hz��������E~G�Ϝs?��g�     