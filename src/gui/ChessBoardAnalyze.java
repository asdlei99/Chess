package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ChessBoardAnalyze extends JFrame {

	private JPanel panel = new JPanel();
	private int columns = 8;
	private int rows = 8;
	private int windowSizeX = 500;
	private int windowSizeY = 500;
	private int windowPosX = 50;
	private int windowPosY = 50;
	private BoardSquare[][] boardArray = new BoardSquare[rows][columns];
	private Position currentHighlight = null;
	private Color colorHighlight = new Color(209, 206, 111); 
	private String userInput;
	private JTextField textfield;
	
	public ChessBoardAnalyze() {
		this.setVisible(true);
		this.setSize(windowSizeX, windowSizeY);
		this.setTitle("Chess Board Analyzer");
		this.setLocation(windowPosX, windowPosY);
		panel.setLayout(new GridLayout(rows, 0));
		this.add(panel);
		AnalyzeInput analyzeWindow = new AnalyzeInput(windowSizeX, windowPosY);
		textfield = analyzeWindow.textfield;
		textfield.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String userInput = textfield.getText();
				if (userInput.length() < 10)
				{
					System.out.println("Invalid FEN code");
				}
				else
				{
					placePieces(userInput);
					textfield.setText("");
				}
			}
		});
		//userInput = analyzeWindow.getUserInput();
		createChessBoard();
		initializePieces();
		this.validate();
		this.repaint();
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	
	private void createChessBoard() {
		
		int row = 1;
		Color colorOne = new Color(106, 133, 78);
		Color colorTwo = new Color(204, 201, 182);
		
		for(int y = 0; y < columns; y++) {
			
			for(int x = 0; x < rows; x++){
				Position pos = new Position(x, (columns - 1) - y);
				BoardSquare bSquare;
								
				if(row % 2 == 0) {
					bSquare = new BoardSquare(pos, colorOne);
				} else {
					bSquare = new BoardSquare(pos, colorTwo);
				}
				
				panel.add(bSquare);
				boardArray[x][(columns - 1) - y] = bSquare;
				
				bSquare.addMouseListener(new MouseAdapter() {
					
				   /* HAR NOE MED SETHIGHLIGHT � GJ�RE, SJEKK KOMMENTARENE I setHighlight METODEN
				    * @Override
				    public void mouseClicked(MouseEvent e) {
				       setHighlight(((BoardSquare) e.getSource()).getPos());
				    } */
				    
				});
				
				if(row % 8 == 0) {
					Color temp = colorOne;
					colorOne = colorTwo;
					colorTwo = temp;
				}
				row++;
			}
			
		}
		
		//boardArray[0][0].setBackground(new Color(255, 0, 0));
	}
	
	private void placePieces(String userInput) {
		FENbombe fenBombe = new FENbombe(userInput);
		System.out.println(fenBombe.checkFenArray() + "dette er getfenarray");
		if (fenBombe.checkFenArray())
		{
			clearBoard();
			String[] fenArray = fenBombe.getFenArray();
			for (int i = 0; i < 64; i++)
			{
				//System.out.println(fenArray[i]);
			}
			int x = 0;
			int y = 0;
			System.out.println(fenArray.length);
			for (int i = 0; i < fenArray.length; i++)
			{
				if (i % 8 == 0 && i != 0)
				{
						x = 0;
						y++;
				}
				switch(fenArray[i])
				{
					case "r":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.ROOK, "black"));
						x++;
						break;
					case "n":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.KNIGHT, "black"));
						x++;
						break;
					case "b":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.BISHOP, "black"));
						x++;
						break;
					case "q":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.QUEEN, "black"));
						x++;
						break;
					case "k":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.KING, "black"));
						x++;
						break;
					case "0":
						x++;
						break;
					case "p":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.PAWN, "black"));
						x++;
						break;
					case "R":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.ROOK, "white"));
						x++;
						break;
					 case "N":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.KNIGHT, "white"));
						x++;
						break;
					case "B": 
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.BISHOP, "white"));
						x++;
						break;
					case "Q":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.QUEEN, "white"));
						x++;
						break;
					case "K":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.KING, "white"));
						x++;
						break;
					case "P":
						boardArray[x][(columns - 1) - y].addPiece(new ChessPiece(pieceType.PAWN, "white"));
						x++;
						break; 
				}
			}
		}
		else {
			System.out.println("Den scuffed ass shit FEN stringen din er invalid as fuck");
		}
	}
	
	
	private void initializePieces()
	{
		for(int i = 0; i < 8; i++) {
			boardArray[i][1].addPiece(new ChessPiece(pieceType.PAWN, "white"));

		}
		
		for(int i = 0; i < 8; i++) {
			boardArray[i][6].addPiece(new ChessPiece(pieceType.PAWN, "black"));
		}

		boardArray[0][0].addPiece(new ChessPiece(pieceType.ROOK, "white"));
		boardArray[7][0].addPiece(new ChessPiece(pieceType.ROOK, "white"));
		boardArray[0][7].addPiece(new ChessPiece(pieceType.ROOK, "black"));
		boardArray[7][7].addPiece(new ChessPiece(pieceType.ROOK, "black"));
		
		boardArray[1][0].addPiece(new ChessPiece(pieceType.KNIGHT, "white"));
		boardArray[6][0].addPiece(new ChessPiece(pieceType.KNIGHT, "white"));
		boardArray[1][7].addPiece(new ChessPiece(pieceType.KNIGHT, "black"));
		boardArray[6][7].addPiece(new ChessPiece(pieceType.KNIGHT, "black"));
	
		boardArray[2][0].addPiece(new ChessPiece(pieceType.BISHOP, "white"));
		boardArray[5][0].addPiece(new ChessPiece(pieceType.BISHOP, "white"));
		boardArray[2][7].addPiece(new ChessPiece(pieceType.BISHOP, "black"));
		boardArray[5][7].addPiece(new ChessPiece(pieceType.BISHOP, "black"));
		
		boardArray[3][0].addPiece(new ChessPiece(pieceType.QUEEN, "white"));
		boardArray[3][7].addPiece(new ChessPiece(pieceType.QUEEN, "black"));
		
		boardArray[4][0].addPiece(new ChessPiece(pieceType.KING, "white"));
		boardArray[4][7].addPiece(new ChessPiece(pieceType.KING, "black"));
	}
	
	private void clearBoard()
	{
		int x = 0;
		int y = 0;
		for (int i = 0; i < 64; i++)
		{
			if (i % 8 == 0 && i != 0)
			{
					x = 0;
					y++;
			} 
			if (boardArray[x][y].hasChild())
			{
				boardArray[x][y].removePiece();
				x++;
			}
			else
			{
				x++;
			}
		}
		this.validate();
		this.repaint();
		
	} 
}
	/* ADDER HIGHLIGHT TIL PIECENE, IKKE N�DVENDIG MEST ANTAGELIG MEN KAN FJERNES SENERE
	public void setHighlight(Position pos) {
				
		if(!boardArray[pos.getX()][pos.getY()].hasChild()) return;
		
		if(currentHighlight != null) {
			
			if(currentHighlight.getPos() == pos.getPos()) return;
			
			BoardSquare target = boardArray[currentHighlight.getX()][currentHighlight.getY()];
			target.setBackground(target.getOriginalColor());
		}
		
		boardArray[pos.getX()][pos.getY()].setBackground(colorHighlight);
		currentHighlight = new Position(pos.getX(), pos.getY());
	} */ 
	
