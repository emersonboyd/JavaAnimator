package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.view.IView;

/**
 * Represents an implementation of an IVisualController to work specifically with and IView. It
 * updates every 5 milliseconds to process the animations that have occurred since the previous UI
 * update.
 */
public class VisualViewController implements IVisualController, ActionListener {
  private IView view;
  private List<AbstractAnimation> animations;
  private List<AbstractShape> shapes;
  private double tempo;
  private Timer timer;
  private long startTime;

  private List<AbstractShape> visibleShapes;

  /**
   * Constructs a VisualViewController with the given animations to be processed.
   *
   * @param view the IView to update hte UI for
   * @param animations the animations associated with the view
   * @param shapes the shapes associated with the view
   * @param tempo the speed of the animation in ticks per second
   */
  public VisualViewController(IView view, List<AbstractAnimation> animations,
                              List<AbstractShape> shapes, double tempo) {
    this.view = view;
    this.animations = animations;
    this.shapes = shapes;
    this.tempo = tempo;
    this.timer = new Timer(5, this);

    this.visibleShapes = new ArrayList<>();
  }

  @Override
  public void startAnimation() {
    timer.start();
    this.startTime = System.nanoTime();
  }

  @Override
  public List<AbstractShape> getVisibleShapes() {
    return this.visibleShapes;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    double secondsElapsed = (System.nanoTime() - this.startTime) / 1000000000.0;
    int ticksElapsed = (int) (secondsElapsed * this.tempo);
    updateVisibleShapes(ticksElapsed);
    for (AbstractAnimation animation : this.animations) {
      if (ticksElapsed >= animation.getStartTime() && ticksElapsed < animation.getEndTime()) {
        animation.animate(ticksElapsed);
      }
    }

    view.refresh();
  }

  /**
   * Updates the list of visible shapes based on what shape should be shown.
   * @param ticksElapsed the total amount of ticks elapsed in the animation
   */
  private void updateVisibleShapes(int ticksElapsed) {
    for (AbstractShape shape : this.shapes) {
      if (ticksElapsed >= shape.getStartTime() && ticksElapsed < shape.getEndTime()
              && !visibleShapes.contains(shape)) {
        visibleShapes.add(shape);
      }
      else if (ticksElapsed >= shape.getEndTime()) {
        visibleShapes.remove(shape);
      }
    }
  }
}
