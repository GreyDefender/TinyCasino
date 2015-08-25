package edu.trident.aparker.casino;

import java.util.ArrayList;

public class BlackjackHand
{
	ArrayList<Card> myHand = new ArrayList<Card>();
	
	private int handValue;
	private boolean aceBoost;
	private boolean busted;
	private boolean handWin;
	
	public BlackjackHand()
	{
		this.addCard();
		this.addCard();
		
		handValue = myHand.get(0).getCardValue() + myHand.get(1).getCardValue();
		aceBoost = false;
		busted = false;
		handWin = false;
	}
	
	public void addCard()
	{
		String[] suitList = {"Spades",
				 			 "Hearts",
				 			 "Clubs",
				 			 "Diamonds"};
		int randVal;
		String randSuit;
		boolean duplicate;
		
		if(myHand.size() == 0)
		{
			randVal = (int)(Math.random()*13) + 1;
			randSuit = suitList[(int)(Math.random()*4)];
			
			myHand.add(new Card(randVal, randSuit));
		}
		else
		{
			do
			{
				duplicate = false;
				randVal = (int)(Math.random()*13) + 1;
				randSuit = suitList[(int)(Math.random()*4)];
				
				for(int handWalk = 0; handWalk < myHand.size(); handWalk++)
				{
					if(randVal == myHand.get(handWalk).getCardFace() && randSuit.equals(myHand.get(handWalk).getCardSuit()))
					{
						duplicate = true;
					}
				}
					
			}
			while(duplicate == true);
			
			myHand.add(new Card(randVal, randSuit));
		}
	}
	
	public String getDeckString()
	{
		String deckString = "";
		for(int deckStrInd = 0; deckStrInd < myHand.size(); deckStrInd++)
		{
			deckString += myHand.get(deckStrInd).getCardString();
			if(deckStrInd != myHand.size() - 1)
			{
				deckString += ", ";
			}
		}
		
		return deckString;
	}
	
	public void setBusted(boolean newBusted)
	{
		busted = newBusted;
	}
	
	public boolean getBusted()
	{
		return busted;
	}
	
	public void setHandWin(boolean newHandWin)
	{
		handWin = newHandWin;
	}
	
	public boolean getHandWin()
	{
		return handWin;
	}
	
	public void aceUpgrade()
	{
		aceBoost = true;
	}
	
	public void aceDowngrade()
	{
		aceBoost = false;
	}
	
	public int cardsInHand()
	{
		return myHand.size();
	}
	
	public boolean hasAce()
	{
		boolean acePresent = false;
		
		for(int aceWalk = 0; aceWalk < myHand.size(); aceWalk++)
		{
			if(myHand.get(aceWalk).getCardFace() == 1)
			{
				acePresent = true;
			}
		}
		
		return acePresent;
	}
	
	public int getHandValue()
	{
		int tempValue = 0;
		
		for(int handCount = 0; handCount < myHand.size(); handCount++)
		{
			tempValue += myHand.get(handCount).getCardValue();
		}
		
		if(aceBoost && this.hasAce())
		{
			tempValue += 10;
		}
		
		handValue = tempValue;
		
		return handValue;
	}
	
}
