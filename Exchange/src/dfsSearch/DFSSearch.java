package dfsSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import dbClasses.DBqueryGenerator;
import dbClasses.QueryExecutor;
import guestSession.ItemContainer;
import guestSession.ItemsHaveObject;
import guestSession.RealItemsObject;
import ModelClasses.Cycle;
import ModelClasses.Pair;
import ModelClasses.User;

public class DFSSearch extends Thread{
	private static final int HashKey = 7013053;
	
	private DBqueryGenerator generator;
	private QueryExecutor executor;
	private List<ArrayList<Integer>> cycles;
	private int userID;
	
	public DFSSearch(DataSource datasource, int userID){
		generator = new DBqueryGenerator();
		executor = new QueryExecutor(datasource);
		this.userID = userID;
	}
	
	@Override
	public void run(){
		startDFS();
		for (int i = 0; i < cycles.size(); i++){
			int hash = generateHash(cycles.get(i));
			String cycle = cycles.get(i).get(0) +"";
			for (int j = 1; j < cycles.get(i).size(); j++){
				cycle += "*" + cycles.get(i).get(j);
			}
			int k = executor.executeQuery(generator.insertIntoCyclesHash(cycles.get(i).size(), hash, cycle));
//			System.out.println(k);
			if (k != 0){
				
				List<Pair<User, ItemsHaveObject>> list = new ArrayList<Pair<User, ItemsHaveObject>>();
				for (int j = 0; j < cycles.get(i).size(); j++){
					ItemsHaveObject item = new ItemsHaveObject(generator, executor, cycles.get(i).get(j));
					User user = new User(executor, generator, item.getItemOwner());
					Pair<User, ItemsHaveObject> pair = new Pair<User, ItemsHaveObject>(user, item);
					list.add(pair);
				}
				Cycle newCycle = new Cycle(executor, generator, list);
				newCycle.addToTheBases();
			}
		}
		System.out.println("userma: " + userID + " daasrula mushaoba");
	}

	private int generateHash(List<Integer> hashHelper){
        Collections.sort(hashHelper);
        int curr = hashHelper.get(0);
        for(int i = 1 ; i < hashHelper.size() ; i++){
                curr = cantorFunction(curr, hashHelper.get(i));
        }

        return curr;
	}

	private int cantorFunction(int first, int second){
        int firstElem, secondElem;
        firstElem = first + second;
        secondElem = firstElem + 1;
        
        return ((firstElem*secondElem)/2)%HashKey + second%HashKey;
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
		if (cycle.size() >= 4 || cycle.contains(item.getItemId())) return;
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
