drop table IF EXISTS CARDS;
create TABLE CARDS (
  id LONG PRIMARY KEY,
  question varchar(255) DEFAULT NULL,
  answer varchar(255) DEFAULT NULL,
  PRIMARY KEY(`id`)
);
delete from CARDS;
insert into CARDS(id, question,answer)
values (1, 'year','2020');