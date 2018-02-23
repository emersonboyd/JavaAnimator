package cs3500.hw05;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PosnTest {
  Posn p1;
  Posn p2;
  Posn p3;
  Posn p4;

  /**
   * Sets up the posn objects for testing
   */
  @Before
  public void setup() {
    this.p1 = new Posn(3.0, -2.5);
    this.p2 = new Posn(2.4, -2.5);
    this.p3 = new Posn(3.0, 5.0);
    this.p4 = new Posn(3.0, -2.5);
  }

  // Test that getting the x position works as expected
  @Test
  public void testGetX() {
    assertEquals(3.0, p1.getX(), 0.01);
  }

  // Test that getting the y position works as expected
  @Test
  public void testGetY() {
    assertEquals(-2.5, p1.getY(), 0.01);
  }

  // Test that timeOverlaps returns false with a posn in a different x position
  @Test
  public void testEqualsDifferentXPosition() {
    assertFalse(p1.equals(p2));
  }

  // Test that timeOverlaps returns false with a posn in a different y position
  @Test
  public void testEqualsDifferentYPosition() {
    assertFalse(p1.equals(p3)); }

  // Test that timeOverlaps works with a posn at the same position
  @Test
  public void testEqualsSamePosition() {
    assertTrue(p1.equals(p4));
  }
}
