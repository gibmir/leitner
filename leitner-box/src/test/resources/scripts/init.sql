CREATE TABLE box (
    box_id bigint NOT NULL,
    box_title character varying(100)
);

CREATE TABLE card (
    card_id bigint NOT NULL,
    folder_id bigint
);

CREATE TABLE folder (
    folder_id bigint NOT NULL,
    box_id bigint
);

ALTER TABLE IF EXISTS box ADD PRIMARY KEY (box_id);

ALTER TABLE IF EXISTS folder ADD PRIMARY KEY (folder_id);

ALTER TABLE IF EXISTS card ADD FOREIGN KEY (folder_id) REFERENCES folder(folder_id) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE IF EXISTS folder ADD FOREIGN KEY (box_id) REFERENCES box(box_id) ON UPDATE CASCADE ON DELETE CASCADE;

INSERT INTO box(box_id,box_title)
VALUES(1,'testbox');

INSERT INTO folder(folder_id,box_id)
VALUES (1,1);

INSERT INTO card(card_id,folder_id)
VALUES (1,1);



