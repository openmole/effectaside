
import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

name := "effectaside"
scalaVersion in ThisBuild := "2.12.4"
crossScalaVersions in ThisBuild := Seq("2.11.11", "2.12.4")
scalacOptions in ThisBuild += "-Ypartial-unification"
organization in ThisBuild := "fr.iscpif.effectaside"

libraryDependencies in ThisBuild += "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle in ThisBuild := true
publishArtifact in Test in ThisBuild := false
pomIncludeRepository in ThisBuild := { _ => false }
licenses in ThisBuild := Seq("LGPL" -> url("http://www.gnu.org/licenses/"))
homepage in ThisBuild := Some(url("https://github.com/ISCPIF/effectaside"))
scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/ISCPIF/effectaside.git"), "scm:git:git@github.com:ISCPIF/effectaside.git"))
pomExtra in ThisBuild := (
  <developers>
    <developer>
      <id>romainreuillon</id>
      <name>Romain Reuillon</name>
    </developer>
  </developers>
)


releaseVersionBump := sbtrelease.Version.Bump.Minor

releaseTagComment    := s"Releasing ${(version in ThisBuild).value}"

releaseCommitMessage := s"Bump version to ${(version in ThisBuild).value}"

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

def settings = scalariformSettings(autoformat = true) ++ Seq (
  // macro paradise doesn't work with scaladoc
  sources in (Compile, doc) := Nil,
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  libraryDependencies += "org.typelevel"  %% "squants"  % "1.3.0"
)

lazy val effect = Project(id = "effect", base = file("effect")) settings(settings: _*)
//lazy val random = Project(id = "random", base = file("random")) settings(settings: _*) dependsOn(effect)
//lazy val log = Project(id = "log", base = file("log")) settings(settings: _*)
//lazy val system = Project(id = "system", base = file("system")) settings(settings: _*) settings (
//  libraryDependencies += "org.typelevel"  %% "squants"  % "1.3.0"
//)
////lazy val io = Project(id = "io", base = file("io")) settings(settings: _*)
//lazy val filesystem = Project(id = "filesystem", base = file("filesystem")) settings(settings: _*)
//lazy val errorhandler = Project(id = "errorhandler", base = file("errorhandler")) settings(settings: _*)
//lazy val tool = Project(id = "tool", base = file("tool")) settings(settings: _*)
//lazy val example = Project(id = "example", base = file("example")) settings(settings: _*) dependsOn(random, system, log)



