package cs3500.viewtests;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Color;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.Posn;
import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.animation.ChangeColor;
import cs3500.animator.model.animation.Move;
import cs3500.animator.model.animation.Scale;
import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.view.IView;
import cs3500.animator.view.TextualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TextualViewTest {
  AbstractShape r;
  AbstractShape o;
  AbstractAnimation m1;
  AbstractAnimation m2;
  AbstractAnimation m3;
  AbstractAnimation m4;
  AbstractAnimation m5;
  AbstractAnimation s1;
  AbstractAnimation s2;
  AbstractAnimation cc1;
  AbstractAnimation cc2;

  IAnimatorModel model;

  IView textual;

  Appendable out;

  /**
   * Sets up the model objects for testing.
   */
  @Before
  public void setup() {
    this.r = new Rectangle(1, 110, "R", new Posn(200.0, 200.0), new Color(1.0, 0.0, 0.0), 50.0,
            100.0);
    this.o = new Oval(6, 100, "C", new Posn(500.0, 100.0), new Color(0.0, 0.0, 1.0), 60.0, 30.0);
    this.m1 = new Move(10, 50, r, new Posn(300.0, 300.0));
    this.m2 = new Move(20, 70, o, new Posn(500.0, 400.0));
    this.cc1 = new ChangeColor(50, 80, o, new Color(0.0, 1.0, 0.0));
    this.s1 = new Scale(51, 70, r, 0.5, 1.0);
    this.m3 = new Move(70, 100, r, new Posn(200.0, 200.0));

    this.m4 = new Move(51, 65, o, new Posn(250.0, 150.0));
    this.cc2 = new ChangeColor(10, 15, o, new Color(1.0, 0.0, 0.0));
    this.m5 = new Move(100, 110, r, new Posn(100.0, 50.0));
    this.s2 = new Scale(100, 105, r, 3.0, 2.0);

    this.model = new AnimatorModel();

    // add the animations in a weird order because they should be sorted later
    this.model.addAnimation(cc1);
    this.model.addAnimation(s1);
    this.model.addAnimation(m1);
    this.model.addAnimation(m2);
    this.model.addAnimation(m3);

    out = new StringWriter();

    // create a textual view
    this.textual = new TextualView(this.model.getAnimations(), 2, out);
  }

  // Tests that the getAnimatorDescription method works as expected
  @Test
  public void testGetAnimatorDescription() {
    String expected = "Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Min-corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=0.5s\n" +
            "Disappears at t=55.0s\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=3.0s\n" +
            "Disappears at t=50.0s\n" +
            "\n" +
            "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=5.0s to t=25.0s\n" +
            "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=10.0s to t=35.0s\n" +
            "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=25.0s to t=40.0s\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from " +
            "t=25.5s to t=35.0s\n" +
            "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s";

    assertEquals(expected, textual.getAnimatorDescription());
  }

  @Test
  public void testWriteAnimatorDescription() {
    String expected = "Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Min-corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=0.5s\n" +
            "Disappears at t=55.0s\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=3.0s\n" +
            "Disappears at t=50.0s\n" +
            "\n" +
            "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=5.0s to t=25.0s\n" +
            "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=10.0s to t=35.0s\n" +
            "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=25.0s to t=40.0s\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from " +
            "t=25.5s to t=35.0s\n" +
            "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s";

    try {
      textual.writeAnimatorDescription();
      assertEquals(expected, out.toString());
    }
    catch (IOException e) {
      fail();
    }
  }
}
