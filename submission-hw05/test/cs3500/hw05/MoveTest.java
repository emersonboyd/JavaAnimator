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

public class MoveTest {
  Posn p;
  Color c;
  Rectangle r;
  Move m;
  Move valid;
  Scale invalid;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 100, "test", p, c, 2.4, 1.0);
    this.m = new Move(3, 5, r, p);
    this.valid = new Move(3, 5, r, new Posn(100.0, 200.0));
    this.invalid = new Scale(3, 5, r, 1.90, 1.0);
  }

  // Test that the constructor throws an error when the destination is null
  @Test
  public void testConstructorErrorNullDestination() {
    try {
      new Move(3, 5, r, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(Move.ERROR_DESTINATION_NULL, e.getMessage());
    }
  }

  // Test that getting the destination works as expected
  @Test
  public void testGetDestination() {
    assertEquals(p, m.getDestination());
  }

  // Test that animate shape alters the shape as expected
  @Test
  public void testAnimate() {
    valid.animate(r);

    assertEquals(valid.getDestination(), r.getLocation());
  }

  // Test that sameType returns true with a valid Move
  @Test
  public void testSameTypeValid() {
    assertTrue(m.sameType(valid));
  }

  // Test that sameType returns true with an invalid Scale
  @Test
  public void testSameTypeInvalid() {
    assertFalse(m.sameType(invalid));
  }

  // Test that the getAction method works as expected
  @Test
  public void testGetAction() {
    assertEquals("moves from (0.0,0.0) to (100.0,200.0)", valid.getAction(valid.getShape()));
  }
}
