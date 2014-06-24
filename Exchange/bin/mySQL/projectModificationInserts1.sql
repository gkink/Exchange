insert into users (firstName, lastName, email, ranking)
	values ('giorgi', 'ghambashidze', 'ggham@.gmail.com', 0);

insert into users (firstName, lastName, email, ranking)
	values ('giorgi', 'kinkladze', 'gkink@.gmail.com', 0);

insert into users (firstName, lastName, email, ranking)
	values ('irakli', 'kobalava', 'ikoba@.gmail.com', 0);

insert into users (firstName, lastName, email, ranking)
	values ('archil', 'bakhsoliani', 'abakh@.gmail.com', 0);




insert into itemsHave (name, description, keywords, userID)
	values ('tusheti3', 'tusheti lamazia', 'tusheti 3, 3-e tusheti', 1);

insert into itemsHave (name, description, keywords, userID)
	values ('tusheti 2', 'bakhtrionidan gicqerdi', 'tusheti 2, 2-e tusheti', 2);

insert into itemsHave (name, description, keywords, userID)
	values ('borjomi 1', 'a bardjomi', 'borjomi 1, 1-li borjomi', 3);



insert into realItems (userID, itemID)
	values (1,  3);

insert into realItems (userID, itemID)
	values (2,  1);

insert into realItems (userID, itemID)
	values (3,  2);



insert into cycles() values();



insert into cycleInfo(cycleID, itemID, accept)
	values('1', 1, '0');
insert into cycleInfo(cycleID, itemID, accept)
	values('1', 2, '0');
insert into cycleInfo(cycleID, itemID, accept)
	values('1', 3, '0');