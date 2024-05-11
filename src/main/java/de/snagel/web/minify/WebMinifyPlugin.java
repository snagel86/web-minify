package de.snagel.web.minify;

import de.snagel.web.minify.extension.RootExtension;
import de.snagel.web.minify.task.JsMinifyTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class WebMinifyPlugin implements Plugin<Project> {

  public void apply(Project project) {
    project.getExtensions().create("webMinify", RootExtension.class, project);
    project.getTasks().create("jsMinify", JsMinifyTask.class);
  }
}
