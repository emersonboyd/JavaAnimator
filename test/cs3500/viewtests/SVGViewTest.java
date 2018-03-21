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
import cs3500.animator.view.SVGView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SVGViewTest {
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

  IView svg;

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
    this.svg = new SVGView(this.model.getAnimations(), 2, out);
  }

  // Tests that the getAnimatorDescription method works as expected
  @Test
  public void testGetAnimatorDescription() {
    String expected = "<svg width=\"10000\" height=\"10000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" " +
            "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
            "    <animate attributeType=\"xml\" begin=\"5000ms\" dur=\"20000ms\" " +
            "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"5000ms\" dur=\"20000ms\" " +
            "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"25500ms\" dur=\"9500ms\" " +
            "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"35000ms\" dur=\"15000ms\" " +
            "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"35000ms\" dur=\"15000ms\" " +
            "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"500\" cy=\"100\" rx=\"60\" ry=\"30\" " +
            "fill=\"rgb(0,0,255)\" visibility=\"visible\" >\n" +
            "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"25000ms\" " +
            "attributeName=\"cy\" from=\"100.0\" to=\"400.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"25000ms\" dur=\"15000ms\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,255,0)\" " +
            "fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>";

    assertEquals(expected, svg.getAnimatorDescription());
  }

  @Test
  public void testWriteAnimatorDescription() {
    String expected = "<svg width=\"10000\" height=\"10000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"R\" x=\"200\" y=\"200\" width=\"50\" height=\"100\" " +
            "fill=\"rgb(255,0,0)\" visibility=\"visible\" >\n" +
            "    <animate attributeType=\"xml\" begin=\"5000ms\" dur=\"20000ms\" " +
            "attributeName=\"x\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"5000ms\" dur=\"20000ms\" " +
            "attributeName=\"y\" from=\"200.0\" to=\"300.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"25500ms\" dur=\"9500ms\" " +
            "attributeName=\"width\" from=\"50.0\" to=\"25.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"35000ms\" dur=\"15000ms\" " +
            "attributeName=\"x\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"35000ms\" dur=\"15000ms\" " +
            "attributeName=\"y\" from=\"300.0\" to=\"200.0\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"C\" cx=\"500\" cy=\"100\" rx=\"60\" ry=\"30\" fill=\"rgb(0,0,255)\" " +
            "visibility=\"visible\" >\n" +
            "    <animate attributeType=\"xml\" begin=\"10000ms\" dur=\"25000ms\" " +
            "attributeName=\"cy\" from=\"100.0\" to=\"400.0\" fill=\"freeze\" />\n" +
            "    <animate attributeType=\"xml\" begin=\"25000ms\" dur=\"15000ms\" " +
            "attributeName=\"fill\" from=\"rgb(0,0,255)\" to=\"rgb(0,255,0)\" " +
            "fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>";

    try {
      svg.writeAnimatorDescription();
      assertEquals(expected, out.toString());
    }
    catch (IOException e) {
      fail();
    }
  }
}
