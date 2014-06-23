package dfsSearch;

import java.util.concurrent.BlockingQueue;

import javax.sql.DataSource;

public class DFSStarter extends Thread{

	private BlockingQueue<Integer> usersQueue;
	private DataSource datasource;
	
	
	public DFSStarter(BlockingQueue<Integer> usersQueue, DataSource datasource){

		this.usersQueue = usersQueue;
		this.datasource = datasource;
	}
	
	@Override
	public void run(){
		
		while(true){
			try {
				int userID = usersQueue.take();
				DFSSearch search = new DFSSearch(datasource, userID);
				//shemowmeba ramdenjer da ra userID_it shemova am adgilas.
				System.out.println("shemovida user: "+ userID + " dzebnis dasawyisi");
				search.start();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
