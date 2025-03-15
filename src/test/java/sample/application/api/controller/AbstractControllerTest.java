package sample.application.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RestController;

/**
 * Uma classe abstrata para a criação de testes de APIs REST,
 * implementadas por meio de uma classe {@link RestController}.
 *
 * @author Manoel Campos
 * @see <a href="https://www.baeldung.com/spring-5-webclient">WebClient and WebClientTest</a>
 * @see <a href="https://docs.spring.io/spring-framework/reference/testing/webtestclient.html#webtestclient-context-config">Spring WebClientTest</a>
 * @see <a href="https://34codefactory.medium.com/spring-5-webclient-and-webtestclient-tutorial-code-factory-84e32978149a">Spring WebClientTest Tutorial</a>
 * @see <a href="https://rieckpil.de/spring-webtestclient-for-efficient-testing-of-your-rest-api/">testing-of-your-rest-api</a>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {
    @Autowired
    protected WebTestClient client;

    /**
     * {@link LocalServerPort} obtém a porta do servidor Spring com a instância da aplicação para testes automatizados.
     * Não é necessário, mas pode ser usado para saber qual porta está sendo usada.
     */
    @LocalServerPort
    private int port;

    public WebTestClient client() {
        return client;
    }
}
