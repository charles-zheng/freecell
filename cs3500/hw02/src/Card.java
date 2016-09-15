/**
 * Created by Charles on 9/14/16.
 */
public class Card {

  int rank; // must be in range [1,13] where 1 = ace and 11 = jack, etc.
  Suit suit;

  enum Suit { DIAMONDS, CLUBS, HEARTS, SPADES}

  Card(int rank, Suit suit) {
    if (rank < 1 || rank > 13) {
      throw new IllegalArgumentException("Illegal card rank!");
    }
    this.rank = rank;
    this.suit = suit;
  }

  int getRank() {
    return this.rank;
  }

  Suit getSuit() {
    return this.suit;
  }

  boolean sameColor(Card other) {
    if (this.suit == Suit.DIAMONDS || this.suit == Suit.HEARTS) {
      return (other.suit == Suit.DIAMONDS || other.suit == Suit.HEARTS);
    }
    else return (other.suit == Suit.CLUBS || other.suit == Suit.SPADES);
  }

  @Override public String toString() {
    String result = " ";
    switch (rank) {
      case 1: result += "A";
        break;
      case 11: result += "J";
        break;
      case 12: result += "Q";
        break;
      case 13: result += "K";
        break;
      default: result += rank;
    }
    switch (suit) {
      case CLUBS: result += "♣";
        break;
      case DIAMONDS: result += "♦";
        break;
      case HEARTS: result += "♥";
        break;
      case SPADES: result += "♠";
        break;
    }
    return result;
  }
}
