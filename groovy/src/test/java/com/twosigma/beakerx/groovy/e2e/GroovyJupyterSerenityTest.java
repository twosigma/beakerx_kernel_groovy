package com.twosigma.beakerx.groovy.e2e;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("e2e")
class GroovyJupyterSerenityTest {

  private static final int JUPYTER_PORT = 8899;
  private static final String JUPYTER_TOKEN = "serenity-groovy";

  private WebDriver browser;

  private Process jupyter;
  private Path notebookDirectory;

  @BeforeEach
  void startJupyter() throws IOException, InterruptedException {
    notebookDirectory = Files.createTempDirectory("beakerx-serenity-");
    Files.writeString(notebookDirectory.resolve("Untitled.ipynb"), "{\n"
        + "  \"cells\": [{\"cell_type\": \"code\", \"execution_count\": null, \"metadata\": {}, \"outputs\": [], \"source\": [\"0\\n\"]}],\n"
        + "  \"metadata\": {\"kernelspec\": {\"display_name\": \"Groovy\", \"language\": \"groovy\", \"name\": \"groovy\"}},\n"
        + "  \"nbformat\": 4,\n  \"nbformat_minor\": 5\n}\n");
    ChromeOptions options = new ChromeOptions();
    options.setBinary("/usr/bin/chromium");
    options.addArguments("--no-sandbox", "--disable-gpu", "--window-size=1440,1100");
    System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");
    browser = new ChromeDriver(options);
    jupyter = new ProcessBuilder(
        "jupyter", "lab", "--no-browser", "--ip=127.0.0.1",
        "--port=" + JUPYTER_PORT,
        "--ServerApp.token=" + JUPYTER_TOKEN,
        "--ServerApp.root_dir=" + notebookDirectory)
        .redirectErrorStream(true)
        .start();
    waitForJupyter();
  }

  @AfterEach
  void stopJupyter() throws InterruptedException {
    if (browser != null) {
      browser.quit();
    }
    if (jupyter != null) {
      jupyter.destroy();
      jupyter.waitFor();
    }
  }

  @Test
  void launchesGroovyNotebookAndExecutesCode() {
    browser.get("http://127.0.0.1:" + JUPYTER_PORT
        + "/lab/tree/Untitled.ipynb?token=" + JUPYTER_TOKEN);

    WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(60));
    WebElement editor = wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector(".jp-CodeCell .cm-content")));
    editor.click();
    Keys modifier = System.getProperty("os.name").toLowerCase().contains("mac")
        ? Keys.COMMAND : Keys.CONTROL;
    editor.sendKeys(modifier, "a");
    editor.sendKeys("6 * 7");
    editor.sendKeys(Keys.SHIFT, Keys.ENTER);

    WebElement output = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector(".jp-OutputArea-output")));
    assertThat(output.getText()).contains("42");
  }

  private void waitForJupyter() throws IOException, InterruptedException {
    long deadline = System.nanoTime() + Duration.ofSeconds(60).toNanos();
    while (System.nanoTime() < deadline) {
      if (!jupyter.isAlive()) {
        throw new IllegalStateException("Jupyter exited before startup");
      }
      try {
        new java.net.URL("http://127.0.0.1:" + JUPYTER_PORT
            + "/lab?token=" + JUPYTER_TOKEN).openConnection().connect();
        return;
      } catch (IOException ignored) {
        Thread.sleep(500);
      }
    }
    throw new IllegalStateException("Jupyter did not start within 60 seconds");
  }
}
