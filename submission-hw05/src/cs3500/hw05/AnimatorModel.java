package cs3500.hw05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.hw05.animator.model.animation.AbstractAnimation;
import cs3500.hw05.animator.model.shape.AbstractShape;

/**
 * Represents an implementation of the IAnimatorModel interface that contains a list of animations
 * and determines the shapes available by parsing through that list of animations.
 */
public class AnimatorModel implements IAnimatorModel {
  private List<AbstractAnimation> animations;

  /**
   * Constructs a default AnimatorModel with an empty list of animations.
   */
  public AnimatorModel() {
    this.animations = new ArrayList<AbstractAnimation>();
  }

  @Override
  public void addAnimation(AbstractAnimation anim) throws IllegalArgumentException {
    // check if this animation conflicts with any other animation currently in the stack
    for (AbstractAnimation animation : animations) {
      if (anim.sameType(animation) &&
              anim.getShape().equals(animation.getShape()) &&
              anim.getStartTime() < animation.getEndTime() &&
              anim.getEndTime() > animation.getStartTime()) {
        throw new IllegalArgumentException(IAnimatorModel.ERROR_CONFLICTING_ANIMATION);
      }
    }

    this.animations.add(anim);
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

    return builder.toString();
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
