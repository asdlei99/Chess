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
		if (xMove == yMove) { //Sjekker om du g�r skr�tt
			if (fromX < toX && fromY < toY) // Sjekker om du g�r opp mot h�yre
			{
				if (canIMoveUpRight(fromX, fromY, toX, toY, xMove) == false) { //Sjekker at ingenting er i veien n�r du g�r opp mot h�yre
					return false;
				}
			}
			if (fromX > toX && fromY < toY) // Sjekker om du g�r opp mot venstre
			{
				if (canIMoveUpLeft(fromX, fromY, toX, toY, xMove) == false) { //Sjekker at ingenting er i veien n�r du g�r opp mot venstre
					return false;
				}
			}
			if (fromX < toX && fromY > toY) // Sjekker om du g�r ned mot h�yre
			{
				if (canIMoveDownRight(fromX, fromY, toX, toY, xMove) == false) { //Sjekker at ingenting er i veien n�r du g�r ned mot h�yre
					return false;
				}
			}
			if (fromX > toX && fromY > toY) // Sjekker om du g�r ned mot venstre
			{
				if (canIMoveDownLeft(fromX, fromY, toX, toY, xMove) == false) { //Sjekker at ingenting er i veien n�r du g�r ned mot venstre
					return false;
				}
			}
			//S� disse if setningene som sjekker hvor brikken g�r vil for deg, v�re at du sjekker om den g�r opp, ned, h�yre eller venstre.
			//F�rst sjekker du om han g�r rett fra, du kan se p� Pawn.java tror jeg for � confirme det. Erstatt xMove == yMove med for eksempel if fromX == toX sjekker horizontalt mens
			//fromY == toY sjekker om brikken beveger seg vertikalt.
			//N�r du har funnet ut hvilken retning brikken din g�r i, s� m� du se om det er noen brikker som st�r i veien. Som et eksempel kan vi si at nederst til venstre i brettet er X0Y0
			//Logikken er at hvis du flytter en brikke fra X0Y0 til X0Y3 (alts� 3 opp) s� m� du sjekke om X0Y2 og X0Y1 har brikker i seg.
			//Paramtere jeg sender til canIMove metodene er fromX, fromY, toX, toY og en "xMove". xMove sier hvor mange ruter brikken har bevegd seg. For rook s� kan du regne ut
			//din egen xMove i eksemplet v�rt med � ta xMove(ikke semantisk btw Xd) = toY - fromY. Y3-Y0 = 3, alts� du har bevegd brikka 3 ruter.
			//for kapittel 2 av coolstorybro the book se canIMoveUpRight metoden. 
			return true;
		}
		return false;
	}

	private boolean canIMoveUpRight(int fromX, int fromY, int toX, int toY, int squaresMoved) {
		for (int i = 1; i <= squaresMoved; i++) { //Viktig at i starter p� 1. "i" representerer ruta bak deg. i = 1 betyr ruta rett bak destinasjonen, i = 2 betyr 2 ruter bak destinasjonen.
			int[] previousBoxX = new int[8]; //Lager et array for ruter p� X aksen
			int[] previousBoxY = new int[8]; //Lager et array for ruter p� Y aksen
			Position[] positionArray = new Position[8]; //Lager et array for posisjonene til de to over
			BoardSquare[] squareArray = new BoardSquare[8]; //Laget et array for alle rutene i en linje (max 8).
			previousBoxX[i] = toX - i; //previousBoxX[i] er X verdien til forrige rute. hvis "i" er 1 s� ser tar han destinasjonX - 1 som er forrige rute
			previousBoxY[i] = toY - i; //Samme som over men med Y. Bishop krever b�de X og Y verdi, men hvis rook beveger seg opp eller ned, s� trenger du ikke ta hensyn til X. 

			if (previousBoxX[i] != fromX && previousBoxY[i] != fromY) { //en if sjekk som stopper rutesjekkinga n�r han treffer ruta du starta p�.
				positionArray[i] = new Position(previousBoxX[i], previousBoxY[i]); //lager en posisjon med X og Y verdier
				squareArray[i] = getSquareAt(positionArray[i]); //henter ruta i posisjonen du mekka forrige linje. Se bilde i discord for eksempel.

				if (squareArray[i].hasChild()) {	//Hvis det er en brikke der
					System.out.println("Du pr�vde � g� " + i + "trekk over en brikke"); //Sier ifra i console som en sjekk, kan fjernes 
					return false; //Returner false, alts� han fant en brikke som sto i veien. 
				}
			}
		}

		return true; //Hvis han ikke finner noen brikker, s� return true. 
	}
	// I for loopen s� m� i starte p� 1, fordi hvis den starter p� 0 s� sjekker du ruta i destinasjonen og da blir det bare tull. Du sjekker om i er st�rre eller lik antall ruter du har bevegd deg
	//hvis du bytter ut squaresMoved med for eksempel 5, s� vil metoden alltid annta at du har bevegd deg 5 ruter, og vil sjekke 5 ruter bak deg. Hvis du beveger deg til X0Y3 s� sjekker han
	//X0Y2, X0Y1, X0Y0, X0Y-1 og da f�r du out of bounds exception. squaresMoved er xMove fra forrige kommentar. 
	//Jeg m�tte ha 4 metoder fordi bishop kan g� opp til h�yre, opp til venstre, ned til h�yre og ned til venstre. Du trenger fire metoder du ogs�, en for opp, ned, h�yre og venstre.
	//Metodene for opp og ned trenger bare Y verdi tenker jeg. Bilde i discord viser forskjellen mellom brikkene v�re og hvorfor du trenger bare enten X eller Y mens jeg m�tte ha begge.
	//Husk at �verst i programmet, at du lager en sjekk for om Rook beveger seg p� X aksen eller Y aksen. G�r rook 3 til venstre, s� er det en horizontal move og da kaller du opp 
	//canIMoveLeft med fromX, toX og squaresMoved (som er toX - fromX). 
	//**VIKTIG** legg merke til at forskjellen mellom canIMoveUpRight og canIMoveUpLeft er previousBoxX[i] = toX - i vs previousBoxX[i] = toX + i
	
	private boolean canIMoveUpLeft(int fromX, int fromY, int toX, int toY, int squaresMoved) {
		for (int i = 1; i <= squaresMoved; i++) { // hvis du f�r out bounds sjekk her
			int[] previousBoxX = new int[8];
			int[] previousBoxY = new int[8];
			Position[] positionArray = new Position[8];
			BoardSquare[] squareArray = new BoardSquare[8];
			previousBoxX[i] = toX + i;
			previousBoxY[i] = toY - i;

			if (previousBoxX[i] != fromX && previousBoxY[i] != fromY) {
				System.out.println("HALLO" + previousBoxX[i] + "" + previousBoxY[i]);
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

	private boolean canIMoveDownRight(int fromX, int fromY, int toX, int toY, int squaresMoved) {
		for (int i = 1; i <= squaresMoved; i++) { // hvis du f�r out bounds sjekk her
			int[] previousBoxX = new int[8];
			int[] previousBoxY = new int[8];
			Position[] positionArray = new Position[8];
			BoardSquare[] squareArray = new BoardSquare[8];
			previousBoxX[i] = toX - i;
			previousBoxY[i] = toY + i;

			if (previousBoxX[i] != fromX && previousBoxY[i] != fromY) {
				System.out.println("HALLO" + previousBoxX[i] + "" + previousBoxY[i]);
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

	private boolean canIMoveDownLeft(int fromX, int fromY, int toX, int toY, int squaresMoved) {
		for (int i = 1; i <= squaresMoved; i++) { // hvis du f�r out bounds sjekk her
			int[] previousBoxX = new int[8];
			int[] previousBoxY = new int[8];
			Position[] positionArray = new Position[8];
			BoardSquare[] squareArray = new BoardSquare[8];
			previousBoxX[i] = toX - i;
			previousBoxY[i] = toY + i;

			if (previousBoxX[i] != fromX && previousBoxY[i] != fromY) {
				System.out.println("HALLO" + previousBoxX[i] + "" + previousBoxY[i]);
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

// behold, frankenstein
/*
 * int toX1 = toX-1; int toY1 = toY-1; int toX2 = toX-2; int toY2 = toY-2; int
 * toX3 = toX-3; int toY3 = toY-3; int toX4 = toX-4; int toY4 = toY-4; int toX5
 * = toX-5; int toY5 = toY-5; int toX6 = toX-6; int toY6 = toY-6; int toX7 =
 * toX-7; int toY7 = toY-7; if (toX1 != fromX && toY1 != fromY) {
 * 
 * Position pos1 = new Position(toX1, toY1); //Posisjonen er den rett bak deg
 * BoardSquare square1 = getSquareAt(pos1); //Henter firkanten ruta f�r ruta du
 * g�r til if (square1.hasChild()) {
 * System.out.println("Du pr�vde � g� EN over en brikke"); return false; }
 * 
 * if (toX2 != fromX && toY2 != fromY) {
 * 
 * Position pos2 = new Position(toX2, toY2); //Posisjonen er den rett bak deg
 * BoardSquare square2 = getSquareAt(pos2); //Henter firkanten ruta f�r ruta du
 * g�r til if (square2.hasChild()) {
 * System.out.println("Du pr�vde � g� TO over en brikke"); return false; }
 * 
 * if (toX3 != fromX && toY3 != fromY) { Position pos3 = new Position(toX3,
 * toY3); //Posisjonen er den rett bak deg BoardSquare square3 =
 * getSquareAt(pos3); //Henter firkanten ruta f�r ruta du g�r til if
 * (square3.hasChild()) {
 * System.out.println("Du pr�vde � g� TRE over en brikke"); return false; }
 * 
 * if (toX4 != fromX && toY4 != fromY) { Position pos4 = new Position(toX4,
 * toY4); //Posisjonen er den rett bak deg BoardSquare square4 =
 * getSquareAt(pos4); //Henter firkanten ruta f�r ruta du g�r til if
 * (square4.hasChild()) {
 * System.out.println("Du pr�vde � g� FIRE over en brikke"); return false; }
 * 
 * if (toX5 != fromX && toY5 != fromY) { Position pos5 = new Position(toX5,
 * toY5); //Posisjonen er den rett bak deg BoardSquare square5 =
 * getSquareAt(pos5); //Henter firkanten ruta f�r ruta du g�r til if
 * (square5.hasChild()) {
 * System.out.println("Du pr�vde � g� FEM over en brikke"); return false; }
 * 
 * if (toX6 != fromX && toY6 != fromY) { Position pos6 = new Position(toX6,
 * toY6); //Posisjonen er den rett bak deg BoardSquare square6 =
 * getSquareAt(pos6); //Henter firkanten ruta f�r ruta du g�r til if
 * (square6.hasChild()) {
 * System.out.println("Du pr�vde � g� SEKS over en brikke"); return false; }
 * 
 * if (toX7 != fromX && toY7 != fromY) { Position pos7 = new Position(toX7,
 * toY7); //Posisjonen er den rett bak deg BoardSquare square7 =
 * getSquareAt(pos7); //Henter firkanten ruta f�r ruta du g�r til if
 * (square7.hasChild()) {
 * System.out.println("Du pr�vde � g� SYV over en brikke"); return false; } } }
 * }
 * 
 * } } } } return true;
 */