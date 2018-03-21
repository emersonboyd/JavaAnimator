package cs3500.modeltests;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AnimatorModelTest {
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

  List<AbstractAnimation> animations = new ArrayList<>();
  List<AbstractShape> shapes = new ArrayList<>();

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

    this.animations = new ArrayList<>();

    this.animations.add(cc1);
    this.animations.add(s1);
    this.animations.add(m1);
    this.animations.add(m2);
    this.animations.add(m3);

    this.shapes = new ArrayList<AbstractShape>();
    this.shapes.add(o);
    this.shapes.add(r);

  }

  // Tests that the addAnimation method throws an error when there are two of the same animation
  // type at the same time operating the same shape
  @Test
  public void testAddAnimationErrorSameAnimationTypeSameTimeSameShape() {
    try {
      model.addAnimation(m4);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals(IAnimatorModel.ERROR_CONFLICTING_ANIMATION, e.getMessage());
    }
  }

  // Tests that the addAnimation method works as expected
  @Test
  public void testAddAnimationValid() {
    String expected = "Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Min-corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=110\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=6\n" +
            "Disappears at t=100\n" +
            "\n" +
            "Shape C changes color from (0.0,0.0,1.0) to (1.0,0.0,0.0) from t=10 to t=15\n" +
            "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50\n" +
            "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20 to t=70\n" +
            "Shape C changes color from (1.0,0.0,0.0) to (0.0,1.0,0.0) from t=50 to t=80\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from " +
            "t=51 to t=70\n" +
            "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to t=100\n" +
            "Shape R scales from Width: 25.0, Height: 100.0 to Width: 75.0, Height: 200.0 from " +
            "t=100 to t=105\n" +
            "Shape R moves from (200.0,200.0) to (100.0,50.0) from t=100 to t=110";

    model.addAnimation(cc2);
    model.addAnimation(m5);
    model.addAnimation(s2);

    assertEquals(expected, model.getAnimatorDescription());
  }

  // Tests that the getAnimatorDescription method works as expected
  @Test
  public void testGetAnimatorDescription() {
    String expected = "Shapes:\n" +
            "Name: R\n" +
            "Type: rectangle\n" +
            "Min-corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=110\n" +
            "\n" +
            "Name: C\n" +
            "Type: oval\n" +
            "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=6\n" +
            "Disappears at t=100\n" +
            "\n" +
            "Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50\n" +
            "Shape C moves from (500.0,100.0) to (500.0,400.0) from t=20 to t=70\n" +
            "Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=50 to t=80\n" +
            "Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0, Height: 100.0 from " +
            "t=51 to t=70\n" +
            "Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to t=100";

    assertEquals(expected, model.getAnimatorDescription());
  }

  @Test
  public void testGetAnimations() {
    assertEquals(this.animations, this.model.getAnimations());
  }

  @Test
  public void testGetAnimationsCannotModifyModel() {
    List<AbstractAnimation> tempList = this.model.getAnimations();
    tempList.add(m5);
    assertEquals(this.animations, this.model.getAnimations());
  }

  @Test
  public void testGetShapes() {
    assertEquals(this.shapes, this.model.getShapes());
  }

  @Test
  public void testGetShapesCannotModifyModel() {
    List<AbstractShape> tempList = this.model.getShapes();
    tempList.add(r);
    assertEquals(this.shapes, this.model.getShapes());
  }

}
