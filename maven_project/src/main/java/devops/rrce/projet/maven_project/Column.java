package devops.rrce.projet.maven_project;

import java.util.ArrayList;

public class Column<T> {
	String label;
	ArrayList<T> list ;
	public Column(String label, ArrayList<T> list){
		this.label = label;
		this.list = list;
	}
}