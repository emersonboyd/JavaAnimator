package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.controller.IVisualController;

/**
 * Represents a view that can be operated on by animators.
 */
public interface IView {

  /**
   * Produces a textual description of the animation. First the shapes are listed
   * in order, and then the animations are listed.
   * @return a string that represents the textual view
   */
  String getAnimatorDescription();

  /**
   * Writes the animator description to an output source specified by the constructor.
   * @throws IOException if the appendable object doesn't accept input
   */
  void writeAnimatorDescription() throws IOException;

  /**
   * Produces a visual representation of the animation according to the parameters specified
   * by the model.
   */
  void animate();

  /**
   * Performs the actions necessary when given a controller.
   *
   * @param controller the controller to operate with
   */
  void setController(IVisualController controller);

  /**
   * Signal the view to draw itself.
   */
  void refresh();

}
