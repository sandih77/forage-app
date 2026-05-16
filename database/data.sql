INSERT INTO client (nom, contact, adresse) VALUES ('John Doe', '123456789', '123 Main St');
INSERT INTO client (nom, contact, adresse) VALUES ('Alice', '123456789', '234 Main St');

INSERT INTO region (nom) VALUES ('Region A');

INSERT INTO district (nom, id_region) VALUES ('District A', 1);

INSERT INTO commune (nom, id_district) VALUES ('Commune A', 1);

INSERT INTO statut(libelle) VALUES
('Demande creee'),
('Demande etude creee'),
('Demande etude refusee'),
('Demande etude acceptee'),
('Demande forage creee'),
('Demande forage refusee'),
('Demande forage acceptee'),
('Travail commence'),
('Travail termine');

INSERT INTO type_devis(type) VALUES
('Etude'),
('Forage');