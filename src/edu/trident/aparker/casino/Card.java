package edu.trident.aparker.casino;

public class Card
{
	private int cardValue;
	private int cardFace;
	private String cardSuit;
	
	public Card(int newVal, String newSuit)
	{
		cardSuit = newSuit;
		cardFace = newVal;
		if(cardFace == 11 || cardFace == 12 || cardFace == 13)
		{
			cardValue = 10;
		}
		else
		{
			cardValue = cardFace;
		}
	}
	
	public String getCardSuit()
	{
		return cardSuit;
	}
	
	public int getCardFace()
	{
		return cardFace;
	}
	
	public int getCardValue()
	{
		return cardValue;
	}
	
	public String getCardString()
	{
		String valueString;
		if(cardFace == 1)
		{
			valueString = "Ace";
		}
		else if(cardValue == 11)
		{
			valueString = "Jack";
		}
		else if(cardValue == 12)
		{
			valueString = "Queen";
		}
		else if(cardValue == 13)
		{
			valueString = "King";
		}
		else
		{
			valueString = String.valueOf(cardValue);
		}
		
		return (valueString + " of " + cardSuit);
	}
}
