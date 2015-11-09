# Fill Drawable

[![Maven Central](https://img.shields.io/maven-central/v/ru.noties/filldrawable.svg)](http://search.maven.org/#search|ga|1|g%3A%22ru.noties%22%20AND%20a%3A%22filldrawable%22)

![](https://raw.githubusercontent.com/noties/FillDrawable/master/fill_drawable.gif)

## Introduction
Convert any `Drawable` into *filled* drawable depending on `progress` value (.0F - 100.F). One image resource is used. No overdraw, no bitmap allocations.

There are 4 possible directions of progress: `from_left`, `from_top`, `from_right`, `from_bottom`

## Usage
The idea behind `FillDrawable` is `PorterDuffColorFilter`, one for normal state & one for progress. The best image resource to use would be **solid white**
```java
// obtain a Drawable object
final Drawable drawable = getDrawable(...);

// initialize bounds
drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

// create a FillDrawable
// Note, that you should call `mutate()`, otherwise changes made by FillDrawable
// will be reflected in other places where this drawable is used.
// It has to do it Android drawable cache
final FillDrawable fillDrawable = new FillDrawable(FillDrawable.FROM_LEFT, drawable.mutate())
    .setNormalColor(int color)
    .setFillColor(int color);

...

fillDrawable.setFillPercent(float percent);
```

Also, there is a `FillImageView` widget to initialize FillDrawable via XML. Attributes:
```xml
app:fiv_normalColor="@color/normal_color"
app:fiv_fillColor="@color/fill_color"
app:fiv_drawable="@drawable/ic_launcher"
app:fiv_from="left" <!-- (left|top|right"bottom) -->
app:fiv_percent="50.0"
```

## License

```
  Copyright 2015 Dimitry Ivanov (mail@dimitryivanov.ru)

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```