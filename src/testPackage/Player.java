package testPackage;

import java.util.ArrayList;

public class Player {
	
	//name, position, and team variables
	private String firstName;
	private String lastName;
	private Position teamSpot ;
	private String team;
	
	//batter variables
	private int ab;
	private int sb;
	private float avg;
	private float obp;
	private float slg;
	
	private boolean drafted;
	
	//default constructor class
	public Player() {
		//needed for default
		teamSpot = Position.empty;
	}
	
	//debug constructor class
	public Player(String lname, String fname) {
		lastName = lname;
		firstName = fname;
		
		drafted = false;
	}
	
	//constructor class for batters
	public Player(String fname, String lname, String p, String t, int ab, int sb, float avg, float obp, float slg) {

		firstName = fname;
		lastName = lname;
		teamSpot = stringToPosition(p);
		team = t;
		
		this.ab = ab;
		this.sb = sb;		
		this.avg = avg;
		this.obp = obp;
		this.slg = slg;
		
		drafted = false;
	}
	
	public static Position stringToPosition(String fromFile) {
		switch(fromFile) {
			case "C": return Position.C;
			case "1B": return Position.B1;
			case "2B": return Position.B2;
			case "3B": return Position.B3;
			case "SS": return Position.SS;
			case "LF": return Position.LF;
			case "RF": return Position.RF;
			case "CF": return Position.CF;
			case "DH": return Position.DH;
			default: return Position.empty;
		}
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String F) {
		firstName = F;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String L) {
		lastName = L;
	}
	
	public Position getPosition() {
		return teamSpot;
	}
	
	public void setPosition(Position teamSpot) {
		this.teamSpot = teamSpot;
	}
	
	public String getTeam() {
		return team;
	}
	
	public void setTeam(String newTeam) {
		team = newTeam;
	}

	public int getAB() {
		return ab;
	}
	
	public void setAB(int newAB) {
		ab = newAB;
	}
	
	public int getSB() {
		return sb;
	}
	
	public void setSB(int newSB) {
		sb = newSB;
	}

	public float getAVG() {
		return avg;
	}

	public void setAVG(float newAvg) {
		avg = newAvg;
	}
	
	public float getOBP() {
		return obp;
	}
	
	public void setOBP(float newObp) {
		obp = newObp;
	}
	
	public float getSLG() {
		return slg;
	}
	
	public void setSLG(float newSlg) {
		slg = newSlg;
	}

	public boolean getDrafted() {
		return drafted;
	}
	
	public void setDrafted(boolean draftStatus) {
		drafted = draftStatus;
	}
	
	@Override
	public String toString() {
		
		return this.firstName + " " + this.lastName + " " + this.team + " " + this.teamSpot + " " + this.avg;
		//later add Evalfun
	}
	public String toTeamString() {
		return this.teamSpot + " "+ this.firstName + " " + this.lastName; 
	}

	public static void overall() {
	ArrayList<Player> player = RunDraft.availablePlayersToDraft;//get available players 
	ArrayList<String> evalPlayers = RunDraft.evalFunList;		//updated player list after evalFun has been set
	Player[] aTeam = RunDraft.getA().getMyTeam();
	
	
	if(evalPlayers.isEmpty()) {//if no eval function called use batting avg
		for(int i = 0;i<player.size();i++) {
			if(player.get(i).drafted == false)//if player is drafted don't display
				if(!hasPosition(aTeam,player.get(i).getPosition())) //if user A  hasn't drafted that position then display
					System.out.print(player.get(i).toString()+ " " + "\n" );
				}	
	}
	
	else //else use eval function
		for(int i = 0; i < evalPlayers.size();i++) {
			if(player.get(i).drafted ==false)//if player is drafted don't display
				if(!hasPosition(aTeam,player.get(i).getPosition())) //if user A hasn't drafted that position then display
					System.out.print(evalPlayers.get(i) + "\n");
		}
	}
	
	
	
	public static void overall(String pos) {
		Position position = stringToPosition(pos); //get specified position
		
		ArrayList<Player> player = RunDraft.availablePlayersToDraft; 
		ArrayList<String> evalPlayers = RunDraft.evalFunList;
		
		
		if(evalPlayers.isEmpty()) { //if no eval function called using batting avg
			for(int i = 0;i<player.size();i++) {
				if(player.get(i).getPosition() == position) {//if player position matches specified position
					if(player.get(i).drafted == false)		 //if player isn't drafted then display
						System.out.print(player.get(i).toString()+ " "  + "\n" );
				}
			}
		}
		
		else //else use eval function
			for(int i=0;i<player.size();i++) {
				if(player.get(i).getPosition() == position) { //confirm position matches
					if(player.get(i).drafted == false)		  //if player isn't drafted then display
						System.out.print(evalPlayers.get(i) + "\n");
			}
		}
	}
	
	public static boolean hasPosition(Player[] roster,Position p) { //helper function to determine if team A has a specified position for overall()
		for(int i = 0; i < roster.length;i++) {
			if(Team.spotLocation(roster[i].getPosition()) == Team.spotLocation(p) && roster[i].getFirstName() != null)
				return true;
		}
		return false;
		
	}
}
