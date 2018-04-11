package devops.rrce.projet.maven_project;

import java.util.ArrayList;

public class Dataframe {
	private ArrayList<Column<?>> columns;
	private int size;
	
	public Dataframe (String[] labels, ArrayList<?>  ... data) throws Exception{
		
		for(int i = 0; i < data.length; i++){
			ArrayList<Object> temp = new ArrayList<Object>();
			for (int j = 0; j < data[i].size(); j++) {
				if(data[i].get(j).getClass().equals(data[i].get(0).getClass())){
					temp.add((Object)data[i].get(j));
				}
				else{
					throw new Exception("Les élements de la colonne numéro " + i + " ne sont pas tous du même type");
				}
			}
			columns.add(new Column<Object>(labels[i],temp));
		}
		
		size = data.length;
	
	}
	
	public int getSize(){
		return size;
	}
	

}
