package br.com.sandra.automation.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report.html", "json:target/cucumber-report.json", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        features = "src/test/resources/features",
        glue = {"br.com.sandra.automation.steps", "br.com.sandra.automation.support"},
        tags = "@regressivo" // Coloque aqui a tag que deseja rodar
)
public class TestRunner {
}
