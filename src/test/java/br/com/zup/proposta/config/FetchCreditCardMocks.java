package br.com.zup.proposta.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class FetchCreditCardMocks {

    public static void setupFetchCreditCardWhenCardExists(WireMockServer mockServer) throws IOException {
        mockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/api/contas"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        FetchCreditCardMocks.class
                                                .getClassLoader()
                                                .getResourceAsStream("test-data/credit_cards.json"), defaultCharset()))));
    }
}
