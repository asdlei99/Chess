package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AnalyzeInput extends JFrame {
	private JPanel containerPanel = new JPanel();
	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	//private JLabel label = new JLabel();
	public JTextField fenInputTextfield = new JTextField(30);
	public JButton bestMoveButton = new JButton("Foresl�tt trekk");
	private JLabel bestMoveLabel = new JLabel();
	private JLabel showCurrentFenLabel = new JLabel();
	private JLabel fenTitle = new JLabel("N�v�rende FEN: ");
	private JLabel fenInputTitle = new JLabel("Sett inn ny FEN: ");
	private JLabel uciCommandTitle = new JLabel("Velg en funksjon: ");
	
	public AnalyzeInput(int windowPosX, int windowPosY, int windowSizeY)
	{
		setTitle("Analyze Sjakk");
		setVisible(true);
		setSize(600,windowSizeY);
		setLayout(new BorderLayout());
		setLocation(windowPosX + 50, windowPosY);
		panel1.add(fenTitle);
		panel1.add(showCurrentFenLabel);
		panel1.add(fenInputTitle);
		panel1.add(fenInputTextfield);
		panel1.add(uciCommandTitle);
		panel1.add(bestMoveButton);
		//panel.add(label);
		containerPanel.add(panel1);
		containerPanel.add(panel2);
		panel1.setLayout(new GridLayout(0,1));
		containerPanel.setLayout(new GridLayout(0,1));
		add(containerPanel);
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	
	public void showBestMove(String bestMove){
		bestMoveLabel.setText(bestMove);
		panel2.add(bestMoveLabel);
		revalidate();
		System.out.println(bestMove);
	}

	public void showCurrentFen(String fen)
	{
		showCurrentFenLabel.setText(fen);
		revalidate();
		System.out.println("showCurrentFen ble bes�kt");
	}
	
}