# VectorLerpKit
Utilities and maths for transforming 3D and real world sensor data 

_Not Your Father's Java 1.0 Vector_

Use a bunch of nice Kotlin fluff to write neat code that is less prone to bugs. These operations do not work like proper algebraic matrix operations. They operator straight across the indexes which can be more useful in situations where you're reading hardware data and need to transform it meaningfully.

`Vector(a, b, c) * Vector(d, e, f) * someScalar - someOffset`

...results in a vector that would be `Vector(a * d * someScalar - someOffset, b * e * ..., c * f * ...)`

_Linear Interpolation Made Less Insane_

More Kotlin fluff so that you may express values in different ranges. If you have a joystick x-axis reading of 0.14 between defined bounds of -1.0 and 1.0, and you want to paint the indicator at the correct pixel in a window from coordinates 10 to 82, then the correct x pixel is 51. While simple, this gets annoying if you have to set up many of these translations. Behold:

`val x = 0.14 from -1.0..1.0 projectInto (10..82)`

Lambda factories to capture this as reusable function are available.

_My Head Hertz_

My 7th grade math teacher would be disappointed at how many times I still have to take the reciprocal of something to see if I've even got the right unit. In a lot of hardware scenarios you might be given frequencies for one thing but period time for another. Use our handy Hz classes and extension functions to express it as the one thing that doesn't confuse you and handily convert it when needed.

```
val framesPerSecond = 60.Hz()
framesPerSecond.milliseconds == 16.667
