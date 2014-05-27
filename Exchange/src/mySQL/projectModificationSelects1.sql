select users.ID, firstName, lastName, itemsHave.name
from users
	join itemsHave
		on itemsHave.userID = users.ID
	join cycleInfo
		on cycleInfo.itemID = itemsHave.ID
where cycleInfo.cycleID = '1';

select * from itemsHave
 where keyWords like '%3%';

select cycles.ID
from (select ID from users where ID = 1) as A
	join itemsHave
		on itemsHave.userID = A.ID
	join cycleInfo
		on cycleInfo.itemID = itemsHave.ID
	join cycles
		on cycleInfo.cycleID = cycles.ID
where users.ID = 4;
