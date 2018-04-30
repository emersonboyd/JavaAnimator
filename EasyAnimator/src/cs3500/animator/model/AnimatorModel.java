package cs3500.animator.model;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs3500.animator.object.Color;
import cs3500.animator.object.Posn;
import cs3500.animator.object.animation.ChangeColor;
import cs3500.animator.object.animation.IAnimation;
import cs3500.animator.object.animation.Move;
import cs3500.animator.object.animation.Rotation;
import cs3500.animator.object.animation.Scale;
import cs3500.animator.object.shape.IShape;
import cs3500.animator.object.shape.Oval;
import cs3500.animator.object.shape.Rectangle;
import cs3500.animator.util.TweenModelBuilder;

/**
 * Represents an implementation of the IAnimatorModel interface that contains a list of animations
 * and determines the shapes available by parsing through that list of animations.
 */
public class AnimatorModel implements IAnimatorModel {
  private List<IAnimation> animations;
  private List<IShape> shapes;
  private Map<IShape, Integer> shapeOrder;

  /**
   * Constructs a default AnimatorModel with an empty list of animations.
   */
  public AnimatorModel() {
    this.animations = new ArrayList<IAnimation>();
    this.shapes = new ArrayList<IShape>();
    this.shapeOrder = new HashMap<>();
  }

  @Override
  public void addAnimation(IAnimation anim) throws IllegalArgumentException {
    // check if this animation conflicts with any other animation currently in the stack
    for (IAnimation animation : animations) {
      if (anim.sameType(animation) &&
              anim.getShape().equals(animation.getShape()) &&
              anim.getStartTime() < animation.getEndTime() &&
              anim.getEndTime() > animation.getStartTime()) {
        throw new IllegalArgumentException(IAnimatorModel.ERROR_CONFLICTING_ANIMATION);
      }
    }

    this.animations.add(anim);

    if (!containsShapeWithName(anim.getShape().getName())) {
      this.shapes.add(anim.getShape());
    }
  }

  @Override
  public String getAnimatorDescription() {
    Collections.sort(animations);
    List<IShape> shapeCopies = getShapeCopies();

    StringBuilder builder = new StringBuilder();
    builder.append("Shapes:");

    for (IShape shape : shapeCopies) {
      builder.append("\n")
              .append(shape.toString())
              .append("\n");
    }

    for (IAnimation animation : animations) {
      IShape animationShape = animation.getShape();
      IShape animationShapeCopy = null;

      for (IShape shapeCopy : shapeCopies) {
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
  private List<IShape> getShapeCopies() {
    List<IShape> newShapes = new ArrayList<IShape>();
    List<IShape> shapesTracker = new ArrayList<IShape>();

    for (IAnimation animation : animations) {
      IShape tempShape = animation.getShape();

      if (!shapesTracker.contains(tempShape)) {
        newShapes.add(tempShape.clone());
        shapesTracker.add(tempShape);
      }
    }

    Collections.sort(newShapes);

    return newShapes;
  }

  @Override
  public List<IAnimation> getAnimations() {
    return new ArrayList<>(this.animations);
  }

  @Override
  public List<IShape> getShapes() {
    return new ArrayList<>(this.shapes);
  }

  @Override
  public void setShapeOrder(Map<IShape, Integer> shapeOrder) {
    this.shapeOrder = shapeOrder;
  }

  @Override
  public Map<IShape, Integer> getShapeOrder() {
    return new HashMap<>(this.shapeOrder);
  }

  /**
   * Represents a builder class to construct an animation model from an outside source.
   */
  public static final class Builder implements TweenModelBuilder<IAnimatorModel> {

    private List<IShape> shapes;
    private List<IAnimation> animations;
    private int currentLayer;
    private Map<IShape, Integer> layers;

    /**
     * Constructs a new builder object that in turn initializes the lists of shapes
     * and animations to be added to the model.
     */
    public Builder() {
      this.shapes = new ArrayList<>();
      this.animations = new ArrayList<>();
      this.currentLayer = 0;
      this.layers = new HashMap<>();
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addOval(
            String name,
            float cx, float cy,
            float xRadius, float yRadius,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {
      IShape oval = new Oval(startOfLife, endOfLife, name, new cs3500.animator.object.Posn(cx, cy),
              new cs3500.animator.object.Color(red, green, blue), xRadius, yRadius);
      this.shapes.add(oval);
      this.layers.put(oval, currentLayer);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRectangle(
            String name,
            float lx, float ly,
            float width, float height,
            float red, float green, float blue,
            int startOfLife, int endOfLife) {
      IShape rect = new Rectangle(startOfLife, endOfLife, name,
              new cs3500.animator.object.Posn(lx, ly),
              new cs3500.animator.object.Color(red, green, blue), width, height);
      this.shapes.add(rect);
      this.layers.put(rect, currentLayer);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addMove(
            String name,
            float moveFromX, float moveFromY, float moveToX, float moveToY,
            int startTime, int endTime) {
      IAnimation move = new Move(startTime, endTime, this.getShapeWithName(name),
              new Posn(moveFromX, moveFromY), new Posn(moveToX, moveToY));
      this.animations.add(move);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addColorChange(
            String name,
            float oldR, float oldG, float oldB, float newR, float newG, float newB,
            int startTime, int endTime) {
      IAnimation colorChange = new ChangeColor(startTime, endTime,
              this.getShapeWithName(name), new Color(oldR, oldG, oldB),
              new Color(newR, newG, newB));
      this.animations.add(colorChange);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addScaleToChange(String name, float fromSx, float
            fromSy, float toSx, float toSy, int startTime, int endTime) {
      IAnimation scale = new Scale(startTime, endTime,
              this.getShapeWithName(name), fromSx, fromSy, toSx, toSy);
      this.animations.add(scale);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRotation(String name, float fromTheta,
                                                         float toTheta, int startTime,
                                                         int endTime) {
      IAnimation rotation = new Rotation(startTime, endTime,
              this.getShapeWithName(name), fromTheta, toTheta);
      this.animations.add(rotation);
      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> setCurrentLayer(int layer) {
      this.currentLayer = layer;
      return this;
    }

    @Override
    public IAnimatorModel build() {
      IAnimatorModel model = new AnimatorModel();
      for (IAnimation animation : this.animations) {
        model.addAnimation(animation);
      }

      List<IShape> modelShapes = model.getShapes();
      for (IShape shape : shapes) {
        if (!modelShapes.contains(shape)) {
          IAnimation emptyAnimation = new Scale(shape.getStartTime(), shape.getEndTime(),
                  shape, shape.getSizeX(), shape.getSizeY(), shape.getSizeX(), shape.getSizeY());
          model.addAnimation(emptyAnimation);
        }
      }

      // create a set of all of the layers in numerical order
      List<Integer> layerNumberList = new ArrayList<>();
      Map<IShape, Integer> shapeOrder = new HashMap<IShape, Integer>();
      for (Integer layerNumber : layers.values()) {
        if (!layerNumberList.contains(layerNumber)) {
          layerNumberList.add(layerNumber);
        }
      }
      Collections.sort(layerNumberList);

      // put the shapes in order according to their layer
      int currentShapeOrder = 0;
      for (Integer layerNumber : layerNumberList) {
        for (IShape shape : shapes) {
          if (layerNumber.equals(layers.get(shape))) {
            shapeOrder.put(shape, currentShapeOrder);
            currentShapeOrder++;
          }
        }
      }

      model.setShapeOrder(shapeOrder);

      return model;
    }

    /**
     * Searches this list of shapes for a shape given it's name.
     *
     * @param name the name of the shape being searched for
     * @return the shape being looked for
     * @throws IllegalArgumentException if the shape is not in the list
     */
    private IShape getShapeWithName(String name) {
      for (IShape shape : shapes) {
        if (shape.getName().equals(name)) {
          return shape;
        }
      }
      throw new IllegalArgumentException("Shape is not in the list.");
    }
  }

  @Override
  public void removeShapeByIndex(int shapeIndex) {
    this.shapes.remove(shapeIndex);
  }

  @Override
  public void removeAnimationByIndex(int animationIndex) {
    this.animations.remove(animationIndex);
  }


  @Override
  public void addShape(IShape newShape) {
    if (!this.containsShapeWithName(newShape.getName())) {
      shapes.add(newShape);
    }
  }

  private boolean containsShapeWithName(String shapeName) {
    for (IShape shape : shapes) {
      if (shape.getName().equals(shapeName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void removeAnimationByShapeName(String name) {
    for (int i = 0; i < animations.size(); i++) {
      if (animations.get(i).getShape().getName().equals(name)) {
        animations.remove(i);
        i--;
      }
    }
  }


}
