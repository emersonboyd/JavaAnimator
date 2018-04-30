package cs3500.animator.object.animation;

import cs3500.animator.object.Posn;
import cs3500.animator.object.shape.IShape;

import static cs3500.animator.object.animation.Move.ERROR_DESTINATION_NULL;

/**
 * Represents a rotation animation to rotate an object to a destination rotation.
 */
public class Rotation extends AbstractAnimation{
  private double startTheta;
  private double endTheta;

  public Rotation(int startTime, int endTime, IShape shape, double startTheta,
                  double endTheta) throws IllegalArgumentException {
    super(startTime, endTime, shape);

    this.startTheta = startTheta;
    this.endTheta = endTheta;
  }

  @Override
  public void animate(IShape s) {
    s.setRotation(endTheta);
  }

  @Override
  public void animate(int ticksElapsed) {
    double newTheta = this.startTheta * this.getStartCoef(ticksElapsed)
            + this.endTheta * this.getEndCoef(ticksElapsed);
    shape.setRotation(newTheta);
  }

  @Override
  public boolean sameType(IAnimation other) {
    return other instanceof Rotation;
  }

  @Override
  public String getType() {
    return "rotation";
  }

  @Override
  public String getAction(IShape s) {
    StringBuilder builder = new StringBuilder();

    builder.append("rotates from ")
            .append(s.getRotationDescription())
            .append(" to ")
            .append(s.getRotationDescriptionWithTheta(this.endTheta));

    return builder.toString();
  }

  @Override
  public double getStartTheta() {
    return this.startTheta;
  }

  @Override
  public double getEndTheta() {
    return this.endTheta;
  }
}
