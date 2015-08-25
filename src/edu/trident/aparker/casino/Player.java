package edu.trident.aparker.casino;

public class Player
{	
	private String playerName;
	private String[] dealerList = {"Adam",
						   "Eve",
						   "Jack",
						   "Jane",
						   "Grey",
						   "Jessica",
						   "Mark",
						   "Faye",
						   "Steven",
						   "Stephanie",
						   "Alex",
						   "Alexis",
						   "Roger",
						   "Veronica",
						   "Daniel",
						   "Brittany",
						   "Nathan",
						   "Whitney",
						   "Andrew",
						   "Shannon"};
	private String dealerName;
	private int playerCredits;
	private int blackjackWins;
	
	public Player(String name, String credits, String loadBlackjackWins, String loadDealer)
	{
		playerName = name;
		playerCredits = Integer.parseInt(credits);
		blackjackWins = Integer.parseInt(loadBlackjackWins);
		dealerName = loadDealer;
	}
	
	public Player(String name, int credits, int dealerId)
	{
		playerName = name;
		playerCredits = credits;
		dealerName = dealerList[dealerId];
		blackjackWins = 0;
	}
	
	public void addCredits(int creditsAdded)
	{
		playerCredits += creditsAdded;
	}
	
	public void removeCredits(int creditsRemoved)
	{
		playerCredits -= creditsRemoved;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public String getDealerName()
	{
		return dealerName;
	}
	
	public void setDealerName(String newName)
	{
		dealerName = newName;
	}
	
	public void newRandomDealer()
	{
		int newDeal = (int)(Math.random()*20);
		dealerName = dealerList[newDeal];
	}
	
	public int getPlayerCredits()
	{
		return playerCredits;
	}
	
	public int getBlackjackWins()
	{
		return blackjackWins;
	}
	
	public void addBlackjackWins()
	{
		blackjackWins++;
	}
	
	public void resetPlayer()
	{
		playerCredits = 100;
		blackjackWins = 0;
	}
}
