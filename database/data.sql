SET session_replication_role = replica;

TRUNCATE TABLE
    alerte,
    client,
    commune,
    demande,
    demande_statut,
    detail_devis,
    devis,
    district,
    region,
    statut,
    type_devis
RESTART IDENTITY;

SET session_replication_role = DEFAULT;

INSERT INTO
    client (nom, contact, adresse)
VALUES
    ('John Doe', '123456789', '123 Main St');

INSERT INTO
    client (nom, contact, adresse)
VALUES
    ('Alice', '123456789', '234 Main St');

INSERT INTO
    region (nom)
VALUES
    ('Region A');

INSERT INTO
    district (nom, id_region)
VALUES
    ('District A', 1);

INSERT INTO
    commune (nom, id_district)
VALUES
    ('Commune A', 1);

INSERT INTO
    statut(libelle)
VALUES
    ('Dossier creee'),
    ('Demande etude creee'),
    ('Demande etude acceptee'),
    ('Demande forage creee'),
    ('Demande forage acceptee'),
    ('Forage commence'),
    ('Forage termine');

INSERT INTO
    type_devis(type)
VALUES
    ('Etude'),
    ('Forage');

INSERT INTO alerte (couleur, id_statut1, id_statut2, dureetravail1, dureetravail2)
VALUES
    ('orange', 1, 2, 480, 600), 
    ('rouge',  1, 2, 1440, 2880),

    ('orange', 2, 3, 240,  300), 
    ('rouge',  2, 3, 360, 480),

    ('orange', 3, 4, 60,  120), 
    ('rouge',  3, 4, 180, 240),

    ('orange', 4, 5, 240,  480), 
    ('rouge',  4, 5, 600, 720),

    ('orange', 5, 6, 1200,  1800), 
    ('rouge',  5, 6, 1801, 3600),

    ('orange', 6, 7, 3600,  4200), 
    ('rouge',  6, 7, 4800, 6000);