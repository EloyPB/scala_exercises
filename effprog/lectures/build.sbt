val scala3Version = "3.0.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies ++= List(
        "com.novocode" % "junit-interface" % "0.11" % "test",
        "com.lihaoyi" %% "fansi" % "0.3.1",
        "org.scalameta" %% "munit" % "0.7.29" % Test)
  )
