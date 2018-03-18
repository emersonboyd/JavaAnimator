package cs3500.hw05.modelTests;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.animator.model.AbstractCanvasObject;
import cs3500.hw05.animator.model.Color;
import cs3500.hw05.animator.model.Posn;
import cs3500.hw05.animator.model.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AbstractCanvasObjectTest {
  Posn p;
  Color c;
  AbstractCanvasObject r1;
  AbstractCanvasObject r2;
  AbstractCanvasObject r3;
  AbstractCanvasObject r4;
  AbstractCanvasObject r5;
  AbstractCanvasObject r6;

  /**
   * Sets up the objects for testing.
   */
  @Before
  public void setup() {
    this.p = new Posn(0.0, 0.0);
    this.c = new Color(0.0, 0.0, 0.0);
    this.r1 = new Rectangle(3, 7, "", p, c, 1.0, 1.0);
    this.r2 = new Rectangle(2, 4, "", p, c, 1.0, 1.0);
    this.r3 = new Rectangle(3, 6, "", p, c, 1.0, 1.0);
    this.r4 = new Rectangle(3, 8, "", p, c, 1.0, 1.0);
    this.r5 = new Rectangle(4, 6, "", p, c, 1.0, 1.0);
    this.r6 = new Rectangle(3, 7, "", p, c, 1.0, 1.0);
  }

  // Tests that the constructor throws an error when the end time is before the start time
  @Test
  public void testConstructorErrorEndTimeBeforeStartTime() {
    try {
      new Rectangle(1, 1, "", p, c, 2, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractCanvasObject.ERROR_END_TIME_BEFORE_START_TIME, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the start time is less than 0
  @Test
  public void testConstructorErrorStartTimeNegative() {
    try {
      new Rectangle(-1, 1, "", p, c, 2, 3);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(AbstractCanvasObject.ERROR_START_TIME_NEGATIVE, e.getMessage());
    }
  }

  // Tests that getting the start time works as expected
  @Test
  public void testGetStartTime() {
    assertEquals(3, r1.getStartTime());
  }

  // Tests that getting the end time works as expected
  @Test
  public void testGetEndTime() {
    assertEquals(7, r1.getEndTime());
  }

  // Tests the compareTo method when the other's start time comes before
  @Test
  public void testCompareToStartTimeBefore() {
    assertEquals(1, r1.compareTo(r2));
  }

  // Tests the compareTo method when the other's start time comes at the same time but the end
  // time comes before
  @Test
  public void testCompareToStartTimeSameEndTimeBefore() {
    assertEquals(1, r1.compareTo(r3));
  }

  // Tests the compareTo method when the other's start time comes at the same time but the end
  // time comes after
  @Test
  public void testCompareToStartTimeSameEndTimeAfter() {
    assertEquals(-1, r1.compareTo(r4));
  }

  // Tests the compareTo method when the other's start time comes after
  @Test
  public void testCompareToStartTimeAfter() {
    assertEquals(-1, r1.compareTo(r5));
  }

  // Tests the compareTo method when the other's start time comes at the same time and the
  // other's end time comes at the same time
  @Test
  public void testCompareToStartTimeSameEndTimeSame() {
    assertEquals(0, r1.compareTo(r6));
  }
}
