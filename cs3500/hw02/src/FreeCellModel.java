import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Arrays;

/**
 * Created by Charles on 9/14/16.
 */
public class FreeCellModel implements IFreeCellModel<Card> {

  List<Card> deck;
  boolean isGameOver;
  ArrayList<Card> opens;
  ArrayList<Stack<Card>> foundations;
  ArrayList<Stack<Card>> cascades;

  static Card mt = new EmptyCard();

  FreeCellModel() {
    this.deck = new ArrayList<Card>();
    initDeck();

    this.opens = new ArrayList<>();
    this.foundations = new ArrayList<>();
    this.cascades = new ArrayList<>();
    this.isGameOver = true;

    for (int i = 0; i < 4; i++) {
      this.foundations.add(new Stack<>());
    }
  }

  private void initDeck() {
    for (int i = 1; i < 14; i++) {
      for (Card.Suit s : Card.Suit.values()) {
        Card c = new Card(i, s);
        this.deck.add(c);
      }
    }
  }

  private void shuffle() {
    Collections.shuffle(this.deck);
  }

  @Override public List<Card> getDeck() {
    return this.deck;
  }

  @Override public List<Stack<Card>> getCascades() { return this.cascades; }

  @Override public void startGame(List<Card> deck, int numCascades, int numOpens,
      boolean shuffle) throws IllegalArgumentException {
    this.deck = deck;
    if (numCascades < 4 || numCascades > 52) {
      throw new IllegalArgumentException("that's stupid");
    }
    for (int i = numCascades; i > 0; i--) {
      this.cascades.add(new Stack<>());
    }

    this.opens = new ArrayList<>();
    for (int i = 0; i < numOpens; i++) {
      this.opens.add(mt);
    }


    if (shuffle) shuffle();

    for (int i = 0; i < numCascades; i++) {
      this.cascades.get(i).push(this.deck.remove(0));
      if (i == numCascades - 1) {
        i = -1;
      }
      if (this.deck.size() == 0) break;
    }
    this.isGameOver = false;
  }

  @Override public void move(PileType sourceType, int sourcePileNumber, int cardIndex,
      PileType destinationType, int destPileNumber) throws IllegalArgumentException {
    Card c;
    switch (destinationType) {
      case OPEN:
        if (opens.get(destPileNumber) == mt) {
          switch (sourceType) {
            case OPEN:
              opens.set(destPileNumber, opens.get(sourcePileNumber));
              opens.set(sourcePileNumber, mt);
              break;

            case CASCADE: opens.set(destPileNumber, cascades.get(sourcePileNumber).pop());
              break;

            case FOUNDATION: throw new IllegalArgumentException("don't take from foundation");
          }
        }
        else throw new IllegalArgumentException("can't stack cards in open pile");
        break;

      case CASCADE:
        switch (sourceType) {
          case OPEN: {
            c = opens.get(sourcePileNumber);
            if (c == mt) throw new IllegalArgumentException("there's no card here!");

            Card top = cascades.get(destPileNumber).peek();
            if (c.getRank() == top.getRank() - 1 && !c.sameColor(top)) {
              cascades.get(destPileNumber).push(c);
              opens.set(sourcePileNumber, mt);
            }
            else throw new IllegalArgumentException("can't move that there");
          }
          break;

          case CASCADE: {
            if (sourcePileNumber == destPileNumber)
              throw new IllegalArgumentException("same cascade pile");

            c = cascades.get(sourcePileNumber).peek();
            if (c == null) throw new IllegalArgumentException("there's no card here!");

            if (cascades.get(destPileNumber).size() == 0) {
              cascades.get(destPileNumber).add(cascades.get(sourcePileNumber).pop());
            }
            else {
              Card top = cascades.get(destPileNumber).peek();
              if (c.getRank() == top.getRank() - 1 && !c.sameColor(top)) {
                cascades.get(destPileNumber).push(cascades.get(sourcePileNumber).pop());
              }
              else
                throw new IllegalArgumentException("can't move that there");
            }
          }
          break;

          case FOUNDATION: throw new IllegalArgumentException("don't take from foundation");
        }
        break;

      case FOUNDATION:
        Stack<Card> cur;
        switch (sourceType) {
          case OPEN:
            c = opens.get(sourcePileNumber);
            cur = foundations.get(destPileNumber);
            if (cur.size() == 0 && c.getRank() == 1) {
              cur.push(c);
              opens.set(sourcePileNumber, mt);
            }
            else if (cur.size() > 0) {
              Card top = cur.peek();
              if (c.getSuit() == top.getSuit() && c.getRank() == top.getRank() + 1) {
                cur.push(c);
                opens.set(sourcePileNumber, mt);
              }
            }
            else {
              throw new IllegalArgumentException("doesn't match foundation pile");
            }

            this.isGameOver = true;
            for (Stack<Card> s: foundations) {
              if (s.size() != 13) {
                this.isGameOver = false;
              }
            }
            break;

          case CASCADE:
            c = cascades.get(sourcePileNumber).peek();
            cur = foundations.get(destPileNumber);
            if (cur.size() == 0 && c.getRank() == 1) {
              cur.push(cascades.get(sourcePileNumber).pop());
              cascades.get(sourcePileNumber).push(mt);
            }
            else if (cur.size() > 0) {
              Card top = cur.peek();
              if (c.getSuit() == top.getSuit() && c.getRank() == top.getRank() + 1) {
                cur.push(cascades.get(sourcePileNumber).pop());
                cascades.get(sourcePileNumber).push(mt);
              }
            }
            else {
              throw new IllegalArgumentException("doesn't match foundation pile");
            }

            this.isGameOver = true;
            for (Stack<Card> s: foundations) {
              if (s.size() != 13) {
                this.isGameOver = false;
              }
            }
            break;

          case FOUNDATION:
            throw new IllegalArgumentException("really? really???");
        }
        break;
    }
  }

  @Override public String getGameState() {
    String result = "";
    for (int i = 0; i < this.foundations.size(); i++) {
      result += "F" + i + ":";
      for (Card c : this.foundations.get(i)) {
        result += (c.toString());
      }
      result += "\n";
    }
    for (int i = 0; i < this.opens.size(); i++) {
      result += "O" + i + ":" + this.opens.get(i).toString() + "\n";
    }
    for (int i = 0; i < this.cascades.size(); i++) {
      result += "C" + i + ":";
      for (Card c : this.cascades.get(i)) {
        result += (c.toString());
      }
      result += "\n";
    }
    return result;
  }

  @Override public boolean isGameOver() {
    return this.isGameOver;
  }
}
