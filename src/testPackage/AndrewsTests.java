package testPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class AndrewsTests {

	

	@Test
	public void saveMethodTest() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Andrew");
		String value = list.get(0);
		
		RunDraft junit = new RunDraft();
		boolean result = junit.save(list);
		assertEquals(true,result);
	}
	@Test
	public void restoreMethodTest() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Andrew");
		String value = list.get(0);
		
		RunDraft junit = new RunDraft();
		boolean result = junit.restore(list, value);
		assertEquals(true,result);
	}
	
	@Test
	public void preEvalFunMethodTest() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Andrew");
		String value = list.get(0);
		
		RunDraft junit = new RunDraft();
		boolean result = junit.preEvalFun(list,value);
		assertEquals(true,result);
	}
	
	@Test
	public void evalFunMethodTest() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Andrew");
		String value = list.get(0);
		
		RunDraft junit = new RunDraft();
		boolean result = junit.evalFun(list,value);
		assertEquals(true,result);
	}
	
	@Test
	public void showMethodTest() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Andrew");
		String value = list.get(0);
		
		RunDraft junit = new RunDraft();
		boolean result = junit.show(list);
		assertEquals(true,result);
	}
	
}