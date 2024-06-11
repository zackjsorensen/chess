  package chess.pieces;

  import chess.*;

  import java.util.ArrayList;

  public class RookMoves {
      private ChessGame.TeamColor color;
      private ChessBoard board;
      private int row;
      private int col;
      private final ChessPosition myPosition;
      private ArrayList<ChessPosition> positions;

      public RookMoves(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor color) {
          this.board = board;
          this.myPosition = myPosition;
          this.color = color;
          row = myPosition.getRow();
          col = myPosition.getColumn();
          positions = new ArrayList<>();
      }

      public ArrayList<ChessMove> findPositions(){
          tryOneWay(1, 0);
          tryOneWay(-1, 0);
          tryOneWay(0, 1);
          tryOneWay(0, -1);
          return makeMovesArray(positions);
      }

      private void tryOneWay(int rowIncrement, int colIncrement) {
          ChessPosition nextPosition;
          for(int i = 1; i <= 7; i++){
              nextPosition= new ChessPosition(row+ rowIncrement*i, col+ colIncrement*i);
              if (!inBounds(nextPosition)){
                  break;
              }
              if( board.getPiece(nextPosition) != null){ // if nextPosition is not empty
                  if (board.getPiece(nextPosition).getTeamColor() == color){ // if it's our teammate, break, we can't move there
                      break;
                  } else { // if it's an enemy piece, we can move there
                      positions.add(nextPosition); // add position of capturable enemy piece
                      break;
                  }
              }
              positions.add(nextPosition); // if the spot is empty, add the position
          }
      }

      /** Takes the ArrayList of available positions and uses it to make an ArrayList of Chess Moves*/
      private ArrayList<ChessMove> makeMovesArray(ArrayList<ChessPosition> positions){
          ArrayList<ChessMove> moves = new ArrayList<>();
          for (ChessPosition item : positions){
              moves.add(new ChessMove(myPosition, item, null));  // figure that out later...
          }
          return moves;
      }
      public boolean inBounds(ChessPosition positionToCheck) {
          PieceHelper rookHelper = new PieceHelper(board, positions, color, myPosition);
          return rookHelper.inBounds(positionToCheck);
      }
}
