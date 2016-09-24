package GamePieces;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class OType extends Polyominoes {

	public OType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.OTYPE, tetrisBoard);
	}

	@Override
	protected boolean createPiece() {
		createNewPieceFromArray(GameConstants.OTYPE_START, TetrisBlockColor.YELLOW, this);
		return checkIfGameOver();
	}

}
