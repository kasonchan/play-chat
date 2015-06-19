# PlayChat (Under construction) #

[![Build Status](https://travis-ci.org/KasonChan/play-chat.svg)](https://travis-ci.org/KasonChan/play-chat)

A simple web chat application.

### Requirements ###

This web chat application allows the user to chat with other joined users.

### Development ###

This application is built with [Scala](http://www.scala-lang.org/), 
[Play framework](https://playframework.com/), [Akka](http://akka.io/),
[SASS](http://sass-lang.com/), and [Intellij](https://www.jetbrains.com/idea/).

### Running the code locally ###

- Download/Unzip this repository.
- Enter `sbt compile` to compile.
- Enter `sbt start` to execute.
- Enter `http://localhost:9000/chat/<username>` in browser.
- Enter `sbt clean` to clean up the built files.

### References ###

- https://github.com/jlitola/play-sass
- https://www.playframework.com/documentation/2.3.x/ScalaWebSockets
- https://www.playframework.com/documentation/2.3.x/ScalaStream
- https://www.playframework.com/documentation/2.3.x/Iteratees
- https://www.playframework.com/documentation/2.3.x/Enumerators
- http://www.webjars.org/documentation
- https://github.com/playframework/playframework/issues/3779
