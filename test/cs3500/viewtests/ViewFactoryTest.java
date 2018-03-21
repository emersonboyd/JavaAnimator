package cs3500.viewtests;

import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;

import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.Color;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.Posn;
import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.animation.Move;
import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.TextualView;
import cs3500.animator.view.ViewFactory;
import cs3500.animator.view.VisualView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ViewFactoryTest {
  IAnimatorModel model;
  double tempo;

  AbstractShape r;
  AbstractShape o;
  AbstractAnimation m1;
  AbstractAnimation m2;

  Appendable out;

  /**
   * Sets up the factory class for testing.
   */
  @Before
  public void setup() {
    model = new AnimatorModel();
    tempo = .001;
    this.out = new StringWriter();

    this.r = new Rectangle(1, 110, "R", new Posn(200.0, 200.0), new Color(1.0, 0.0, 0.0), 50.0,
            100.0);
    this.o = new Oval(6, 100, "C", new Posn(500.0, 100.0), new Color(0.0, 0.0, 1.0), 60.0, 30.0);
    this.m1 = new Move(10, 50, r, new Posn(300.0, 300.0));
    this.m2 = new Move(20, 70, o, new Posn(500.0, 400.0));
  }

  @Test
  public void testCreateTextViewType() {
    IView view = ViewFactory.create("TEXT", model, tempo, out);
    assertTrue(view instanceof TextualView);
  }

  @Test
  public void testCreateVisualViewType() {
    IView view = ViewFactory.create("Visual", model, tempo, out);
    assertTrue(view instanceof VisualView);

  }

  @Test
  public void testCreateSvgViewType() {
    IView view = ViewFactory.create("svg", model, tempo, out);
    assertTrue(view instanceof SVGView);
  }

  @Test
  public void testCreateNullViewType() {
    try {
      ViewFactory.create(null, model, tempo, out);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(ViewFactory.ERROR_INVALID_VIEWTYPE, e.getMessage());
    }
  }

  @Test
  public void testCreateJibberishViewType() {
    try {
      ViewFactory.create("abcde", model, tempo, out);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(ViewFactory.ERROR_INVALID_VIEWTYPE, e.getMessage());
    }
  }

  @Test
  public void testCreateNullModel() {
    try {
      ViewFactory.create("svg", null, tempo, out);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(ViewFactory.ERROR_NULL_MODEL, e.getMessage());
    }
  }

  @Test
  public void testCreateInvalidTempo() {
    try {
      ViewFactory.create("svg", model, 0, out);
      fail();
    }
    catch (IllegalArgumentException e) {
      assertEquals(ViewFactory.ERROR_INVALID_TEMPO, e.getMessage());
    }
  }

}
