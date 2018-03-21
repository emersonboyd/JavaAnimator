package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cs3500.animator.controller.IVisualController;
import cs3500.animator.controller.VisualViewController;
import cs3500.animator.model.animation.AbstractAnimation;
import cs3500.animator.model.animation.Move;
import cs3500.animator.model.shape.AbstractShape;
import cs3500.animator.util.NumUtil;

/**
 * This class represents a visual view of the animations and shapes. The animation will be
 * played inside a window. Each animation will be displayed as specified by the list of
 * animations and according the the tempo.
 */
public class VisualView extends AbstractView {
  private JPanel shapePanel;

  private IVisualController controller;

  /**
   * Constructs a VisualView with the given animations.
   * @param animations the animations associated with this view
   * @param shapes the shapes associated with this view
   * @param tempo the speed of the animation in ticks per second
   */
  public VisualView(List<AbstractAnimation> animations, List<AbstractShape> shapes, double tempo) {
    super(animations, shapes, tempo);
    JScrollPane scrollPane;

    this.controller = new VisualViewController(this, animations, shapes, tempo);

    this.setTitle("Shapes!");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // create a borderlayout with drawing panel in center
    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel(controller.getVisibleShapes());
    initializeShapePanelSize();
    scrollPane = new JScrollPane(shapePanel);
    scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH + 250, PANEL_HEIGHT + 250));

    this.add(scrollPane,BorderLayout.CENTER);

    this.pack();
    this.animate();
  }

  @Override
  public void animate() {
    this.setVisible(true);
    this.controller.startAnimation();
  }

  /**
   * Determines the optimal size for the shape panel based on the shapes and where they move. Sets
   * this calculated size as the preferred size of the shape panel.
   */
  private void initializeShapePanelSize() {
    int maxWidth = PANEL_WIDTH;
    int maxHeight = PANEL_HEIGHT;

    for (AbstractShape shape : shapes) {
      maxWidth = Integer.max(maxWidth, NumUtil.round(shape.getLocation().getX()));
      maxHeight = Integer.max(maxHeight, NumUtil.round(shape.getLocation().getY()));
    }

    for (AbstractAnimation animation : animations) {
      if (animation instanceof Move) {
        Move move = (Move) animation;
        maxWidth = Integer.max(maxWidth, NumUtil.round(move.getDestination().getX()));
        maxHeight = Integer.max(maxHeight, NumUtil.round(move.getDestination().getY()));
      }
    }

    shapePanel.setPreferredSize(new Dimension(maxWidth + 200, maxHeight + 200));
  }

}
