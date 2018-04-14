package devops.rrce.projet.maven_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dataframe {
	private ArrayList<Column<?>> columns = new ArrayList<Column<?>>();
	private int columnSize;
	private int lineSize;
	
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
		lineSize = data[0].size();
		columnSize = data.length;
	
	}
	
	public Dataframe (String inputName) throws Exception{
		
		ArrayList<String> labels = new ArrayList<String>();
		ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
		
		
		File file = new File(inputName);

        BufferedReader br = null;

        try {
            FileReader fr = new FileReader(file);
            br = new BufferedReader(fr);

            String line;
            Boolean firstLine = true;
            int currentLine = 1;
            while( (line = br.readLine()) != null ) {
            	
            	String[] parts = line.split(",");
            	if(firstLine){
            		//Add labels
            		firstLine = false;
	            	for(String onePart : parts){
	            		labels.add(onePart);
	            	}
            	}
            	else{
            		for(int i=0; i<parts.length; i++){
            			String onePart = parts[i];
//            			//Type checking of the parser
//            			String type = "int";
//            			Boolean point = false;
//	            		for(int i = 0; i<onePart.length(); i++){
//	            			char c = onePart.charAt(i);
//	            			if(!Character.isDigit(c)){
//	            				if(c == '.' && point == false){
//	            					point = true;
//	            				}
//	            				else{
//	            					type = "string";
//	            				}
//	            			}
//	            		}
//	            		if(point == true){
//	            			type = "float";
//	            		}
	            		//Create future column
            			if(currentLine == 2){
            				data.add(new ArrayList<Object>());
            			}
//	            		//Store it in right variable
//	            		if(type == "int"){
//	            			int value = Integer.parseInt(onePart);
//	            			data.get(currentLine-2).add(value);
//	            		}
            			data.get(i).add(onePart);
	            	}
            	}
            	currentLine++;
            }
            
            for(int i=0; i<data.size();i++){
            	Column<Object> temp = new Column<Object>(labels.get(i),data.get(i));
            	columns.add(i, temp);
            }
            columnSize = data.size();
            lineSize = data.get(0).size();

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
	
	public void printall(){
		for(int i =0; i<columnSize; i++){
			System.out.printf("%15s | ", columns.get(i).getLabel());
		}
		System.out.println("");
		for(int i =0; i<lineSize; i++){
			for(int j =0; j<columnSize; j++){
				System.out.printf("%15s | ", columns.get(j).getElement(i));
			}
			System.out.println("");
		}
	}
	
	public void printFirstLines(int nbLines){
		for(int i =0; i<columnSize; i++){
			System.out.printf("%15s | ", columns.get(i).getLabel());
		}
		System.out.println("");
		int maxLines;
		if(lineSize<nbLines){
			maxLines = lineSize;
		}
		else{
			maxLines = nbLines;
		}
		for(int i =0; i<maxLines; i++){
			for(int j =0; j<columnSize; j++){
				System.out.printf("%15s | ", columns.get(j).getElement(i));
			}
			System.out.println("");
		}
	}
	
	public void printLastLines(int nbLines){
		for(int i =0; i<columnSize; i++){
			System.out.printf("%15s | ", columns.get(i).getLabel());
		}
		System.out.println("");
		int maxLines;
		if(lineSize<nbLines){
			maxLines = lineSize;
		}
		else{
			maxLines = nbLines;
		}
		
		for(int i =lineSize-maxLines; i<lineSize; i++){
			for(int j =0; j<columnSize; j++){
				System.out.printf("%15s | ", columns.get(j).getElement(i));
			}
			System.out.println("");
		}
	}
	
	public int getColumnSize(){
		return columnSize;
	}
	
	public int getLineSize(){
		return columnSize;
	}
	
	public Column<?> getColumn(int index){
		return columns.get(index);
	}
	
	
}
