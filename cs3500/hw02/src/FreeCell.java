/**
 * Created by Charles on 9/14/16.
 */

import java.util.ArrayList;
public class FreeCell {

  public static void main(String[] args) {
    ArrayList<Card> deck = new ArrayList<>();
    for (int i = 1; i < 14; i++) {
      for (Card.Suit s : Card.Suit.values()) {
        deck.add(new Card(i, s));
      }
    }

    IFreeCellModel model = new FreeCellModel();
    model.startGame(deck, 8, 4, false);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 2);
    model.move(PileType.CASCADE, 5, 0, PileType.CASCADE, 2);
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 2);
    model.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 5);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 1);
    model.move(PileType.CASCADE, 0, 0, PileType.OPEN, 3);
    model.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 1);
    System.out.println(model.getGameState());
  }
}
