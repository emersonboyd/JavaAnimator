package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.animator.model.shape.Oval;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    this.p = new Posn(1.4, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.o = new Oval(0, 1, "test", p, c, 2.4, 1.0);
  }

  // Test that the constructor throws an error when the width is non-positive
  @Test
  public void testConstructorErrorNonPositiveWidth() {
    try {
      new Oval(0, 1, "", p, c, 0.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Oval.ERROR_RADIUS_BOUNDS, e.getMessage());
    }
  }

  // Test that the constructor throws an error when the height is non-positive
  @Test
  public void testConstructorErrorNonPositiveHeight() {
    try {
      new Oval(0, 1, "", p, c, 1.0, 0.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Oval.ERROR_RADIUS_BOUNDS, e.getMessage());
    }
  }

  // Test that getting the type works as expected
  @Test
  public void testGetType() {
    assertEquals("oval", o.getType());
  }

  // Test that the getAttributes method works as expected
  @Test
  public void testGetAttributes() {
    assertEquals("Center: (1.4,0.0), X radius: 2.4, Y radius: 1.0, Color: (0.0,0.0,0.0)", o
            .getAttributes());
  }

  // Test that the getSizeDescriptionWithScale method works as expected
  @Test
  public void testGetSizeDescriptionWithScale() {
    assertEquals("X radius: 2.4, Y radius: 3.0", o.getSizeDescriptionWithScale(1.0, 3.0));
  }

  // Test that the clone method works by creating a new reference to the shape
  @Test
  public void testClone() {
    Oval clone = o.clone();

    assertFalse(o == clone);
    assertEquals(o.getStartTime(), clone.getStartTime());
    assertEquals(o.getEndTime(), clone.getEndTime());
    assertEquals(o.getName(), clone.getName());
    assertEquals(o.getLocation(), clone.getLocation());
    assertEquals(o.getColor(), clone.getColor());
    assertEquals(o.getRadiusX(), clone.getRadiusX(), 0.01);
    assertEquals(o.getRadiusY(), clone.getRadiusY(), 0.01);
  }

  // Test that the scale method works as expected
  @Test
  public void testScale() {
    o.scale(1.5, 0.5);

    assertEquals(3.6, o.getRadiusX(), 0.01);
    assertEquals(0.5, o.getRadiusY(), 0.01);
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
