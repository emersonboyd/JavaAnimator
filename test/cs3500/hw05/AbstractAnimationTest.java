package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.css.Rect;

import cs3500.hw05.animation.AbstractAnimation;
import cs3500.hw05.animation.ChangeColor;
import cs3500.hw05.animation.Move;
import cs3500.hw05.animation.Scale;
import cs3500.hw05.shape.Oval;
import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AbstractAnimationTest {
  Posn p;
  Color c;
  Rectangle r;
  Rectangle r2;
  Rectangle r3;
  Scale s1;
  Scale s2;
  Scale s3;
  Scale s4;
  Scale s5;
  Scale s6;
  Move m1;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r = new Rectangle(0, 100, "test", p, c, 2.4, 1.0);
    this.r2 = new Rectangle(0, 100, "test", p, c, 2.4, 1.0);
    this.r3 = new Rectangle(4, 6, "test", p, c, 2.4, 1.0);
    this.s1 = new Scale(3, 5, r, 1.0, 1.0);
    this.s2 = new Scale(4, 6, r, 1.0, 1.0);
    this.s3 = new Scale(5, 6, r, 1.0, 1.0);
    this.s4 = new Scale(2, 3, r, 1.0, 1.0);
    this.s5 = new Scale(2, 4, r2, 1.0, 1.0);
    this.s6 = new Scale(3, 4, r, 1.0, 1.0);
    this.m1 = new Move(4, 6, r, p);
  }

  // Tests that the constructor throws an error when the shape is null
  @Test
  public void testConstructorErrorNullShape() {
    try {
      new Scale(3, 5, null, 1.0, 1.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_NULL_SHAPE, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the animation start time is too early
  @Test
  public void testConstructorErrorStartTimeTooEarly() {
    try {
      new Scale(3, 5, r3, 1.0, 1.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_START_TOO_EARLY, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the animation start time is too late
  @Test
  public void testConstructorErrorStartTimeTooLate() {
    try {
      new Scale(7, 10, r3, 1.0, 1.0);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_START_TOO_LATE, e.getMessage());
    }
  }

  // Tests that conflictsWithAnimation throws an error when the animation is null
  @Test
  public void testConflictsWithAnimationErrorNullAnimation() {
    try {
      s1.conflictsWithAnimation(null);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_NULL_ANIMATION, e.getMessage());
    }
  }

  // Tests that conflictsWithAnimation is false when the two shapes are different
  @Test
  public void testConflictsWithAnimationDifferentShapes() {
    assertFalse(s1.conflictsWithAnimation(s5));
  }

  // Tests that conflictsWithAnimation is false when the two times do not overlap
  @Test
  public void testConflictsWithAnimationNonOverlappingTimes() {
    assertFalse(s1.conflictsWithAnimation(s4));
  }

  // Test that conflictsWithAnimation is false when the two animation types are different
  @Test
  public void testConflictsWithAnimationDifferentAnimationTypes() {
    assertFalse(s1.conflictsWithAnimation(m1));
  }

  // Test that conflictsWithAnimation is true when expected
  @Test
  public void testConflictsWithAnimationTrue() {
    assertTrue(s1.conflictsWithAnimation(s6));
  }

  // Tests that timeOverlaps returns true when the start time falls in the middle
  @Test
  public void testTimeOverlapsStartTimeMiddle() {
    assertTrue(s1.timeOverlaps(s2));
  }

  // Tests that timeOverlaps returns false when the start time falls at the end
  @Test
  public void testTimeOverlapsStartTimeEnd() {
    assertFalse(s1.timeOverlaps(s3));
  }

  // Tests that timeOverlaps returns true when the end time falls at the start
  @Test
  public void testTimeOverlapsEndTimeStart() {
    assertFalse(s1.timeOverlaps(s4));
  }

  // Tests that timeOverlaps returns true when the end time falls in the middle
  @Test
  public void testTimeOverlapsEndTimeMiddle() {
    assertTrue(s1.timeOverlaps(s5));
  }

  // Tests that timeOverlaps returns true when both times fall in the middle
  @Test
  public void testTimeOverlapsBothTimesMiddle() {
    assertTrue(s1.timeOverlaps(s6));
  }
}
