package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.shape.Oval;
import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OvalTest {
  Posn p;
  Color c;
  Oval o;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.o = new Oval(0, 1, "test", p, c, 2.4, 1.0);
  }

  // Test that the constructor throws an error when the width is non-positive
  @Test
  public void testConstructorErrorNonPositiveWidth() {
    try {
      new Oval(0, 1, "", p, c, 0.0, 1.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(Oval.ERROR_RADIUS_BOUNDS, e.getMessage());
    }
  }

  // Test that the constructor throws an error when the height is non-positive
  @Test
  public void testConstructorErrorNonPositiveHeight() {
    try {
      new Oval(0, 1, "", p, c, 1.0, 0.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(Oval.ERROR_RADIUS_BOUNDS, e.getMessage());
    }
  }

  // Test that getting the x radius works as expected
  @Test
  public void testGetRadiusX() {
    assertEquals(2.4, o.getRadiusX(), 0.01);
  }

  // Test that getting the y radius works as expected
  @Test
  public void testGetRadiusY() {
    assertEquals(1.0, o.getRadiusY(), 0.01);
  }
}
