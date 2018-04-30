package cs3500.animator.view;

import java.awt.*;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import cs3500.animator.controller.IVisualController;
import cs3500.animator.object.animation.IAnimation;
import cs3500.animator.object.shape.IShape;
import cs3500.animator.util.NumUtil;

/**
 * This class represents a visual view of the animations and shapes. The animation will be
 * played inside a window. Each animation will be displayed as specified by the list of
 * animations and according the the tempo.
 */
public class VisualView extends AbstractView {
  private JPanel shapePanel;

  /**
   * Constructs a VisualView with the given animations.
   *
   * @param animations the animations associated with this view
   * @param shapes     the shapes associated with this view
   * @param shapeOrder the mapping describing the ordering for each shape
   * @param tempo      the speed of the animation in ticks per second
   */
  public VisualView(List<IAnimation> animations, List<IShape> shapes,
                    Map<IShape, Integer> shapeOrder, double tempo) {
    super(animations, shapes, tempo);

    this.setTitle("Shapes!");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // create a borderlayout with drawing panel in center
    this.setLayout(new BorderLayout());
  }

  @Override
  public void animate() {
    this.setVisible(true);
  }

  @Override
  public void setController(IVisualController controller) {
    shapePanel = new ShapePanel(controller.getVisibleShapes());
    initializeShapePanelSize();

    JScrollPane scrollPane;
    scrollPane = new JScrollPane(shapePanel);
    scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH + 250, PANEL_HEIGHT + 250));
    this.add(scrollPane, BorderLayout.CENTER);

    this.pack();
    this.animate();

    controller.startAnimation();
  }

  /**
   * Determines the optimal size for the shape panel based on the shapes and where they move. Sets
   * this calculated size as the preferred size of the shape panel.
   */
  private void initializeShapePanelSize() {
    int maxWidth = PANEL_WIDTH;
    int maxHeight = PANEL_HEIGHT;

    for (IShape shape : shapes) {
      maxWidth = Integer.max(maxWidth, NumUtil.round(shape.getLocation().getX()));
      maxHeight = Integer.max(maxHeight, NumUtil.round(shape.getLocation().getY()));
    }

    for (IAnimation animation : animations) {
      if (animation.getType().equalsIgnoreCase("move")) {
        maxWidth = Integer.max(maxWidth, NumUtil.round(animation.getEndPosition().getX()));
        maxHeight = Integer.max(maxHeight, NumUtil.round(animation.getEndPosition().getY()));
      }
    }

    shapePanel.setPreferredSize(new Dimension(maxWidth + 200, maxHeight + 200));
  }
}
