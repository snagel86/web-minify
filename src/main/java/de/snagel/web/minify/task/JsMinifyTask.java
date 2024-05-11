package de.snagel.web.minify.task;

import com.google.javascript.jscomp.*;
import de.snagel.web.minify.extension.RootExtension;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Collections;
import java.util.stream.Collectors;

public class JsMinifyTask extends DefaultTask {

  private final RootExtension rootExtension;

  @Inject
  public JsMinifyTask() {
    rootExtension = getProject().getExtensions().getByType(RootExtension.class);
  }

  @TaskAction
  public void compileJs() {
    rootExtension.getScript().getJs().forEach(js -> {
      try {
        jsMinify(js.getInDir(), js.getOutDir());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  private void jsMinify(File inJsFile, File outDir) throws IOException {
    var compiler = new Compiler();
    var compileLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS;
    var warnLevel = WarningLevel.QUIET;
    var options = new CompilerOptions();
    compileLevel.setOptionsForCompilationLevel(options);
    warnLevel.setOptionsForWarningLevel(options);

    var externs = CommandLineRunner.getBuiltinExterns(CompilerOptions.Environment.BROWSER);
    var inputs = Collections.singletonList(
        SourceFile.fromPath(inJsFile.toPath(), StandardCharsets.UTF_8)
    );

    compiler.compile(externs, inputs, options);
    if (compiler.hasErrors()) {
      String errMsg = compiler
          .getErrorManager().getErrors().stream()
          .map(error ->
              "${error.getSourceName()} : ${String.valueOf(error.getLineNumber())} - ${error.getDescription()}"
          ).collect(Collectors.joining(System.lineSeparator()));
      throw new GradleException(errMsg);
    }

    outDir.mkdirs();
    var path = new File(
        outDir,
        inJsFile.getName().replaceFirst("\\.js", ".min.js")
    ).toPath();
    Files.write(path, compiler.toSource().getBytes(Charset.defaultCharset()));
  }
}
