package edu.trident.aparker.casino;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Casino extends JFrame implements ActionListener
{
	private static final long serialVersionUID = -1735706887088736131L;
	SaveManager manager = new SaveManager("SaveFile.csv");
	Player myPlayer;
	JFrame mainframe;
	JLabel titleLabel;
	JLabel playerNameLabel;
	JLabel dealerNameLabel;
	JLabel creditsLabel;
	JLabel blackjackLabel;
	JLabel newDealerLabel;
	JLabel randomDealerLabel;
	JLabel saveLabel;
	JLabel resetLabel;
	JButton blackjackButton;
	JButton setDealerButton;
	JButton randomDealerButton;
	JButton saveButton;
	JButton resetButton;
	JTextField newDealerField;
	
	public Casino()
	{
		myPlayer = manager.load(JOptionPane.showInputDialog(null, "Please enter your name:"));
		
		mainframe = new JFrame("Tiny Casino");
		mainframe.setLayout(new GridLayout(15,1));
		mainframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainframe.setSize(300, 350);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		JPanel newDealerPanel = new JPanel();
		newDealerPanel.setLayout(new FlowLayout());
		JPanel randomDealerPanel = new JPanel();
		randomDealerPanel.setLayout(new FlowLayout());
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new FlowLayout());
		JPanel resetPanel = new JPanel();
		resetPanel.setLayout(new FlowLayout());
		
		titleLabel = new JLabel("Welcome to the Tiny Casino!");
		playerNameLabel = new JLabel("Player: " + myPlayer.getPlayerName());
		dealerNameLabel = new JLabel("Dealer: " + myPlayer.getDealerName());
		creditsLabel = new JLabel("Credits: " + myPlayer.getPlayerCredits());
		blackjackLabel = new JLabel("Blackjack wins: " + myPlayer.getBlackjackWins());
		newDealerLabel = new JLabel("Change dealer name");
		randomDealerLabel = new JLabel("Choose random dealer");
		saveLabel = new JLabel("Manual data save");
		resetLabel = new JLabel("Reset credits and wins");
		
		blackjackButton = new JButton("Play Blackjack");
		setDealerButton = new JButton("Change Dealer");
		randomDealerButton = new JButton("Random Dealer");
		saveButton = new JButton("Save");
		resetButton = new JButton("Reset");
		
		blackjackButton.addActionListener(this);
		setDealerButton.addActionListener(this);
		randomDealerButton.addActionListener(this);
		saveButton.addActionListener(this);
		resetButton.addActionListener(this);
		
		newDealerField = new JTextField("New dealer name", 15);
		
		titlePanel.add(titleLabel);
		mainframe.add(titlePanel);
		
		mainframe.add(playerNameLabel);
		mainframe.add(dealerNameLabel);
		mainframe.add(creditsLabel);
		mainframe.add(blackjackLabel);
		mainframe.add(blackjackButton);
		newDealerPanel.add(newDealerLabel);
		mainframe.add(newDealerPanel);
		mainframe.add(newDealerField);
		mainframe.add(setDealerButton);
		randomDealerPanel.add(randomDealerLabel);
		mainframe.add(randomDealerPanel);
		mainframe.add(randomDealerButton);
		savePanel.add(saveLabel);
		mainframe.add(savePanel);
		mainframe.add(saveButton);
		resetPanel.add(resetLabel);
		mainframe.add(resetPanel);
		mainframe.add(resetButton);
		
		mainframe.setResizable(false);
		mainframe.setVisible(true);
	}
	
	public void refresh()
	{
		playerNameLabel.setText("Player: " + myPlayer.getPlayerName());
		dealerNameLabel.setText("Dealer: " + myPlayer.getDealerName());
		creditsLabel.setText("Credits: " + myPlayer.getPlayerCredits());
		blackjackLabel.setText("Blackjack wins: " + myPlayer.getBlackjackWins());
	}
	
	public void disableButtons()
	{
		blackjackButton.setEnabled(false);
		setDealerButton.setEnabled(false);
		randomDealerButton.setEnabled(false);
		saveButton.setEnabled(false);
		resetButton.setEnabled(false);
	}
	
	public void enableButtons()
	{
		blackjackButton.setEnabled(true);
		setDealerButton.setEnabled(true);
		randomDealerButton.setEnabled(true);
		saveButton.setEnabled(true);
		resetButton.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		
		if(source == blackjackButton)
		{
			if(myPlayer.getPlayerCredits() >= 0)
			{
				this.disableButtons();
				@SuppressWarnings("unused")
				Blackjack myBlackjack = new Blackjack(myPlayer, manager, this);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "You're out of credits! Reset your account to start again.", "Tiny Casino", JOptionPane.PLAIN_MESSAGE);
			}
			
		}
		else if(source == setDealerButton)
		{
			String dealerInput = newDealerField.getText();
			if(dealerInput.equals(""))
			{
				myPlayer.setDealerName("Anonymous");
				dealerNameLabel.setText("Dealer: " + myPlayer.getDealerName());
			}
			else
			{
				myPlayer.setDealerName(dealerInput);
				dealerNameLabel.setText("Dealer: " + myPlayer.getDealerName());
			}
		}
		else if(source == randomDealerButton)
		{
			myPlayer.newRandomDealer();
			dealerNameLabel.setText("Dealer: " + myPlayer.getDealerName());
		}
		else if(source == saveButton)
		{
			manager.save(myPlayer);
			JOptionPane.showMessageDialog(null, "                         Save complete!", "Tiny Casino", JOptionPane.PLAIN_MESSAGE);
		}
		else if(source == resetButton)
		{
			myPlayer.resetPlayer();
			blackjackLabel.setText("Blackjack wins: " + myPlayer.getBlackjackWins());
			creditsLabel.setText("Credits: " + myPlayer.getPlayerCredits());
		}
	}
}
