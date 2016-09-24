package GamePieces;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class TType extends Polyominoes {

	public TType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.TTYPE, tetrisBoard);
	}

	@Override
	protected boolean createPiece() {
		createNewPieceFromArray(GameConstants.TTYPE_START, TetrisBlockColor.PURPLE, this);
		return checkIfGameOver();
	}

}
