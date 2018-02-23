package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RectangleTest {
  Posn p;
  Color c;
  Rectangle r;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 1, "test", p, c, 2.4, 1.0);
  }

  // Test that the constructor throws an error when the width is non-positive
  @Test
  public void testConstructorErrorNonPositiveWidth() {
    try {
      new Rectangle(0, 1, "", p, c, 0.0, 1.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(Rectangle.ERROR_WIDTH_BOUNDS, e.getMessage());
    }
  }

  // Test that the constructor throws an error when the height is non-positive
  @Test
  public void testConstructorErrorNonPositiveHeight() {
    try {
      new Rectangle(0, 1, "", p, c, 1.0, 0.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(Rectangle.ERROR_HEIGHT_BOUNDS, e.getMessage());
    }
  }

  // Test that getting the width works as expected
  @Test
  public void testGetWidth() {
    assertEquals(2.4, r.getWidth(), 0.01);
  }

  // Test that getting the height works as expected
  @Test
  public void testGetHeight() {
    assertEquals(1.0, r.getHeight(), 0.01);
  }

}
