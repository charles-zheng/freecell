/**
 * Created by Charles on 9/14/16.
 */
public class Card {

  int rank;
  Suit suit;

  enum Suit { DIAMONDS, CLUBS, HEARTS, SPADES}

  public Card(int rank, EmptyCard.Suit suit) {
    if (rank < 1 || rank > 13) {
      throw new IllegalArgumentException("Illegal card rank!");
    }
    this.rank = rank;
    this.suit = suit;
  }

  public Card() {
    this.rank = 0;
    this.suit = null;
  }

  public int getRank() {
    return this.rank;
  };

  public Suit getSuit() {
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

  @Override public boolean equals(Object other) {
    if (!(other instanceof Card)) {
      return false;
    }
    Card c = (Card)other;
    return this.suit == c.getSuit() && this.rank == c.getRank();
  }

  @Override public int hashCode() {
    return (this.getRank() + 61) * this.suit.hashCode();
  }
}
