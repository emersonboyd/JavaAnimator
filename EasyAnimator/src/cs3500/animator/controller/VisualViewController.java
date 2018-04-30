package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import cs3500.animator.object.animation.IAnimation;
import cs3500.animator.object.shape.IShape;
import cs3500.animator.view.IView;

/**
 * Represents an implementation of an IVisualController to work specifically with and IView. It
 * updates every 5 milliseconds to process the animations that have occurred since the previous UI
 * update.
 */
public class VisualViewController implements IVisualController, ActionListener {
  private IView view;
  private List<IAnimation> animations;
  private List<IShape> shapes;
  private Map<IShape, Integer> shapeOrder;
  private double tempo;
  private Timer timer;
  private long startTime;

  private List<IShape> visibleShapes;

  /**
   * Constructs a VisualViewController with the given animations to be processed.
   *
   * @param view the IView to update hte UI for
   * @param animations the animations associated with the view
   * @param shapes the shapes associated with the view
   * @param tempo the speed of the animation in ticks per second
   */
  public VisualViewController(IView view, List<IAnimation> animations,
                              List<IShape> shapes, Map<IShape, Integer> shapeOrder,
                              double tempo) {
    this.view = view;
    this.animations = animations;
    this.shapes = shapes;
    this.shapeOrder = shapeOrder;
    this.tempo = tempo;
    this.timer = new Timer(1, this);

    this.visibleShapes = new ArrayList<>();
  }

  @Override
  public void startAnimation() {
    timer.start();
    this.startTime = System.nanoTime();
  }

  @Override
  public List<IShape> getVisibleShapes() {
    return this.visibleShapes;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    double secondsElapsed = (System.nanoTime() - this.startTime) / 1000000000.0;
    int ticksElapsed = (int) (secondsElapsed * this.tempo);
    updateVisibleShapes(ticksElapsed);
    for (IAnimation animation : this.animations) {
      if (ticksElapsed >= animation.getStartTime() && ticksElapsed <= animation.getEndTime()) {
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
    for (IShape shape : this.shapes) {
      if (ticksElapsed >= shape.getStartTime() && ticksElapsed < shape.getEndTime()
              && !visibleShapes.contains(shape)) {
        visibleShapes.add(0, shape);
      }
      else if (ticksElapsed >= shape.getEndTime()) {
        visibleShapes.remove(shape);
      }
    }

    Collections.sort(visibleShapes, new Comparator<IShape>() {
      @Override
      public int compare(IShape o1, IShape o2) {
        return shapeOrder.get(o1) - shapeOrder.get(o2);
      }
    });
  }
}
