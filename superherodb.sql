DROP DATABASE IF EXISTS superheroesDB;
CREATE DATABASE superheroesDB;

USE superheroesDB;

CREATE TABLE superpower(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`description` VARCHAR(255)
);

CREATE TABLE hero_villain(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` varchar(50) NOT NULL,
`description` VARCHAR(255),
superpowerId int,
Foreign Key (superpowerId) REFERENCES superpower(id)
);

CREATE TABLE organization(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`addressContact` VARCHAR(100) NOT NULL,
`description` VARCHAR(255)
);

CREATE TABLE member_org(
orgId INT NOT NULL,
memberId INT NOT NULL,
PRIMARY KEY(orgId, memberId),
FOREIGN KEY (orgId) REFERENCES organization(id),
FOREIGN KEY (memberId) REFERENCES hero_villain(id)
);

CREATE TABLE location(
id INT PRIMARY KEY AUTO_INCREMENT,
`heroImage` mediumblob,
`name` VARCHAR(50) NOT NULL,
`address` VARCHAR(100) NOT NULL,
`longitude` VARCHAR(100),
`latitude` VARCHAR(100),
`description` VARCHAR(255)
);

CREATE TABLE sightings(
id int primary key auto_increment,
`date` DATETIME NOT NULL,
locId INT NOT NULL,
heroId INT NOT NULL,
FOREIGN KEY (locId) REFERENCES location(id),
FOREIGN KEY (heroId) REFERENCES hero_villain(id)
);
