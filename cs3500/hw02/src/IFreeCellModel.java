/**
 * Created by Charles on 9/14/16.
 */

import java.util.List;
import java.util.Stack;

public interface IFreeCellModel<K> {

  List<K> getDeck();

  void startGame(List<K> deck, int numCascades, int numOpens, boolean shuffle) throws
      IllegalArgumentException;

  void move(PileType sourceType, int sourcePileNumber, int cardIndex, PileType destinationType,
      int destPileNumber) throws IllegalArgumentException;

  String getGameState();

  boolean isGameOver();

  List<Stack<K>> getCascades();
}
