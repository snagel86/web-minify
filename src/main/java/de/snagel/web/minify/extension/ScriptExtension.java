package de.snagel.web.minify.extension;

import groovy.lang.Closure;
import lombok.Data;
import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScriptExtension {

  private final Project project;
  private List<JavaScriptExtension> js;

  public ScriptExtension(Project project) {
    this.project = project;
    js = new ArrayList<>();
  }

  void js(Closure<JavaScriptExtension> closure) {
    var extension = new JavaScriptExtension(project);
    this.project.configure(extension, closure);
    this.js.add(extension);
  }
}
