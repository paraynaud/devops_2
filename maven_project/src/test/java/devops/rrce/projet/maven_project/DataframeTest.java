package devops.rrce.projet.maven_project;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class DataframeTest {
	
		String[] labels;
		ArrayList<String> l1;
		ArrayList<Integer> l2;
		ArrayList<Object> l3;
		@Before
	    public void init() {
			labels = new String[]{ "MyString", "MyInteger", "MyFloat" };
			l1 = new ArrayList<String>(Arrays.asList("oui", "non", "peut-etre"));
			l2 = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
			l3 = new ArrayList<Object>(Arrays.asList("oui", 4, 1.2));
	    }
//		@Test
//	    public void testNumberColumns() {
//			try{
//				Dataframe dt = new Dataframe(labels,l1,l2,l3);
//				assertEquals(dt.getSize(),3);
//			} catch(Exception e){
//				System.err.println(e.getMessage());
//			}	
//	    }
//		
		@Test
		public void testNumberColumnscsv()throws Exception {
				Dataframe dt = new Dataframe("Input_files/input1.csv");
				assertEquals(dt.getColumnSize(),3);
	    }
		@Test
		public void testData() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input1.csv");
			assertEquals(dt.getColumn(0).getElement(1),"non");
			assertEquals(dt.getColumn(2).getElement(2),"c");
		}
		@Test
		public void testfirstlines() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.printFirstLines(3),3);	
		}
		@Test
		public void testlastlines() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.printLastLines(2),2);	
		}
		@Test
		public void testfirstlines2() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.printFirstLines(70),7);	
		}
		@Test
		public void testlastlines2() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.printLastLines(70),7);	
		}
		@Test
		public void getcolumnstest() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.getColumnSize(),3);	
		}
		
		@Test
		public void printalltest() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.printall(),8);	
		}
		
		@Test
		public void getlinesizetest() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.getLineSize(),7);	
		}
		
		@Test
		public void dataframetest() throws Exception{
			ArrayList<String> liste1 = new ArrayList<String>();
			liste1.add("Jean");
			liste1.add("Paul");
			liste1.add("Arnaud");
			ArrayList<Integer> liste2 = new ArrayList<Integer>();
			liste2.add(18);
			liste2.add(22);
			liste2.add(23);
			String labels[] = {"Nom","Age"};
			Dataframe df = new Dataframe(labels,liste1,liste2);
			assertEquals(df.columns.size(),2);
		}
		
		@Test(expected = TypeCheckingException.class)
		public void TypeCheckingException() throws Exception {
				Dataframe dt;
				String labels[] = {"Nom Du Dataframe"};
				dt = new Dataframe("Input_files/input2.csv");
				ArrayList<Dataframe> dfarray = new ArrayList<Dataframe>();
				dfarray.add(dt);
				Dataframe dtdt = new Dataframe(labels,dfarray);
			
		}
		
		@Test(expected = Exception.class)
		public void Exception1() throws Exception {
				Dataframe dt = new Dataframe("Input_files/input3.csv");		
		}

		@Test
		public void select() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			Dataframe dt2 = dt.selectColumns("MyString","MyFloat");
			assertEquals(dt2.getColumnSize(),2);	
		}
		
		@Test
		public void meanstest() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			assertEquals(dt.means()[1],(float)3.0,0.01);				
		}
		
		@Test
		public void maxtest() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input2.csv");
			System.out.println("Hello");
		}
		
}
