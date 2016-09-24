package GamePieces;

import Game.*;
import Game.GameConstants.PolyominoeType;
import Game.GameConstants.TetrisBlockColor;

public class JType extends Polyominoes {

	public JType(TetrisBlock[][] tetrisBoard) {
		// TODO Auto-generated constructor stub
		super(PolyominoeType.JTYPE, tetrisBoard);
	}

	@Override
	protected boolean createPiece() {
		createNewPieceFromArray(GameConstants.JTYPE_START, TetrisBlockColor.BLUE, this);
		return checkIfGameOver();
	}

}
