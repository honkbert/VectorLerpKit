# VectorLerpKit
Utilities and maths for transforming 3D and real world sensor data 

## Not Your Father's Java 1.0 Vector

Stir some syntactic sugar into your tea and write neat code that is less prone to bugs. These operations do not work like proper algebraic matrix operations. They operate straight across the element indexes which can be more useful in situations where you're reading hardware data and need to transform it meaningfully.

```kotlin
Vector(a, b, c) * Vector(d, e, f) * someScalar - someOffset
```

...results in a vector that would be `Vector(a * d * someScalar - someOffset, b * e * ..., c * f * ...)`. I wrote `...` because I didn't want to type it all out, and now you don't have to either.

## Linear Interpolation Made Less Insane

More Kotlin fluff so that you may express values in different ranges. If you have a joystick x-axis reading of 0.14 between defined bounds of -1.0 and 1.0, and you want to paint the indicator at the correct pixel in a window from coordinates 10 to 82, then the correct x pixel is 51. While simple, this gets annoying if you have to set up many of these translations. Behold:

```kotlin
val x = 0.14 from -1.0..1.0 projectInto (10..82)
```

Lambda factories to capture this as reusable function are available.

## My Head Hertz

Inverses of inverses of inverses and headaches from middle school algebra. In a lot of hardware scenarios you might be given frequencies for one thing but period duration for another. Use the handy `Hz` class and extension functions to express it as one easy idea and handily convert it when needed.

```kotlin
val framesPerSecond = 60.Hz()
framesPerSecond.milliseconds == 16.667 //frame duration
```
