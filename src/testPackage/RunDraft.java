package testPackage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import java.io.*;


public class RunDraft {

	//instantiate list of players
	public static ArrayList<Player> availablePlayersToDraft = new ArrayList<>();
	public static ArrayList<Pitcher> availablePitchersToDraft = new ArrayList<>();
	public static ArrayList<String> evalFunList = new ArrayList<>();
	public static ArrayList<String> preEvalFunList = new ArrayList<>();

	
	//instantiate restore variables
	public static ArrayList<String> restoredTeamA = new ArrayList<>();
	public static ArrayList<String> restoredTeamB = new ArrayList<>();
	public static ArrayList<String> restoredTeamC = new ArrayList<>();
	public static ArrayList<String> restoredTeamD = new ArrayList<>();
	public static boolean restore = false;


	//instantiate all teams
	private static Team A = new Team();
	private static Team B = new Team();
	private static Team C = new Team();
	private static Team D = new Team();

	public static void main(String[] args) throws Exception {
		

		createPitcherList();
		createBatterList();
		

		//instantiate user input
		Scanner input = new Scanner(System.in);
		String command;
		String [] fullCommand;

		//instantiate possible names for drafted players
		String draftedLname;
		String draftedFname;
		Team draftTo;

		//instantiate positions for overall statistics
		String pos;

		//instantiate delimiters
		String delims = "[ ,\"]+";

		//instantiate loop continuation
		boolean repeat = true;

		//greet user
		System.out.println("Welcome to your Fantasy League Draft terminal!");
		System.out.println("Please enter a command!");

		do {
			//take and break down user input
			command = input.nextLine();
			fullCommand = command.split(delims);

			//clear first name for draft commands
			draftedFname = "";
			
			


			try {
				switch (fullCommand[0].toLowerCase()) {

				case "odraft": //[odraft, playerName, leagueMember]

					//obtain last name
					draftedLname = fullCommand[1];

					if (fullCommand.length == 4) {  //if first name given

						//obtain first name and team destination
						draftedFname = fullCommand[2];
						draftTo = translateStringToTeam(fullCommand[3]);
					}
					else  //no first name given

						//obtain team destination
						draftTo = translateStringToTeam(fullCommand[2]);

					//attempt to draft player
					performDraft(draftedLname, draftedFname, draftTo);

					break;

				case "idraft":   //[idraft, playerName]

					//obtain last name
					draftedLname = fullCommand[1];

					if (fullCommand.length == 3)  //if first name given

						//obtain first name
						draftedFname = fullCommand[2];

					//attempt to draft player into team A
					performDraft(draftedLname, draftedFname, getA());

					break;

				case "overall": 
					if(fullCommand.length == 2) {
						
						pos = fullCommand[1].toUpperCase().trim(); //get position user entered
						
						Position position = Player.stringToPosition(pos);
						int positionNum = Team.spotLocation(position);
						
						//if user A already drafted this position print an error message
						Player[] aTeam = getA().getMyTeam();
						
						if(aTeam[positionNum].getFirstName() == null)												
						Player.overall(pos);
						else 
							System.out.println("ERROR: Team A has already drafted this position");
					}
					
					else {
						Player.overall(); 	
					}
					
					break;
					
				case "poverall":
					if(getA().getNumOfPitchers() == 5) {
						System.out.println("ERROR: Team A already has 5 pitchers ");
					}
					else
					poverall();
					break;

				case "team":
					if(restore == false) {
					if(fullCommand[1].equalsIgnoreCase("A"))
						team(getA());
					else if(fullCommand[1].equalsIgnoreCase("B"))
						team(B);
					else if(fullCommand[1].equalsIgnoreCase("C"))
						team(C);
					else if(fullCommand[1].equalsIgnoreCase("D"))
						team(D);
					else
						System.out.print("Team not recognized, try again");
					break;
					}
					else {
						if(fullCommand[1].equalsIgnoreCase("A"))
							printRestoredStars(restoredTeamA);
						else if(fullCommand[1].equalsIgnoreCase("B"))
							printRestoredStars(restoredTeamB);
						else if(fullCommand[1].equalsIgnoreCase("C"))
							printRestoredStars(restoredTeamC);
						else if(fullCommand[1].equalsIgnoreCase("D"))
							printRestoredStars(restoredTeamD);
						else
							System.out.print("Team not recognized, try again");
						break;
						
					}


				case "stars":
					if(restore == false) {

					if(fullCommand[1].equalsIgnoreCase("A"))
						stars(getA());
					else if(fullCommand[1].equalsIgnoreCase("B"))
						stars(B);
					else if(fullCommand[1].equalsIgnoreCase("C"))
						stars(C);
					else if(fullCommand[1].equalsIgnoreCase("D"))
						stars(D);
					else
						System.out.print("Team not recognized, try again");
					break;
					}
					else {
						if(fullCommand[1].equalsIgnoreCase("A"))
							printRestoredStars(restoredTeamA);
						else if(fullCommand[1].equalsIgnoreCase("B"))
							printRestoredStars(restoredTeamB);
						else if(fullCommand[1].equalsIgnoreCase("C"))
							printRestoredStars(restoredTeamC);
						else if(fullCommand[1].equalsIgnoreCase("D"))
							printRestoredStars(restoredTeamD);
						else
							System.out.print("Team not recognized, try again");
						break;
						
					}

				case "evalfun":

					determineEvalFun();

					break;

				case "pevalfun":

					determinePreEvalFun();			

					break;

				case "save":


					String fileName =input.nextLine();

					save(fileName);
					saveTeamA(fileName);
					saveTeamB(fileName);
					saveTeamC(fileName);
					saveTeamD(fileName);
				
					break;

				case "restore":
						
						restore = true;

	
						String fileName1 = input.nextLine();
						

						ArrayList<String> restoredTeamAPlayers = restoreTeamAPlayers(fileName1);
						ArrayList<String> restoredTeamAPitchers = restoreTeamAPitchers(fileName1);
						restoredTeamA = concatinateFullRoster(restoredTeamAPlayers,restoredTeamAPitchers);
						
						restoredTeamA=(removeNull(restoredTeamA));
					
						ArrayList<String> restoredTeamBPlayers = restoreTeamBPlayers(fileName1);
						ArrayList<String> restoredTeamBPitchers = restoreTeamBPitchers(fileName1);
						restoredTeamB = concatinateFullRoster(restoredTeamBPlayers,restoredTeamBPitchers);
						
						restoredTeamB=(removeNull(restoredTeamB));
						
						ArrayList<String> restoredTeamCPlayers = restoreTeamCPlayers(fileName1);
						ArrayList<String> restoredTeamCPitchers = restoreTeamCPitchers(fileName1);
						restoredTeamC = concatinateFullRoster(restoredTeamCPlayers,restoredTeamCPitchers);
					
						restoredTeamC=(removeNull(restoredTeamC));
						
						ArrayList<String> restoredTeamDPlayers = restoreTeamDPlayers(fileName1);
						ArrayList<String> restoredTeamDPitchers = restoreTeamDPitchers(fileName1);
						restoredTeamD = concatinateFullRoster(restoredTeamDPlayers,restoredTeamDPitchers);
					
						restoredTeamD=(removeNull(restoredTeamD));
		
				
					break;

				case "quit":  //stop running program
					repeat = false;
					break;

				case "help":  //give user a list of valid commands

					//help for odraft command
					System.out.println("odraft: last name, first name or initial (optional), team name\n"
							+ "\tInput a player's name and add them to the indicated team.\n"
							+ "\tFirst name or initial is only required if there are multiple people with the given last name.\n"
							+ "\tMust be ordered last name -> first name.\n");

					//help for idraft command
					System.out.println("idraft: last name, first name or initial (optional)\n"
							+ "\tInput a player's name and add them to Team A.\n"
							+ "\tFirst name or initial is only required if there are multiple people with the given last name.\n"
							+ "\tMust be ordered last name -> first name.\n");

					//help for overall command
					System.out.println("overall: position (optional)\n"
							+ "\tGives a list of undrafted players for the specified position.\n"
							+ "\tIf no position given, list all undrafted, non-pitcher players.\n"
							+ "\tIf the user has already drafted a player into the indicated position, no players will be listed.\n");

					//help for poverall command
					System.out.println("poverall\n"
							+ "\tGives a list of undrafted pitchers.\n"
							+ "\tIf the user has already drafted five pitchers, no players will be listed.\n");

					//help for team command
					System.out.println("team: team name\n"
							+ "\tGives a list of players on the indicated team.\n"
							+ "\tPlayers are listed in order based on their position: C, 1B, 2B, 3B, SS, LF, CF, RF, P1, P2, P3, P4, P5\n");

					//help for stars command
					System.out.println("stars: team name\n"
							+ "\tGives a list of players on the indicated team.\n"
							+ "\tPlayers are listed in order based on the order they were drafted in.\n");

					//help for evalfun command
					System.out.println("evalfun: expression\n"
							+ "\tTakes a mathematical expression and ranks players based on it.\n"
							+ "\tOnly takes stats that apply to batters: ab, sb, avg, obp, and slg\n");

					//help for pevalfun command
					System.out.println("pevalfun: expression\n"
							+ "\tTakes a mathematical expression and ranks pitchers based on it.\n"
							+ "\tOnly takes stats that apply to pitchers: era, g, gs, ip, and bb\n");

					//help for save command
					System.out.println("save\n"
							+ "\tRecords the team and drafted information\n");

					//help for restore command
					System.out.println("restore\n"
							+ "\tRestores a previous save made for teams\n");

					//help for quit command
					System.out.println("quit\n"
							+ "\tStop running the program.\n");
					break;

				default: System.out.println("Your input " + fullCommand[0] + " is not a valid input. Type \"help\" for a list of commands.");

				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("Command incomplete. Please try again.");
			}
			catch(NullPointerException e) {
				System.out.println("Team not recognized. Try again.");
			}
		} while (repeat);

		System.out.println("Closing program.");
	}
	public static void team(Team team) {
		Player[] currentPlayers = team.getMyTeam(); //get team's current roster in order of position
		Pitcher[] currentPitchers = team.getPitchers(); //get team's current pitchers


		for(int i = 0; i<currentPlayers.length;i++) {
			if(currentPlayers[i].getFirstName() == null) {
				//if position is empty dont print anything
			}
			else 
				System.out.print(currentPlayers[i].toTeamString() + "\n");
			//else print roster, position first + lastname	
		}

		for(int i = 0;i<team.getNumOfPitchers();i++) {     
			if(currentPitchers[i].getFirstName() == null) {
				//if pitcher at position dont print anything
			}
			else
				System.out.print(currentPitchers[i].toPitcherString() + "\n");
				//print pitchers after, P first + lastname

		}
	}			


	public static void stars(Team team) {
		ArrayList<Integer> draftOrder = team.getDraftOrder();//get draft order of team by position #
		Object[] fullRoster = team.getFullRoster();//get teams full roster
		Pitcher[] currentPitchers = team.getPitchers(); //get team's current pitchers
		
		//print roster by draft order
		int j = 0;
		for(int i = 0; i<draftOrder.size();i++) {			
			if(fullRoster[draftOrder.get(i)].getClass().equals(Player.class))//if player object print player string, otherwise print pitcher string
				System.out.print(((Player) fullRoster[draftOrder.get(i)]).toTeamString() + "\n");
			else {
				System.out.print((currentPitchers[j].toPitcherString() + "\n"));
				j++;
			}
		}
	}

	/**
	 * Attempt to draft a named player into the indicated team
	 * 
	 * @param lname		Last name of the desired player
	 * @param fname		First name of the desired player (optional)
	 * @param intoTeam	Team that is attempting to draft the indicated player
	 */
	public static void performDraft(String lname, String fname, Team intoTeam) {

		//initialize player's location in both arraylists
		int playerLoc;
		int pitcherLoc;

		if (fname.compareTo("") == 0) {  //if no first name is given

			//look through player and pitcher arraylists for indicated last name
			playerLoc = findPlayer(lname);
			pitcherLoc = findPitcher(lname);
		}

		else {  //if first name is given

			//look through player and pitcher arraylists for indicated full name
			playerLoc = findPlayer(lname, fname);
			pitcherLoc = findPitcher(lname, fname);
		}

		if (pitcherLoc == -1 && playerLoc == -1)  //if name is not valid in either list
			System.out.println("Player not added to team. Either no players with given name found or more than one player with the name " + lname + " found.");

		else if(pitcherLoc > -1 && playerLoc > -1)  //if name is valid in both lists
			System.out.println("Player not added to team. More than one player with the name " + lname + "found.");

		else if (playerLoc > -1)  //if name is a valid player
			intoTeam.draftPlayer(availablePlayersToDraft.get(playerLoc));

		else  //if name is a valid pitcher
			intoTeam.draftPitcher(availablePitchersToDraft.get(pitcherLoc));
	}

	/**
	 * Locates the index of a given last name in the Player arraylist
	 * 
	 * @param 	lname	Last name of the player to find
	 * @return			Index of the player in the list
	 * 					or -1 if not found
	 */
	public static int findPlayer(String lname) {

		//instantiate number of players with the last name found
		int numOfLast = 0;

		//instantiate the index the player is in at to -1
		int index = -1;

		//search arraylist of Players
		for (int i = 0; i < availablePlayersToDraft.size(); i++) {
			if (availablePlayersToDraft.get(i).getLastName().equalsIgnoreCase(lname)) {  //if last name found
				index = i;  //record index location
				numOfLast++;  //increase number of names found
			}
		}

		if (numOfLast > 1)  //if more than one name found
			return -1;

		else  //if 1 or no names found
			return index;
	}

	/**
	 * Locates the index of a given last name and first initial in the Player arraylist
	 * 
	 * @param lname			Last name of the player to find
	 * @param firstInit		First initial of the player to find
	 * @return				Index of the player in the list
	 * 						or -1 if not found
	 */
	public static int findPlayer(String lname, String firstInit) {

		//instantiate number of players found
		int numOfLast = 0;

		//instantiate the index the player is in at to -1
		int index = -1;

		//search arraylist of players
		for (int i = 0; i < availablePlayersToDraft.size(); i++) {
			if (availablePlayersToDraft.get(i).getLastName().equalsIgnoreCase(lname)) {  //if last name found
				if (Character.compare(availablePlayersToDraft.get(i).getFirstName().toLowerCase().charAt(0),
						firstInit.toLowerCase().charAt(0)) == 0) {  //if first initial matches first initial in Player's name
					index = i;  //record index location
					numOfLast++;  //increase number of names found
				}
			}
		}

		if (numOfLast > 1)  //if more than one name found
			return -1;
		else  //if one or no names found
			return index;
	}

	/**
	 * Locates the index of a given last name in the Pitcher arraylist
	 * 
	 * @param lname		Last name of the pitcher to find
	 * @return			Index of the player in the list
	 * 					or -1 if not found
	 */
	public static int findPitcher(String lname) {

		//instantiate the number of pitchers with the last name found
		int numOfLast = 0;

		//instantiate the index the pitcher is at to -1
		int index = -1;

		//search arraylist of pitchers
		for (int i = 0; i < availablePitchersToDraft.size(); i++) {
			if (availablePitchersToDraft.get(i).getLastName().equalsIgnoreCase(lname)) {  //if last name found
				index = i;  //record index location
				numOfLast++;  //increase number of names found
			}
		}

		if (numOfLast > 1)  //if more than one name found
			return -1;
		else  //if one or no names found
			return index;
	}

	/**
	 * Locates the index of a given last name and first initial in the Pitcher arraylist
	 * 
	 * @param lname			Last name of the pitcher to find
	 * @param firstInit		First initial of the pitcher to find
	 * @return				Index of the player in the list
	 * 						or -1 if not found
	 */
	public static int findPitcher(String lname, String firstInit) {

		//instantiate the number of pitchers with the last name found
		int numOfLast = 0;

		//instantiate the index the pitcher is at to -1
		int index = -1;

		//search arraylist of pitchers
		for (int i = 0; i < availablePitchersToDraft.size(); i++) {
			if (availablePitchersToDraft.get(i).getLastName().equalsIgnoreCase(lname)) {  //if last name found
				if (Character.compare(availablePitchersToDraft.get(i).getFirstName().toLowerCase().charAt(0),
						firstInit.toLowerCase().charAt(0)) == 0) {  //if first initial and pitcher's first initial match
					index = i;  //record index location
					numOfLast++;  //increase number of names found
				}
			}
		}

		if (numOfLast > 1)  //if more than one name found
			return -1;
		else  //if 1 or no names found
			return index;
	}

	/**
	 * Convert string to the specified team
	 * 
	 * @param teamName	String referencing team
	 * @return			The user's intended team
	 */
	public static Team translateStringToTeam(String teamName) {
		switch(teamName.toUpperCase()) {
		case "A": return getA();
		case "B": return B;
		case "C": return C;
		case "D": return D;
		default: return null;
		}
	}


	public static void save(String fileName) throws Exception {

		ArrayList<Object> currentFullRoster = new ArrayList<>();

		Player[] playerArray = getA().getMyTeam();
		Pitcher[] pitcherArray = getA().getPitchers();


		/*
			Team roster = new Team();
			roster.getFullRoster();
			Object[] array = roster.getFullRoster();
		 */
		Collections.addAll(currentFullRoster, playerArray);
		Collections.addAll(currentFullRoster, pitcherArray);


		fileName = fileName.trim();

		//creates the file to be used later
		File fileToBeCreated = new File(fileName);
		//file already exists exception
		if (fileToBeCreated.exists()) {
			System.out.println("I'm sorry, but that file already exists. :(");
			System.exit(0);
		}

		//Creates array list of player objects as string
		ArrayList<String> rosterToString = new ArrayList <>();
		rosterToString.addAll(createFullRosterArrayList(currentFullRoster));



		//print out 
		PrintWriter output = new PrintWriter(fileToBeCreated);
		for (int i = 0; i< rosterToString.size();i++) {
			output.println(rosterToString.get(i));
		}
		output.close();




	}

	public static void saveTeamA(String fileName) throws Exception {
		ArrayList<Player> teamAPlayers = new ArrayList<>();
		ArrayList<Pitcher> teamAPitchers = new ArrayList<>();

		Player[] playerArray = getA().getMyTeam();
		Pitcher[] pitcherArray = getA().getPitchers();


		/*
		Team roster = new Team();
		roster.getFullRoster();
		Object[] array = roster.getFullRoster();
		 */
		Collections.addAll(teamAPlayers, playerArray);
		Collections.addAll(teamAPitchers, pitcherArray);


		fileName = fileName.trim();

		String playerFileName = fileName + "TeamAPlayers" + ".txt";
		String pitcherFileName = fileName + "TeamAPitchers" + ".txt";
		//creates the file to be used later
		File teamAPlayerFile = new File(playerFileName);
		File teamAPitcherFile = new File(pitcherFileName);
		//file already exists exception
		if (teamAPlayerFile.exists()) {
			System.out.println("I'm sorry, but that file already exists. :(");
			System.exit(0);
		}

		//Creates array list of player objects as string
		ArrayList<String> playerToString = new ArrayList <>();
		playerToString.addAll(createArrayListOfPlayerAsStrings(teamAPlayers));

		ArrayList<String> pitcherToString = new ArrayList <>();
		pitcherToString.addAll(createArrayListOfPitcherAsStrings(teamAPitchers));


		//print out 
		PrintWriter output = new PrintWriter(teamAPlayerFile);
		for (int i = 0; i< playerToString.size();i++) {
			output.println(playerToString.get(i));

		}
		output.close();
		PrintWriter output2 = new PrintWriter(teamAPitcherFile);
		for (int i = 0; i<pitcherToString.size();i++) {
			output2.println(pitcherToString.get(i));
		}
		output2.close();


	}

	public static void saveTeamB(String fileName) throws Exception {
		ArrayList<Player> teamBPlayers = new ArrayList<>();
		ArrayList<Pitcher> teamBPitchers = new ArrayList<>();

		Player[] playerArray = B.getMyTeam();
		Pitcher[] pitcherArray = B.getPitchers();


		/*
		Team roster = new Team();
		roster.getFullRoster();
		Object[] array = roster.getFullRoster();
		 */
		Collections.addAll(teamBPlayers, playerArray);
		Collections.addAll(teamBPitchers, pitcherArray);



		fileName = fileName.trim();

		String playerFileName = fileName + "TeamBPlayers" + ".txt";
		String pitcherFileName = fileName + "TeamBPitchers" + ".txt";
		//creates the file to be used later
		File teamBPlayerFile = new File(playerFileName);
		File teamBPitcherFile = new File(pitcherFileName);
		//file already exists exception
		if (teamBPlayerFile.exists()) {
			System.out.println("I'm sorry, but that file already exists. :(");
			System.exit(0);
		}

		//Creates array list of player objects as string
		ArrayList<String> playerToString = new ArrayList <>();
		playerToString.addAll(createArrayListOfPlayerAsStrings(teamBPlayers));

		ArrayList<String> pitcherToString = new ArrayList <>();
		pitcherToString.addAll(createArrayListOfPitcherAsStrings(teamBPitchers));


		//print out 
		PrintWriter output = new PrintWriter(teamBPlayerFile);
		for (int i = 0; i< playerToString.size();i++) {
			output.println(playerToString.get(i));
		}
		output.close();
		PrintWriter output2 = new PrintWriter(teamBPitcherFile);
		for (int i = 0; i<pitcherToString.size();i++) {
			output2.println(pitcherToString.get(i));
		}
		output2.close();

		
	}

	public static void saveTeamC(String fileName) throws Exception {
		ArrayList<Player> teamCPlayers = new ArrayList<>();
		ArrayList<Pitcher> teamCPitchers = new ArrayList<>();

		Player[] playerArray = C.getMyTeam();
		Pitcher[] pitcherArray = C.getPitchers();


		/*
		Team roster = new Team();
		roster.getFullRoster();
		Object[] array = roster.getFullRoster();
		 */
		Collections.addAll(teamCPlayers, playerArray);
		Collections.addAll(teamCPitchers, pitcherArray);


		fileName = fileName.trim();

		String playerFileName = fileName + "TeamCPlayers" + ".txt";
		String pitcherFileName = fileName + "TeamCPitchers" + ".txt";
		//creates the file to be used later
		File teamCPlayerFile = new File(playerFileName);
		File teamCPitcherFile = new File(pitcherFileName);
		//file already exists exception
		if (teamCPlayerFile.exists()) {
			System.out.println("I'm sorry, but that file already exists. :(");
			System.exit(0);
		}

		//Creates array list of player objects as string
		ArrayList<String> playerToString = new ArrayList <>();
		playerToString.addAll(createArrayListOfPlayerAsStrings(teamCPlayers));

		ArrayList<String> pitcherToString = new ArrayList <>();
		pitcherToString.addAll(createArrayListOfPitcherAsStrings(teamCPitchers));


		//print out 
		PrintWriter output = new PrintWriter(teamCPlayerFile);
		for (int i = 0; i< playerToString.size();i++) {
			output.println(playerToString.get(i));
		}
		output.close();
		PrintWriter output2 = new PrintWriter(teamCPitcherFile);
		for (int i = 0; i< pitcherToString.size();i++) {
			output2.println(pitcherToString.get(i));
		}
		output2.close();



	}
	public static void saveTeamD(String fileName) throws Exception {
		ArrayList<Player> teamDPlayers = new ArrayList<>();
		ArrayList<Pitcher> teamDPitchers = new ArrayList<>();

		Player[] playerArray = D.getMyTeam();
		Pitcher[] pitcherArray = D.getPitchers();


		/*
		Team roster = new Team();
		roster.getFullRoster();
		Object[] array = roster.getFullRoster();
		 */
		Collections.addAll(teamDPlayers, playerArray);
		Collections.addAll(teamDPitchers, pitcherArray);


		fileName = fileName.trim();

		String playerFileName = fileName + "TeamDPlayers" + ".txt";
		String pitcherFileName = fileName + "TeamDPitchers" + ".txt";
		//creates the file to be used later
		File teamDPlayerFile = new File(playerFileName);
		File teamDPitcherFile = new File(pitcherFileName);
		//file already exists exception
		if (teamDPlayerFile.exists()) {
			System.out.println("I'm sorry, but that file already exists. :(");
			System.exit(0);
		}

		//Creates array list of player objects as string
		ArrayList<String> playerToString = new ArrayList <>();
		playerToString.addAll(createArrayListOfPlayerAsStrings(teamDPlayers));

		ArrayList<String> pitcherToString = new ArrayList <>();
		pitcherToString.addAll(createArrayListOfPitcherAsStrings(teamDPitchers));


		//print out 
		PrintWriter output = new PrintWriter(teamDPlayerFile);
		for (int i = 0; i< playerToString.size();i++) {
			output.println(playerToString.get(i));

		}
		output.close();
		PrintWriter output2 = new PrintWriter(teamDPitcherFile);
		for (int i = 0; i< pitcherToString.size();i++) {
			output2.println(pitcherToString.get(i));
		}
		output2.close();

	}

	
	
	

	
	public static ArrayList<String> concatinateFullRoster(ArrayList<String> l1, ArrayList<String> l2 ) {
		ArrayList<String> aPlusB = new ArrayList<>();
		aPlusB.addAll(l1);
		aPlusB.addAll(l2);
		return aPlusB;
	}
	
	


	public static ArrayList<String> restoreTeamAPlayers (String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPlayers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamAPlayers" + ".txt";
		


		File file = new File(fileName);



		// Create a Scanner for the file
		Scanner input = new Scanner(file);

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}


			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPlayers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
		
		
		
	return restoredPlayers;

	}
	
	public static ArrayList<String> restoreTeamAPitchers(String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPitchers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamAPitchers" + ".txt";
		
		

		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPitchers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
	
		
		return restoredPitchers;
	

	}
	
public static ArrayList<String> restoreTeamBPlayers (String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPlayers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamBPlayers" + ".txt";
		


		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPlayers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
		
		
		
	return restoredPlayers;

	}
	
	public static ArrayList<String> restoreTeamBPitchers(String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPitchers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamBPitchers" + ".txt";
		
		

		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);

			
			
			String lastName = creating.get(1);
			

		
			String position = creating.get(2);
			
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPitchers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
	
		
		return restoredPitchers;
	

	}
	
public static ArrayList<String> restoreTeamCPlayers (String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPlayers = new ArrayList<>();


		fileName = fileName.trim();


		fileName = fileName + "TeamCPlayers" + ".txt";
		


		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}


			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPlayers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
		
		
		
	return restoredPlayers;


	}
	
	public static ArrayList<String> restoreTeamCPitchers(String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPitchers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamCPitchers" + ".txt";
		
		

		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPitchers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
	
		
		return restoredPitchers;
	

	}
	
public static ArrayList<String> restoreTeamDPlayers (String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPlayers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamDPlayers" + ".txt";
		


		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPlayers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
		
		
		
	return restoredPlayers;

	}
	
	public static ArrayList<String> restoreTeamDPitchers(String fileName) throws Exception {
		
		ArrayList<String> creating = new ArrayList<>();
		ArrayList<String> restoredPitchers = new ArrayList<>();

		fileName = fileName.trim();

		fileName = fileName + "TeamDPitchers" + ".txt";
		
		File file = new File(fileName);


		// Create a Scanner for the file
		Scanner input = new Scanner(file);
	

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			
			String lastName = creating.get(1);

		
			String position = creating.get(2);
			
			String restoredLine = firstName + " " + lastName + " " + position;
			restoredPitchers.add(restoredLine);
		
			
			creating.clear();
		}
		input.close();
		
	
		
		return restoredPitchers;
	

	}

	public static ArrayList<String> determineEvalFun() throws ScriptException {
		Scanner input = new Scanner(System.in);
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String expression = input.nextLine();



		for (int i=0; i<availablePlayersToDraft.size(); i++) {

			int ab = availablePlayersToDraft.get(i).getAB();
			int sb = availablePlayersToDraft.get(i).getSB();
			float avg = availablePlayersToDraft.get(i).getAVG();
			float obp = availablePlayersToDraft.get(i).getOBP();
			float slg = availablePlayersToDraft.get(i).getSLG();


			engine.put("AB", ab);
			engine.put("SB", sb);
			engine.put("AVG", avg);
			engine.put("OBP", obp);
			engine.put("SLG", slg);




			String computation = engine.eval(expression.toUpperCase()).toString();

			String elementToAdd = availablePlayersToDraft.get(i).getFirstName().toString() + " " + availablePlayersToDraft.get(i).getLastName().toString() + " " 
					+ availablePlayersToDraft.get(i).getPosition().toString() + " " + computation;

			evalFunList.add(elementToAdd);

		}
		
		return evalFunList;

	}

	public static ArrayList<String> determinePreEvalFun() throws ScriptException {
		Scanner input = new Scanner(System.in);

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		String expression = input.nextLine();


		for (int i=0; i<availablePitchersToDraft.size(); i++) {

			double era = availablePitchersToDraft.get(i).getERA();
			int g = availablePitchersToDraft.get(i).getG();
			int gs = availablePitchersToDraft.get(i).getGS();
			double ip = availablePitchersToDraft.get(i).getIP();
			int bb = availablePitchersToDraft.get(i).getBB();


			engine.put("ERA", era);
			engine.put("G", g);
			engine.put("GS", gs);
			engine.put("IP", ip);
			engine.put("BB", bb);


			String computation = engine.eval(expression.toUpperCase()).toString();

			String elementToAdd = availablePitchersToDraft.get(i).getFirstName().toString() + " " + availablePitchersToDraft.get(i).getLastName().toString() + " " 
					+ computation;

			preEvalFunList.add(elementToAdd);

		}

		return preEvalFunList;


	}




	public static ArrayList<String> createArrayListOfPlayerAsStrings (ArrayList<Player> list) {

		ArrayList<String> playerToString = new ArrayList<>();

		for (int i =0; i< list.size(); i++) {

			String objectAsString = list.get(i).getFirstName() + " " + list.get(i).getLastName() + " " + list.get(i).getPosition().toString() + " " +
					list.get(i).getTeam() + " " + String.valueOf(list.get(i).getAB()) + " " + String.valueOf(list.get(i).getSB()) + " " + 
					String.valueOf(list.get(i).getAVG()) + " " + String.valueOf(list.get(i).getOBP()) + " " + String.valueOf(list.get(i).getSLG());
			playerToString.add(objectAsString);
		}
		return playerToString;
	}

	public static ArrayList<String> createArrayListOfPitcherAsStrings (ArrayList<Pitcher> list) {

		ArrayList<String> pitcherToString = new ArrayList<>();

		for (int i =0; i< list.size(); i++) {
			//public Pitcher(String fname, String lname, String t, float erA, int games, int average, float inningsPitched, int walks)
			String objectAsString = list.get(i).getFirstName() + " " + list.get(i).getLastName() + " " + list.get(i).getTeam() + " " +
					String.valueOf(list.get(i).getERA()) + " " + String.valueOf(list.get(i).getGS()) + " " + 
					String.valueOf(list.get(i).getIP()) + " " + String.valueOf(list.get(i).getBB());
			pitcherToString.add(objectAsString);
		}
		return pitcherToString;



	}
	
	

	public static ArrayList<String> createFullRosterArrayList (ArrayList<Object> list) {

		ArrayList<String> fullRosterToString = new ArrayList<>();

		for (int i =0; i< list.size(); i++) {
			fullRosterToString.add(i, list.get(i).toString());

		}
		return fullRosterToString;



	}
	
	public static void printRestoredStars (ArrayList<String> list) {
		for (int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
		
	}
	public static ArrayList<String> removeNull(ArrayList<String> list) {
		ArrayList<String> nullFree = new ArrayList<>();
			for(int i=0; i<list.size(); i++) {
				if(!(list.get(i).contains("null"))) {
					nullFree.add(list.get(i));
				}
				
			}
			
				
		
	
			
		
		return nullFree;
	}

	public static void printPitchers(ArrayList<Pitcher> list) {


		//creates an arraylist of pitchers to be printed
		ArrayList<String> pitcherToString = new ArrayList<>();
		pitcherToString.addAll(createArrayListOfPitcherAsStrings(list));

		//prints them out
		for (int i =0; i<pitcherToString.size(); i++) {
			System.out.println(pitcherToString.get(i));
		}

	}

	public static void printPlayers(ArrayList<Player> list) {


		//creates an arraylist of pitchers to be printed
		ArrayList<String> pitcherToString = new ArrayList<>();
		pitcherToString.addAll(createArrayListOfPlayerAsStrings(list));

		//prints them out
		for (int i =0; i<pitcherToString.size(); i++) {
			System.out.println(pitcherToString.get(i));
		}

	}
	public static void poverall() throws ScriptException {
		Pitcher.pOverall();
	}

	public static void createBatterList() throws Exception{

		File file = new File("batting.txt");

		Scanner input = new Scanner(file);

		ArrayList<Player> batters = new ArrayList<>();

		ArrayList<String> creating = new ArrayList<>();


		while (input.hasNext()) {

			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			String lastName = creating.get(1);

			String position = creating.get(2);
			String team = creating.get(3);

			int ab = Integer.parseInt(creating.get(4));

			int sb = Integer.parseInt(creating.get(5));


			float avg = Float.parseFloat(creating.get(6));
			float obp = Float.parseFloat(creating.get(7));
			float slg = Float.parseFloat(creating.get(8));
			Player p = new Player (firstName, lastName, position, team, ab, sb, avg, obp, slg);
			batters.add(p);
			creating.clear();
		}
		input.close();

		availablePlayersToDraft.addAll(batters);
	}

	public static void createPitcherList() throws Exception {
		// Create a File instance
		File file = new File("pitchers.txt");

		// Create a Scanner for the file
		Scanner input = new Scanner(file);

		ArrayList<Pitcher> pitchers = new ArrayList<>();
		ArrayList<String> creating = new ArrayList<>();

		while (input.hasNext()) {
			String line = input.nextLine();
			String strA[] = line.split(" ");
			for (int i = 0; i < strA.length; i++) {
				creating.add(strA[i]);
			}

			String firstName = creating.get(0);
			String lastName = creating.get(1);


			String team = creating.get(2);

			float era = Float.parseFloat(creating.get(3));

			int games = Integer.parseInt(creating.get(4));


			int avg = Integer.parseInt(creating.get(5));
			float ip = Float.parseFloat(creating.get(6));
			int bb = Integer.parseInt(creating.get(7));
			Pitcher p = new Pitcher (firstName, lastName,  team, era, games, avg, ip, bb);
			pitchers.add(p);
			creating.clear();
		}
		input.close();

		availablePitchersToDraft.addAll(pitchers);

	}
	public static Team getA() {
		return A;
	}
	public static void setA(Team a) {
		A = a;
	}

}


