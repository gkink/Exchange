drop database if exists  exchange;

create database Exchange;

use exchange;

create table users 
(ID int not null auto_increment primary key,
firstName varchar(30), lastName varchar(30),
email varchar(30) unique, ranking int);

create table itemsHave
(ID int not null auto_increment primary key,
name varchar(30), description varchar(120),
keyWords varchar(50),userID int,
constraint foreign key (userID) references users(ID));

create table itemsNeed
(ID int not null auto_increment primary key,
name varchar(30),
keyWords varchar(50), userID int,
constraint foreign key (userID) references users(ID)
);

create table realItems(
ID int not null auto_increment primary key,
userId int,
constraint foreign key (userID) references users(ID),
itemId int,
constraint foreign key (itemID) references itemsHave(ID));


create table cycles(
ID int not null auto_increment primary key);

create table cycleInfo(
cycleID int,
constraint foreign key (cycleId) references cycles(ID),
itemId int,
constraint foreign key (itemID) references itemsHave(ID),
accept tinyint(1));


create table transactions(
ID int not null auto_increment primary key,
transDate datetime);

create table itemsChanged(
ID int not null primary key,
userId int not null,
description varchar(120));

create table transactionInfo(
transactionID int,
constraint foreign key (transactionID) references transactions(ID),
userID int,
constraint foreign key (userID) references users(ID),
itemID int,
constraint foreign key (itemID) references itemsChanged(ID));


#Tu es notificationi ertxel naxa userma ramiT unda movnishnot ro meoret agar vanaxoT!
create table cycleNotifications(
userID int,
cycleID int,
defaulttext varchar(30),#es tu yvelas saerTo aqvs bazashi shenaxvas jobia romelime klaisi ststic cvladad agvwerot.
ifSeen tinyint,
constraint foreign key (userID) references users(ID),
constraint foreign key (cycleID) references cycles(ID)
);


create table cycleRejectOrApprove(
userID int,
notificationSenderID int,
type tinyint,
ifSeen tinyint,
constraint foreign key (userID) references users(ID),
constraint foreign key (userID) references users(ID)
);


create table transactionCreated(
userID int,
transactionID int,
constraint foreign key (userID) references users(ID),
constraint foreign key (transactionID) references transactions(ID)
);


create table Conversations(
	id int not null auto_increment primary key,
	ActivationDate datetime
);


create table ConversationUsers(
	id int not null auto_increment primary key,
	userID int,
	ConvID int,
	deleted tinyint(0),
	constraint foreign key (userID) references users(ID),
	constraint foreign key (ConvID) references Conversations(id)
);


create table ConversationLines(
	id bigint not null auto_increment primary key,
	userID int,
	ConvID int,
	line_text varchar(100),
	line_date datetime,
	constraint foreign key (userID) references users(ID),
	constraint foreign key (ConvID) references Conversations(id)
)