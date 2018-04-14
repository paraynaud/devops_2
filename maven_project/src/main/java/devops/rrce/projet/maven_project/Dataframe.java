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
			if(data[i].get(0) instanceof Integer){
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for (int j = 0; j < data[i].size(); j++) {
					if(data[i].get(j).getClass().equals(data[i].get(0).getClass())){
						temp.add((Integer)data[i].get(j));
					}
					else{
						throw new Exception("The elements of column number " + i + " must be of the same type");
					}
				}
				
				columns.add(new Column<Integer>(labels[i],temp));
			}
			else if (data[i].get(0) instanceof Float){
				ArrayList<Float> temp = new ArrayList<Float>();
				for (int j = 0; j < data[i].size(); j++) {
					if(data[i].get(j).getClass().equals(data[i].get(0).getClass())){
						temp.add((Float)data[i].get(j));
					}
					else{
						throw new Exception("The elements of column number " + i + " must be of the same type");
					}
				}
				
				columns.add(new Column<Float>(labels[i],temp));
			}
			else if (data[i].get(0) instanceof String){
				ArrayList<String> temp = new ArrayList<String>();
				for (int j = 0; j < data[i].size(); j++) {
					if(data[i].get(j).getClass().equals(data[i].get(0).getClass())){
						temp.add((String)data[i].get(j));
					}
					else{
						throw new Exception("The elements of column number " + i + " must be of the same type");
					}
				}
				
				columns.add(new Column<String>(labels[i],temp));
			}
			else{
				throw new TypeCheckingException("Unsupported type");
			}
			
		}
		lineSize = data[0].size();
		columnSize = data.length;
	
	}
	
	public Dataframe (String inputName) throws Exception{
		
		ArrayList<String> labels = new ArrayList<String>();
		
		
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
            			//Type checking of the parser
            			String type = "int";
            			Boolean point = false;
	            		for(int j = 0; j<onePart.length(); j++){
	            			char c = onePart.charAt(j);
	            			if(!Character.isDigit(c)){
	            				if(c == '.' && point == false){
	            					point = true;
	            				}
	            				else{
	            					type = "string";
	            				}
	            			}
	            		}
	            		if(point == true){
	            			type = "float";
	            		}
	            		//Create future column
            			if(currentLine == 2){
            				if(type.equals("int")){
            					Column<Integer> temp = new Column<Integer>(labels.get(i),new ArrayList<Integer>());
            					columns.add(temp);
            				}
            				else if(type.equals("float")){
            					Column<Float> temp = new Column<Float>(labels.get(i),new ArrayList<Float>());
            					columns.add(temp);
            				}
            				else{
            					Column<String> temp = new Column<String>(labels.get(i),new ArrayList<String>());
            					columns.add(temp);
            				}
            			}
//	            		//Store it with right type
	            		if(type == "int"){
	            			int value = Integer.parseInt(onePart);
	            			columns.get(i).addData(value);
	            			//Type checking for the columns
		            		if(currentLine > 2){
		            			if (!(columns.get(i).getElement(0) instanceof Integer)){
		            				throw new Exception("The elements of column number " + i + " must be of the same type");
		            			}
		            		}
	            		}
	            		else if(type == "float"){
	            			float value = Float.parseFloat(onePart);
	            			columns.get(i).addData(value);
	            			//Type checking for the columns
		            		if(currentLine > 2){
		            			if (!(columns.get(i).getElement(0) instanceof Float)){
		            				throw new Exception("The elements of column number " + i + " must be of the same type");
		            			}
		            		}
	            		}
	            		else{
	            			columns.get(i).addData(onePart);
	            			//Type checking for the columns
		            		if(currentLine > 2){
		            			if (!(columns.get(i).getElement(0) instanceof String)){
		            				throw new Exception("The elements of column number" + i + " must be of the same type");
		            			}
		            		}
	            		}
	            		
	            	}
            	}
            	currentLine++;
            }
            
            columnSize = columns.size();
            lineSize = currentLine - 2;

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
