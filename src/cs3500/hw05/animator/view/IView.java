package cs3500.hw05.animator.view;

/**
 * Represents a view that can be operated on by animators.
 */
public interface IView {

  /**
   * Produces a textual description of the animation. First the shapes are listed
   * in order, and then the animations are listed.
   * @return a string that represents the textual view
   */
  public String getAnimatorDescription();
}
