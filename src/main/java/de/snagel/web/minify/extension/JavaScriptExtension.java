package de.snagel.web.minify.extension;

import lombok.Data;
import org.gradle.api.Project;

import java.io.File;

/**
 * <pre>
 * ___ {
 *     inDir = file("$projectDir/src/main/resources/static/js")
 *     outDir = file("$projectDir/src/main/resources/static/js")
 * }
 * </pre>
 */
@Data
public class JavaScriptExtension {

  private final Project project;
  private File inDir;
  private File outDir;

  public JavaScriptExtension(Project project) {
    this.project = project;
  }
}
