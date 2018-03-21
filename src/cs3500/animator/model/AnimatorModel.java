package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.animation.ChangeColor;
import cs3500.animator.model.animation.Move;
import cs3500.animator.model.animation.Scale;
import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.util.TweenModelBuilder;

/**
 * Represents an implementation of the IAnimatorModel interface that contains a list of animations
 * and determines the shapes available by parsing through that list of animations.
 */
public class AnimatorModel implements IAnimatorModel {
  private List<AbstractAnimation> animations;
  private List<AbstractShape> shapes;

  /**
   * Constructs a default AnimatorModel with an empty list of animations.
   */
  public AnimatorModel() {
    this.animations = new ArrayList<AbstractAnimation>();
    this.shapes = new ArrayList<AbstractShape>();
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

    if (!this.shapes.contains(anim.getShape())) {
      this.shapes.add(anim.getShape());
    }
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

  @Override
  public List<AbstractAnimation> getAnimations() {
    return new ArrayList<>(this.animations);
  }

  @Override
  public List<AbstractShape> getShapes() {
    return new ArrayList<>(this.shapes);
  }

  /**
   * Represents a builder class to construct an animation model from an outside source.
   */
  public static final class Builder implements TweenModelBuilder<IAnimatorModel> {

    private List<AbstractShape> shapes;
    private List<AbstractAnimation> animations;

    /**
     * Constructs a new builder object that in turn initializes the lists of shapes
     * and animations to be added to the model.
     */
    public Builder() {
      this.shapes = new ArrayList<>();
      this.animations = new ArrayList<>();
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addOval(
            String name,
            float cx, float cy,
            float xRadius, float yRadius,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {
      AbstractShape oval = new Oval(startOfLife, endOfLife, name, new Posn(cx, cy),
              new Color(red, green, blue), xRadius, yRadius);
      this.shapes.add(oval);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRectangle(
            String name,
            float lx, float ly,
            float width, float height,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {
      AbstractShape rect = new Rectangle(startOfLife, endOfLife, name, new Posn(lx, ly),
              new Color(red, green, blue), width, height);
      this.shapes.add(rect);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addMove(
            String name,
            float moveFromX, float moveFromY, float moveToX, float moveToY,
            int startTime, int endTime) {
      AbstractAnimation move = new Move(startTime, endTime, this.getShapeWithName(name),
              new Posn(moveToX, moveToY));
      this.animations.add(move);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addColorChange(
            String name,
            float oldR, float oldG, float oldB, float newR, float newG, float newB,
            int startTime, int endTime) {
      AbstractAnimation colorChange = new ChangeColor(startTime, endTime,
              this.getShapeWithName(name), new Color(newR, newG, newB));
      this.animations.add(colorChange);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addScaleToChange(String name, float fromSx, float
            fromSy, float toSx, float toSy, int startTime, int endTime) {
      AbstractAnimation scale = new Scale(startTime, endTime,
              this.getShapeWithName(name), toSx / fromSx, toSy / fromSy);
      this.animations.add(scale);
      return this;
    }

    @Override
    public IAnimatorModel build() {
      IAnimatorModel model = new AnimatorModel();
      for (AbstractAnimation animation : this.animations) {
        model.addAnimation(animation);
      }
      return model;
    }

    /**
     * Searches this list of shapes for a shape given it's name.
     *
     * @param name the name of the shape being searched for
     * @return the shape being looked for
     * @throws IllegalArgumentException if the shape is not in the list
     */
    private AbstractShape getShapeWithName(String name) {
      for (AbstractShape shape : shapes) {
        if (shape.getName().equals(name)) {
          return shape;
        }
      }
      throw new IllegalArgumentException("Shape is not in the list.");
    }
  }

}
