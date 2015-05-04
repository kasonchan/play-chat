name := "PlayChat"

version := "0.0"

lazy val `playchat` = (project in file(".")).enablePlugins(PlayScala, net.litola.SassPlugin).settings(
  sassOptions := Seq("--compass")
)

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")