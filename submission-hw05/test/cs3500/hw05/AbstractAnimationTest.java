package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.animation.AbstractAnimation;
import cs3500.hw05.animation.ChangeColor;
import cs3500.hw05.animation.Move;
import cs3500.hw05.animation.Scale;
import cs3500.hw05.shape.AbstractShape;
import cs3500.hw05.shape.Oval;
import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AbstractAnimationTest {
  Posn p;
  Color c;
  AbstractShape r;
  AbstractShape r2;
  AbstractShape r3;
  AbstractShape r4;
  AbstractShape o1;
  AbstractAnimation s1;
  AbstractAnimation s2;
  AbstractAnimation s3;
  AbstractAnimation s4;
  AbstractAnimation s5;
  AbstractAnimation s6;
  AbstractAnimation s7;
  AbstractAnimation m1;
  AbstractAnimation m2;
  AbstractAnimation cc1;

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
    this.r4 = new Rectangle(1, 100, "R", new Posn(200.0, 200.0), new Color(1.0, 0.0, 0.0), 50.0,
            100.0);
    this.o1 = new Oval(6, 100, "C", new Posn(500.0, 100.0), new Color(0.0, 0.0, 1.0), 60.0, 30.0);
    this.s1 = new Scale(3, 5, r, 1.0, 1.0);
    this.s2 = new Scale(4, 6, r, 1.0, 1.0);
    this.s3 = new Scale(5, 6, r, 1.0, 1.0);
    this.s4 = new Scale(2, 3, r, 1.0, 1.0);
    this.s5 = new Scale(2, 4, r2, 1.0, 1.0);
    this.s6 = new Scale(3, 4, r, 1.0, 1.0);
    this.s7 = new Scale(51, 70, r4, 0.5, 1.0);
    this.m1 = new Move(4, 6, r, p);
    this.m2 = new Move(10, 50, r4, new Posn(300.0, 300.0));
    this.cc1 = new ChangeColor(50, 80, o1, new Color(0.0, 1.0, 0.0));
  }

  // Tests that the constructor throws an error when the shape is null
  @Test
  public void testConstructorErrorNullShape() {
    try {
      new Scale(3, 5, null, 1.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_NULL_SHAPE, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the animation start time is too early
  @Test
  public void testConstructorErrorStartTimeTooEarly() {
    try {
      new Scale(3, 5, r3, 1.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_START_TOO_EARLY, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the animation start time is too late
  @Test
  public void testConstructorErrorStartTimeTooLate() {
    try {
      new Scale(7, 10, r3, 1.0, 1.0);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractAnimation.ERROR_START_TOO_LATE, e.getMessage());
    }
  }

  // Tests that conflictsWithAnimation throws an error when the animation is null
  @Test
  public void testConflictsWithAnimationErrorNullAnimation() {
    try {
      s1.conflictsWithAnimation(null);
      fail();
    } catch (IllegalArgumentException e) {
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

  // Tests that getting the shape works as expected
  @Test
  public void testGetShape() {
    assertEquals(r, s1.getShape());
  }

  // Tests that the toString method works as expected with a move animation
  @Test
  public void testToStringMove() {
    String expected = "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50";

    assertEquals(expected, m2.toString(m2.getShape()));
  }

  // Tests that the toString method works as expected with a scale animation
  @Test
  public void testToStringScale() {
    String expected = "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: " +
            "100.0 from t=51 to t=70";

    assertEquals(expected, s7.toString(s7.getShape()));
  }

  // Tests that the toString method works as expected with a change color animation
  @Test
  public void testToStringChangeColor() {
    String expected = "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=50 to t=80";

    assertEquals(expected, cc1.toString(cc1.getShape()));
  }
}
