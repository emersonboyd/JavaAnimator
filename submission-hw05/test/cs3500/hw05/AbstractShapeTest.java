package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.shape.AbstractShape;
import cs3500.hw05.shape.Oval;
import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AbstractShapeTest {
  Posn p;
  Color c;
  AbstractShape r;
  AbstractShape r2;
  AbstractShape r3;
  AbstractShape r4;
  AbstractShape r5;
  AbstractShape o;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 1, "test", p, c, 1.0, 1.0);
    this.r2 = new Rectangle(0, 1, "test", p, c, 1.0, 1.0);
    this.r3 = r;
    this.r4 = new Rectangle(0, 1, "test", p, c, 2.4, 1.0);
    this.r5 = new Rectangle(1, 100, "R", new Posn(200.0, 200.0), new Color(1.0, 0.0, 0.0), 50.0,
            100.0);
    this.o = new Oval(6, 100, "C", new Posn(500.0, 100.0), new Color(0.0, 0.0, 1.0), 60.0, 30.0);
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

  // Test that the getSizeDescription method works as expected
  @Test
  public void testGetSizeDescription() {
    assertEquals("Width: 2.4, Height: 1.0", r4.getSizeDescription());
  }

  // Test that the move method works as expected
  @Test
  public void testMove() {
    Posn newLocation = new Posn(50.0, 75.0);
    r.move(newLocation);

    assertEquals(newLocation, r.getLocation());
  }

  // Test that the changeColor method works as expected
  @Test
  public void testChangeColor() {
    Color newColor = new Color(0.5, 1.0, 0.0);
    r.changeColor(newColor);

    assertEquals(newColor, r.getColor());
  }

  // Test that equals returns false for the same values in different memory locations
  @Test
  public void testEqualsSameValuesDifferentObject() {
    assertFalse(r.equals(r2));
  }

  // Test that equals returns true for the same values in the same memory location
  @Test
  public void testEqualsSameValuesSameObject() {
    assertTrue(r.equals(r3));
  }

  // Test that the toString method works as expected with a rectangle
  @Test
  public void testToStringRectangle() {
    String expected = "Name: R\n" +
            "Type: rectangle\n" +
            "Lower-left corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=100";

    assertEquals(expected, r5.toString());
  }

  // Test that the toString method works as expected with an oval
  @Test
  public void testToStringOval() {
    String expected = "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=6\n" +
            "Disappears at t=100";

    assertEquals(expected, o.toString());
  }
}
