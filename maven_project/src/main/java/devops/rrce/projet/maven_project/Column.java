package devops.rrce.projet.maven_project;

import java.util.ArrayList;

public class Column<T> {
	private String label;
	private ArrayList<T> list ;
	public Column(String label, ArrayList<T> list){
		this.label = label;
		this.list = list;
	}
	
	public String getLabel(){
		return label;
	}
	
	public T getElement(int index){
		return list.get(index);
	}
	
	public void addData(Object elt){
		list.add((T)elt);
	}
	
	public Column<T> clone(){
		
		return null;
	}
	
}