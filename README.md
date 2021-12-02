# VectorLerpKit
Utilities and maths for transforming 3D and real world sensor data 

## Vectors

Pretty much everyone writes their own 3-member data class and this one is mine. It's well tested and it comes with handy operations.

```kotlin
Vector(a, b, c) * Vector(d, e, f) * someScalar - someOffset
```

This results in a vector that would be `Vector(a * d * someScalar - someOffset, b * e * ..., c * f * ...)`. I wrote `...` because I didn't want to type it all out, and now you don't have to either. (Note: These operations do not work like traditional algebraic matrix operations)

## Linear Interpolation and Linear Projection

Express values relatively in different ranges.

A simple example of linear interpolation `lerpBy`:
```kotlin
val interpolatedAmount = 2.0..22.0 lerpBy 0.4
```
The result is that `interpolatedAmount` is `10.0`. (that is, what is 40% of the way between 2 and 22?)

But what if you have a value in one range that needs to "mapped" to the same relative value in another range? Say you have a joystick x-axis reading of 0.14 between defined bounds of -1.0 and 1.0, and you want to paint the indicator at the correct pixel in a window that spans x-coordinates 10 to 82, then the correct x pixel is 51. While simple, this gets tedious if you have to write out many of these translations. Behold, linear projection, or `linject`:

```kotlin
val x = -1.0..1.0 linject (10..82) by 0.14
```

`linject` can be used in a one-liner like above, but it's also factory for capturing a projection between two ranges as a reusable object:

```kotlin
val justin = 1..5
val kelly = 6..10
val fromJustinToKelly = justin linject kelly
```

Doing a "reverse" linear interpolation is simple using `from`:

```kotlin
val result = 7 from 6..8
```
`result` is `0.5`

Note: wherever a division by zero error or `NaN` would occur, the logic instead divides by `Doube.MIN_VALUE`

## My Head Hertz

Inverses of inverses of inverses cause headaches. With hardware, it's often the case you'll get specs that give you frequency values for one thing but period duration for another. Use the handy `Hz` class and extension functions to express it as one easy thing and handily convert it where different respresentations needed.

```kotlin
val framesPerSecond = 60.Hz()
framesPerSecond.milliseconds == 16.667 //frame duration
```
