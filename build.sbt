import play.PlayJava

name := """stt"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies += javaEbean

scalaVersion := "2.11.1"
