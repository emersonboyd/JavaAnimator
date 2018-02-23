package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.shape.Rectangle;
import cs3500.hw05.shape.AbstractShape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AbstractShapeTest {
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
    this.r = new Rectangle(0, 1, "test", p, c, 1.0, 1.0);
  }

  // Test that the constructor throws an error if the name is null
  @Test
  public void testConstructorErrorNullName() {
    try {
      new Rectangle(0, 1, null, p, c, 1.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractShape.ERROR_NAME_NULL, e.getMessage());
    }
  }

  // Test that the constructor throws an error if the location is null
  @Test
  public void testConstructorErrorNullLocation() {
    try {
      new Rectangle(0, 1, "", null, c, 1.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractShape.ERROR_LOCATION_NULL, e.getMessage());
    }
  }

  // Test that the constructor throws an error if the color is null
  @Test
  public void testConstructorErrorNullColor() {
    try {
      new Rectangle(0, 1, "", p, null, 1.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractShape.ERROR_COLOR_NULL, e.getMessage());
    }
  }

  // Test that getting the name works as expected
  @Test
  public void testGetName() {
    assertEquals("test", r.getName());
  }

  // Test that getting the location works as expected
  @Test
  public void testGetLocation() {
    assertEquals(p, r.getLocation());
  }

  // Test that getting the color works as expected
  @Test
  public void testGetColor() {
    assertEquals(c, r.getColor());
  }
}
