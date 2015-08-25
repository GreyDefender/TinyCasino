package edu.trident.aparker.casino;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Blackjack extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -1735706887088736131L;
	Player blackjackPlayer;
	SaveManager blackjackManager;
	Casino blackjackCasino;
	JFrame blackjackFrame;
	BlackjackHand dealerHand;
	BlackjackHand playerHand;
	
	private int wager;
	private int prize;
	
	JComboBox<String> wagerBox;
	JButton newHandButton;
	JButton hitButton;
	JButton stayButton;
	JLabel creditPriceLabel;
	JLabel creditPrizeLabel;
	JLabel blackjackCreditsLabel;
	JLabel dealerHandLabel;
	JLabel playerHandLabel;
	JLabel resultLabel;
	
	Dimension size;
	
	public Blackjack(Player bPlayer, SaveManager bManager, Casino bCasino)
	{
		blackjackPlayer = bPlayer;
		blackjackManager = bManager;
		blackjackCasino = bCasino;
		blackjackFrame = new JFrame("Tiny Casino - Blackjack");
		blackjackFrame.setLayout(null);
		blackjackFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		blackjackFrame.setSize(750, 190);
		blackjackFrame.setResizable(false);
		blackjackFrame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				blackjackManager.save(blackjackPlayer);
				blackjackCasino.enableButtons();
				JOptionPane.showMessageDialog(null, "                         Save complete!", "Tiny Casino", JOptionPane.PLAIN_MESSAGE);
				blackjackCasino.refresh();
				blackjackFrame.dispose();
			}
		});
		
		String[] wagerList = {"10", "100", "1000"};
		wagerBox = new JComboBox<String>(wagerList);
		wagerBox.setSelectedItem("10");
		wager = Integer.parseInt((String) wagerBox.getSelectedItem());
		prize = (int)(wager * 2.5);
		
		newHandButton = new JButton("Deal");
		hitButton = new JButton("Hit");
		hitButton.setEnabled(false);
		stayButton = new JButton("Stay");
		stayButton.setEnabled(false);
		creditPriceLabel = new JLabel("Cost to play (credits): ");
		creditPrizeLabel = new JLabel("Prize: " + prize + " credits");
		blackjackCreditsLabel = new JLabel("Current credits: " + blackjackPlayer.getPlayerCredits() + " credits");
		dealerHandLabel = new JLabel(blackjackPlayer.getDealerName() + "'s hand: ");
		playerHandLabel = new JLabel(blackjackPlayer.getPlayerName() + "'s hand: ");
		resultLabel = new JLabel();
		
		newHandButton.addActionListener(this);
		hitButton.addActionListener(this);
		stayButton.addActionListener(this);
		wagerBox.addActionListener(this);
		
		Insets insets = blackjackFrame.getInsets();
		
		size = newHandButton.getPreferredSize();
		newHandButton.setBounds(250 + insets.left, 10 + insets.top, size.width, 30);
		size = hitButton.getPreferredSize();
		hitButton.setBounds(10 + insets.left, 120 + insets.top, size.width, 30);
		size = stayButton.getPreferredSize();
		stayButton.setBounds(65 + insets.left, 120 + insets.top, size.width, 30);
		size = creditPriceLabel.getPreferredSize();
		creditPriceLabel.setBounds(10 + insets.left, 10 + insets.top, size.width, size.height);
		size = wagerBox.getPreferredSize();
		wagerBox.setBounds(135 + insets.left, 10 + insets.top, size.width, size.height);
		size = creditPriceLabel.getPreferredSize();
		creditPrizeLabel.setBounds(10 + insets.left, 25 + insets.top, size.width, size.height);
		size = blackjackCreditsLabel.getPreferredSize();
		blackjackCreditsLabel.setBounds(10 + insets.left, 40 + insets.top, 400, size.height);
		size = dealerHandLabel.getPreferredSize();
		dealerHandLabel.setBounds(10 + insets.left, 70 + insets.top, 730, size.height);
		size = playerHandLabel.getPreferredSize();
		playerHandLabel.setBounds(10 + insets.left, 95 + insets.top, 730, size.height);
		size = blackjackCreditsLabel.getPreferredSize();
		resultLabel.setBounds(140 + insets.left, 127 + insets.top, 150, size.height);
		
		
		blackjackFrame.add(newHandButton);
		blackjackFrame.add(hitButton);
		blackjackFrame.add(stayButton);
		blackjackFrame.add(creditPriceLabel);
		blackjackFrame.add(creditPrizeLabel);
		blackjackFrame.add(blackjackCreditsLabel);
		blackjackFrame.add(dealerHandLabel);
		blackjackFrame.add(playerHandLabel);
		blackjackFrame.add(resultLabel);
		blackjackFrame.add(wagerBox);
		blackjackFrame.setVisible(true);
	}
	
	public void blackjackRefresh()
	{
		blackjackCreditsLabel.setText("Current credits: " + blackjackPlayer.getPlayerCredits() + " credits");
	}
	
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == newHandButton)
		{
			if(blackjackPlayer.getPlayerCredits() < 0)
			{
				JOptionPane.showMessageDialog(null, "Uh oh, you ran out of credits, so they kicked you out of the game!", "Tiny Casino", JOptionPane.PLAIN_MESSAGE);
				blackjackCasino.enableButtons();
				blackjackManager.save(blackjackPlayer);
				JOptionPane.showMessageDialog(null, "                         Save complete!", "Tiny Casino", JOptionPane.PLAIN_MESSAGE);
				blackjackCasino.refresh();
				blackjackFrame.dispose();
			}
			hitButton.setEnabled(true);
			stayButton.setEnabled(true);
			resultLabel.setText("");
			this.blackjackRefresh();
			blackjackPlayer.removeCredits(wager);
			
			dealerHand = new BlackjackHand();
			dealerHand.aceUpgrade();
			playerHand = new BlackjackHand();
			playerHand.aceUpgrade();
			
			
			
			while(dealerHand.cardsInHand() < 5 && dealerHand.getHandValue() < 17)
			{
				dealerHand.addCard();
				if(dealerHand.getHandValue() > 21)
				{
					if(dealerHand.hasAce())
					{
						dealerHand.aceDowngrade();
						if(dealerHand.getHandValue() > 21)
						{
							dealerHand.setBusted(true);
						}
					}
					else
					{
						dealerHand.setBusted(true);
					}
				}
			}
			dealerHandLabel.setText(blackjackPlayer.getDealerName() + "'s hand: " + dealerHand.getDeckString());
			playerHandLabel.setText(blackjackPlayer.getPlayerName() + "'s hand: " + playerHand.getDeckString());
			
			if(dealerHand.getHandValue() == 21)
			{
				dealerHand.setHandWin(true);
			}
			
			if(playerHand.getHandValue() == 21)
			{
				playerHand.setHandWin(true);
			}
			
			if(dealerHand.getBusted() == true)
			{
				playerHand.setHandWin(true);
			}
			
			if(dealerHand.getHandWin())
			{
				resultLabel.setText("You lose!");
				this.blackjackRefresh();
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
			}
			else if(playerHand.getHandWin())
			{
				blackjackPlayer.addCredits(prize);
				blackjackPlayer.addBlackjackWins();
				resultLabel.setText("You win!");
				this.blackjackRefresh();
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
			}
			
		}
		else if(source == hitButton)
		{
			playerHand.addCard();
			playerHandLabel.setText(blackjackPlayer.getPlayerName() + "'s hand: " + playerHand.getDeckString());
			
			if(playerHand.getHandValue() > 21)
			{
				if(playerHand.hasAce())
				{
					playerHand.aceDowngrade();
					if(playerHand.getHandValue() > 21)
					{
						playerHand.setBusted(true);
					}
				}
				else
				{
					playerHand.setBusted(true);
				}
			}
			
			if(playerHand.getHandValue() == 21)
			{
				playerHand.setHandWin(true);
			}
			
			if(playerHand.cardsInHand() >= 5)
			{
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
				if(dealerHand.getHandValue() >= playerHand.getHandValue())
				{
					dealerHand.setHandWin(true);
				}
				else
				{
					playerHand.setHandWin(true);
				}
			}
			
			if(playerHand.getBusted() == true)
			{
				dealerHand.setHandWin(true);
			}
			
			if(dealerHand.getHandWin())
			{
				resultLabel.setText("You lose!");
				this.blackjackRefresh();
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
			}
			else if(playerHand.getHandWin())
			{
				blackjackPlayer.addCredits(prize);
				blackjackPlayer.addBlackjackWins();
				resultLabel.setText("You win!");
				this.blackjackRefresh();
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
			}
		}
		else if(source == stayButton)
		{
			hitButton.setEnabled(false);
			stayButton.setEnabled(false);
			if(dealerHand.getHandValue() >= playerHand.getHandValue())
			{
				dealerHand.setHandWin(true);
			}
			else
			{
				playerHand.setHandWin(true);
			}
			
			if(dealerHand.getHandWin())
			{
				resultLabel.setText("You lose!");
				this.blackjackRefresh();
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
			}
			else if(playerHand.getHandWin())
			{
				blackjackPlayer.addCredits(prize);
				blackjackPlayer.addBlackjackWins();
				resultLabel.setText("You win!");
				this.blackjackRefresh();
				hitButton.setEnabled(false);
				stayButton.setEnabled(false);
			}
		}
		else if(source == wagerBox)
		{
			wager = Integer.parseInt((String)wagerBox.getSelectedItem());
			prize = (int)(wager * 2.5);
			creditPrizeLabel.setText("Prize: " + prize + " credits");
		}
	}
}
