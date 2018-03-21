package cs3500.animator.view;

import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;

import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.shape.AbstractShape;

/**
 * This class represents a general view that has a tempo in ticks per second. The tempo
 * determines the speed of the animations.
 */
public abstract class AbstractView extends JFrame implements IView {

  protected static final int PANEL_WIDTH = 500;
  protected static final int PANEL_HEIGHT = 500;
  protected List<AbstractAnimation> animations;
  protected List<AbstractShape> shapes;
  protected double tempo;

  /**
   * Constructs a View with the given animations.
   * @param animations the animations associated with this view
   *                   @param shapes the shapes associated with this view
   * @param tempo the speed of the animation in ticks per second
   */
  AbstractView(List<AbstractAnimation> animations, List<AbstractShape> shapes, double tempo) {
    this.animations = animations;
    this.shapes = shapes;
    this.tempo = tempo;
  }

  @Override
  public String getAnimatorDescription() {
    return "";
  }

  @Override
  public void writeAnimatorDescription() throws IOException {
    return;
  }

  @Override
  public void animate() {
    return;
  }

  @Override
  public void refresh() {
    this.repaint();
  }
}
