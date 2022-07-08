package testPackage;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.*;

public class StephenOK_Tests {

	private Team A = new Team();
	private Team B = new Team();
	private Team C = new Team();
	private Team D = new Team();
	
	private Player firstPlayer = new Player("James", "Martinez", "C", "PHX", 
			0, 0, 0, 0, 0);
	private Player secondPlayer = new Player("Quentin", "Martinez", "CF", "PHX", 
			0, 0, 0, 0, 0);
	private Player thirdPlayer = new Player("Quentin", "Hayden", "C", "PHX", 
			0, 0, 0, 0, 0);


	private Pitcher firstPitcher = new Pitcher("Burt", "Benjamin", "PHX", 
			0, 0, 0, 0, 0);
	private Pitcher secondPitcher = new Pitcher("Joseph", "Rentworth", "PHX", 
			0, 0, 0, 0, 0);
	private Pitcher thirdPitcher = new Pitcher("Peter", "Jonsen", "PHX", 
			0, 0, 0, 0, 0);
	private Pitcher fourthPitcher = new Pitcher("John", "Doe", "PHX", 
			0, 0, 0, 0, 0);
	private Pitcher fifthPitcher = new Pitcher("Ruby", "Rentworth", "PHX", 
			0, 0, 0, 0, 0);
	private Pitcher sixthPitcher = new Pitcher("John", "Doent", "PHX", 
			0, 0, 0, 0, 0);
	
	private RunDraft testMain = new RunDraft();
	
	@Before
	public void addToList() {
		testMain.availablePlayersToDraft.add(firstPlayer);
		testMain.availablePlayersToDraft.add(secondPlayer);
		testMain.availablePlayersToDraft.add(thirdPlayer);
		
		testMain.availablePitchersToDraft.add(firstPitcher);
		testMain.availablePitchersToDraft.add(secondPitcher);
		testMain.availablePitchersToDraft.add(thirdPitcher);
		testMain.availablePitchersToDraft.add(fourthPitcher);
		testMain.availablePitchersToDraft.add(fifthPitcher);
		testMain.availablePitchersToDraft.add(sixthPitcher);
	}
	
	@Test
	public void testFindPlayer() {
		assertEquals(-1, testMain.findPlayer("", ""));	
		assertEquals(-1, testMain.findPlayer(""));	
		assertEquals(0, testMain.findPlayer("Martinez", "J"));
		assertEquals(-1, testMain.findPlayer("Martinez"));
		assertEquals(2, testMain.findPlayer("Hayden"));
		assertEquals(-1, testMain.findPlayer("Quentin"));
		
		assertEquals(-1, testMain.findPitcher("", ""));
		assertEquals(-1, testMain.findPitcher(""));
		assertEquals(1, testMain.findPitcher("Rentworth", "J"));
		assertEquals(-1, testMain.findPitcher("Rentworth"));
		assertEquals(0, testMain.findPitcher("Benjamin"));
		assertEquals(-1, testMain.findPitcher("Burt"));
	}
	
	@Test
	public void testDraftFromMain() {
		testMain.performDraft("", "", A);
		testMain.performDraft("Martinez", "J", B);
		testMain.performDraft("Martinez", "", C);
		testMain.performDraft("Hayden", "", D);
		testMain.performDraft("Quentin", "", A);
		
		testMain.performDraft("Rentworth", "J", B);
		testMain.performDraft("Rentworth", "", C);
		testMain.performDraft("Benjamin", "", D);
		testMain.performDraft("John", "", A);
		
		assertFalse(A.onTeam("", ""));
		assertTrue(B.onTeam("James", "Martinez"));
		assertFalse(C.onTeam("Quentin", "Martinez"));
		assertFalse(C.onTeam("James", "Martinez"));
		assertTrue(D.onTeam("Quentin", "Hayden"));
		assertFalse(A.onTeam("Quentin", "Hayden"));
		assertFalse(A.onTeam("Quentin", "Martinez"));
		
		assertTrue(B.onTeam("Joseph", "Rentworth"));
		assertFalse(C.onTeam("Ruby", "Rentworth"));
		assertFalse(C.onTeam("Joseph", "Rentworth"));
		assertTrue(D.onTeam("Burt", "Benjamin"));
		assertFalse(D.onTeam("John", "Doent"));
		assertFalse(D.onTeam("John", "Doe"));
	}
	
	@Test
	public void testConvertStringToPosition() {
		assertEquals(Position.C, firstPlayer.getPosition());
		assertEquals(Position.CF, secondPlayer.getPosition());
	}
	
	@Test
	public void testSuccessfulDraft() {
		assertFalse(B.onTeam("James", "Martinez"));
		B.draftPlayer(firstPlayer);
		assertTrue(B.onTeam("James", "Martinez"));
		B.draftPlayer(secondPlayer);
		assertTrue(B.onTeam("Quentin", "Martinez"));
		assertTrue(B.onTeam("James", "Martinez"));
	}

	@Test
	public void testRedraftFailure() {
		B.draftPlayer(firstPlayer);
		C.draftPlayer(firstPlayer);
		assertFalse(C.onTeam("James", "Martinez"));
	}
	
	@Test
	public void testReplacePosition() {
		B.draftPlayer(firstPlayer);
		B.draftPlayer(thirdPlayer);
		assertTrue(B.onTeam("Quentin", "Hayden"));
		assertFalse(B.onTeam("James", "Martinez"));
		assertFalse(firstPlayer.getDrafted());
	}
	
	@Test
	public void testPitcherDraft() {
		assertTrue(B.getPitchers()[0].getLastName() == null);
		
		B.draftPitcher(firstPitcher);
		B.draftPitcher(secondPitcher);
		B.draftPitcher(thirdPitcher);
		B.draftPitcher(fourthPitcher);
		B.draftPitcher(fifthPitcher);
		
		assertTrue(B.onTeam("Burt", "Benjamin"));
		assertTrue(B.onTeam("Joseph", "Rentworth"));
		assertTrue(B.onTeam("Peter", "Jonsen"));
		assertTrue(B.onTeam("John", "Doe"));
		assertTrue(B.onTeam("Ruby", "Rentworth"));
		
		B.draftPitcher(sixthPitcher);
		assertTrue(B.onTeam("John", "Doent"));
		assertFalse(B.onTeam("Burt", "Benjamin"));
		assertTrue(B.onTeam("Joseph", "Rentworth"));
		assertTrue(B.onTeam("Peter", "Jonsen"));
		assertTrue(B.onTeam("John", "Doe"));
		assertTrue(B.onTeam("Ruby", "Rentworth"));
		
		C.draftPitcher(firstPitcher);
		assertTrue(C.onTeam("Burt", "Benjamin"));
	}
}
