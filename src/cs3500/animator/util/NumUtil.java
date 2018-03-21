package cs3500.animator.util;

/**
 * Represents different operations that can be performed on numbers.
 */
public class NumUtil {
  /**
   * Rounds the given double to the nearest integer.
   *
   * @param num the double to round
   * @return the rounded integer
   */
  public static int round(double num) {
    return Math.round((float) num);
  }

  /**
   * Converts the double in the range [0.0, 1.0] to an integer in the range [0, 255]
   *
   * @param num the double to convert
   *            @return the converted integer
   */
  public static int convert1To255(double num) {
    return round(num * 255);
  }
}
