CREATE TABLE IF NOT EXISTS users (
                       id bigint auto_increment primary key,
                       reference varchar(255) not null,
                       first_name varchar(255) not null,
                       last_name varchar(255) not null,
                       email varchar(255) not null,
                       is_active bit default true not null,
                       created datetime not null,
                       updated datetime not null,
                       version int default 0 not null
);

CREATE INDEX IF NOT EXISTS idx_users_reference ON users(reference);
