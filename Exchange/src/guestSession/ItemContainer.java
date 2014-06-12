package guestSession;

import java.util.List;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;

public class ItemContainer {
	private List<Item> items;
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	public ItemContainer(DBqueryGenerator generator, QueryExecutor executor){
		this.generator=generator;
		this.executor=executor;
		
	}
}
