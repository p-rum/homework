create table if not exists transactions
(
    id      bigint generated always as identity
        primary key,
    sender  varchar(255) not null,
    amount  numeric(38, 2),
    tx_hash varchar(255),
    status  varchar(255),
    address varchar(255)
);


