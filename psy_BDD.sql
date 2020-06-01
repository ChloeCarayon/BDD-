CREATE DATABASE psy;
USE psy;

create table Client(
    Id_Client     int auto_increment 
        primary key,
    Nom_client    varchar(50) not null,
    Prenom_client varchar(50) not null,
    mdp           varchar(50) not null,
    mail          varchar(50) not null,
    pub           varchar(50) not null,
    sexe          tinyint(1)  not null,
    Date_client   date        not null,
    constraint email_unique
        unique (mail)
);

create table Consultation(
    Id_consultation int auto_increment
        primary key,
    Anxiete         int          not null,
    Mots_cles       varchar(600) not null,
    Posture         varchar(250) not null
);

create table rdv
(
    Id_rdv          int auto_increment
        primary key,
    Date            date        not null,
    Heure           varchar(50) null,
    Prix            float       not null,
    Payement        varchar(50) not null,
    Id_Client       int         not null,
    Id_Client_2     int         null,
    Id_Client_3     int         null,
    constraint Rdv_Client_FK
        foreign key (Id_Client) references Client (Id_Client),
    constraint Rdv_Client_FK_2
        foreign key (Id_Client_2) references Client (Id_Client),
    constraint Rdv_Client_FK_3
        foreign key (Id_Client_3) references Client (Id_Client)
);

create table client_consultation
(
    Id_consultation int not null,
    Id_Client       int not null,
    Id_RDV          int not null,
    primary key (Id_consultation, Id_Client),
    constraint Id_Rdv_FK
        foreign key (Id_RDV) references rdv (Id_rdv),
    constraint client_consultation_Client0_FK
        foreign key (Id_Client) references Client (Id_Client),
    constraint client_consultation_Consultation_FK
        foreign key (Id_consultation) references Consultation (Id_consultation)
);

create table TYPE_P
(
    Date_type date default current_timestamp() not null,
    Nom_type  varchar(50)                      not null,
    Id_Client int                              not null,
    primary key (Date_type, Id_Client),
    constraint Id_CLient_type
        foreign key (Id_Client) references Client (Id_Client)
);

create table couple
(
    Date_couple date default current_timestamp() not null,
    en_couple   tinyint(1)                       not null,
    Id_Client   int                              not null,
    primary key (Date_couple, Id_Client),
    constraint Id_CLient_couple
        foreign key (Id_Client) references Client (Id_Client)
);

create table prof_client
(
    Nom_prof  varchar(50) not null,
    Id_Client int         not null,
    Prof_date date        null,
    primary key (Nom_prof, Id_Client),
    constraint prof_client_Client0_FK
        foreign key (Id_Client) references Client (Id_Client)
);

INSERT INTO `psy`.`client` (`Nom_client`, `Prenom_client`, `mdp`, `mail`, `pub`, `sexe`, `Date_client`) 
	VALUES ('admin', 'admin', 'admin', 'admin', 'NULL','0', '2020-06-01'); 