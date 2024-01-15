CREATE SEQUENCE annonces_idannonce_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE categories_idcategorie_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE couleurs_idcouleur_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE etatannonces_idetatannonce_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE favoris_idfavoris_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE marques_idmarque_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE modeles_idmodele_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE photos_idphoto_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE sessions_idsession_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE typeoccasions_idtypeoccasion_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE utilisateurs_idutilisateur_seq START WITH 1 INCREMENT BY 1;

CREATE  TABLE categories (
                             idcategorie          integer DEFAULT nextval('categories_idcategorie_seq'::regclass) NOT NULL  ,
                             nomcategorie         varchar(255)  NOT NULL  ,
                             CONSTRAINT pk_categories PRIMARY KEY ( idcategorie )
);

CREATE  TABLE commission_defaut (
                                    valeur               numeric(10,2) DEFAULT 20 NOT NULL
);

CREATE  TABLE couleurs (
                           idcouleur            integer DEFAULT nextval('couleurs_idcouleur_seq'::regclass) NOT NULL  ,
                           nomcouleur           varchar(255)  NOT NULL  ,
                           CONSTRAINT pk_couleurs PRIMARY KEY ( idcouleur )
);

CREATE  TABLE marques (
                          idmarque             integer DEFAULT nextval('marques_idmarque_seq'::regclass) NOT NULL  ,
                          nommarque            varchar(255)  NOT NULL  ,
                          CONSTRAINT pk_marques PRIMARY KEY ( idmarque )
);

CREATE  TABLE modeles (
                          idmodele             integer DEFAULT nextval('modeles_idmodele_seq'::regclass) NOT NULL  ,
                          nommodele            varchar(255)  NOT NULL  ,
                          CONSTRAINT pk_modeles PRIMARY KEY ( idmodele )
);

CREATE  TABLE typeoccasions (
                                idtypeoccasion       integer DEFAULT nextval('typeoccasions_idtypeoccasion_seq'::regclass) NOT NULL  ,
                                nomtypeoccasion      varchar(255)  NOT NULL  ,
                                CONSTRAINT pk_typeoccasions PRIMARY KEY ( idtypeoccasion )
);

CREATE  TABLE utilisateurs (
                               idutilisateur        integer DEFAULT nextval('utilisateurs_idutilisateur_seq'::regclass) NOT NULL  ,
                               email                varchar(255)  NOT NULL  ,
                               motdepasse           varchar(255)  NOT NULL  ,
                               isadmin              integer  NOT NULL  ,
                               CONSTRAINT pk_utilisateurs PRIMARY KEY ( idutilisateur )
);

CREATE  TABLE annonces (
                           idannonce            integer DEFAULT nextval('annonces_idannonce_seq'::regclass) NOT NULL  ,
                           idutilisateur        integer  NOT NULL  ,
                           idtypeoccasion       integer  NOT NULL  ,
                           idcategorie          integer  NOT NULL  ,
                           idcouleur            integer  NOT NULL  ,
                           idmodele             integer  NOT NULL  ,
                           idmarque             integer  NOT NULL  ,
                           prix                 numeric(10,2)  NOT NULL  ,
                           pourcentagecommission numeric(10,2) DEFAULT 0 NOT NULL  ,
                           description          text  NOT NULL  ,
                           etatannonce          integer DEFAULT 0 NOT NULL  ,
                           dateheurecreation    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
                           CONSTRAINT pk_annonces PRIMARY KEY ( idannonce )
);

CREATE  TABLE etatannonces (
                               idetatannonce        integer DEFAULT nextval('etatannonces_idetatannonce_seq'::regclass) NOT NULL  ,
                               idannonce            integer  NOT NULL  ,
                               typeetat             integer  NOT NULL  ,
                               dateheureetat        timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
                               idutilisateur        integer  NOT NULL  ,
                               CONSTRAINT pk_etatannonces PRIMARY KEY ( idetatannonce )
);

CREATE  TABLE favoris (
                          idfavoris            integer DEFAULT nextval('favoris_idfavoris_seq'::regclass) NOT NULL  ,
                          idutilisateur        integer  NOT NULL  ,
                          idannonce            integer  NOT NULL  ,
                          CONSTRAINT pk_favoris PRIMARY KEY ( idfavoris )
);

CREATE  TABLE photos (
                         idphoto              integer DEFAULT nextval('photos_idphoto_seq'::regclass) NOT NULL  ,
                         idannonce            integer  NOT NULL  ,
                         repertoire           varchar(255)  NOT NULL  ,
                         CONSTRAINT pk_photos PRIMARY KEY ( idphoto )
);

CREATE  TABLE sessions (
                           idsession            integer DEFAULT nextval('sessions_idsession_seq'::regclass) NOT NULL  ,
                           idutilisateur        integer  NOT NULL  ,
                           dateheurelogin       timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
                           code                 varchar(255)  NOT NULL  ,
                           isconnected          integer DEFAULT 1 NOT NULL  ,
                           token                text  NOT NULL  ,
                           CONSTRAINT pk_sessions PRIMARY KEY ( idsession )
);

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_categories FOREIGN KEY ( idcategorie ) REFERENCES categories( idcategorie );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_couleurs FOREIGN KEY ( idcouleur ) REFERENCES couleurs( idcouleur );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_marques FOREIGN KEY ( idmarque ) REFERENCES marques( idmarque );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_modeles FOREIGN KEY ( idmodele ) REFERENCES modeles( idmodele );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_typeoccasions FOREIGN KEY ( idtypeoccasion ) REFERENCES typeoccasions( idtypeoccasion );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

ALTER TABLE etatannonces ADD CONSTRAINT fk_etatannonces_annonces FOREIGN KEY ( idannonce ) REFERENCES annonces( idannonce );

ALTER TABLE etatannonces ADD CONSTRAINT fk_etatannonces_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

ALTER TABLE favoris ADD CONSTRAINT fk_favoris_annonces FOREIGN KEY ( idannonce ) REFERENCES annonces( idannonce );

ALTER TABLE favoris ADD CONSTRAINT fk_favoris_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

ALTER TABLE photos ADD CONSTRAINT fk_photos_annonces FOREIGN KEY ( idannonce ) REFERENCES annonces( idannonce );

ALTER TABLE sessions ADD CONSTRAINT fk_sessions_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

CREATE VIEW v_annonce_commission AS SELECT a.idannonce,
                                           a.idutilisateur,
                                           a.idtypeoccasion,
                                           a.idcategorie,
                                           a.idcouleur,
                                           a.idmodele,
                                           a.idmarque,
                                           a.prix,
                                           a.pourcentagecommission,
                                           a.description,
                                           a.etatannonce,
                                           a.dateheurecreation,
                                           e.typeetat,
                                           e.dateheureetat,
                                           e.idutilisateur AS idutilisateuretat,
                                           (a.prix * (a.pourcentagecommission / (100)::numeric)) AS commission
                                    FROM (annonces a
                                        JOIN etatannonces e ON ((e.idannonce = a.idannonce)));

CREATE VIEW v_chiffre_affaire_defaut AS SELECT sum(v_annonce_commission.commission) AS total_commission
                                        FROM v_annonce_commission
                                        WHERE ((v_annonce_commission.typeetat = 100) AND (v_annonce_commission.dateheureetat >= date_trunc('MONTH'::text, (CURRENT_DATE)::timestamp with time zone)) AND (v_annonce_commission.dateheureetat <= ((date_trunc('MONTH'::text, (CURRENT_DATE)::timestamp with time zone) + '1 mon'::interval) - '1 day'::interval)));

CREATE VIEW v_commission_defaut AS SELECT max(v_annonce_commission.commission) AS max_commission,
                                          avg(v_annonce_commission.commission) AS moyenne_commission,
                                          min(v_annonce_commission.commission) AS min_commission
                                   FROM v_annonce_commission
                                   WHERE ((v_annonce_commission.typeetat = 100) AND (v_annonce_commission.dateheureetat >= date_trunc('MONTH'::text, (CURRENT_DATE)::timestamp with time zone)) AND (v_annonce_commission.dateheureetat <= ((date_trunc('MONTH'::text, (CURRENT_DATE)::timestamp with time zone) + '1 mon'::interval) - '1 day'::interval)));

INSERT INTO categories( idcategorie, nomcategorie ) VALUES (DEFAULT , 'Voitures de Luxe');
INSERT INTO categories( idcategorie, nomcategorie ) VALUES (DEFAULT , 'Vehicules Electriques');
INSERT INTO categories( idcategorie, nomcategorie ) VALUES (DEFAULT , 'Vehicules Hybrides');
INSERT INTO categories( idcategorie, nomcategorie ) VALUES (DEFAULT , 'Vehicules Utilitaires');
INSERT INTO categories( idcategorie, nomcategorie ) VALUES (DEFAULT , 'Vehicules Sportifs');
INSERT INTO commission_defaut( valeur ) VALUES ( 20 );
INSERT INTO couleurs( idcouleur, nomcouleur ) VALUES (DEFAULT , 'Rouge');
INSERT INTO couleurs( idcouleur, nomcouleur ) VALUES (DEFAULT , 'Bleu');
INSERT INTO couleurs( idcouleur, nomcouleur ) VALUES (DEFAULT , 'Noir');
INSERT INTO couleurs( idcouleur, nomcouleur ) VALUES (DEFAULT , 'Blanc');
INSERT INTO couleurs( idcouleur, nomcouleur ) VALUES (DEFAULT , 'Gris');
INSERT INTO marques( idmarque, nommarque ) VALUES (DEFAULT , 'Toyota');
INSERT INTO marques( idmarque, nommarque ) VALUES (DEFAULT , 'Honda');
INSERT INTO marques( idmarque, nommarque ) VALUES (DEFAULT , 'Ford');
INSERT INTO marques( idmarque, nommarque ) VALUES (DEFAULT , 'BMW');
INSERT INTO marques( idmarque, nommarque ) VALUES (DEFAULT , 'Mercedes-Benz');
INSERT INTO modeles( idmodele, nommodele ) VALUES (DEFAULT , 'Sedan');
INSERT INTO modeles( idmodele, nommodele ) VALUES (DEFAULT , 'SUV');
INSERT INTO modeles( idmodele, nommodele ) VALUES (DEFAULT , 'Coup‚');
INSERT INTO modeles( idmodele, nommodele ) VALUES (DEFAULT , 'Cabriolet');
INSERT INTO modeles( idmodele, nommodele ) VALUES (DEFAULT , 'Hatchback');
INSERT INTO typeoccasions( idtypeoccasion, nomtypeoccasion ) VALUES (DEFAULT , 'Occasion Standard');
INSERT INTO typeoccasions( idtypeoccasion, nomtypeoccasion ) VALUES (DEFAULT , 'Vente Flash');
INSERT INTO typeoccasions( idtypeoccasion, nomtypeoccasion ) VALUES (DEFAULT , 'Liquidation');
INSERT INTO typeoccasions( idtypeoccasion, nomtypeoccasion ) VALUES (DEFAULT , 'Promotion');
INSERT INTO typeoccasions( idtypeoccasion, nomtypeoccasion ) VALUES (DEFAULT , 'Sp‚cial');
INSERT INTO utilisateurs( idutilisateur, email, motdepasse, isadmin ) VALUES (DEFAULT , 'admin@gmail.com', '1234', 1);
INSERT INTO utilisateurs( idutilisateur, email, motdepasse, isadmin ) VALUES (DEFAULT , 'u1@gmail.com', '1234', 0);
INSERT INTO utilisateurs( idutilisateur, email, motdepasse, isadmin ) VALUES (DEFAULT , 'u2@gmail.com', '1234', 0);
INSERT INTO utilisateurs( idutilisateur, email, motdepasse, isadmin ) VALUES (DEFAULT , 'u3@gmail.com', '1234', 0);
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 2, 4, 5, 2, 1, 4, 28000000, 0, 'Cabriolet BMW … vendre, offre speciale.', 1, '2024-01-08 08:29:37 AM');
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 2, 2, 1, 5, 4, 2, 18000000, 7.50, 'Promotion sur les voitures electriques Ford.', 1, '2024-01-08 08:29:37 AM');
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 2, 5, 3, 4, 3, 1, 42000000, 10, 'Mercedes-Benz Coupe, edition limitee.', 1, '2024-01-08 08:29:37 AM');
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 2, 5, 4, 1, 5, 5, 40000000, 0, 'Tesla', 1, '2024-01-08 01:16:13 PM');
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 2, 1, 2, 3, 4, 5, 25000000, 12.50, 'Toyota Sedan en vente, excellente condition.', 1, '2024-01-08 08:29:37 AM');
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 3, 4, 1, 2, 2, 3, 35000000, 0, 'Porsche', 1, '2024-01-10 01:15:01 PM');
INSERT INTO annonces( idannonce, idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description, etatannonce, dateheurecreation ) VALUES (DEFAULT , 2, 3, 4, 1, 2, 3, 35000000, 16, 'Liquidation SUV Honda, neuf jamais utilise.', 1, '2024-01-08 08:29:37 AM');
INSERT INTO etatannonces( idetatannonce, idannonce, typeetat, dateheureetat, idutilisateur ) VALUES (DEFAULT , 3, 10, '2024-01-07 07:47:47 PM', 1);
INSERT INTO etatannonces( idetatannonce, idannonce, typeetat, dateheureetat, idutilisateur ) VALUES (DEFAULT , 5, 10, '2024-01-08 09:40:29 AM', 1);
INSERT INTO etatannonces( idetatannonce, idannonce, typeetat, dateheureetat, idutilisateur ) VALUES (DEFAULT , 1, 10, '2024-01-08 06:58:27 PM', 1);
INSERT INTO etatannonces( idetatannonce, idannonce, typeetat, dateheureetat, idutilisateur ) VALUES (DEFAULT , 3, 100, '2024-01-08 07:05:01 PM', 2);
INSERT INTO etatannonces( idetatannonce, idannonce, typeetat, dateheureetat, idutilisateur ) VALUES (DEFAULT , 5, 100, '2024-01-08 07:53:19 PM', 2);
INSERT INTO etatannonces( idetatannonce, idannonce, typeetat, dateheureetat, idutilisateur ) VALUES (DEFAULT , 2, 10, '2024-01-10 01:34:28 PM', 1);
INSERT INTO favoris( idfavoris, idutilisateur, idannonce ) VALUES (DEFAULT , 3, 2);
INSERT INTO favoris( idfavoris, idutilisateur, idannonce ) VALUES (DEFAULT , 2, 2);
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 1, 'https://i.ibb.co/grG1hMS/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 2, 'https://i.ibb.co/grG1hMS/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 3, 'https://i.ibb.co/grG1hMS/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 5, 'https://i.ibb.co/grG1hMS/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 7, 'https://i.ibb.co/grG1hMS/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 1, 'https://i.ibb.co/19QQtHK/view-3d-car-23-2150796894.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 2, 'https://i.ibb.co/19QQtHK/view-3d-car-23-2150796894.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 4, 'https://i.ibb.co/19QQtHK/view-3d-car-23-2150796894.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 5, 'https://i.ibb.co/19QQtHK/view-3d-car-23-2150796894.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 7, 'https://i.ibb.co/19QQtHK/view-3d-car-23-2150796894.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 1, 'https://i.ibb.co/D7Sn2pN/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 3, 'https://i.ibb.co/D7Sn2pN/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 4, 'https://i.ibb.co/D7Sn2pN/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 5, 'https://i.ibb.co/D7Sn2pN/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 7, 'https://i.ibb.co/D7Sn2pN/silver-sedan-car-53876-84522.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 2, 'https://i.ibb.co/zRwSSYd/white-offroader-jeep-parking-114579-4007.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 3, 'https://i.ibb.co/zRwSSYd/white-offroader-jeep-parking-114579-4007.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 4, 'https://i.ibb.co/zRwSSYd/white-offroader-jeep-parking-114579-4007.jpg');
INSERT INTO photos( idphoto, idannonce, repertoire ) VALUES (DEFAULT , 7, 'https://i.ibb.co/zRwSSYd/white-offroader-jeep-parking-114579-4007.jpg');
INSERT INTO sessions( idsession, idutilisateur, dateheurelogin, code, isconnected, token ) VALUES (DEFAULT , 3, '2024-01-14 10:41:41 AM', '6898', 0, 'QM9aHtcaOr99oig1n3A+bjLDnN4w=B2QJA35UDXDLurE');
INSERT INTO sessions( idsession, idutilisateur, dateheurelogin, code, isconnected, token ) VALUES (DEFAULT , 2, '2024-01-15 08:45:25 AM', '7656', 0, 'dFdI66bkFO2eUPtp0cojSj3BVfGm3oyX=fVBVc45xd69');