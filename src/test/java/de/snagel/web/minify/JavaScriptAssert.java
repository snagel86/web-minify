package de.snagel.web.minify;

import org.assertj.core.api.AbstractAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class JavaScriptAssert extends AbstractAssert<JavaScriptAssert, File> {

  public JavaScriptAssert(File actual) {
    super(actual, JavaScriptAssert.class);
  }

  public static JavaScriptAssert assertThat(File actual) {
    return new JavaScriptAssert(actual);
  }

  public void hasTheExactSameContentAs(File expected) {
    try {
      if (!Objects.equals(Files.readString(actual.toPath()), Files.readString(expected.toPath()))) {
        failWithMessage("The expected JavaScript does not match the given one.");
      }
    } catch (IOException ioException) {
      failWithMessage(ioException.getMessage());
    }
  }
}
