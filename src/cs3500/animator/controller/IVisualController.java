package cs3500.animator.controller;

import java.util.List;

import cs3500.animator.model.shape.AbstractShape;

/**
 * Represents an interface to control any visual view by receiving commands from the view and
 * providing the view with updates on the UI state.
 */
public interface IVisualController {
  /**
   * Begins the animation process by determining what views should be updated in the UI.
   */
  void startAnimation();

  /**
   * Produces a list of shapes that should be visible in the view.
   *
   * @return the list of visible shapes
   */
  List<AbstractShape> getVisibleShapes();
}
