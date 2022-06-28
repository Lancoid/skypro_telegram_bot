-- liquibase formatted sql

-- changeset lancoid:1
DROP TABLE IF EXISTS notification_task;

CREATE TABLE notification_task
(
    id       INT          NOT NULL,
    chat_id  INT          NOT NULL,
    message  varchar(255) NOT NULL,
    datetime TIMESTAMP    NOT NULL,
    is_sent  BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);