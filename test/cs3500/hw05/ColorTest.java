package cs3500.hw05;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ColorTest {

  // Tests that the constructor throws an error when the red value is below 0
  @Test
  public void testConstructorErrorRedBelowBounds() {
    try {
      new Color(-1, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Color.ERROR_RED_OUT_OF_BOUNDS, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the red value is above 1.0
  @Test
  public void testConstructorErrorRedAboveBounds() {
    try {
      new Color(1.01, 0, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Color.ERROR_RED_OUT_OF_BOUNDS, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the green value is below 0
  @Test
  public void testConstructorErrorGreenBelowBounds() {
    try {
      new Color(0, -1, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Color.ERROR_GREEN_OUT_OF_BOUNDS, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the green value is above 1.0
  @Test
  public void testConstructorErrorGreenAboveBounds() {
    try {
      new Color(0, 1.01, 0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Color.ERROR_GREEN_OUT_OF_BOUNDS, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the blue value is below 0
  @Test
  public void testConstructorErrorBlueBelowBounds() {
    try {
      new Color(0, 0, -1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Color.ERROR_BLUE_OUT_OF_BOUNDS, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the blue value is above 1.0
  @Test
  public void testConstructorErrorBlueAboveBounds() {
    try {
      new Color(0, 0, 1.01);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Color.ERROR_BLUE_OUT_OF_BOUNDS, e.getMessage());
    }
  }

  // Tests that the constructor doesn't throw an error at the color bounds
  @Test
  public void testConstructorNoErrorAtBounds() {
    new Color(1.0, 0.0, 1.0);
  }

  // Tests that getting the red color works as expected
  @Test
  public void testGetRed() {
    Color c = new Color(0.5, 0.3, 0.2);
    assertEquals(0.5, c.getRed(), 0.01);
  }

  // Tests that getting the green color works as expected
  @Test
  public void testGetGreen() {
    Color c = new Color(0.5, 0.3, 0.2);
    assertEquals(0.3, c.getGreen(), 0.01);
  }

  // Tests that getting the blue color works as expected
  @Test
  public void testGetBlue() {
    Color c = new Color(0.5, 0.3, 0.2);
    assertEquals(0.2, c.getBlue(), 0.01);
  }
}