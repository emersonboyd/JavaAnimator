package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.object.IColor;
import cs3500.animator.object.Posn;
import cs3500.animator.object.animation.IAnimation;
import cs3500.animator.object.animation.Move;
import cs3500.animator.object.animation.Rotation;
import cs3500.animator.object.shape.IShape;
import cs3500.animator.util.NumUtil;

/**
 * This class represents an SVG implementation of a view that can print out a string
 * XML-style description of the shapes/animations in the SVG format. See
 * https://www.w3.org/TR/SVG11/ for more information on the SVG format.
 */
public class SVGView extends TextualView {


  private Map<IShape, List<String>> rectangles = new HashMap<>();
  private Map<IShape, List<String>> ovals = new HashMap<>();

  /**
   * Constructs a SVGView with the given animations.
   *
   * @param animations the animations associated with this view
   * @param shapes the shapes associated with this view
   * @param tempo      the speed of the animation in ticks per second
   * @param out        the appendable object to append the animation to
   */
  public SVGView(List<IAnimation> animations, List<IShape> shapes, double tempo, Appendable out) {
    super(animations, shapes, tempo, out);

    for (IAnimation animation : animations) {
      IShape currentShape = animation.getShape();
      if (currentShape.getType().equalsIgnoreCase("rectangle")
              && !rectangles.containsKey(currentShape)) {
        rectangles.put(currentShape, new ArrayList<String>());
      } else if (currentShape.getType().equalsIgnoreCase("oval")
              && !ovals.containsKey(currentShape)) {
        ovals.put(currentShape, new ArrayList<String>());
      }
    }

    String animatorDescription = super.getAnimatorDescription();
    String[] animationStrings = animatorDescription.split("Shape ");
    for (int i = 1; i < animationStrings.length; i++) {
      String shapeName = animationStrings[i].split(" ")[0];

      for (IShape rect : rectangles.keySet()) {
        if (shapeName.equals(rect.getName())) {
          List<String> animationDescriptors = rectangles.get(rect);
          animationDescriptors.add(animationStrings[i]);
        }
      }
      for (IShape oval : ovals.keySet()) {
        if (shapeName.equals(oval.getName())) {
          List<String> animationDescriptors = ovals.get(oval);
          animationDescriptors.add(animationStrings[i]);
        }
      }
    }
  }

  @Override
  public String getAnimatorDescription() {
    StringBuilder builder = new StringBuilder();
    builder.append("<svg width=\"10000\" height=\"10000\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">");
    builder.append("\n");

    for (IShape rect : rectangles.keySet()) {
      builder.append("<rect id=\"")
              .append(rect.getName())
              .append("\" x=\"")
              .append(NumUtil.round(rect.getLocation().getX()))
              .append("\" y=\"")
              .append(NumUtil.round(rect.getLocation().getY()))
              .append("\" width=\"")
              .append(NumUtil.round(rect.getSizeX()))
              .append("\" height=\"")
              .append(NumUtil.round(rect.getSizeY()))
              .append("\" fill=\"")
              .append(rect.getColor().toSVG())
              .append("\" visibility=\"visible\" >");
      builder.append("\n");

      for (String animationTextual : rectangles.get(rect)) {
        builder.append(buildAnimationSvg(animationTextual, "x", "y", "width", "height"));
        builder.append("\n");
      }
      builder.append("</rect>\n");
    }

    for (IShape oval : ovals.keySet()) {
      builder.append("<ellipse id=\"")
              .append(oval.getName())
              .append("\" cx=\"")
              .append(NumUtil.round(oval.getLocation().getX()))
              .append("\" cy=\"")
              .append(NumUtil.round(oval.getLocation().getY()))
              .append("\" rx=\"")
              .append(NumUtil.round(oval.getSizeX()))
              .append("\" ry=\"")
              .append(NumUtil.round(oval.getSizeY()))
              .append("\" fill=\"")
              .append(oval.getColor().toSVG())
              .append("\" visibility=\"visible\" >");
      builder.append("\n");

      for (String animationTextual : ovals.get(oval)) {
        builder.append(buildAnimationSvg(animationTextual, "cx", "cy", "rx", "ry"));
        builder.append("\n");
      }
      builder.append("</ellipse>\n");
    }

    builder.append("</svg>");

    String result = addRotationAnimations(builder.toString());

    return result;
  }

  /**
   * Produces an SVG description of a single animation instance on a shape.
   *
   * @param animationTextual the texutal animation description to produce the SVG string from
   * @param locationXParam   the attribute name of the shape's X coordinate
   * @param locationYParam   the attribute name of the shape's Y coordinate
   * @param widthParam       the attribute name of the shape's width
   * @param heightParam      the attribute name of the shape's height
   * @return the SVG description of the animation instance
   */
  private String buildAnimationSvg(String animationTextual, String locationXParam,
                                   String locationYParam, String widthParam, String heightParam) {
    StringBuilder builder = new StringBuilder();
    builder.append("    ");

    String[] timeSplit = animationTextual.split("t=");
    double beginTime = Double.valueOf(timeSplit[1].split("s")[0]);
    double endTime = Double.valueOf(timeSplit[2].split("s")[0]);
    String beginString = String.valueOf(NumUtil.round((beginTime * 1000)));
    String durationString = String.valueOf(NumUtil.round((endTime - beginTime) * 1000));

    builder.append("<animate attributeType=\"xml\"");
    builder.append(" begin=\"")
            .append(beginString)
            .append("ms\"");
    builder.append(" dur=\"")
            .append(durationString)
            .append("ms\"");

    builder.append(" attributeName=\"");

    if (animationTextual.contains("changes color")) {
      String startColorString = animationTextual.split(" ")[4];
      String endColorString = animationTextual.split(" ")[6];
      IColor startColor = IColor.parseString(startColorString);
      IColor endColor = IColor.parseString(endColorString);

      builder.append("fill\"")
              .append(" from=\"")
              .append(startColor.toSVG())
              .append("\" to=\"")
              .append(endColor.toSVG())
              .append("\"");
    } else if (animationTextual.contains("moves")) {
      String startXPosition = animationTextual.split(" \\(")[1].split(",")[0];
      String endXPosition = animationTextual.split(" \\(")[2].split(",")[0];
      String startYPosition = animationTextual.split(" \\(")[1].split(",")[1].split("\\)")[0];
      String endYPosition = animationTextual.split(" \\(")[2].split(",")[1].split("\\)")[0];

      if (!startXPosition.equals(endXPosition) && !startYPosition.equals(endYPosition)) {
        StringBuilder endBuilder = new StringBuilder(builder);

        builder.append(locationXParam)
                .append("\" from=\"")
                .append(startXPosition)
                .append("\" to=\"")
                .append(endXPosition)
                .append("\"");
        builder.append(" fill=\"freeze\" />");
        builder.append("\n");

        endBuilder.append(locationYParam)
                .append("\" from=\"")
                .append(startYPosition)
                .append("\" to=\"")
                .append(endYPosition)
                .append("\"");

        builder.append(endBuilder);
      } else if (!startXPosition.equals(endXPosition)) {
        builder.append(locationXParam)
                .append("\" from=\"")
                .append(startXPosition)
                .append("\" to=\"")
                .append(endXPosition)
                .append("\"");
      } else if (!startYPosition.equals(endYPosition)) {
        builder.append(locationYParam)
                .append("\" from=\"")
                .append(startYPosition)
                .append("\" to=\"")
                .append(endYPosition)
                .append("\"");
      }
    } else if (animationTextual.contains("scale")) {
      String startWidth = animationTextual.split(": ")[1].split(",")[0];
      String startHeight = animationTextual.split(": ")[2].split(" ")[0];
      String endWidth = animationTextual.split(": ")[3].split(",")[0];
      String endHeight = animationTextual.split(": ")[4].split(" ")[0];

      if (!startWidth.equals(endWidth) && !startHeight.equals(endHeight)) {
        StringBuilder endBuilder = new StringBuilder(builder);

        builder.append(widthParam)
                .append("\" from=\"")
                .append(startWidth)
                .append("\" to=\"")
                .append(endWidth)
                .append("\"");
        builder.append(" fill=\"freeze\" />");
        builder.append("\n");

        endBuilder.append(heightParam)
                .append("\" from=\"")
                .append(startHeight)
                .append("\" to=\"")
                .append(endHeight)
                .append("\"");

        builder.append(endBuilder);
      } else if (!startWidth.equals(endWidth)) {
        builder.append(widthParam)
                .append("\" from=\"")
                .append(startWidth)
                .append("\" to=\"")
                .append(endWidth)
                .append("\"");
      } else if (!startHeight.equals(endHeight)) {
        builder.append(heightParam)
                .append("\" from=\"")
                .append(startHeight)
                .append("\" to=\"")
                .append(endHeight)
                .append("\"");
      } else {
        builder.append("\"");
      }
    } else if (animationTextual.contains("rotate")) {
      builder.append("\"");
    }
    builder.append(" fill=\"freeze\" />");

    return builder.toString();
  }

  @Override
  public void writeAnimatorDescription() throws IOException {
    this.out.append(getAnimatorDescription());
  }

  private String addRotationAnimations(String svgString) {
    for (IShape shape : this.shapes) {
      List<IAnimation> moveAnimations = new ArrayList<>();

      for (IAnimation animation : this.animations)
      {
        if (animation.getShape().equals(shape) && animation.getType().equalsIgnoreCase("move")) {
          moveAnimations.add(animation);
        }
      }

      for (IAnimation animation : this.animations) {
        if (animation.getType().equalsIgnoreCase("rotation")
                && animation.getShape().equals(shape)) {
          IAnimation moveAnimationAtStartOfRotation
                  = calculateAnimationPriorToTime(moveAnimations, animation.getStartTime());
          IAnimation moveAnimationAtEndOfRotation
                  = calculateAnimationPriorToTime(moveAnimations, animation.getEndTime());

          Posn locationAtStartOfRotation = shape.getLocation();
          if (moveAnimationAtStartOfRotation != null) {
            locationAtStartOfRotation
                    = calculateLocationAtTime(moveAnimationAtStartOfRotation,
                    animation.getStartTime());
          }

          Posn locationAtEndOfRotation = shape.getLocation();
          if (moveAnimationAtEndOfRotation != null) {
            locationAtEndOfRotation
                    = calculateLocationAtTime(moveAnimationAtEndOfRotation,
                    animation.getEndTime());
          }

          int svgShapeLocation = svgString.indexOf("id=\"" + shape.getName() + "\"");
          int svgRotationInsertLocation = svgString.indexOf(">", svgShapeLocation) + 1;

          svgString = addRotationAnimation(svgString, svgRotationInsertLocation, animation,
                  locationAtStartOfRotation, locationAtEndOfRotation);
        }
      }
    }

    return svgString;
  }

  /**
   * Inserts a single animation tag into a to a completed svg string.
   * @param svgString the svg string to insert into
   * @param insertLocation the index at which the animation tag should be inserted
   * @param rotation the rotation animation to pull information from
   * @param startLocation the location of the shape at the start of the rotation
   * @param endLocation the location of the shape at the end of the rotation
   * @return the svg string with the inserted rotation tag
   */
  private String addRotationAnimation(String svgString, int insertLocation, IAnimation rotation,
                                      Posn startLocation, Posn endLocation) {
    IShape shape = rotation.getShape();
    double startTheta = rotation.getStartTheta();
    double endTheta = rotation.getEndTheta();

    double startX = startLocation.getX();
    double startY = startLocation.getY();
    double endX = endLocation.getX();
    double endY = endLocation.getY();

    if (rotation.getShape().getType().equalsIgnoreCase("rectangle")) {
      startX = startX + shape.getSizeX() / 2;
      startY = startY + shape.getSizeY() / 2;
      endX = endX + shape.getSizeX() / 2;
      endY = endY + shape.getSizeY() / 2;
    }

    double beginTime = rotation.getStartTime() / this.tempo;
    double endTime = rotation.getEndTime() / this.tempo;
    int beginMs = NumUtil.round(beginTime * 1000);
    int durationMs = NumUtil.round((endTime - beginTime) * 1000);

    StringBuilder rotationSvgBuilder = new StringBuilder();

    rotationSvgBuilder.append("\n    ");
    rotationSvgBuilder.append("<animateTransform attributeType=\"xml\"");
    rotationSvgBuilder.append(" begin=\"")
            .append(beginMs)
            .append("ms\"");
    rotationSvgBuilder.append(" dur=\"")
            .append(durationMs)
            .append("ms\"");

    rotationSvgBuilder.append(" attributeName=\"transform\"")
              .append(" type=\"rotate\"")
              .append(" from=\"")
              .append(startTheta)
              .append(" ")
              .append(startX)
              .append(" ")
              .append(startY)
              .append("\" to=\"")
              .append(endTheta)
              .append(" ")
              .append(endX)
              .append(" ")
              .append(endY)
              .append("\" repeatCount=\"1\"");

    rotationSvgBuilder.append(" fill=\"freeze\" />");

    svgString = svgString.substring(0, insertLocation)
            + rotationSvgBuilder.toString()
            + svgString.substring(insertLocation);

    return svgString;
  }

  /**
   * Calculates an animation that occurs immediately prior to or at the start of the given time.
   *
   * @param animations the animations to look through
   * @param time the time at/before which the animation should start
   * @return the animation that starts closest to the given time
   */
  private IAnimation calculateAnimationPriorToTime(List<IAnimation> animations, int time) {
    int currentStartTime = 0;
    IAnimation priorAnimation = null;

    for (IAnimation animation : animations) {
      if (animation.getStartTime() <= time && animation.getStartTime() > currentStartTime) {
        priorAnimation = animation;
        currentStartTime = priorAnimation.getStartTime();
      }
    }

    return priorAnimation;
  }

  /**
   * This will determine the given shape's location at a specific time during the animation without
   * actually modifying the animation/shape.
   *
   * @param moveAnimation the animation to determine the position of
   * @param time the time at which the position should be determined
   * @return the position of the shape animated on at the given time
   */
  private Posn calculateLocationAtTime(IAnimation moveAnimation, int time) {
    if (time < moveAnimation.getStartTime()) {
      return moveAnimation.getStartPosition();
    }
    else if (time >= moveAnimation.getEndTime()) {
      return moveAnimation.getEndPosition();
    }

    IShape shapeCopy = moveAnimation.getShape().clone();

    IAnimation moveAnimationCopy = new Move(moveAnimation.getStartTime(),
            moveAnimation.getEndTime(), shapeCopy, moveAnimation.getStartPosition(),
            moveAnimation.getEndPosition());

    moveAnimationCopy.animate(time);

    return shapeCopy.getLocation();
  }
}
