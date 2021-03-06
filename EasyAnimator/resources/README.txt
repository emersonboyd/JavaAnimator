---------------------------------------

What works and does not work for the provider's views:

We got a fair amount of features working successfully in our code. Primarily, both their textual
and svg views work as expected. In addition, their visual view and hybrid view compile and work for
the most part by showing/hiding shapes at specified times and performing animations on those shapes.
In the hybrid view, all of the buttons work as expected. All of the subset functionality works as
expected: the user can play a subset of animations and export that subset of animations to an svg
file, and they can export the entire animation to an svg file as well.

The only part of the visual/hybrid view display that does not work is the ordering of
shapes/animations being displayed to the screen. Sometimes, shapes that are supposed to appear on
top end up beneath other shapes and vice versa. This is easily visible when viewing the
visual/hybrid view with buildings.txt.


--------------------------------------

Changes since Homework 6:

Shape classes were updated to have a reset() function to reset their fields to their original
values. Additional fields were also added to these classes to store these original values.

Also added a Map<AbstractShape, Integer> to the model to keep track of the order in which shapes
should appear. This was necessary because we never kept track of when shapes should appear, so some
shapes were hidden in our animations.

In our model builder, we were only adding shapes to the model that had animations associated with
them. This was a bug that we had to address, so in the model builder we made sure to add shapes
without animations to the model.


--------------------------------------

Changes since Homework 5:

All old source files were moved into the package cs3500.animator.model

Some public fields in Oval.java were made private. This is a good design practice so that they
can't be modified and introduce unwanted behavior to the code.

We added list of shapes to model. This was done to make it easier to produce the different views.

We added another animate function to handle partial animations. This was done to calculate how far
each shape should move from frame to frame.

We added an updateSize function to each shape class because in our model, we only stored the scale
factor in the animation and we needed to update the size of the shape from frame to frame.


-------------------------------------


Structure of Design:

The majority of the classes are used for storing data rather than modifying data.

Color:
This class represents RGB values that can fall in the range [0.0,1.0].

Posn:
This class represents a 2D x and y position stored as a double.

AbstractCanvasObject:
Because every object that will be drawn holds a start time and an end time, I made a very generic class to represent this trait.

AbstractShape:
This class represents any shape that has a name, location and color associated with it. Two shapes are only considered equal if they have the same reference in memory.

Oval:
This is one example of a shape that adds in radiusX and radiusY traits.

Rectangle:
This is another example of a shape that has width and height attributes.

AbstractAnimation:
This class represents a generic animation that can occur which has a shape associated with it. An animation can animate a shape that is passed to it by altering the shape based on the specific type of animation that the class is. Currently, all animations are performed in their entirety, but the animate method can be updated to take a time tick such that the animation would only be performed to a certain extent at that time tick.

Move:
This class represents a movement animation of a shape from one location to another.

Scale:
This class represents a scale animation of a shape in the x and y directions.

ChangeColor:
This class represents a color alteration animation of a shape from one color to another.

IAnimatorModel:
This interface represents the operations that can be performed on a model. An animation (which has a reference to a shape) can be added to the model and the model should keep a record of that animation. In addition, the model should be able to return a textual description of the animations that will take place during runtime.

AnimatorModel:
This is an implementation of the IAnimatorModel that only maintains a list of the animations, as each animation has a reference to the shape that it modifies. If necessary, the model can retrieve a list of shapes created during runtime, but it does not need to keep track of that for now. In order to print out a sequence of runtime animations that would occur, the model creates copies of each shape and performs the full animations on them and then prints out the current state after each animation has been performed.

IView:
This is an interface for operations that can be performed on a view.

AbstractView:
This is an implementation of an IView that contains generic information common across all views. By default we suppress the animator description functions.

TextualView:
This view extends the abstract view and contains everything necessary to render a textual representation of an animation. We don't have a controller for this view because it is a static view.

VisualView:
This view extends the abstract view and renders an animation with a visual interface using the swing java library. This class uses a controller to process the visible shapes.

SVGView:
This view extends the abstract view and converts an animation to its representation in SVG. We don't have a controller for this view because it is a static view.

ShapePanel:
This is a class that extends JPanel that draws the visible shapes from the visual view.

ViewFactory:
This class creates the specific view depending on the input argument. It is the entrypoint to the codebase from the main function.

VisualViewController:
This is the controller for the animation. It processes the animations and updates the shapes accordingly. It tells the visual view what shapes to display.

HybridView
This is the view that contains the combined functionalities of the svg view and the visual view.

HybridViewController:
This is the controller for the hybrid view. It processes all of the button inputs and the text inputs.

MockView:
This is a mock view created to test the controller.
