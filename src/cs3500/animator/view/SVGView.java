package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.model.Color;
import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.model.shape.Oval;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.util.NumUtil;

/**
 * This class represents an SVG implementation of a view that can print out a string
 * XML-style description of the shapes/animations in the SVG format. See
 * https://www.w3.org/TR/SVG11/ for more information on the SVG format.
 */
public class SVGView extends TextualView {


  private Map<Rectangle,List<String>> rectangles = new HashMap<>();
  private Map<Oval,List<String>> ovals = new HashMap<>();

  /**
   * Constructs a SVGView with the given animations.
   * @param animations the animations associated with this view
   * @param tempo the speed of the animation in ticks per second
   * @param out the appendable object to append the animation to
   */
  public SVGView(List<AbstractAnimation> animations, double tempo, Appendable out) {
    super(animations, tempo, out);

    for (AbstractAnimation animation : animations) {
      AbstractShape currentShape = animation.getShape();
      if (currentShape instanceof Rectangle
              && !rectangles.containsKey(currentShape)) {
        rectangles.put((Rectangle) currentShape, new ArrayList<String>());
      }
      else if (currentShape instanceof Oval
              && !ovals.containsKey(currentShape)) {
        ovals.put((Oval) currentShape, new ArrayList<String>());
      }
    }

    String animatorDescription = super.getAnimatorDescription();
    String[] animationStrings = animatorDescription.split("Shape ");
    for (int i = 1; i < animationStrings.length; i++) {
      String shapeName = animationStrings[i].split(" ")[0];

      for (Rectangle rect : rectangles.keySet()) {
        if (shapeName.equals(rect.getName())) {
          List<String> animationDescriptors = rectangles.get(rect);
          animationDescriptors.add(animationStrings[i]);
        }
      }
      for (Oval oval : ovals.keySet()) {
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
    
    for (Rectangle rect : rectangles.keySet()) {
      builder.append("<rect id=\"")
              .append(rect.getName())
              .append("\" x=\"")
              .append(NumUtil.round(rect.getLocation().getX()))
              .append("\" y=\"")
              .append(NumUtil.round(rect.getLocation().getY()))
              .append("\" width=\"")
              .append(NumUtil.round(rect.getWidth()))
              .append("\" height=\"")
              .append(NumUtil.round(rect.getHeight()))
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

    for (Oval oval : ovals.keySet()) {
      builder.append("<ellipse id=\"")
              .append(oval.getName())
              .append("\" cx=\"")
              .append(NumUtil.round(oval.getLocation().getX()))
              .append("\" cy=\"")
              .append(NumUtil.round(oval.getLocation().getY()))
              .append("\" rx=\"")
              .append(NumUtil.round(oval.getRadiusX()))
              .append("\" ry=\"")
              .append(NumUtil.round(oval.getRadiusY()))
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
    
    return builder.toString();
  }

  /**
   * Produces an SVG description of a single animation instance on a shape.
   *
   * @param animationTextual the texutal animation description to produce the SVG string from
   * @param locationXParam the attribute name of the shape's X coordinate
   * @param locationYParam the attribute name of the shape's Y coordinate
   * @param widthParam the attribute name of the shape's width
   * @param heightParam the attribute name of the shape's height
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
      Color startColor = Color.parseString(startColorString);
      Color endColor = Color.parseString(endColorString);

      builder.append("fill\"")
              .append(" from=\"")
              .append(startColor.toSVG())
              .append("\" to=\"")
              .append(endColor.toSVG())
              .append("\"");
    }
    else if (animationTextual.contains("moves")) {
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
      }
      else if (!startXPosition.equals(endXPosition)) {
        builder.append(locationXParam)
                .append("\" from=\"")
                .append(startXPosition)
                .append("\" to=\"")
                .append(endXPosition)
                .append("\"");
      }
      else if (!startYPosition.equals(endYPosition)) {
        builder.append(locationYParam)
                .append("\" from=\"")
                .append(startYPosition)
                .append("\" to=\"")
                .append(endYPosition)
                .append("\"");
      }
    }
    else if (animationTextual.contains("scale")) {
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
      }
      else if (!startWidth.equals(endWidth)) {
        builder.append(widthParam)
                .append("\" from=\"")
                .append(startWidth)
                .append("\" to=\"")
                .append(endWidth)
                .append("\"");
      }
      else if (!startHeight.equals(endHeight)) {
        builder.append(heightParam)
                .append("\" from=\"")
                .append(startHeight)
                .append("\" to=\"")
                .append(endHeight)
                .append("\"");
      }
    }
    builder.append(" fill=\"freeze\" />");

    return builder.toString();
  }

  @Override
  public void writeAnimatorDescription() throws IOException {
    this.out.append(getAnimatorDescription());
  }
}
