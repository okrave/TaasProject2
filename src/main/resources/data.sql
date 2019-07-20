-- Create table https://o7planning.org/en/11705/create-a-login-application-with-spring-boot-spring-security-jpa
/*
CREATE EXTENSION postgis;

-- Enable Topology
CREATE EXTENSION postgis_topology;
*/


--- Create table

create table APP_USER
(
    USER_ID           BIGINT not null,
    USER_NAME         VARCHAR(36) not null unique,
    ENCRYTED_PASSWORD VARCHAR(128) not null,
    USER_EMAIL        VARCHAR(36) not null,
    ENABLED           Int not null,
    PRIMARY KEY (USER_ID)
) ;

create table APP_GROUP
(
    GROUP_ID  BIGINT not null,
    CREATOR_ID BIGINT not null,
    GROUP_NAME varchar (128),
    DESCRIPTION VARCHAR(512) not null,
    DATE DATE not null,
    CREATOR VARCHAR (36),
    LOCATION_ID BIGINT,
    PRIMARY KEY (GROUP_ID)
) ;

create table LOCATION
(
    LOCATION_ID BIGINT not null,
    --GEOM geometry(Point, 4326) not null,
    LAT DOUBLE PRECISION,
    LNG DOUBLE PRECISION,
    NAME VARCHAR(30),
    PRIMARY KEY (LOCATION_ID)
) ;

create table APP_ROLE
(
    ROLE_ID   BIGINT not null,
    ROLE_NAME VARCHAR(30) not null,
    PRIMARY KEY (ROLE_ID)
) ;

create table APP_TAG
(
    TAG_ID   BIGINT not null,
    TAG_NAME VARCHAR(30) not null,
    PRIMARY KEY (TAG_ID)
) ;

create table USER_ROLE
(
    ID      BIGINT not null,
    USER_ID BIGINT not null,
    ROLE_ID BIGINT not null,
    PRIMARY KEY (ID)
);

create table GROUP_TAG
(
    ID      BIGINT not null,
    GROUP_ID BIGINT not null,
    TAG_ID BIGINT not null,
    PRIMARY KEY (ID)
);


create table GROUP_USER
(
    ID      BIGINT not null,
    GROUP_ID BIGINT not null,
    USER_ID BIGINT not null,
    PRIMARY KEY (ID)
);


-- Used by Spring Remember Me API.
CREATE TABLE Persistent_Logins
(
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);


--- ALTERs table


-- app_user

alter table APP_USER
    add constraint APP_USER_UK unique (USER_NAME);

-- app_group

alter table APP_GROUP
    add constraint APP_GROUP_FK_CREATOR foreign key (CREATOR)
        references APP_USER (USER_NAME);

alter table APP_GROUP
    add constraint APP_GROUP_FK_CREATOR_ID foreign key (CREATOR_ID)
        references APP_USER (USER_ID);

alter table APP_GROUP
    add constraint APP_GROUP_FK_LOCATION foreign key (LOCATION_ID)
        references LOCATION (LOCATION_ID);

-- app_role

alter table APP_ROLE
    add constraint APP_ROLE_UK unique (ROLE_NAME);

-- app_tag

alter table APP_TAG
    add constraint APP_TAG_UK unique (TAG_NAME);


-- user_role

alter table USER_ROLE
    add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);

alter table USER_ROLE
    add constraint USER_ROLE_FK1 foreign key (USER_ID)
        references APP_USER (USER_ID);

alter table USER_ROLE
    add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
        references APP_ROLE (ROLE_ID);

--

alter table GROUP_TAG
    add constraint GROUP_TAG_UK unique (GROUP_ID, TAG_ID);

alter table GROUP_TAG
    add constraint GROUP_TAG_FK1 foreign key (GROUP_ID)
        references APP_GROUP (GROUP_ID);

alter table GROUP_TAG
    add constraint GROUP_TAG_FK2 foreign key (TAG_ID)
        references APP_TAG (TAG_ID);

--

alter table GROUP_USER
    add constraint GROUP_USER_UK unique (GROUP_ID, USER_ID);

alter table GROUP_USER
    add constraint GROUP_USER_FK1 foreign key (GROUP_ID)
        references APP_GROUP (GROUP_ID);

alter table GROUP_USER
    add constraint GROUP_USER_FK2 foreign key (USER_ID)
        references APP_USER (USER_ID);


--- INSERTION

insert into app_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');

insert into app_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');

---

/*
insert into App_User (USER_ID), USER_NAME, USER_EMAIL, ENCRYTED_PASSWORD, ENABLED)
values (2, 'dbuser1','ff@asd.it','$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into App_User (USER_ID), USER_NAME,USER_EMAIL, ENCRYTED_PASSWORD, ENABLED)
values (1, 'dbadmin1','cc@asd.it','$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into App_User (USER_ID), USER_NAME, USER_EMAIL, ENCRYTED_PASSWORD, ENABLED)
values (3, 'ciao','ciao@ciao.it','$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8az9E97k2FZbFsvGFFaSsiTUgl.TCrFu', 1);

insert into App_User (USER_ID), USER_NAME,USER_EMAIL, ENCRYTED_PASSWORD, ENABLED)
values (4, 'lonevetad','lonevetad@lonevetad.it','$2a$10$PrI5Gk9L.tSZaW9FXhTS8O8asMz9E97k2FZdFvGFSsiTUIl.TCrFg', 1);

insert into App_User (USER_ID), USER_NAME,USER_EMAIL, ENCRYTED_PASSWORD, ENABLED)
values (5, 'Bender','im_drunk@beer.it','$2a$10$PrI5Gk9L.tqZa29FXhTSsO8asMzgE97k2FfdFvGFSsiTUel.TCrFg', 1);

---

insert into user_role (ID, USER_ID, ROLE_ID)
values (1, 1, 1);

insert into user_role (ID, USER_ID, ROLE_ID)
values (2, 1, 2);

insert into user_role (ID, USER_ID, ROLE_ID)
values (3, 2, 2);

---

insert  into app_group(GROUP_ID, GROUP_NAME, DESCRIPTION, DATE, CREATOR)
values (1,'Esperienza cavalli','stupenda esperienza tra i boschi e cavalli','2019-05-28','ciao');

insert  into app_group(GROUP_ID, GROUP_NAME, DESCRIPTION, DATE, CREATOR)
values (2,'TarTAASSiamoci','Lavoriamo al progetto!','2019-06-06','lonevetad');

insert  into app_group(GROUP_ID, GROUP_NAME, DESCRIPTION, DATE, CREATOR)
values (3,'IngrAASSiamo','Si ragiona meglio a stomaco pieno: all you can eat!','2019-06-06','lonevetad');

insert  into app_group(GROUP_ID, GROUP_NAME, DESCRIPTION, DATE, CREATOR)
values (4,'Lindy Hop','Un ballo di coppia stile anni 20-30-40, molto rilassante ma energico, per ben concludere la serata.','2019-06-06','lonevetad');

insert  into app_group(GROUP_ID, GROUP_NAME, DESCRIPTION, DATE, CREATOR)
values (5,'Cin Cin','Bevuta rinfrescante a tema relax."','2019-06-06','Bender');

---

insert into app_tag (TAG_ID, TAG_NAME)
values (1, 'relax');
insert into app_tag (TAG_ID, TAG_NAME)
values (2, 'bere');
insert into app_tag (TAG_ID, TAG_NAME)
values (3, 'alcolici');
insert into app_tag (TAG_ID, TAG_NAME)
values (4, 'lavoro');
insert into app_tag (TAG_ID, TAG_NAME)
values (5, 'cavalli');
insert into app_tag (TAG_ID, TAG_NAME)
values (6, 'natura');
insert into app_tag (TAG_ID, TAG_NAME)
values (7, 'divertimento');
insert into app_tag (TAG_ID, TAG_NAME)
values (8, 'amici');
insert into app_tag (TAG_ID, TAG_NAME)
values (9, 'sport');
insert into app_tag (TAG_ID, TAG_NAME)
values (10, 'TAASS');
insert into app_tag (TAG_ID, TAG_NAME)
values (11, 'progetto');
insert into app_tag (TAG_ID, TAG_NAME)
values (12, 'lavoro');
insert into app_tag (TAG_ID, TAG_NAME)
values (13, 'studio');
insert into app_tag (TAG_ID, TAG_NAME)
values (14, 'università');
insert into app_tag (TAG_ID, TAG_NAME)
values (15, 'amici');
insert into app_tag (TAG_ID, TAG_NAME)
values (16, 'cibo');
insert into app_tag (TAG_ID, TAG_NAME)
values (17, 'pranzo');

---

insert into group_tag (ID, GROUP_ID, TAG_ID)
values (1, 1, 5);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (2, 1, 6);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (3, 1, 7);

insert into group_tag (ID, GROUP_ID, TAG_ID)
values (4, 2, 10);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (5, 2, 11);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (6, 2, 12);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (7, 2, 13);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (8, 2, 14);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (9, 2, 15);

insert into group_tag (ID, GROUP_ID, TAG_ID)
values (10, 3, 16);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (11, 3, 17);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (12, 3, 15);
insert into group_tag (ID, GROUP_ID, TAG_ID)
values (13, 3, 10);

*/

/*
insert into LOCATION(LOCATION_ID,GEOM, GROUP_ID)
values (1,ST_GeomFromText('POINT(-71.060316 48.432044)', 4326),1);
*/
Commit;