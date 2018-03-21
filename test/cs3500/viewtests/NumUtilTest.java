package cs3500.viewtests;

import org.junit.Test;

import cs3500.animator.util.NumUtil;

import static org.junit.Assert.assertEquals;

public class NumUtilTest {
  @Test
  public void testRoundDown() {
    assertEquals(3, NumUtil.round(3.49));
  }

  @Test
  public void testRoundUp() {
    assertEquals(3, NumUtil.round(2.50));
  }

  @Test
  public void testConvert1To255With1() {
    assertEquals(255, NumUtil.convert1To255(1.0));
  }

  @Test
  public void testConvert1To255With0() {
    assertEquals(0, NumUtil.convert1To255(0.0));
  }

  @Test
  public void testConvert1To255WithOneHalf() {
    assertEquals(128, NumUtil.convert1To255(0.5));
  }
}
