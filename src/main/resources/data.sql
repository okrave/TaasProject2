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
    DESCRIPTION VARCHAR not null,
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

create table APP_MESSAGE
(
    MESS_ID   BIGINT not null,
    GROUP_ID  BIGINT not null,
    USER_ID   BIGINT not null,
    TESTO     VARCHAR not null,
    PRIMARY KEY (MESS_ID)
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


-- app_message


alter table APP_MESSAGE
    add constraint APP_MESSAGE_FK_USER foreign key (USER_ID)
        references APP_USER(USER_ID);

alter table APP_MESSAGE
    add constraint APP_MESSAGE_FK_GROUP foreign key (GROUP_ID)
        references APP_GROUP(GROUP_ID);

-- user_role

alter table USER_ROLE
    add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);

alter table USER_ROLE
    add constraint USER_ROLE_FK1 foreign key (USER_ID)
        references APP_USER (USER_ID);

alter table USER_ROLE
    add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
        references APP_ROLE (ROLE_ID);

-- group_tag

alter table GROUP_TAG
    add constraint GROUP_TAG_UK unique (GROUP_ID, TAG_ID);

alter table GROUP_TAG
    add constraint GROUP_TAG_FK1 foreign key (GROUP_ID)
        references APP_GROUP (GROUP_ID);

alter table GROUP_TAG
    add constraint GROUP_TAG_FK2 foreign key (TAG_ID)
        references APP_TAG (TAG_ID);

-- group_user

alter table GROUP_USER
    add constraint GROUP_USER_UK unique (GROUP_ID, USER_ID);

alter table GROUP_USER
    add constraint GROUP_USER_FK1 foreign key (GROUP_ID)
        references APP_GROUP (GROUP_ID);

alter table GROUP_USER
    add constraint GROUP_USER_FK2 foreign key (USER_ID)
        references APP_USER (USER_ID);

-- location

/*
alter table LOCATION
    add constraint LOCATION_LAT_LNG_UNIQUE unique ()
*/

--- INSERTION

insert into app_role (ROLE_ID, ROLE_NAME)
values (1, 'ROLE_ADMIN');

insert into app_role (ROLE_ID, ROLE_NAME)
values (2, 'ROLE_USER');

---


--

-- POSTGIS

/*
-- Enable PostGIS (includes raster)
CREATE EXTENSION postgis;
-- Enable Topology
CREATE EXTENSION postgis_topology;
-- Enable PostGIS Advanced 3D
-- and other geoprocessing algorithms
-- sfcgal not available with all distributions
CREATE EXTENSION postgis_sfcgal;
-- fuzzy matching needed for Tiger
CREATE EXTENSION fuzzystrmatch;
-- rule based standardizer
CREATE EXTENSION address_standardizer;
-- example rule data set
CREATE EXTENSION address_standardizer_data_us;
-- Enable US Tiger Geocoder
CREATE EXTENSION postgis_tiger_geocoder;

-- Upgrade PostGIS (includes raster) to latest version
ALTER EXTENSION postgis UPDATE;
ALTER EXTENSION postgis_topology UPDATE;
*/

--

Commit;