package rules;

import gui.BoardSquare;
import gui.ChessPiece;
import gui.Position;

public class Bishop extends Rules implements RulesInterface {

	public boolean isLegalMove(BoardSquare[][] board, ChessPiece piece, Position from, Position to) {
		b = board;

		int fromX = from.getX();
		int fromY = from.getY();
		int toX = to.getX();
		int toY = to.getY();
		int test = (toX);
		int xMove = Math.abs(fromX - toX);
		int yMove = Math.abs(fromY - toY);
		String direction = null;
		if (xMove == yMove) { //Sjekker om du g�r skr�tt
			if (fromX < toX && fromY < toY) // Sjekker om du g�r opp mot h�yre
			{
				direction = "upRight";
				if (canIMovePiece(fromX, fromY, toX, toY, xMove, direction) == false) { 
					return false;
				}
			}
			if (fromX > toX && fromY < toY) // Sjekker om du g�r opp mot venstre
			{
				direction = "upLeft";
				if (canIMovePiece(fromX, fromY, toX, toY, xMove, direction) == false) { 
					return false;
				}
			}
			if (fromX < toX && fromY > toY) // Sjekker om du g�r ned mot h�yre
			{
				direction = "downRight";
				if (canIMovePiece(fromX, fromY, toX, toY, xMove, direction) == false) { 
					return false;
				}
			}
			if (fromX > toX && fromY > toY) // Sjekker om du g�r ned mot venstre
			{
				direction = "downLeft";
				if (canIMovePiece(fromX, fromY, toX, toY, xMove, direction) == false) { 
					return false;
				}
			}
			return true;
		}
		return false;
	}

	private boolean canIMovePiece(int fromX, int fromY, int toX, int toY, int squaresMoved, String direction) {
		for (int i = 1; i <= squaresMoved; i++) { 
			String movedDirection = direction;
			int[] previousBoxX = new int[8];
			int[] previousBoxY = new int[8]; 
			Position[] positionArray = new Position[8]; 
			BoardSquare[] squareArray = new BoardSquare[8]; 
			switch (movedDirection) {
			case "upRight":
				previousBoxX[i] = toX - i;
				previousBoxY[i] = toY - i;
				break;
			case "upLeft":
				previousBoxY[i] = toX + i;
				previousBoxY[i] = toY - i;
				break;
			case "downRight":
				previousBoxX[i] = toX - i;
				previousBoxY[i] = toY + i;
				break;
			case "downLeft":
				previousBoxX[i] = toX + i;
				previousBoxY[i] = toY + i;
				break;
			}
			if (previousBoxX[i] != fromX && previousBoxY[i] != fromY) { 
				positionArray[i] = new Position(previousBoxX[i], previousBoxY[i]); 
				squareArray[i] = getSquareAt(positionArray[i]); 

				if (squareArray[i].hasChild()) {	
					System.out.println("Du pr�vde � g� " + i + "trekk over en brikke"); 
					return false; 
				}
			}
		}

		return true; 
	}
}
	// I for loopen s� m� i starte p� 1, fordi hvis den starter p� 0 s� sjekker du ruta i destinasjonen og da blir det bare tull. Du sjekker om i er st�rre eller lik antall ruter du har bevegd deg
	//hvis du bytter ut squaresMoved med for eksempel 5, s� vil metoden alltid annta at du har bevegd deg 5 ruter, og vil sjekke 5 ruter bak deg. Hvis du beveger deg til X0Y3 s� sjekker han
	//X0Y2, X0Y1, X0Y0, X0Y-1 og da f�r du out of bounds exception. squaresMoved er xMove fra forrige kommentar. 
	//Jeg m�tte ha 4 metoder fordi bishop kan g� opp til h�yre, opp til venstre, ned til h�yre og ned til venstre. Du trenger fire metoder du ogs�, en for opp, ned, h�yre og venstre.
	//Metodene for opp og ned trenger bare Y verdi tenker jeg. Bilde i discord viser forskjellen mellom brikkene v�re og hvorfor du trenger bare enten X eller Y mens jeg m�tte ha begge.
	//Husk at �verst i programmet, at du lager en sjekk for om Rook beveger seg p� X aksen eller Y aksen. G�r rook 3 til venstre, s� er det en horizontal move og da kaller du opp 
	//canIMoveLeft med fromX, toX og squaresMoved (som er toX - fromX). 
	//**VIKTIG** legg merke til at forskjellen mellom canIMoveUpRight og canIMoveUpLeft er previousBoxX[i] = toX - i vs previousBoxX[i] = toX + i
	
