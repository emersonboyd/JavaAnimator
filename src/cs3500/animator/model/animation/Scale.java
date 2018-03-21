package cs3500.animator.model.animation;

import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;

/**
 * Represents a scaling animation to resize an object based on a scaling factor.
 */
public class Scale extends AbstractAnimation {
  public static final String ERROR_SCALE_FACTOR_BOUNDS =
          "The scale factor must be greater than 0.";

  private double scaleX;
  private double scaleY;
  private double startWidth;
  private double endWidth;
  private double startHeight;
  private double endHeight;

  /**
   * Constructs an scale animation with a given scale factor.
   *
   * @param startTime the time to show the object
   * @param endTime   the time to hide the object
   * @param shape     the shape to apply the animation to
   * @param scaleX    the factor to scale the shape to in the x direction
   * @param scaleY    the factor to scale the shape to in the y direction
   * @throws IllegalArgumentException if the either scaleX or scaleY is null
   */
  public Scale(int startTime, int endTime, AbstractShape shape, double scaleX, double scaleY)
          throws IllegalArgumentException {
    super(startTime, endTime, shape);

    if (scaleX <= 0 || scaleY <= 0) {
      throw new IllegalArgumentException(ERROR_SCALE_FACTOR_BOUNDS);
    }

    this.scaleX = scaleX;
    this.scaleY = scaleY;
  }

  /**
   * A getter for the x scale factor.
   *
   * @return the x scale factor value
   */
  public double getScaleX() {
    return this.scaleX;
  }

  /**
   * A getter for the y scale factor.
   *
   * @return the y scale factor value
   */
  public double getScaleY() {
    return this.scaleY;
  }

  @Override
  public void animate(AbstractShape s) {
    s.scale(this.scaleX, this.scaleY);
  }

  @Override
  public void animate(int ticksElapsed) {
    if (!this.animationStarted) {
      if (this.shape instanceof Rectangle) {
        Rectangle rect = (Rectangle) this.shape;
        this.startWidth = rect.getWidth();
        this.startHeight = rect.getHeight();
      }
      else if (this.shape instanceof Oval) {
        Oval oval = (Oval) this.shape;
        this.startWidth = oval.getRadiusX();
        this.startHeight = oval.getRadiusY();

      }
      this.endWidth = this.startWidth * this.scaleX;
      this.endHeight = this.startHeight * this.scaleY;
      this.animationStarted = true;
    }
    double newWidth = this.startWidth * this.getStartCoef(ticksElapsed)
            + this.endWidth * this.getEndCoef(ticksElapsed);
    double newHeight = this.startHeight * this.getStartCoef(ticksElapsed)
            + this.endHeight * this.getEndCoef(ticksElapsed);
    shape.updateSize(newWidth, newHeight);
  }

  @Override
  public boolean sameType(AbstractAnimation other) {
    return other instanceof Scale;
  }

  @Override
  public String getAction(AbstractShape s) {
    StringBuilder builder = new StringBuilder();

    builder.append("scales from ")
            .append(s.getSizeDescription())
            .append(" to ")
            .append(s.getSizeDescriptionWithScale(scaleX, scaleY));

    return builder.toString();
  }
}
