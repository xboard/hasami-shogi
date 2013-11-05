/**
 Java implementation of a computer engine to play the 
 Hasami Shogi game. See: http://en.wikipedia.org/wiki/Hasami_shogi

 Copyright (C) 2010 Flavio Regis de Arruda <https://bitbucket.org/flavio_regis>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package hasami;

import hasami.Board.Color;
import java.util.Scanner;
public class CLI {
	public enum MODE {
		HxH, HxC, CxH, CxC
	};

	final int MAX_DEPTH = 4;
	int moveNumber = 2;
	MODE mode;
	Board board = new Board(9);
	Engine engine = new Engine(MAX_DEPTH);

	public CLI(MODE mode) {
		this.mode = mode;
	}

	public void run() {
		printBoard(board);
		while (!board.isGameOver()) {
			HasamiMove move = null;
			long stime = System.currentTimeMillis();
			if (board.currentPlayer() == Color.WHITE) {
				if (mode == MODE.HxC || mode == MODE.HxH) {
					move = getHumanMove(board);
				} else {
					System.out.println("Computer thinking...");
					move = engine.machineMove(board, board.currentPlayer());
					System.out.println("Computer move: "+move);
				}
			} else {
				if (mode == MODE.HxC || mode == MODE.CxC) {
					System.out.println("Computer thinking...");
					move = engine.machineMove(board, board.currentPlayer());
					System.out.println("Computer move: "+move);
				} else {
					move = getHumanMove(board);
				}
			}
			long etime = System.currentTimeMillis();
			try{
				validateMove(move);
				board.makeMove(move);
				printBoard(board);
				System.out.println("Elapsed time: "+(etime-stime)/1000+" seconds");
				moveNumber++;
			}catch(Throwable t){
				System.out.println(t.getMessage());
				printBoard(board);
			}
			if (mode == MODE.CxC){ 
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
		if(board.hasWinner())
			if(board.currentPlayer()==Color.BLACK)
				System.out.println("\\> WHITE WON!");
			else
				System.out.println("\\> BLACK WON!");
		else
			System.out.println("\\> DRAW.");
	}

	private void validateMove(HasamiMove move) {
		if(!board.getMoves().contains(move)){
			throw new RuntimeException("Invalid move: "+move);
		}
	}

	private HasamiMove getHumanMove(Board b) {
		System.out.println(b.currentPlayer()+" to move:");
		if(moveNumber%2==0)
			System.out.print((moveNumber/2)+". ");
		else
			System.out.print((moveNumber/2)+"... ");
		Scanner scanner = new Scanner(System.in);
		String move = scanner.nextLine();
		return parseMove(move);
	}

	private HasamiMove parseMove(String move) {
		move=move.toLowerCase();
		String alfa = "["+'a'+"-"+(char)('a'+board.getBoardSize()-1)+"]";
		String digit = "[1-"+board.getBoardSize()+"]";
		String regexp = alfa+digit+"[xX\\-]?"+alfa+digit;
		if(move ==null || !move.matches(regexp)){
			System.out.println("Erro: move="+move);
			return null;
		}
		if(move.length()==5)
			return new HasamiMove(Integer.parseInt(move.substring(1, 2))-1,
								  move.charAt(0)-'a',
								  Integer.parseInt(move.substring(4, 5))-1,
								  move.charAt(3)-'a');
		else
			return new HasamiMove(Integer.parseInt(move.substring(1, 2))-1,
					  move.charAt(0)-'a',
					  Integer.parseInt(move.substring(3, 4))-1,
					  move.charAt(2)-'a');
	}

	public static void printBoard(Board b) {
		int size=b.getBoardSize();
        for(int line=size-1; line >= 0; line--){
            printLinha();
            System.out.print((line+1)+" ");
            for(int col=0; col < size; col++){
                Color piece = b.get(line, col);
                if(piece==null)
                    System.out.print("|   ");
                else if( piece == Color.BLACK)
                    System.out.print("| X ");
                else if(piece == Color.WHITE)
                    System.out.print("| O ");
                else
                    System.out.print("ERRO: Piece not recognized!");  
            }
            System.out.println("|");
        }
         printLinha();
         printColumnLetters(size);
		
	}
	
	private static void printColumnLetters(int size) {
		System.out.print("  ");

		for(char c='A'; c < 'A'+size; c++)
			System.out.print("  "+c+" ");

		System.out.println();
		
	}

	private static void printLinha(){
        System.out.println("  +---+---+---+---+---+---+---+---+---+");
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("********   CLI Hasami   ********");
		(new CLI(MODE.HxC)).run();
	}


}
