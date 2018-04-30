package cs3500.animator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import cs3500.animator.controller.HybridViewController;
import cs3500.animator.controller.IInteractiveController;
import cs3500.animator.controller.IVisualController;
import cs3500.animator.controller.VisualViewController;
import cs3500.animator.model.AnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.util.AnimationFileReader;
import cs3500.animator.view.IInteractiveView;
import cs3500.animator.view.IView;
import cs3500.animator.view.ViewFactory;

public final class EasyAnimator {
  /**
   * Main method to run the program.
   *
   * @param args the list of command line arguments
   * @throws IOException if the file cannot be made
   */
  public static void main(String[] args) throws IOException {

    String fileName = null;
    String viewType = null;
    Double tempo = 1.0;
    String outputFileName = "outputFiles/out.txt";
    Appendable out = System.out;
    String providerName = null;
    AnimationFileReader fileReader = new AnimationFileReader();


    // string and option parsing
    if (args.length % 2 == 1) {
      throw new IllegalArgumentException("Not enough arguments.");
    }
    for (int i = 0; i < args.length; i += 2) {
      switch (args[i]) {
        case "-if":
          fileName = "inputFiles/" + args[i + 1];
          break;
        case "-iv":
          viewType = args[i + 1];
          break;
        case "-o":
          if (args[i + 1].equals("out")) {
            break;
          }
          outputFileName = "outputFiles/" + args[i + 1];
          out = new BufferedWriter(new FileWriter(outputFileName));
          break;
        case "-speed":
          tempo = Double.valueOf(args[i + 1]);
          break;
        default:
          throw new IllegalArgumentException("Invalid option.");
      }
    }

    if (fileName == null || viewType == null) {
      throw new IllegalArgumentException("Filename or View type cannot be null.");
    }

    IAnimatorModel model = fileReader.readFile(fileName, new AnimatorModel.Builder());
    IView view = ViewFactory.create(viewType, model, tempo, out);
    if (viewType.equals("visual")) {
      IVisualController controller = new VisualViewController(view, model.getAnimations(),
              model.getShapes(), model.getShapeOrder(), tempo);
      view.setController(controller);
    } else if (viewType.equals("hybrid")) {
      IInteractiveController controller = new HybridViewController((IInteractiveView) view,
              model.getAnimations(), model.getShapes(), model.getShapeOrder(), tempo);
      ((IInteractiveView) view).setController(controller);
    }

    view.writeAnimatorDescription();
    if (out instanceof Writer) {
      ((Writer) out).close();
    }
  }
}

