package edu.trident.aparker.casino;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SaveManager
{
	ArrayList<Player> playerList = new ArrayList<Player>();
	private String savefile;
	public SaveManager(String filename)
	{
		savefile = filename;
	}
	
	public void openFile()
	{
		try
		{
			Scanner s = new Scanner(new BufferedReader(new FileReader(savefile)));
			
			while (s.hasNextLine())
			{
				String line = s.nextLine();
				String[] splitLine = line.split(",");
				try
				{
					playerList.add(new Player(splitLine[0], splitLine[1], splitLine[2], splitLine[3]));
				}
				catch(NumberFormatException nfe)
				{
					System.out.println("Invalid save entry: " + line);
				}
			}
			
			s.close();
		}
		catch (FileNotFoundException fne)
		{
			System.out.println("Save file not found.");
		}
		
		
	}
	
	public void save(Player savePlayer)
	{
		try
		{
			PrintWriter fileWriter = new PrintWriter(new File(savefile));
			
			for(int pInd = 0; pInd < playerList.size(); pInd++)
			{
				if(savePlayer.getPlayerName().equals(playerList.get(pInd).getPlayerName()))
				{
					playerList.set(pInd, savePlayer);
				}
				fileWriter.println(playerList.get(pInd).getPlayerName() + "," +
								   playerList.get(pInd).getPlayerCredits() + "," +
								   playerList.get(pInd).getBlackjackWins() + "," +
								   playerList.get(pInd).getDealerName());
			}
			fileWriter.close();
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
		}
	}
	
	public Player load(String loadName)
	{
		int dealGen;
		boolean found = false;
		this.openFile();
		Player returnPlayer = null;

		for(int pInd = 0; pInd < playerList.size(); pInd++)
		{
			if(loadName.equals(playerList.get(pInd).getPlayerName()))
			{
				found = true;
				returnPlayer = playerList.get(pInd);
			}
		}
		
		if(found == false)
		{
			dealGen = (int)(Math.random()*20);
			playerList.add(new Player(loadName, 100, dealGen));
			returnPlayer = playerList.get(playerList.size() - 1 );
			System.out.println("Generating new save profile with name " + loadName);
		}
		
		return returnPlayer;
	}
}
