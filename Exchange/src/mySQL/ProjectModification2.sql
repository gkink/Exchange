drop database if exists  exchange;

create database Exchange;

use exchange;

create table users 
(ID int not null auto_increment primary key,
firstName varchar(30), lastName varchar(30),
email varchar(30) unique,
password varchar(40),
ranking int);

create table itemsHave
(ID int not null auto_increment primary key,
name varchar(30),
description varchar(120),
keyWords varchar(50),
userID int,
createDate DateTime,
constraint foreign key (userID) references users(ID));

create table itemsNeed
(ID int not null auto_increment primary key,
keyWords varchar(50),
name varchar(50),
userID int,
constraint foreign key (userID) references users(ID)
);

create table realItems(
ID int not null auto_increment primary key,
userId int,
constraint foreign key (userID) references users(ID),
itemId int,
constraint foreign key (itemID) references itemsHave(ID) on delete cascade
);

create table itemsChanged(
ID int not null auto_increment primary key,
userId int not null,
constraint foreign key (userID) references users(ID),
name varchar(120));

create table cycles( 
ID int not null auto_increment primary key);

create table cycleInfo(
cycleID int,
constraint foreign key (cycleId) references cycles(ID),
itemId int,
constraint foreign key (itemID) references itemsHave(ID) on delete cascade,
accept tinyint(1));


create table transactions(
ID int not null auto_increment primary key,
transDate datetime);



create table transactionInfo(
transactionID int,
constraint foreign key (transactionID) references transactions(ID),
userID int,
constraint foreign key (userID) references users(ID),
itemID int,
constraint foreign key (itemID) references itemsChanged(ID) on delete cascade
);


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

-- Am xazis qvemot aris Inbox gverdis agwera da table-ebi romlebsac eg gverdi gamoiyelebs;

/*
Vizualurad didad datvirtuli gverdi araa. am gverdze aris chamonatvali bazashi arsebuli user-is yvela conversationis.
Conversationebistvis gveqneba calke erti table -- Conversations -- erti veli eqneba am tables da yovel jerze, roca axali
chat gaixsneba am tableshi daemateba axali row. Mtliani conversationis agsadgenad kide ori sxva table gveqneba. Conversations
table periodulad ganaxldeba(es sheidzleba arc gavaketot ase) anu dzalian dzveli Conversationebi waishleba. Aseve users unda 
mivcet sashualeba washalos romelime Conversation. amitom gverdis zeda zolshi iqneba ragac menubar-is msgavsi funqciit delete.
Users sheedzeba monishnos bevri conversationi da washalos.
*/

create table Conversations(
	id int not null auto_increment primary key,
	ActivationDate datetime
);

/*
es table userebs akavshirebs conversationebtan, deleted velshi weria washala tu ara am userma es conversation.
tu romelime conversation yvela monawile userma washala, mashin ishleba Conversations tablidanac da kide mesame tablidanac
magastan dakavshirebuli yvela row.
*/

create table ConversationUsers(
	id int not null auto_increment primary key,
	userID int,
	ConvID int,
	deleted tinyint(0),
	constraint foreign key (userID) references users(ID),
	constraint foreign key (ConvID) references Conversations(id)
);

/*
am tabledan agdgeba mteli conversation. ConversationUsers-shi tuki yvela userma washala conversation, mashin shesabamisad ganaxldeba
es tablec da conversations tablec.
*/

create table ConversationLines(
	id bigint not null auto_increment primary key,
	userID int,
	ConvID int,
	line_text varchar(100),
	line_date datetime,
	constraint foreign key (userID) references users(ID),
	constraint foreign key (ConvID) references Conversations(id)
);

/*
Am gverdze gadmodixar an search-is dabrunebuli item-ebis listidan an boos damatebuli item-ebis listidan. dasagenerireblad gchirdeba
itemsHave table. bevrs verafers vfiqrob am gverdze ubralod surati iqneba nivtis da description, vin atvirta .... magistvis erti select daiwereba 
itemsHave-dan.
*/

insert into users (firstName, lastName, email, ranking) values 
	( 'giorgi', 'ghambashidze', 'ggha@gmail.com', '5');
