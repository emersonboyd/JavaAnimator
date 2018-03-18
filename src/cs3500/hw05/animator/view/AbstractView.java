package cs3500.hw05.animator.view;

import java.util.List;

import cs3500.hw05.animator.model.animation.AbstractAnimation;

/**
 * This class represents a general view that has a tempo in ticks per second. The tempo
 * determines the speed of the animations.
 */
public class AbstractView implements IView {

  protected List<AbstractAnimation> animations;
  protected double tempo;

  /**
   * Constructs a View with the given animations.
   * @param animations the animations associated with this view
   * @param tempo the speed of the animation in ticks per second
   */
  AbstractView(List<AbstractAnimation> animations, double tempo) {
    this.animations = animations;
    this.tempo = tempo;
  }

  @Override
  public String getAnimatorDescription() {
    return "";
  }
}
