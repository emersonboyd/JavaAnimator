package cs3500.hw05.modelTests;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.animator.model.Color;
import cs3500.hw05.animator.model.Posn;
import cs3500.hw05.animator.model.animation.ChangeColor;
import cs3500.hw05.animator.model.animation.Move;
import cs3500.hw05.animator.model.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChangeColorTest {
  Posn p;
  Color c;
  Rectangle r;
  ChangeColor a;
  ChangeColor valid;
  Move invalid;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 100, "test", p, c, 2.4, 1.0);
    this.a = new ChangeColor(3, 5, r, c);
    this.valid = new ChangeColor(3, 5, r, new Color(1.0, 1.0, 1.0));
    this.invalid = new Move(3, 5, r, p);
  }

  // Test that the constructor throws an error when the target is null
  @Test
  public void testConstructorErrorNullTarget() {
    try {
      new ChangeColor(3, 5, r, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(ChangeColor.ERROR_TARGET_NULL, e.getMessage());
    }
  }

  // Test that getting the target works as expected
  @Test
  public void testGetTarget() {
    assertEquals(c, a.getTarget());
  }

  // Test that animate shape alters the shape as expected
  @Test
  public void testAnimate() {
    valid.animate(r);

    assertEquals(valid.getTarget(), r.getColor());
  }

  // Test that sameType returns true with a valid ColorChange
  @Test
  public void testSameTypeValid() {
    assertTrue(a.sameType(valid));
  }

  // Test that sameType returns true with an invalid Move
  @Test
  public void testSameTypeInvalid() {
    assertFalse(a.sameType(invalid));
  }

  // Test that the getAction method works as expected
  @Test
  public void testGetAction() {
    assertEquals("changes color from (0.0,0.0,0.0) to (1.0,1.0,1.0)", valid.getAction(valid
            .getShape()));
  }
}
