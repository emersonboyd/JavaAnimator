package cs3500.hw05.animator.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import cs3500.hw05.animator.model.animation.AbstractAnimation;
import cs3500.hw05.animator.model.shape.AbstractShape;

/**
 * This class represents a textual implementation of a view that can print out a string
 * description of the shapes and animations.
 */
public class TextualView extends AbstractView {

  /**
   * Constructs a TextualView with the given animations.
   * @param animations the animations associated with this view
   * @param tempo the speed of the animation in ticks per second
   */
  public TextualView(List<AbstractAnimation> animations, double tempo) {
    super(animations, tempo);
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
//    while (tempString.indexOf(" t=", offset) != -1) {
//      offset = tempString.indexOf(" t=", offset);
//      int startTimeIndex = offset + 3;
//      int endTimeIndex = tempString.indexOf(" ", offset + 1);
//
//      String timeToModify = tempString.substring(startTimeIndex, endTimeIndex);
//      int ticks = Integer.valueOf(timeToModify);
//      double secs = ticks / tempo;
//
//      tempString = tempString.substring(0, startTimeIndex)
//              + secs
//              + "s"
//              + tempString.substring(endTimeIndex, tempString.length());
//    }

    return timeBuilder.toString();
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
