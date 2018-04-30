package cs3500.animator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cs3500.animator.controller.IInteractiveController;
import cs3500.animator.object.animation.IAnimation;
import cs3500.animator.object.shape.IShape;
import cs3500.animator.util.NumUtil;

/**
 * This class represents a hybrid view of the animations and shapes. The animation will be
 * played inside a window. Each animation will be displayed as specified by the list of
 * animations and according the the tempo. The user will also be provided with the option to
 * export the view to an svg file.
 */
public class HybridView extends AbstractView implements IInteractiveView {

  private JPanel shapePanel;
  private JSlider tempoSlider;
  private JSlider scrubber;
  private JLabel tempoSliderText;
  private JLabel scrubberText;
  private JTextField svgFileName;

  /**
   * Constructs a HybridView with the given animations.
   *
   * @param animations the animations associated with this view
   * @param shapes     the shapes associated with this view
   * @param shapeOrder the mapping describing the ordering for each shape
   * @param tempo      the speed of the animation in ticks per second
   */
  public HybridView(List<IAnimation> animations, List<IShape> shapes,
                    Map<IShape, Integer> shapeOrder, double tempo) {
    super(animations, shapes, tempo);
  }

  @Override
  public void animate() {
    this.setVisible(true);
  }

  @Override
  public void setController(IInteractiveController controller) {
    JScrollPane scrollPane;
    JScrollPane checkboxScrollPane;
    JButton start;
    JButton pause;
    JButton resume;
    JButton restart;
    JButton exportToSVG;
    JCheckBox loopCheckBox;
    JPanel buttonPanel;
    JPanel scrubberPanel;
    JPanel shapeCheckboxPanel;
    Map<JCheckBox, IShape> shapeCheckboxes;
    this.setTitle("Shapes!");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    // create a borderlayout with drawing panel in center
    this.setLayout(new BorderLayout());
    shapePanel = new ShapePanel(controller.getVisibleShapes());
    initializeShapePanelSize();
    scrollPane = new JScrollPane(shapePanel);
    scrollPane.setPreferredSize(new Dimension(PANEL_WIDTH + 250, PANEL_HEIGHT + 250));

    this.add(scrollPane, BorderLayout.CENTER);
    start = new JButton("Start");
    pause = new JButton("Pause");
    resume = new JButton("Resume");
    restart = new JButton("Restart");
    loopCheckBox = new JCheckBox("Loop", false);
    tempoSliderText = new JLabel("Tempo: " + String.valueOf(tempo));
    DefaultBoundedRangeModel tempoSliderModel = new DefaultBoundedRangeModel((int) tempo, 0, 1,
            Integer.max(100, (int) tempo));
    this.tempoSlider = new JSlider(tempoSliderModel);

    start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        scrubber.setEnabled(true);
        controller.onStartClicked();
      }
    });
    pause.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.onPauseClicked();
      }
    });
    resume.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.onResumeClicked();
      }
    });
    restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.onRestartClicked();
      }
    });
    loopCheckBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.onLoopingChanged(loopCheckBox.isSelected());
      }
    });
    this.tempoSlider.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        controller.onTempoChanged(tempoSlider.getValue());
      }
    });
    buttonPanel = new JPanel();
    buttonPanel.add(start);
    buttonPanel.add(pause);
    buttonPanel.add(resume);
    buttonPanel.add(restart);
    buttonPanel.add(loopCheckBox);
    buttonPanel.add(tempoSlider);
    buttonPanel.add(tempoSliderText);
    this.add(buttonPanel, BorderLayout.SOUTH);

    // shape loopCheckBox
    shapeCheckboxPanel = new JPanel();
    shapeCheckboxPanel.setLayout(new BoxLayout(shapeCheckboxPanel, BoxLayout.Y_AXIS));
    shapeCheckboxes = new HashMap<JCheckBox, IShape>();
    for (IShape shape : shapes) {
      JCheckBox currentCheckBox = new JCheckBox(shape.getName(), true);
      shapeCheckboxes.put(currentCheckBox, shape);
      shapeCheckboxPanel.add(currentCheckBox);
      currentCheckBox.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          controller.onShapeVisibilityChanged(shape, currentCheckBox.isSelected());
        }
      });
    }

    checkboxScrollPane = new JScrollPane(shapeCheckboxPanel);
    checkboxScrollPane.setPreferredSize(new Dimension(150, scrollPane.getHeight()));
    this.add(checkboxScrollPane, BorderLayout.EAST);

    // export to svg
    this.svgFileName = new JTextField("Enter SVG Filename.");
    buttonPanel.add(this.svgFileName);
    exportToSVG = new JButton("Export to SVG");
    exportToSVG.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        controller.onExportClicked(svgFileName.getText());
      }
    });
    buttonPanel.add(exportToSVG);

    // scubber panel
    scrubberPanel = new JPanel();
    DefaultBoundedRangeModel scrubberModel = new DefaultBoundedRangeModel(1, 0, 1, controller.calculateEndTime() - 1);
    this.scrubber = new JSlider(scrubberModel);
    scrubberPanel.add(scrubber);
    scrubberText = new JLabel("Current tick: 0");
    scrubberPanel.add(scrubberText);
    this.add(scrubberPanel, BorderLayout.NORTH);
    this.scrubber.setEnabled(false);
    this.scrubber.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        int scrubberValue = scrubber.getValue();

        if (scrubberValue < scrubber.getMinimum()) {
          scrubberValue = scrubber.getMinimum();
        }
        else if (scrubberValue > scrubber.getMaximum()) {
          scrubberValue = scrubber.getMaximum();
        }

        controller.onTickChanged(scrubberValue);
      }
    });
    this.scrubber.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        // do nothing
      }

      @Override
      public void mousePressed(MouseEvent e) {
        if (scrubber.isEnabled()) {
          controller.onScrubberPressed();
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        if (scrubber.isEnabled()) {
          controller.onScrubberReleased();
        }
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        // do nothing
      }

      @Override
      public void mouseExited(MouseEvent e) {
        // do nothing
      }
    });

    this.pack();
    this.animate();
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

  @Override
  public void updateTempoLabel(double tempo) {
    this.tempoSliderText.setText("Tempo: " + String.valueOf(tempo));
  }

  @Override
  public void updateScrubberLabel(int tick) {
    this.scrubber.setValue(tick);
    this.scrubberText.setText("Current tick: " + String.valueOf(tick));
  }

  @Override
  public void showExportError(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}
