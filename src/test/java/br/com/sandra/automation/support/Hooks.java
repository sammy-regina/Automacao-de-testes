package br.com.sandra.automation.support;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import io.qameta.allure.Allure;

public class Hooks {

    public static WebDriver driver;
    private static String baseURI;

    @Before
    public void iniciarCenario(Scenario cenario) {
        System.out.println("\n🔹 Iniciando cenário: " + cenario.getName());

        // Configurações para API (RestAssured)
        // A URL base da API JSONPlaceholder
        baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.baseURI = baseURI;

        // Configurações para Web (Selenium) - Opcional, descomente se for usar testes de UI
        // WebDriverManager.chromedriver().setup();
        // driver = new ChromeDriver();
        // driver.manage().window().maximize();
    }

    @After
    public void finalizarCenario(Scenario cenario) {
        System.out.println("🔸 Finalizando cenário: " + cenario.getName());

        // Captura evidência visual se o cenário falhar (para testes de UI)
        if (driver != null && cenario.isFailed()) {
            System.out.println("❌ Cenário falhou (UI): " + cenario.getName());
            capturarScreenshot(cenario);
        } else if (cenario.isFailed()) {
            System.out.println("❌ Cenário falhou (API): " + cenario.getName());
            // Para API, pode-se adicionar logs da requisição/resposta aqui se desejado
        } else {
            System.out.println("✅ Cenário passou: " + cenario.getName());
        }

        // Finaliza o WebDriver (se inicializado)
        if (driver != null) {
            driver.quit();
            driver = null; // Reseta o driver para o próximo cenário
        }
    }

    private void capturarScreenshot(Scenario cenario) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String nomeCenario = cenario.getName().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]", "");
            String caminho = "target/evidencias/" + nomeCenario + "_" + System.currentTimeMillis() + ".png";
            Files.createDirectories(Paths.get("target/evidencias"));
            Files.copy(screenshot.toPath(), Paths.get(caminho));
            System.out.println("📸 Evidência de UI salva em: " + caminho);
            // Anexa a imagem ao relatório Allure
            Allure.addAttachment("Screenshot da Falha", "image/png", Files.newInputStream(Paths.get(caminho)), ".png");
        } catch (IOException e) {
            System.out.println("⚠️ Erro ao salvar evidência: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getBaseURI() {
        return baseURI;
    }
}
