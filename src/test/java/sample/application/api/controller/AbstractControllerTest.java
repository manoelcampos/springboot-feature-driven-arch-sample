package sample.application.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.RestController;

/// An abstract class for creating REST API tests,
/// implemented through a [RestController] annotated class.
///
/// @author Manoel Campos
/// @link [WebClient and WebClientTest](https://www.baeldung.com/spring-5-webclient)
/// @link [Spring WebClientTest](https://docs.spring.io/spring-framework/reference/testing/webtestclient.html#webtestclient-context-config)
/// @link [Spring WebClientTest Tutorial](a href="https://34codefactory.medium.com/spring-5-webclient-and-webtestclient-tutorial-code-factory-84e32978149a)
/// @link [testing-of-your-rest-api](https://rieckpil.de/spring-webtestclient-for-efficient-testing-of-your-rest-api/)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractControllerTest {
    @Autowired
    protected WebTestClient client;

    /// [LocalServerPort] gets the HTTP server port where the application is running for testing.
    /// Not required, it but can be used to know which port the server is listening to.
    @LocalServerPort
    private int port;

    public WebTestClient client() {
        return client;
    }
}
