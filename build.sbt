name := "PlayChat"

version := "0.1"

lazy val `playchat` = (project in file("."))
  .enablePlugins(PlayScala, net.litola.SassPlugin)
  .settings(
    sassOptions := Seq("--compass")
  )

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "jquery" % "2.1.4"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")