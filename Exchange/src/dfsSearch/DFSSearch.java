package dfsSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import guestSession.ItemContainer;
import guestSession.ItemsHaveObject;
import guestSession.RealItemsObject;
import ModelClasses.User;

public class DFSSearch extends Thread{
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	private List<ArrayList<Integer>> cycles;
	private int userID;
	private BlockingQueue<List<ArrayList<Integer>>> resultsQueue;
	
	public DFSSearch(DataSource datasource, int userID, BlockingQueue<List<ArrayList<Integer>>> resultsQueue){
		generator = new DBqueryGenerator();
		executor = new QueryExecutor(datasource);
		this.resultsQueue = resultsQueue;
		this.userID = userID;
	}
	
	@Override
	public void run(){
		startDFS();
		if (cycles.size() != 0){
			try {
				resultsQueue.put(cycles);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("userma: " + userID + " daasrula mushaoba");
	}
	
	private void startDFS(){
		ItemContainer container = new ItemContainer(generator, executor);
		List<RealItemsObject> realItems = container.getUserItemsReal(userID);
		cycles = new ArrayList<ArrayList<Integer>>();
		List<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < realItems.size(); i++){
			DFSrec(realItems.get(i), temp, userID);
		}
		
	}
	
	private void DFSrec(RealItemsObject item, List<Integer> cycle, int stopUserID){
		if (cycle.size() >= 4) return;
		cycle.add(item.getItemId());
		ItemsHaveObject newitem = new ItemsHaveObject(generator, executor, item.getItemId());
		if (newitem.getItemOwner() == stopUserID){
			cycles.add(new ArrayList<Integer>());
			for (int i = 0; i < cycle.size(); i++){
				cycles.get(cycles.size()-1).add(cycle.get(i));
			}
		}else {
			User user = new User(executor, generator, newitem.getItemOwner());
			ItemContainer container = new ItemContainer(generator, executor);
			List<RealItemsObject> items = container.getUserItemsReal(user.getId());
			for (int i = 0; i < items.size(); i++){
				DFSrec(items.get(i), cycle, stopUserID);
			}
		}
		cycle.remove(cycle.size()-1);
	}
}
