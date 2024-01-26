-- all annonces by idUtilisateur
SELECT
    a1_0.idannonce,
    a1_0.idcategorie,
    a1_0.idcouleur,
    a1_0.dateheurecreation,
    a1_0.description,
    a1_0.etatannonce,
    a1_0.idmarque,
    a1_0.idmodele,
    a1_0.pourcentagecommission,
    a1_0.prix,
    a1_0.idutilisateur,
    a1_0.idtypeoccasion,
    CASE WHEN f1_0.idfavoris IS NOT NULL THEN true ELSE false END AS case
FROM
    annonces a1_0
LEFT JOIN
    favoris f1_0 ON a1_0.idannonce = f1_0.idannonce AND f1_0.idutilisateur = 2
LEFT JOIN
    etatannonces e1_0 ON a1_0.idannonce = e1_0.idannonce
    AND e1_0.dateheureetat = (
        SELECT MAX(e.dateheureetat)
        FROM etatannonces e
        WHERE e.idannonce = a1_0.idannonce
    )
WHERE
    a1_0.idutilisateur = 2
ORDER BY
    e1_0.dateheureetat DESC;
