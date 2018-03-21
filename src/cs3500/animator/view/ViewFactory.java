package cs3500.animator.view;

import cs3500.animator.model.IAnimatorModel;

public class ViewFactory {
  public static final String ERROR_INVALID_VIEWTYPE
          = "Inputted view type is not a valid view type.";
  public static final String ERROR_NULL_MODEL = "Inputted model cannot be null.";
  public static final String ERROR_INVALID_TEMPO = "Inputted tempo must be a positive real number.";

  /**
   * Produces an instance of an IView specific to the information provided by the user.
   *
   * @param viewType the type of IView to produce
   * @param model the model to pull information from
   * @param tempo the ticks per second to run the program at
   * @param out the appendable object to append the animator description to
   * @return the IView instance corresponding to the view type
   * @throws IllegalArgumentException if any of the inputs are invalid
   */
  public static IView create(String viewType, IAnimatorModel model, double tempo, Appendable out)
          throws IllegalArgumentException {
    IView view;

    if (model == null) {
      throw new IllegalArgumentException(ERROR_NULL_MODEL);
    }

    if (tempo <= 0) {
      throw new IllegalArgumentException(ERROR_INVALID_TEMPO);
    }

    if (viewType == null) {
      throw new IllegalArgumentException(ERROR_INVALID_VIEWTYPE);
    }
    else if (viewType.equalsIgnoreCase("text")) {
      view = new TextualView(model.getAnimations(), tempo, out);
    }
    else if (viewType.equalsIgnoreCase("visual")) {
      view = new VisualView(model.getAnimations(), model.getShapes(), tempo);
    }
    else if (viewType.equalsIgnoreCase("svg")) {
      view = new SVGView(model.getAnimations(), tempo, out);
    }
    else {
      throw new IllegalArgumentException(ERROR_INVALID_VIEWTYPE);
    }

    return view;
  }

}
