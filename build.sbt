import sbtcrossproject.CrossPlugin.autoImport.crossProject
import BuildHelper._

inThisBuild(
  List(
    name := "zio-schema",
    organization := "dev.zio",
    homepage := Some(url("https://github.com/zio/zio-schema")),
    developers := List(
      Developer(
        "ioleo",
        "Piotr Gołębiewski",
        "ioleo+zio@protonmail.com",
        url("https://github.com/ioleo")
      ),
      Developer(
        "jczuchnowski",
        "Jakub Czuchnowski",
        "jakub.czuchnowski@gmail.com",
        url("https://github.com/jczuchnowski")
      )
    ),
    scmInfo := Some(
      ScmInfo(
        homepage.value.get,
        "scm:git:git@github.com:zio/zio-schema.git"
      )
    ),
    licenses := Seq("Apache-2.0" -> url(s"${scmInfo.value.map(_.browseUrl).get}/blob/v${version.value}/LICENSE")),
    pgpPassphrase := sys.env.get("PGP_PASSWORD").map(_.toArray),
    pgpPublicRing := file("/tmp/public.asc"),
    pgpSecretRing := file("/tmp/secret.asc")
  )
)

ThisBuild / publishTo := sonatypePublishToBundle.value

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")
addCommandAlias("testRelease", ";set every isSnapshot := false;+clean;+compile")

lazy val root = project
  .in(file("."))
  .settings(
    name := "zio-schema",
    skip in publish := true
  )
  .aggregate(
    core
  )

lazy val core = project
  .in(file("core"))
  .settings(stdSettings("zio-schema-core"))
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"         % zioVersion,
      "com.propensive" %% "magnolia"     % magnoliaVersion,
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % Provided
    )
  )