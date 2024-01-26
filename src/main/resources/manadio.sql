DROP SEQUENCE annonces_idannonce_seq;

DROP SEQUENCE categories_idcategorie_seq;

DROP SEQUENCE couleurs_idcouleur_seq;

DROP SEQUENCE etatannonces_idetatannonce_seq;

DROP SEQUENCE favoris_idfavoris_seq;

DROP SEQUENCE marques_idmarque_seq;

DROP SEQUENCE modeles_idmodele_seq;

DROP SEQUENCE photos_idphoto_seq;

DROP SEQUENCE sessions_idsession_seq;

DROP SEQUENCE typeoccasions_idtypeoccasion_seq;

DROP SEQUENCE utilisateurs_idutilisateur_seq;	

drop table categories CASCADE;	
drop table commission_defaut CASCADE;	
drop table couleurs CASCADE;	
drop table etatannonces CASCADE;	
drop table favoris CASCADE;	
drop table marques CASCADE;	
drop table modeles CASCADE;	
drop table photos CASCADE;	
drop table sessions CASCADE;	
drop table typeoccasions CASCADE;	
drop table utilisateurs CASCADE;
drop table annonces CASCADE;