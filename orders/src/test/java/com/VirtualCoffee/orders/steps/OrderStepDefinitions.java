package com.VirtualCoffee.orders.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class OrderStepDefinitions {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private ResponseEntity<String> response;

    private final String baseUrl = "http://localhost:8080/orders";

    @Given("la bebida {string} de tamaño {string} está disponible")
    public void bebidaDisponible(String name, String size) {
        // Aquí suponemos que el BeverageClient siempre retorna true
        // o puedes hacer un mock si se integra con un servicio real
    }

    @When("el cliente realiza un pedido con nombre {string} y tamaño {string}")
    public void clienteRealizaPedido(String name, String size) throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);
        body.put("size", size);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(
                new ObjectMapper().writeValueAsString(body), headers);

        response = restTemplate.postForEntity(baseUrl, request, String.class);
    }

    @Then("el sistema debe responder con estado 201 y un pedido creado")
    public void verificarRespuesta() {
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Latte")); // o lo que esperes
    }
}

