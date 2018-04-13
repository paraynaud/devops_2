package devops.rrce.projet.maven_project;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String[] labels;
		ArrayList<String> l1;
		ArrayList<Integer> l2;
		ArrayList<Object> l3;
		labels = new String[]{ "MyString", "MyInteger", "MyFloat" };
		l1 = new ArrayList<String>(Arrays.asList("oui", "non", "peut-etre"));
		l2 = new ArrayList<Integer>(Arrays.asList(0, 1, 2));
		l3 = new ArrayList<Object>(Arrays.asList("oui", 4, 1.2));
		Dataframe dt = new Dataframe("Input_files/input1.csv");
		System.out.println("oui");
	}

}
