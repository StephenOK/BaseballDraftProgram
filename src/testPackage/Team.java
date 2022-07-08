package testPackage;

import java.util.ArrayList;

public class Team {
	Player [] myTeam;
	private  Pitcher [] pitchers;
	private  int numOfPitchers;
	private ArrayList<Integer> draftOrder;
	private Object[] fullRoster;
	
	public Team() {
		myTeam = new Player [9];
		pitchers = new Pitcher [5];
		numOfPitchers = 0;
		setDraftOrder(new ArrayList<Integer>(14)); 
		fullRoster = new Object[14];
		
		//initialize spaces in team
		for (int i = 0; i < 9; i++)
			myTeam[i] = new Player();
		for (int i = 0; i < 5; i ++)
			pitchers[i] = new Pitcher();
	}
	
	

	public void draftPlayer(Player newGuy) {
		
		if (!newGuy.getDrafted()) {  //if player is available to be drafted
			
			//find location in team list
			int inSpot = spotLocation(newGuy.getPosition());
			
			
			if (inSpot < 0) {  //if player's position is invalid
				System.out.println("Fatal Error");
				return;
			}
			
			else if (inSpot < 9) {  //if player is a fielder 
				myTeam[inSpot].setDrafted(false);
				myTeam[inSpot] = newGuy;						
				fullRoster[inSpot] = newGuy;
				
				
				if(!draftOrder.contains(inSpot))  //if not a duplicate position add them to draft order
					draftOrder.add(inSpot);
				
				else {
					for(int i = 0; i < draftOrder.size();i++) { //if it is a duplicate position
						if(draftOrder.get(i) == inSpot) {//find duplicate and remove them
							draftOrder.remove(i);  //then add new player to end of draft order to update draft order for stars()
							draftOrder.add(inSpot);
						}
					}
				
				}
				
			}
			
			newGuy.setDrafted(true);
			System.out.println("Successfully drafted " + newGuy.getFirstName() + " " + newGuy.getLastName() + " to your team!");
			
		}
		
		else {  //if player is not available to be drafted
			System.out.println("This player has already been drafted.");
		}
	}
	
	public void draftPitcher(Pitcher newGuy) {
		if (!newGuy.getDrafted()) {
			
			if (numOfPitchers < 5) {
				pitchers[numOfPitchers] = newGuy;			
				numOfPitchers++;
			}
			else {
				pitchers[0].setDrafted(false);
				
				Pitcher[] holdList = new Pitcher[5];
				
				for (int i = 0; i < 4; i++)
					holdList[i] = pitchers[i + 1];
							
				holdList[4] = newGuy;
				pitchers = holdList;
			
			}
			newGuy.setDrafted(true);
			fullRoster[8+numOfPitchers]= newGuy;
			draftOrder.add(8 + numOfPitchers);
			
			
			System.out.println("Successfully drafted " + newGuy.getFirstName() + " " + newGuy.getLastName() + " to your team!");
		}
		
		else {
			System.out.println("This player has already been drafted.");
		}
	}
	
	public boolean onTeam(String findFName, String findLName) {
		for (int i = 0; i < 9; i++) {
			if (myTeam[i].getPosition() == Position.empty);  //catch for empty position
			
			else if (myTeam[i].getLastName().equals(findLName) && myTeam[i].getFirstName().equals(findFName))
				return true;
		}
		
		for (int j = 0; j < 5; j++) {
			if (pitchers[j].getLastName() == null);
			else if (pitchers[j].getLastName().equals(findLName) && pitchers[j].getFirstName().equals(findFName))
				return true;
		}
		
		return false;
	}
	
	public static int spotLocation(Position p) {
		switch(p) {
			case C: return 0;
			case B1: return 1;
			case B2: return 2;
			case B3: return 3;
			case SS: return 4;
			case LF: return 5;
			case CF: return 6;
			case RF: return 7;
			case DH: return 8;
			default: return -1;
		}
	}

	public  int getNumOfPitchers() {
		return numOfPitchers;
	}

	public void setNumOfPitchers(int numOfPitchers) {
		this.numOfPitchers = numOfPitchers;
	}
	
	public Player[] setMyTeam(Player[] array) {
		this.myTeam = array;
		return this.myTeam;	
	}
	
	public Player[] getMyTeam() {
		return this.myTeam;	
	}
	
	public Pitcher[] setPitchers(Pitcher[] array) {
		this.pitchers = array;
		return this.pitchers;
	}
	
	public Pitcher[] getPitchers() {
		return this.pitchers;
	}

	public ArrayList<Integer> getDraftOrder() {
		return draftOrder;
	}

	public void setDraftOrder(ArrayList<Integer> draftOrder) {
		this.draftOrder = draftOrder;
	}
	
	void setFullRoster(Object[] arrayList) {
		this.fullRoster = arrayList;
		
	}

	public Object[] getFullRoster() {
		return fullRoster;
	}
	
	
}