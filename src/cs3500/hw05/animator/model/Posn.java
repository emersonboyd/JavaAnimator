package cs3500.hw05.animator.model;

import java.util.Objects;

/**
 * Represents a 2D position with x and y coordinates.
 */
public class Posn {
  private double x;
  private double y;

  /**
   * Constructs a posn with the given x and y coordinates.
   *
   * @param x the x coordinate of this posn
   * @param y the y coordinate of this posn
   */
  public Posn(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * A getter for the x position.
   *
   * @return the x position value
   */
  public double getX() {
    return this.x;
  }

  /**
   * A getter for the y position.
   *
   * @return the y position value
   */
  public double getY() {
    return this.y;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Posn)) {
      return false;
    }

    Posn p = (Posn) o;
    return this.x == p.getX() && this.y == p.getY();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("(")
            .append(this.x)
            .append(",")
            .append(this.y)
            .append(")");

    return builder.toString();
  }
}
