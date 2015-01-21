# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table card (
  id                        varchar(40) not null,
  name                      varchar(255),
  population                integer,
  area                      float,
  indebtedness              float,
  nights                    integer,
  sport_fields              integer,
  ranking                   varchar(40),
  constraint pk_card primary key (id))
;

create table ranking (
  id                        varchar(40) not null,
  name                      varchar(255),
  rank_population           integer,
  rank_area                 integer,
  rank_indebtedness         integer,
  rank_nights               integer,
  rank_sport_fields         integer,
  constraint pk_ranking primary key (id))
;

create table user (
  id                        varchar(40) not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  constraint pk_user primary key (id))
;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists card;

drop table if exists ranking;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

