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

public class Engine {

	private final int MAX_DEPTH;
	private final double INFINITY=1000;
	
	public Engine(int maxdepth){
		MAX_DEPTH = maxdepth;
	}
	
	public HasamiMove machineMove(Board board, Color player){
		HasamiMove move = abNegamax(board, player, 0, -INFINITY, INFINITY);
		System.out.println("Computer eval: "+move.val);
		return move;
	}

	private HasamiMove abNegamax(Board board, Color player, int currentDepth, double alpha, double beta){
		if(board.isGameOver() || currentDepth==MAX_DEPTH){
			return new HasamiMove(null, board.evaluate(player, currentDepth));
		}
		HasamiMove bestMove = null;
		double bestScore = -INFINITY;
		
		for(HasamiMove move : board.getMoves()){
			board.makeMove(move);
			HasamiMove currentMove = abNegamax(board,
									player,
									currentDepth+1,
									-beta,
									-Math.max(alpha, bestScore));
			
			double currentScore = -currentMove.val;
			
			if(currentScore > bestScore){
				bestScore = currentScore;
				bestMove = move;
				if(bestScore >= beta){
					board.undoMove(move);
					return new HasamiMove(bestMove, bestScore);
				}
			}
			
			board.undoMove(move);	
		}//end of for looping.
		
		return new HasamiMove(bestMove, bestScore);
	}
	
}//end class.
