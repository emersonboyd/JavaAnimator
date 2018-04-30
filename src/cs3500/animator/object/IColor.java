package cs3500.animator.object;


public interface IColor {
  /**
   * A getter for the red color.
   *
   * @return this color's red value
   */
  public double getRed();

  /**
   * A getter for the green color.
   *
   * @return this color's green value
   */
  public double getGreen();

  /**
   * A getter for the blue color.
   *
   * @return this color's blue value
   */
  public double getBlue();

  /**
   * Converts this color object to a Java awt color object.
   *
   * @return the awt color object
   */
  public java.awt.Color transformToAwt();

  /**
   * Converts the colors to an SVG string format.
   *
   * @return the SVG string
   */
  String toSVG();

  /**
   * Parses the given string to produce a Color object. The string is in the form (R,G,B)
   *
   * @param s the string to parse
   * @return the new Color object
   */
  static IColor parseString(String s) {
    s = s.replace("(", "");
    s = s.replace(")", "");

    String[] colorStrings = s.split(",");
    return new Color(Double.valueOf(colorStrings[0]),
            Double.valueOf(colorStrings[1]),
            Double.valueOf(colorStrings[2]));
  }

  /**
   * Produces a textual representation of a shape.
   * @return a string representing the features of a shape
   */
  String toString();
}
