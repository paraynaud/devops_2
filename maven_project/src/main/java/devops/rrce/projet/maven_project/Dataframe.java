package devops.rrce.projet.maven_project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dataframe {
	public ArrayList<Column<?>> columns = new ArrayList<Column<?>>();
	private int columnSize;
	private int lineSize;
	
	public Dataframe (String[] labels, ArrayList<?>  ... data) throws Exception{
		int supposedColumnLength = data[0].size();
		for(int i = 0; i < data.length; i++){
			if(data[0].size() != data[i].size()){
				throw new VectorSizeException("Columns must have the same number of lines");
			}
			
			if(data[i].get(0) instanceof Integer){
				ArrayList<Integer> temp = new ArrayList<Integer>();
				for (int j = 0; j < data[i].size(); j++) {
					//Type checking : same type in all columns
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
				//Type checking : same type in all columns
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
				//Type checking : same type in all columns
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
            int supposedLineLength = 0;
            while( (line = br.readLine()) != null ) {
            	
            	String[] parts = line.split(",");
            	if(firstLine){
            		//Add labels
            		firstLine = false;
	            	for(String onePart : parts){
	            		labels.add(onePart);
	            	}
	            	supposedLineLength = parts.length;
            	}
            	else{
            		for(int i=0; i<parts.length; i++){
            			//Length of line checking
            			if(parts.length != supposedLineLength){
            				throw new VectorSizeException("Lines must have the same number of columns");
            			}
            			
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
	
	private Dataframe(ArrayList<Column<?>> columns,int columnSize, int lineSize){
		this.columns = columns;
		this.columnSize = columnSize;
		this.lineSize = lineSize;
	}
	
	public int printall(){
		int numberOfLinesPrinted = 0;
		for(int i =0; i<columnSize; i++){
			System.out.printf("%15s | ", columns.get(i).getLabel());
		}
		numberOfLinesPrinted ++;
		System.out.println("");
		for(int i =0; i<lineSize; i++){
			for(int j =0; j<columnSize; j++){
				System.out.printf("%15s | ", columns.get(j).getElement(i));
			}
			numberOfLinesPrinted ++;
			System.out.println("");
		}
		return numberOfLinesPrinted;
	}
	
	public int printFirstLines(int nbLines){
		int numberOfPrintedLines = 0;
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
			numberOfPrintedLines ++;
			System.out.println("");
		}
		return numberOfPrintedLines;
	}
	
	public int printLastLines(int nbLines){
		int numberOfPrintedLines = 0;
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
			numberOfPrintedLines ++;
			System.out.println("");
		}
		return numberOfPrintedLines;
	}
	
	
	public Dataframe selectLines(int ... indexes){
		
		ArrayList<Column<?>> tmpColumns = new ArrayList<Column<?>>();
		for(int i=0; i < columnSize; i++){
			String tmpLabel = columns.get(i).getLabel();
			
			if(columns.get(i).getElement(0) instanceof Integer){
				Column<Integer> temp = new Column<Integer>(tmpLabel,new ArrayList<Integer>());
				tmpColumns.add(temp);
			}
			else if(columns.get(i).getElement(0) instanceof Float){
				Column<Float> temp = new Column<Float>(tmpLabel,new ArrayList<Float>());
				tmpColumns.add(temp);
			}
			else {
				Column<String> temp = new Column<String>(tmpLabel,new ArrayList<String>());
				tmpColumns.add(temp);
			}
		}
		
		int tmpLineSize = indexes.length;
		for(int i = 0; i < indexes.length; i++){
			if(indexes[i]<lineSize){
				for(int j = 0; j < columnSize; j++){
						tmpColumns.get(j).addData(columns.get(j).getElement(indexes[i]));
				}
			}
			else{
				tmpLineSize--;
			}
		}
		
		return new Dataframe(tmpColumns,tmpColumns.size(),tmpLineSize);
	}
	
	public Dataframe selectColumns(String ... labels){
		ArrayList<Column<?>> tmpColumns = new ArrayList<Column<?>>();
		int tmpColumnSize = 0;
		
		for(String oneLabel : labels){
			System.out.println(oneLabel);
			for(int i = 0; i < columnSize; i++){
				if(columns.get(i).getLabel().equals(oneLabel)){
					//Copy column
					if(columns.get(i).getElement(0) instanceof Integer){
						Column<Integer> temp = new Column<Integer>(oneLabel,new ArrayList<Integer>());
						for(int j = 0; j<lineSize; j++){
							temp.addData(columns.get(i).getElement(j));
						}
						tmpColumns.add(temp);
					}
					else if(columns.get(i).getElement(0) instanceof Float){
						Column<Float> temp = new Column<Float>(oneLabel,new ArrayList<Float>());
						for(int j = 0; j<lineSize; j++){
							temp.addData(columns.get(i).getElement(j));
						}
						tmpColumns.add(temp);
					}
					else {
						Column<String> temp = new Column<String>(oneLabel,new ArrayList<String>());
						for(int j = 0; j<lineSize; j++){
							temp.addData(columns.get(i).getElement(j));
						}
						tmpColumns.add(temp);
					}
					
					tmpColumnSize++;
				}
			}
		}
		return new Dataframe(tmpColumns,tmpColumnSize,lineSize);
	}
	
	public float[] means(){
		float[] result = new float[columnSize];
		
		for(int i = 0; i < columnSize; i++){
			float sum = 0;
			for(int j = 0; j < lineSize; j++){
				if(columns.get(i).getElement(j) instanceof String){
					String wtf = (String)columns.get(i).getElement(j);
					sum +=wtf.length();
				}
				else if((columns.get(i).getElement(j) instanceof Float)){
					sum+= (Float)columns.get(i).getElement(j);
				}
				else {
					sum+= (Integer)columns.get(i).getElement(j);
				}
			}
			result[i] = sum/lineSize;
			System.out.println(result[i]);
		}
		
		return result;
}
	
	public Object[] max(){
		Object[] result = new Object[columnSize];
		for(int i = 0; i < columnSize; i++){
			Object max = null;
			float maxValue = 0; 
			for(int j = 0; j < lineSize; j++){
				if(columns.get(i).getElement(j) instanceof String){
					String wtf = (String)columns.get(i).getElement(j);
					if(maxValue<wtf.length()){
						maxValue = wtf.length();
						max = wtf;
					}
				}
				else if((columns.get(i).getElement(j) instanceof Integer)){
					if(maxValue<(Integer)columns.get(i).getElement(j)){
						maxValue = 0 + (Integer)columns.get(i).getElement(j);
						max = (Integer)columns.get(i).getElement(j);
					}
				}
				else {
					if(maxValue<(Float)columns.get(i).getElement(j)){
						maxValue = (Float)columns.get(i).getElement(j);
						max = maxValue;
					}
				}
			}
			result[i] = max;
		}
		
		return result;
	}
	
	public Object[] min(){
		Object[] result = new Object[columnSize];
		for(int i = 0; i < columnSize; i++){
			Object min = null;
			float minValue = Float.MAX_VALUE;
			for(int j = 0; j < lineSize; j++){
				if(columns.get(i).getElement(j) instanceof String){
					String wtf = (String)columns.get(i).getElement(j);
					if(minValue>wtf.length()){
						minValue = wtf.length();
						min = wtf;
					}
				}
				else if((columns.get(i).getElement(j) instanceof Integer)){
					if(minValue>(Integer)columns.get(i).getElement(j)){
						minValue = 0 + (Integer)columns.get(i).getElement(j);
						min = (Integer)columns.get(i).getElement(j);
					}
				}
				else {
					if(minValue>(Float)columns.get(i).getElement(j)){
						minValue = (Float)columns.get(i).getElement(j);
						min = minValue;
					}
				}
			}
			result[i] = min;
		}
		
		return result;
	}
	
	public void export(String output){
		File file = new File(output);
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file);
            file.createNewFile();
            bw = new BufferedWriter(fw);
            for(int i = 0; i < columnSize; i++){
            	bw.write(columns.get(i).getLabel());
            	bw.write(",");
            }
            bw.newLine();
            for(int j = 0; j < lineSize; j++){
            	for(int i = 0; i < columnSize; i++){
            		bw.write(columns.get(i).getElement(j).toString());
            		bw.write(",");
    			}
            	bw.newLine();
            }         	

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.toString());
        } catch (IOException e) {
            System.out.println("Unable to read file: " + file.toString());
        }
        finally {
            try {
                bw.close();
            } catch (IOException e) {
                System.out.println("Unable to close file: " + file.toString());
            }
            catch(NullPointerException ex) {
            }
        }
	
	}

	
	public int getColumnSize(){
		return columnSize;
	}
	
	public int getLineSize(){
		return lineSize;
	}
	
	public Column<?> getColumn(int index){
		return columns.get(index);
	}
	
	
}
