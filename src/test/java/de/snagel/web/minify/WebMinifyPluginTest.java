package de.snagel.web.minify;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static de.snagel.web.minify.JavaScriptAssert.assertThat;
import static org.apache.commons.io.FileUtils.copyFile;
import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;


class WebMinifyPluginTest {

  @TempDir
  File testProjectDir;

  @BeforeEach
  public void setup() throws IOException {
    String testResources = "src/test/resources";
    File buildFile = new File(testResources, "build.gradle");
    File settingsFile = new File(testResources, "settings.gradle");
    File webflowJs = new File(testResources, "webflow.js");

    copyFile(buildFile, new File(testProjectDir, "build.gradle"));
    copyFile(settingsFile, new File(testProjectDir, "settings.gradle"));
    copyFile(webflowJs, new File(testProjectDir, "webflow.js"));
  }

  @Test
  void test() {
    BuildResult result = GradleRunner.create()
        .withProjectDir(testProjectDir)
        .withDebug(true)
        .build();

    assertEquals(SUCCESS, result.task(":jsMinify").getOutcome());

    String testResources = "src/test/resources";
    File givenJs = new File(testResources, "webflow.min.js");
    File expectedJs = new File(testProjectDir, "/js/webflow.min.js");
    assertThat(expectedJs).hasTheExactSameContentAs(givenJs);
  }
}