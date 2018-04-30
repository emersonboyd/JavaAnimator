package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import cs3500.animator.object.animation.IAnimation;
import cs3500.animator.object.shape.IShape;
import cs3500.animator.util.NumUtil;
import cs3500.animator.util.Stopwatch;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;

/**
 * Class represents the controller for a hybrid view. It handles all of the timing and the button
 * presses.
 */
public class HybridViewController implements IInteractiveController, ActionListener {
  private IInteractiveView view;
  private List<IAnimation> animations;
  private List<IShape> shapes;
  private Map<IShape, Integer> shapeOrder;
  private int currentTicksElapsed;
  private double tempo;
  private Timer timer;
  private Stopwatch stopwatch;
  private AnimationState state;
  private boolean resumeOnRelease;

  private List<IShape> visibleShapes;
  private boolean looping;
  private int endTime;
  private Map<IShape,Boolean> shapeVisibilityMap;

  /**
   * Constructs a VisualViewController with the given animations to be processed.
   *
   * @param view the IView to update hte UI for
   * @param animations the animations associated with the view
   * @param shapes the shapes associated with the view
   * @param shapeOrder the mapping describing the ordering for each shape
   * @param tempo the speed of the animation in ticks per second
   */
  public HybridViewController(IInteractiveView view, List<IAnimation> animations,
                              List<IShape> shapes, Map<IShape, Integer> shapeOrder,
                              double tempo) {
    this.view = view;
    this.animations = animations;
    this.shapes = shapes;
    this.shapeOrder = shapeOrder;
    this.currentTicksElapsed = 0;
    this.tempo = tempo;
    this.timer = new Timer(1, this);
    this.visibleShapes = new ArrayList<>();
    this.shapeVisibilityMap = new HashMap<>();
    for (IShape shape : shapes) {
      shapeVisibilityMap.put(shape, true);
    }
    this.stopwatch = new Stopwatch();
    this.state = AnimationState.NOT_STARTED;
    this.looping = false;
    this.endTime = calculateEndTime();
    this.resumeOnRelease = false;
  }

  @Override
  public int calculateEndTime() {
    int endTime = 0;
    for (IAnimation animation : animations) {
      if (animation.getShape().getEndTime() > endTime) {
        endTime = animation.getShape().getEndTime();
      }
    }
    return endTime;
  }

  @Override
  public List<IShape> getVisibleShapes() {
    return this.visibleShapes;
  }

  /**
   * Updates the list of visible shapes based on what shape should be shown.
   * @param ticksElapsed the total amount of ticks elapsed in the animation
   */
  private void updateVisibleShapes(int ticksElapsed) {
    for (IShape shape : this.shapes) {
      if (shapeVisibilityMap.get(shape) && ticksElapsed >= shape.getStartTime()
              && ticksElapsed < shape.getEndTime()
              && !visibleShapes.contains(shape)) {
        visibleShapes.add(0, shape);
      }
      else if (!shapeVisibilityMap.get(shape) || ticksElapsed < shape.getStartTime()
              || ticksElapsed >= shape.getEndTime()) {
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

  @Override
  public void actionPerformed(ActionEvent e) {
    double secondsElapsed = stopwatch.elapsed() / 1000000000.0;
    this.currentTicksElapsed = Integer.max(1, NumUtil.round(secondsElapsed * this.tempo));
    this.view.updateScrubberLabel(this.currentTicksElapsed);

    switch (state) {
      case NOT_STARTED:
        this.stopwatch = new Stopwatch();
        break;
      case IN_PROGRESS:
        animate(this.currentTicksElapsed);
        if (this.looping && (this.currentTicksElapsed > this.endTime)) {
          this.handleRestart();
        }
        break;
      case PAUSED:
        this.stopwatch.pause();
        animate(this.currentTicksElapsed);
        break;
      default:
        break;

    }
    updateVisibleShapes(this.currentTicksElapsed);
    view.refresh();
  }

  /**
   * Loops through each animation and performs the animations that occur at the current tick.
   *
   * @param ticksElapsed the amount of ticks that have passed by in the animation
   */
  private void animate(int ticksElapsed) {
    for (IAnimation animation : this.animations) {
      if (ticksElapsed >= animation.getStartTime() && ticksElapsed <= animation.getEndTime()) {
        animation.animate(ticksElapsed);
      }
    }
  }

  /**
   * Updates the animation state and resumes the stopwatch as necessary.
   */
  public void handleResume() {
    this.state = AnimationState.IN_PROGRESS;
    this.stopwatch.resume();
  }

  /**
   * Restarts the animation by resetting the shapes to their original states and restarting the
   * stopwatch.
   */
  private void handleRestart() {
    this.state = AnimationState.IN_PROGRESS;
    this.resetShapes();
    this.visibleShapes.clear();
    this.stopwatch.stop();
    this.stopwatch.start();
  }

  @Override
  public void startAnimation() {
    timer.start();
    this.stopwatch.start();
  }

  @Override
  public void onStartClicked() {
    if (this.state == AnimationState.NOT_STARTED) {
      this.state = AnimationState.IN_PROGRESS;
      this.startAnimation();
    }
  }

  @Override
  public void onPauseClicked() {
    if (this.state == AnimationState.IN_PROGRESS) {
      this.state = AnimationState.PAUSED;
    }
  }

  @Override
  public void onResumeClicked() {
    if (this.state == AnimationState.PAUSED) {
      handleResume();
    }
  }

  @Override
  public void onRestartClicked() {
    if (this.state != AnimationState.NOT_STARTED) {
      this.handleRestart();
    }
  }

  @Override
  public void onLoopingChanged(boolean selected) {
    this.looping = selected;
  }

  @Override
  public void onTempoChanged(double tempo) {
    this.stopwatch.alterStartTime(this.tempo / tempo);
    this.tempo = tempo;
    view.updateTempoLabel(tempo);
  }

  @Override
  public void onShapeVisibilityChanged(IShape shape, boolean selected) {
    shapeVisibilityMap.put(shape, selected);
  }

  /**
   * Resets the list of shapes back to their original states.
   */
  private void resetShapes() {
    for (IShape shape : shapes) {
      shape.reset();
    }
  }

  @Override
  public void onExportClicked(String filename) {
    if (this.state != AnimationState.NOT_STARTED) {
      handleRestart();
    }

    Writer out = null;
    try {
      out = new BufferedWriter(new FileWriter("outputFiles/" + filename));
      IView svgView = new SVGView(animations, shapes, tempo, out);
      svgView.writeAnimatorDescription();
      out.close();
    } catch (IOException e) {
      view.showExportError(e.getMessage());
    }
  }

  @Override
  public void onTickChanged(double tickAsDouble) {
    int tick = NumUtil.round(tickAsDouble);
    if (this.currentTicksElapsed == 0) {
      stopwatch.alterStartTime(0);
    }
    else {
      stopwatch.alterStartTime(tickAsDouble / (double) this.currentTicksElapsed);
    }
  }

  @Override
  public void onScrubberPressed() {
    if (this.state == AnimationState.IN_PROGRESS) {
      this.resumeOnRelease = true;
    }
    else {
      this.resumeOnRelease = false;
    }

    this.state = AnimationState.PAUSED;
  }

  @Override
  public void onScrubberReleased() {
    if (this.resumeOnRelease) {
      handleResume();
    }
  }
}
