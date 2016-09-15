/**
 * Created by Charles on 9/15/16.
 */
public class EmptyCard extends Card {

  public EmptyCard() {
    super();
  }

  @Override public String toString() {
    return "";
  }

  @Override public boolean equals(Object other) {
    return other instanceof EmptyCard;
  }

  @Override public int hashCode() {
    return 0;
  }
}
