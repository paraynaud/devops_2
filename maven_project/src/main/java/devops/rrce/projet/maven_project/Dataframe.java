package devops.rrce.projet.maven_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	
	public Dataframe (String inputName) throws Exception{
		
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<ArrayList<?>> data = new ArrayList<ArrayList<?>>();
		
		File file = new File("example.txt");

        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;

            while( (line = br.readLine()) != null ) {
            	Boolean firstLine = true;
            	String[] parts = line.split(",");
            	
            	if(firstLine){
            		firstLine = false;
	            	for(String onePart : parts){
	            		labels.add(onePart);
	            	}
            	}
            	else{
            		for(String onePart : parts){
            			String type = "string";
	            		for(int i = 0; i<onePart.length(); i++){
	            			char c = onePart.charAt(i);
	            			if(!Character.isDigit(c))
	            		}
	            	}
            	}
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + file.toString());
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                System.out.println("Unable to close file: " + file.toString());
            }
            catch(NullPointerException ex) {
            }
        }
	
	}
	
	public int getSize(){
		return size;
	}
	
	
}
