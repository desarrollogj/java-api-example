CREATE MEMORY TABLE temp_users (
                       reference varchar(255) not null,
                       first_name varchar(255) not null,
                       last_name varchar(255) not null,
                       email varchar(255) not null,
                       is_active bit default true not null,
                       created datetime not null,
                       updated datetime not null,
                       version int default 0 not null
) NOT PERSISTENT;

INSERT INTO temp_users (reference, first_name, last_name, email, is_active, created, updated, version)
VALUES
('17334fcc-8b65-11ed-a1eb-0242ac120002', 'Gustavo', 'Ceratti', 'gceratti_flux@foobar.com.ar', true, '2023-09-04 10:04:00', '2023-09-04 10:04:00', 0),
('17335260-8b65-11ed-a1eb-0242ac120002', 'Fabiana', 'Cantilo', 'fcantilo_flux@foobar.com.ar', true, '2023-09-04 10:04:00', '2023-09-04 10:04:00', 0),
('17335396-8b65-11ed-a1eb-0242ac120002', 'Luca', 'Prodan', 'lprodan_flux@foobar.com.ar', true, '2023-09-04 10:04:00', '2023-09-04 10:04:00', 0),
('173354c2-8b65-11ed-a1eb-0242ac120002', 'Miguel', 'Mateos', 'mmateos_flux@foobar.com.ar', true, '2023-09-04 10:04:00', '2023-09-04 10:04:00', 0),
('173355da-8b65-11ed-a1eb-0242ac120002', 'Ricardo', 'Mollo', 'rmollo_flux@foobar.com.ar', false, '2023-09-04 10:04:00', '2023-09-04 10:04:00', 0);

MERGE INTO users AS u USING temp_users AS t ON u.reference = t.reference
WHEN NOT MATCHED THEN
    INSERT (reference, first_name, last_name, email, is_active, created, updated, version)
    VALUES (t.reference, t.first_name, t.last_name, t.email, t.is_active, t.created, t.updated, t.version);

DROP TABLE temp_users;
