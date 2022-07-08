import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
class alexTest {

	
	@Test
	void testOverall() {
		ArrayList<String> players = new ArrayList<String>();
		RunDraft test = new RunDraft();
		ArrayList<String> results = test.overall("1B");
		assertEquals(results.get(0),"PlayerName, realTeam, position");
		
	}
	
	@Test
	void testPOverall() {
		RunDraft test = new RunDraft();
		assertEquals(test.pOverall().get(0),"name, team, position");
		
	}
	
	@Test
	void testTeam() {
		
		RunDraft test = new RunDraft();
		assertEquals(test.team("A"),"Position, playerName");
		
	}
	
	@Test
	void testStars() {
		RunDraft test = new RunDraft();
		assertEquals(test.team("B"),"Position, playerName");
		
	}

}
