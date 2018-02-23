package cs3500.hw05;

/**
 * Represents an RGB color where each color code falls in the range of [0.0, 1.0].
 */
public class Color {
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
   * @param red the red color this object should store
   * @param green the green color this object should store
   * @param blue the blue color this object should store
   * @throws IllegalArgumentException if any of the red, green or blue falls outside [0.0, 1.0]
   */
  public Color(double red, double green, double blue) {
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

  /**
   * A getter for the red color.
   *
   * @return this color's red value
   */
  public double getRed() {
    return this.red;
  }

  /**
   * A getter for the green color.
   *
   * @return this color's green value
   */
  public double getGreen() {
    return this.green;
  }

  /**
   * A getter for the blue color.
   *
   * @return this color's blue value
   */
  public double getBlue() {
    return this.blue;
  }
}
