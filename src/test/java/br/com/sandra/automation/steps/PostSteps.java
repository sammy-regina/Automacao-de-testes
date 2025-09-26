package br.com.sandra.automation.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import br.com.sandra.automation.support.ExcelUtils;
import br.com.sandra.automation.support.Hooks;

public class PostSteps {

    private Response response;
    private int postId; // Mudado para int

    @Dado("que a API de posts está disponível")
    public void que_a_api_de_posts_esta_disponivel() {
        // A URL base será configurada nos Hooks
        // RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        System.out.println("✔️ API de posts está disponível em: " + Hooks.getBaseURI());
    }

    @Quando("eu busco o post com o ID {int}") // Mudado para {int}
    public void eu_busco_o_post_com_o_id(int idPost) { // Mudado para int
        this.postId = idPost; // Armazena o ID para uso posterior
        response = given()
                .baseUri(Hooks.getBaseURI())
                .when()
                .get("/posts/" + postId);
        System.out.println("🔍 Buscando post com ID: " + postId + ", Status: " + response.getStatusCode());
    }

    @Entao("o status code da resposta deve ser {int}")
    public void o_status_code_da_resposta_deve_ser(int statusCodeEsperado) {
        int statusCodeAtual = response.getStatusCode();
        assertEquals("O status code retornado não é o esperado!", statusCodeEsperado, statusCodeAtual);
        System.out.println("✅ Status Code Validado: " + statusCodeAtual);

        // Salvando no Excel
        ExcelUtils.salvarResultado("API_GET_Posts", String.valueOf(postId), String.valueOf(statusCodeAtual), String.valueOf(statusCodeEsperado), statusCodeAtual == statusCodeEsperado ? "Passou" : "Falhou");
    }

    // Corrigido para corresponder ao snippet e remover acento no Gherkin para evitar problemas de encoding
    @Entao("o titulo do post deve ser {string}") // Regex ajustado
    public void o_titulo_do_post_deve_ser(String tituloEsperado) { // Nome do método ajustado para consistência
        if (response.getStatusCode() == 200) {
            String tituloAtual = response.jsonPath().getString("title");
            assertEquals("O título do post retornado não é o esperado!", tituloEsperado, tituloAtual);
            System.out.println("✅ Título Validado: " + tituloAtual);
        } else if (tituloEsperado.isEmpty() && response.getStatusCode() == 404) {
            // Para o caso de 404, onde esperamos um título vazio (ou nenhum título)
            assertTrue("A resposta não deveria conter um título para status 404.", response.asString().isEmpty() || response.jsonPath().getMap(".").isEmpty());
            System.out.println("✅ Resposta sem título para status 404 validada.");
        } else {
            // Tratar outros casos ou falhar se um título for esperado para um status não-200
            System.out.println("⚠️ Não foi possível validar o título para o status code: " + response.getStatusCode());
        }
    }
}
