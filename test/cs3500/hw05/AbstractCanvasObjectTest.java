package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import cs3500.hw05.shape.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AbstractCanvasObjectTest {
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
    this.r = new Rectangle(0, 1, "", p, c, 1.0, 1.0);
  }

  // Tests that the constructor throws an error when the end time is before the start time
  @Test
  public void testConstructorErrorEndTimeBeforeStartTime() {
    try {
      new Rectangle(1, 1, "", p, c, 2, 3);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(AbstractCanvasObject.ERROR_END_TIME_BEFORE_START_TIME, e.getMessage());
    }
  }

  // Tests that the constructor throws an error when the start time is less than 0
  @Test
  public void testConstructorErrorStartTimeNegative() {
    try {
      new Rectangle(-1, 1, "", p, c, 2, 3);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(AbstractCanvasObject.ERROR_START_TIME_NEGATIVE, e.getMessage());
    }
  }

  // Tests that getting the start time works as expected
  @Test
  public void testGetStartTime() {
    assertEquals(0, r.getStartTime());
  }

  // Tests that getting the end time works as expected
  @Test
  public void testGetEndTime() {
    assertEquals(1, r.getEndTime());
  }
}
