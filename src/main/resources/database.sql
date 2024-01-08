CREATE  TABLE categories (
         idcategorie          serial  NOT NULL  ,
         nomcategorie         varchar(255)  NOT NULL  ,
         CONSTRAINT pk_categories PRIMARY KEY ( idcategorie )
);

CREATE  TABLE commission_defaut (
                valeur               numeric(10,2) DEFAULT 20 NOT NULL
);

CREATE  TABLE couleurs (
       idcouleur            serial  NOT NULL  ,
       nomcouleur           varchar(255)  NOT NULL  ,
       CONSTRAINT pk_couleurs PRIMARY KEY ( idcouleur )
);

CREATE  TABLE marques (
      idmarque             serial  NOT NULL  ,
      nommarque            varchar(255)  NOT NULL  ,
      CONSTRAINT pk_marques PRIMARY KEY ( idmarque )
);

CREATE  TABLE modeles (
      idmodele             serial  NOT NULL  ,
      nommodele            varchar(255)  NOT NULL  ,
      CONSTRAINT pk_modeles PRIMARY KEY ( idmodele )
);

CREATE  TABLE typeoccasions (
            idtypeoccasion       serial  NOT NULL  ,
            nomtypeoccasion      varchar(255)  NOT NULL  ,
            CONSTRAINT pk_typeoccasions PRIMARY KEY ( idtypeoccasion )
);

CREATE  TABLE utilisateurs (
           idutilisateur        serial  NOT NULL  ,
           email                varchar(255)  NOT NULL  ,
           motdepasse           varchar(255)  NOT NULL  ,
           isadmin              integer  NOT NULL  ,
           CONSTRAINT pk_utilisateurs PRIMARY KEY ( idutilisateur )
);

CREATE  TABLE annonces (
       idannonce            serial  NOT NULL  ,
       idutilisateur        integer  NOT NULL  ,
       idtypeoccasion       integer  NOT NULL  ,
       idcategorie          integer  NOT NULL  ,
       idcouleur            integer  NOT NULL  ,
       idmodele             integer  NOT NULL  ,
       idmarque             integer  NOT NULL  ,
       prix                 numeric(10,2)  NOT NULL  ,
       pourcentagecommission numeric(10,2)  NOT NULL  ,
       description          text  NOT NULL  ,
       etatannonce          integer DEFAULT 0 NOT NULL  ,
       dateheurecreation    timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
       CONSTRAINT pk_annonces PRIMARY KEY ( idannonce )
);

CREATE  TABLE etatannonces (
           idetatannonce        serial  NOT NULL  ,
           idannonce            integer  NOT NULL  ,
           typeetat             integer  NOT NULL  ,
           dateheureetat        timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
           idutilisateur        integer  NOT NULL  ,
           CONSTRAINT pk_etatannonces PRIMARY KEY ( idetatannonce )
);

CREATE  TABLE photos (
     idphoto              serial  NOT NULL  ,
     idannonce            integer  NOT NULL  ,
     repertoire           varchar(255)  NOT NULL  ,
     CONSTRAINT pk_photos PRIMARY KEY ( idphoto )
);

CREATE  TABLE sessions (
       idsession            serial NOT NULL  ,
       idutilisateur        integer  NOT NULL  ,
       dateheurelogin       timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL  ,
       code                 varchar(255)  NOT NULL  ,
       isconnected          integer DEFAULT 1 NOT NULL  ,
       token                text  NOT NULL  ,
       CONSTRAINT pk_sessions PRIMARY KEY ( idsession )
);

CREATE  TABLE favoris (
      idfavoris            serial  NOT NULL  ,
      idutilisateur        integer  NOT NULL  ,
      idannonce            integer  NOT NULL  ,
      CONSTRAINT pk_favoris PRIMARY KEY ( idfavoris )
);

ALTER TABLE favoris ADD CONSTRAINT fk_favoris_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

ALTER TABLE favoris ADD CONSTRAINT fk_favoris_annonces FOREIGN KEY ( idannonce ) REFERENCES annonces( idannonce );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_typeoccasions FOREIGN KEY ( idtypeoccasion ) REFERENCES typeoccasions( idtypeoccasion );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_categories FOREIGN KEY ( idcategorie ) REFERENCES categories( idcategorie );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_couleurs FOREIGN KEY ( idcouleur ) REFERENCES couleurs( idcouleur );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_modeles FOREIGN KEY ( idmodele ) REFERENCES modeles( idmodele );

ALTER TABLE annonces ADD CONSTRAINT fk_annonces_marques FOREIGN KEY ( idmarque ) REFERENCES marques( idmarque );

ALTER TABLE etatannonces ADD CONSTRAINT fk_etatannonces_annonces FOREIGN KEY ( idannonce ) REFERENCES annonces( idannonce );

ALTER TABLE etatannonces ADD CONSTRAINT fk_etatannonces_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );

ALTER TABLE photos ADD CONSTRAINT fk_photos_annonces FOREIGN KEY ( idannonce ) REFERENCES annonces( idannonce );

ALTER TABLE sessions ADD CONSTRAINT fk_sessions_utilisateurs FOREIGN KEY ( idutilisateur ) REFERENCES utilisateurs( idutilisateur );