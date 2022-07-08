package testPackage;

import java.util.ArrayList;

import javax.script.ScriptException;

public class Pitcher {
	
	private String firstName;
	private String lastName;
	private String team;
	
	private double era;
	private int g;
	private int gs;
	private double ip;
	private int bb;
	
	private boolean drafted;
	
	public Pitcher() {
		drafted = false;
	}
	
	public Pitcher(String fname, String lname, String t, double erA, int games, int average, double inningsPitched, int walks) {
		
		
		firstName = fname;
		lastName = lname;
		team = t;
		
		era = erA;
		g = games;
		gs =  average;
		ip =  inningsPitched;
		bb =  walks;
		
		drafted = false;
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
	
	public String getTeam() {
		return team;
	}
	
	public void setTeam(String newTeam) {
		team = newTeam;
	}
	public double getERA() {
		return era;
	}

	public void setERA(float newEra) {
		era = newEra;
	}

	public int getG() {
		return g;
	}

	public void setG(int newG) {
		g = newG;
	}

	public int getGS() {
		return gs;
	}

	public void setGS(int newGS) {
		gs = newGS;
	}

	public double getIP() {
		return ip;
	}

	public void setIP(float newIP) {
		ip = newIP;
	}

	public int getBB() {
		return bb;
	}

	public void setBB(int newBB) {
		bb = newBB;
	}
	
	public boolean getDrafted() {
		return drafted;
	}
	
	public void setDrafted(boolean draft) {
		drafted = draft;
	}
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName + " " + this.team + " " + this.ip;
		//later add pEvalfun
	}
	public String toPitcherString() {
		return "P " + this.firstName + " " + this.lastName ; 
	}
	
	
	public static void pOverall() throws ScriptException {
		ArrayList<Pitcher> pitcher = RunDraft.availablePitchersToDraft;//all available pitchers
		ArrayList<String> pevalPitchers = RunDraft.preEvalFunList; //same as above but with pevalfun function implemented
		
			
			if(pevalPitchers.isEmpty()) { //if no pevalfun function has been called  just use ip
				for(int i = 0;i<pitcher.size();i++) {
					if(pitcher.get(i).drafted == false)
					System.out.printf(pitcher.get(i).toString()+" " + "\n" );
			}
		}
			
			else 				
			for(int i = 0;i<pevalPitchers.size();i++) {//else print the same list but with peval function added
				if(pitcher.get(i).drafted == false)
				System.out.printf(pevalPitchers.get(i) + "\n");
		}
	}
}
