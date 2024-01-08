INSERT INTO utilisateurs (email, motdepasse, isadmin) VALUES
('admin@gmail.com', '1234', 1),
('u1@gmail.com', '1234', 0),
('u2@gmail.com', '1234', 0),
('u3@gmail.com', '1234', 0);

-- Insertion de donnees dans les tables de reference
INSERT INTO typeoccasions (nomTypeOccasion) VALUES
('Occasion Standard'),
('Vente Flash'),
('Liquidation'),
('Promotion'),
('Special');

INSERT INTO categories (nomCategorie) VALUES
('Voitures de Luxe'),
('Vehicules Electriques'),
('Vehicules Hybrides'),
('Vehicules Utilitaires'),
('Vehicules Sportifs');

INSERT INTO couleurs (nomCouleur) VALUES
('Rouge'),
('Bleu'),
('Noir'),
('Blanc'),
('Gris');

INSERT INTO modeles (nomModele) VALUES
('Sedan'),
('SUV'),
('Coupe'),
('Cabriolet'),
('Hatchback');

INSERT INTO marques (nomMarque) VALUES
('Toyota'),
('Honda'),
('Ford'),
('BMW'),
('Mercedes-Benz');

-- Insertion de donnees dans la table des annonces
INSERT INTO annonces (idutilisateur, idtypeoccasion, idcategorie, idcouleur, idmodele, idmarque, prix, pourcentagecommission, description)
VALUES
(2, 1, 2, 3, 4, 5, 25000000.00, 5.00, 'Toyota Sedan en vente, excellente condition.'),
(2, 3, 4, 1, 2, 3, 35000000.00, 7.50, 'Liquidation SUV Honda, neuf jamais utilise.'),
(2, 2, 1, 5, 4, 2, 18000000.00, 4.00, 'Promotion sur les voitures electriques Ford.'),
(2, 4, 5, 2, 1, 4, 28000000.00, 6.00, 'Cabriolet BMW à vendre, offre speciale.'),
(2, 5, 3, 4, 3, 1, 42000000.00, 9.00, 'Mercedes-Benz Coupe, edition limitee.');

INSERT INTO photos (idannonce, repertoire)
VALUES
(1, 'photo1.jpg'), (1, 'photo2.jpg'), (1, 'photo3.jpg'),
(2, 'photo4.jpg'), (2, 'photo5.jpg'), (2, 'photo6.jpg'),
(3, 'photo7.jpg'), (3, 'photo8.jpg'), (3, 'photo9.jpg'),
(4, 'photo10.jpg'), (4, 'photo11.jpg'), (4, 'photo12.jpg'),
(5, 'photo13.jpg'), (5, 'photo14.jpg'), (5, 'photo15.jpg');
