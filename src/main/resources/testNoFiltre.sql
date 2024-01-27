-- Original
SELECT a.*,
       CASE WHEN
                f.idfavoris IS NOT NULL
                THEN true
            ELSE false END
FROM annonces a
         LEFT JOIN favoris f ON a.idannonce = f.idannonce
         LEFT JOIN etatannonces e ON a.idannonce = e.idannonce
WHERE e.typeetat = 10 AND NOT EXISTS (SELECT 1 FROM etatannonces e2 WHERE e2.idannonce = a.idannonce AND e2.typeetat = 100)
ORDER BY e.dateheureetat DESC;


-- Test
INSERT INTO favoris( idutilisateur, idannonce ) VALUES ( 3, 2);
SELECT a.*,
       CASE WHEN
                f.idannonce IS NOT NULL
                THEN true
            ELSE false END
FROM annonces a
         LEFT JOIN (
                SELECT DISTINCT idannonce
                FROM favoris
            ) f ON a.idannonce = f.idannonce
         LEFT JOIN etatannonces e ON a.idannonce = e.idannonce
WHERE e.typeetat = 10 AND NOT EXISTS (SELECT 1 FROM etatannonces e2 WHERE e2.idannonce = a.idannonce AND e2.typeetat = 100)
ORDER BY e.dateheureetat DESC;




--- Test mais avec idUtilisateur
SELECT a.*, CASE WHEN
    f.idannonce IS NOT NULL AND f.idUtilisateur = :idUtilisateur
    THEN true
    ELSE false END
FROM Annonces a
         LEFT JOIN (
                SELECT DISTINCT idannonce, idutilisateur
                FROM favoris
            ) f ON a.idannonce = f.idannonce
         LEFT JOIN etatannonces e ON a.idannonce = e.idannonce
WHERE e.typeEtat = 10 AND NOT EXISTS (SELECT 1 FROM etatannonces e2 WHERE e2.typeEtat = 100)
                          AND a.idutilisateur = :idUtilisateur
ORDER BY e.dateHeureEtat DESC