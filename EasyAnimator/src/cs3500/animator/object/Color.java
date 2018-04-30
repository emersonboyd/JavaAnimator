package cs3500.animator.object;

import cs3500.animator.util.NumUtil;

/**
 * Represents an RGB color where each color code falls in the range of [0.0, 1.0].
 */
public class Color implements IColor {
  private static final double MIN_COLOR = 0.0;
  private static final double MAX_COLOR = 1.0;

  public static final String ERROR_RED_OUT_OF_BOUNDS =
          "The red parameter must be within the range 0-1.";
  public static final String ERROR_GREEN_OUT_OF_BOUNDS =
          "The green parameter must be within the range 0-1.";
  public static final String ERROR_BLUE_OUT_OF_BOUNDS =
          "The blue parameter must be within the range 0-1.";

  private double red;
  private double green;
  private double blue;

  /**
   * Constructs a Color object with the given red, green and blue parameters.
   *
   * @param red   the red color this object should store
   * @param green the green color this object should store
   * @param blue  the blue color this object should store
   * @throws IllegalArgumentException if any of the red, green or blue falls outside [0.0, 1.0]
   */
  public Color(double red, double green, double blue) throws IllegalArgumentException {
    if (red < MIN_COLOR || red > MAX_COLOR) {
      throw new IllegalArgumentException(ERROR_RED_OUT_OF_BOUNDS);
    }
    if (green < MIN_COLOR || green > MAX_COLOR) {
      throw new IllegalArgumentException(ERROR_GREEN_OUT_OF_BOUNDS);
    }
    if (blue < MIN_COLOR || blue > MAX_COLOR) {
      throw new IllegalArgumentException(ERROR_BLUE_OUT_OF_BOUNDS);
    }

    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @Override
  public double getRed() {
    return this.red;
  }

  @Override
  public double getGreen() {
    return this.green;
  }

  @Override
  public double getBlue() {
    return this.blue;
  }

  @Override
  public java.awt.Color transformToAwt() {
    return new java.awt.Color((float) this.red, (float) this.green, (float) this.blue);
  }

  @Override
  public String toSVG() {
    StringBuilder builder = new StringBuilder();

    builder.append("rgb(")
            .append(NumUtil.convert1To255(this.getRed()))
            .append(",")
            .append(NumUtil.convert1To255(this.getGreen()))
            .append(",")
            .append(NumUtil.convert1To255(this.getBlue()))
            .append(")");

    return builder.toString();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();

    builder.append("(")
            .append(this.red)
            .append(",")
            .append(this.green)
            .append(",")
            .append(this.blue)
            .append(")");

    return builder.toString();
  }
}
