package sun.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import sun.model.Table;


public class TableFactory {
	private static TableFactory tableFactory;
	private long tableId = 0;
	private static List tableList;
	public static TableFactory getInstance(){
		if(tableFactory==null){
			tableFactory = new TableFactory();
			getTableList();
		}
		return tableFactory;
	}
	
	public static List getTableList(){
		if(tableList==null){
			tableList = new ArrayList();
		}
		return tableList;
	}
	
	public Table createTable(){
		++tableId;
		Table table = new Table(tableId);
		tableList.add(table);
		return table;
	}
	
	public static Table getTableByUserId(String userId){
		for (Object object : tableList) {
			for (Object obj : ((Table)object).getUser())  {
				if(((String)obj).equals(userId)){
					return (Table)object;
				}
			}
		}
		return null;
	}
	
	public static String getUserByUserId(String userId){
		Table table = null;
		for (Object object : tableList) {
			for (Object obj : ((Table)object).getUser())  {
				if(((String)obj).equals(userId)){
					table= (Table)object;
				}
			}
		}
		if(table!=null){
			for (Object object : table.getUser()) {
				if(!((String)object).equals(userId)){
					return (String)object;
				}
			}
			
		}
		return null;
	}
}
