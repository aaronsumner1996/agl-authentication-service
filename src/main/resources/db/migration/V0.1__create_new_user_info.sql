-- -- ==============================================================
-- -- Flyway Migration -
-- -- ==============================================================
-- --
-- -- Creates initial PostgreSQL database tables.
-- --
-- -- ==============================================================
--
-- -- Create tables
--

create table users
(
    id SERIAL PRIMARY KEY,
    username varchar(255),
    password varchar(255)
);
