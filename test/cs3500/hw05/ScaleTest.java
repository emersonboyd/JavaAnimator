package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.animation.Move;
import cs3500.hw05.animation.Scale;
import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ScaleTest {
  Posn p;
  Color c;
  Rectangle r;
  Scale s;
  Scale valid;
  Move invalid;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 100, "test", p, c, 2.4, 1.0);
    this.s = new Scale(3, 5, r, 1.0, 1.5);
    this.valid = new Scale(3, 5, r, 2.0, 1.0);
    this.invalid = new Move(3, 5, r, p);
  }

  // Test that the constructor throws an error when the x scale is non-positive
  @Test
  public void testConstructorErrorNonPositiveScaleX() {
    try {
      new Scale(3, 5, r, 0.0, 1.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(Scale.ERROR_SCALE_FACTOR_BOUNDS, e.getMessage());
    }
  }

  // Test that the constructor throws an error when the y scale is non-positive
  @Test
  public void testConstructorErrorNonPositiveScaleY() {
    try {
      new Scale(3, 5, r, 1.0, 0.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(Scale.ERROR_SCALE_FACTOR_BOUNDS, e.getMessage());
    }
  }

  // Test that getting the x scale works as expected.
  @Test
  public void testGetScaleX() {
    assertEquals(1.0, s.getScaleX(), 0.01);
  }

  // Test that getting the y scale works as expected.
  @Test
  public void testGetScaleY() {
    assertEquals(1.5, s.getScaleY(), 0.01);
  }

  // Test that sameType returns true with a valid Scale
  @Test
  public void testSameTypeValid() {
    assertTrue(s.sameType(valid));
  }

  // Test that sameType returns true with an invalid Move
  @Test
  public void testSameTypeInvalid() {
    assertFalse(s.sameType(invalid));
  }
}
