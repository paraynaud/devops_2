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
		public void testData() throws Exception{
			Dataframe dt = new Dataframe("Input_files/input1.csv");
			assertEquals(dt.getColumn(0).getElement(1),"non");
			assertEquals(dt.getColumn(2).getElement(3),2);
		}
}
