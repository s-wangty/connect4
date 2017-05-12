package contestants;

import connectFour.Grid;
import connectFour.Player;
import connectFour.GridUtilities;

public class ArnavTyler_NoodileBot implements Player
{
	private int myPlayer;
	
	public ArnavTyler_NoodileBot(){
		
		myPlayer = -1;
		
	}
	
	private int[] alphaBeta (Grid g, int depth, int alpha, int beta) {
		// Did this move end the game?  If so, score it now based on whether we won.
      if (g.getWinningPlayer() == myPlayer)
      {
          // We won!
          return new int[] { 1000 * (depth + 1), -1 };
      }
      else if (g.getWinningPlayer() == (3 - myPlayer))
      {
          // They won.
          return new int[] { -1000 * (depth + 1), -1 };
      }
      else if (g.getWinningPlayer() == -1)
      {
          // Game ends in a draw.
          return new int[] { 0, -1 };
      }

      int nextPlayer = g.getNextPlayer();

      // We don't want to go any deeper, so just return the immediate heuristic score
      // for this board
      if (depth <= 0)
      {
          // TODO: FOR YOU TO DO!  WRITE THIS getHeuristicScore METHOD
          // TO EXAMINE THE GRID AND COME UP WITH A NUMERIC SCORE FOR IT.
          // THE SCORE SHOULD BE FROM THE POINT OF VIEW OF YOUR PLAYER
          // (HIGH VALUES MEANS GOOD FOR YOU, LOW VALUES MEAN BAD FOR YOU).
          // THEN REPLACE '= 1' WITH '= getHeuristicScore(g)'
          int score = getHeuristicScore(g);

          return new int[] { score, -1 };
      }
      boolean isMax = (nextPlayer == myPlayer);
	  int bestMove = -1;
	  int bestScore;
	  if (isMax)
	  {
	      bestScore = Integer.MIN_VALUE;
	      
	      for (int nextCol = 0; nextCol < g.getCols(); nextCol++)
		  {
		      if (!g.isColumnFull(nextCol))
		      {
		          // Apply the move (temporarily) to the grid...
		          g.makeMove(nextCol);
		          
		          // ... and then call ourselves recursively to move down the decision tree
		          // and come up with a score                
		          int scoreCur = Math.max(bestScore, alphaBeta(g, depth - 1, alpha, beta)[0]);
		          
		          // ... and we must remember to UNDO that move now that the call is done.
		          g.undo();
		          
		          // Update bestScore with what the recursive call returned
		          if (isMax)
		          {
		              if (scoreCur > bestScore)
		              {
		                  bestScore = scoreCur;
		                  bestMove = nextCol;
		              }
		          }
		          else
		          {
		              // minimizing!
		              if (scoreCur < bestScore)
		              {
		                  bestScore = scoreCur;
		                  bestMove = nextCol;
		              }
		          }
		          
		          alpha = Math.max(alpha, bestScore);
		          
		          if (beta <= alpha)
		        	  break;
		      }
		   }
	      return new int[] {bestScore, bestMove};
	  }
	  else
	  {
	      bestScore = Integer.MAX_VALUE;
	      
	      for (int nextCol = 0; nextCol < g.getCols(); nextCol++)
		  {
		      if (!g.isColumnFull(nextCol))
		      {
		          // Apply the move (temporarily) to the grid...
		          g.makeMove(nextCol);
		          
		          // ... and then call ourselves recursively to move down the decision tree
		          // and come up with a score                
		          int scoreCur = Math.min(bestScore, alphaBeta(g, depth - 1, alpha, beta)[0]);
		          
		          // ... and we must remember to UNDO that move now that the call is done.
		          g.undo();
		          
		          // Update bestScore with what the recursive call returned
		          if (isMax)
		          {
		              if (scoreCur > bestScore)
		              {
		                  bestScore = scoreCur;
		                  bestMove = nextCol;
		              }
		          }
		          else
		          {
		              // minimizing!
		              if (scoreCur < bestScore)
		              {
		                  bestScore = scoreCur;
		                  bestMove = nextCol;
		              }
		          }
		          
		          beta = Math.min(beta, bestScore);
		          
		          if (beta <= alpha)
		        	  break;
		      }
		   }
	      return new int[] {bestScore, bestMove};
	  }        	  
	}
	
	private int getHeuristicScore(Grid g)
	{
		int total = 0;
		GridUtilities gu = new GridUtilities(g);
		int[] move = {Grid.UPLEFT,Grid.UP,Grid.UPRIGHT,Grid.UPRIGHT};
		int current = -1;
		for(int r = 0; r < g.getRows(); r++){
			
			for(int c = 0; c < g.getCols(); c++){
				
				current = g.getPlayerAt(r, c);
				
				int pos = 1;
				
				if (current != myPlayer)
					pos = -1;		
				
				for(int direction : move) {
					int[] lands = gu.getLengthAndSpaces(r, c, direction);
					double base = 2;
//					if (lands[0] >= 4) {
//						total += Integer.MAX_VALUE;
//					}
//					else if (lands[0] == 3) {
//						if (lands[1] == 2) {
//							total += Math.pow(base, 5) * pos;
//						}
//						else if(lands[1] == 1) {
//							total += Math.pow(base, 4) * pos;
//						}							
//					}
//					else if (lands[0] == 2) {
//						if (lands[1] == 2) {
//							total += Math.pow(base, 3) * pos;
//						}
//						else if (lands[1] == 1) {
//							total += Math.pow(base, 2) * pos;
//						}
//					}
//					else if (lands[0] == 1) {
//						if (lands[1] == 2) {
//							total += Math.pow(base, 1) * pos;
//						}
//						else if (lands[1] == 1) {
//							total += Math.pow(base, 0) * pos;
//						}
//					}
					if (lands[0] >= 4) {
						total += Integer.MAX_VALUE;
					}
					else if (lands[0] == 3) {
						if (lands[1] == 2) {
							total += 229 * pos;
						}
						else if(lands[1] == 1) {
							total += 211 * pos;
						}							
					}
					else if (lands[0] == 2) {
						if (lands[1] == 2) {
							total += 189 * pos;
						}
						else if (lands[1] == 1) {
							total += 160 * pos;
						}
					}
					else if (lands[0] == 1) {
						if (lands[1] == 2) {
							total += 136 * pos;
						}
						else if (lands[1] == 1) {
							total += 50 * pos;
						}
					}
				}				
			}
			
		}
		//System.out.println(total);
		return total;
	}
	@Override
	public int getMoveColumn(Grid g) {
		
		if(myPlayer == -1){
			
			myPlayer = g.getNextPlayer();
			return g.getCols() / 2;
		}
		int[] ret = alphaBeta(g, 8, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return ret[1];
	}
	@Override
	public String getPlayerName() {
		
		return "NoodileBot";
	}

}