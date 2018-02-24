package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
    this.p = new Posn(1.4, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 1, "test", p, c, 2.4, 1.0);
  }

  // Test that the constructor throws an error when the width is non-positive
  @Test
  public void testConstructorErrorNonPositiveWidth() {
    try {
      new Rectangle(0, 1, "", p, c, 0.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Rectangle.ERROR_WIDTH_BOUNDS, e.getMessage());
    }
  }

  // Test that the constructor throws an error when the height is non-positive
  @Test
  public void testConstructorErrorNonPositiveHeight() {
    try {
      new Rectangle(0, 1, "", p, c, 1.0, 0.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Rectangle.ERROR_HEIGHT_BOUNDS, e.getMessage());
    }
  }

  // Test that getting the type works as expected
  @Test
  public void testGetType() {
    assertEquals("rectangle", r.getType());
  }

  // Test that the getAttributes method works as expected
  @Test
  public void testGetAttributes() {
    assertEquals("Lower-left corner: (1.4,0.0), Width: 2.4, Height: 1.0, Color: (0.0,0.0,0.0)", r
            .getAttributes());
  }

  // Test that the getSizeDescriptionWithScale method works as expected
  @Test
  public void testGetSizeDescriptionWithScale() {
    assertEquals("Width: 2.4, Height: 3.0", r.getSizeDescriptionWithScale(1.0, 3.0));
  }

  // Test that the clone method works by creating a new reference to the shape
  @Test
  public void testClone() {
    Rectangle clone = r.clone();

    assertFalse(r == clone);
    assertEquals(r.getStartTime(), clone.getStartTime());
    assertEquals(r.getEndTime(), clone.getEndTime());
    assertEquals(r.getName(), clone.getName());
    assertEquals(r.getLocation(), clone.getLocation());
    assertEquals(r.getColor(), clone.getColor());
    assertEquals(r.getWidth(), clone.getWidth(), 0.01);
    assertEquals(r.getHeight(), clone.getHeight(), 0.01);
  }

  // Test that the scale method works as expected
  @Test
  public void testScale() {
    r.scale(1.5, 0.5);

    assertEquals(3.6, r.getWidth(), 0.01);
    assertEquals(0.5, r.getHeight(), 0.01);
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
