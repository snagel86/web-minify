package de.snagel.web.minify.extension;

import groovy.lang.Closure;
import lombok.Data;
import org.gradle.api.Project;

@Data
public class RootExtension {

  private final Project project;
  private final ScriptExtension script;

  public RootExtension(Project project) {
    this.project = project;
    this.script = new ScriptExtension(project);
  }

  void script(Closure<ScriptExtension> closure) {
    this.project.configure(this.script, closure);
  }
}
