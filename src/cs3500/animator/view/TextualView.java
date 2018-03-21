package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.shape.AbstractShape;

/**
 * This class represents a textual implementation of a view that can print out a string
 * description of the shapes and animations.
 */
public class TextualView extends AbstractView {

  protected Appendable out;

  /**
   * Constructs a TextualView with the given animations.
   * @param animations the animations associated with this view
   * @param tempo the speed of the animation in ticks per second
   * @param out the appendable object that the animation gets added to
   */
  public TextualView(List<AbstractAnimation> animations, double tempo, Appendable out) {
    super(animations, null, tempo);
    this.out = out;
  }

  @Override
  public String getAnimatorDescription() {
    Collections.sort(animations);
    List<AbstractShape> shapeCopies = getShapeCopies();

    StringBuilder builder = new StringBuilder();
    builder.append("Shapes:");

    for (AbstractShape shape : shapeCopies) {
      builder.append("\n")
              .append(shape.toString())
              .append("\n");
    }

    for (AbstractAnimation animation : animations) {
      AbstractShape animationShape = animation.getShape();
      AbstractShape animationShapeCopy = null;

      for (AbstractShape shapeCopy : shapeCopies) {
        if (animationShape.getName().equals(shapeCopy.getName())) {
          animationShapeCopy = shapeCopy;
        }
      }

      builder.append("\n")
              .append(animation.toString(animationShapeCopy));

      animation.animate(animationShapeCopy);
    }

    String tempString = builder.toString();
    int offset = 0;
    StringBuilder timeBuilder = new StringBuilder();

    String[] splitString = tempString.split(" t=");
    timeBuilder.append(splitString[0]);


    for (int i = 1; i < splitString.length; i++) {
      String sectionString = splitString[i];

      int j = 0;
      for (j = 0; j < sectionString.length(); j++) {
        if (sectionString.charAt(j) < '0' || sectionString.charAt(j) > '9') {
          break;
        }
      }

      int ticks = Integer.valueOf(sectionString.substring(0, j));
      double time = ticks / this.tempo;

      timeBuilder
              .append(" t=")
              .append(String.format("%.1f", time))
              .append("s")
              .append(sectionString.substring(j));
    }

    return timeBuilder.toString();
  }

  @Override
  public void writeAnimatorDescription() throws IOException {
    this.out.append(getAnimatorDescription());
  }

  /**
   * Produces a sorted list of shapes of all the shapes being animated on in this model.
   * Each shape is cloned so that it can be modified.
   *
   * @return a sorted list of copied shapes
   */
  private List<AbstractShape> getShapeCopies() {
    List<AbstractShape> newShapes = new ArrayList<AbstractShape>();
    List<AbstractShape> shapesTracker = new ArrayList<AbstractShape>();

    for (AbstractAnimation animation : animations) {
      AbstractShape tempShape = animation.getShape();

      if (!shapesTracker.contains(tempShape)) {
        newShapes.add(tempShape.clone());
        shapesTracker.add(tempShape);
      }
    }

    Collections.sort(newShapes);

    return newShapes;
  }



}
