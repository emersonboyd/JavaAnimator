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