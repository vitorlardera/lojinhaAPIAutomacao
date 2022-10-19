package modulos.produto;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.ComponentePojo;
import pojo.ProdutoPojo;
import pojo.UsuarioPojo;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@DisplayName("Testes API Rest do modulo de Produto")
public class ProdutoTest {
    private String token;

    @BeforeEach
    public void beforeEach() {
        // configurando os dados da api rest da lojinha
        baseURI = "http://165.227.93.41";
        // port = 8080;
        basePath = "/lojinha";

        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setUsuarioLogin("admin");
        usuario.setUsuarioSenha("admin");


        // obter o token do usuario admin
        this.token = given()
                    .contentType(ContentType.JSON)
                    .body(usuario)
                .when()
                    .post("/v2/login")
                .then()
                    .extract()
                        .path("data.token");
    }


    @Test
    @DisplayName("Validar os limite 0 do valor do produto")
    public void testValidarLimitesZeradoProibidoValorProduto() {

        // tentar inserir um produto com o valor 0.00 e validar que a mensagem de erro foi apresentada
        // e o status code foi 422

        ProdutoPojo produto = new ProdutoPojo();
        produto.setProdutoNome("Play Station 5");
        produto.setProdutoValor(0.00);
        List<String> cores = new ArrayList<String>();
        cores.add("preto");
        cores.add("branco");

        produto.setProdutoCores(cores);
        produto.setProdutoUrlMock("");

        List<ComponentePojo> componentes = new ArrayList<ComponentePojo>();

        ComponentePojo componente = new ComponentePojo();
        componente.setComponenteNome("Controle");
        componente.setComponenteQuantidade(1);

        componentes.add(componente);

        produto.setComponentes(componentes);



        given()
                .contentType(ContentType.JSON)
                .header("token", this.token)
                .body(produto)
        .when()
                .post("/v2/produtos")
        .then()
                .assertThat()
                .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                .statusCode(422);
    }
    @Test
    @DisplayName("Validar os limites proibidos maior que 7000 do valor do produto")
        public void testValidarLimitesMaiorSeteMilProibidoValorProduto() {
            given()
                    .contentType(ContentType.JSON)
                    .header("token", this.token)
                    .body("{\n" +
                            "  \"produtoNome\": \"PlayStation 5\",\n" +
                            "  \"produtoValor\": 7000.01,\n" +
                            "  \"produtoCores\": [\n" +
                            "    \"Preto\", \"Branco\"\n" +
                            "  ],\n" +
                            "  \"produtoUrlMock\": \"\",\n" +
                            "  \"componentes\": [\n" +
                            "    {\n" +
                            "      \"componenteNome\": \"Controle\",\n" +
                            "      \"componenteQuantidade\": 2\n" +
                            "    },\n" +
                            "    {\n" +
                            "    \"componenteNome\": \"Jogo de Aventura\",\n" +
                            "    \"componenteQuantidade\": 1\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}")
            .when()
                    .post("/v2/produtos")
            .then()
                    .assertThat()
                    .body("error", equalTo("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00"))
                    .statusCode(422);


        }

    }
