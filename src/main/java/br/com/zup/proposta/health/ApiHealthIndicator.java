package br.com.zup.proposta.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "api")
public class ApiHealthIndicator implements HealthIndicator {

    @Autowired
    private DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiHealthIndicator.class);

    @Override
    public Health getHealth(boolean includeDetails) {
        return health();
    }

    @Override
    public Health health() {
        String proposalUrl = "http://localhost:8080/api/propostas";
        Map<String, Object> details = new HashMap<>();
        details.put("version", "1.0.0");
        details.put("description", "Custom API Health Check");
        details.put("Address", "127.0.0.1");

        try (Connection connection = dataSource.getConnection()) {
        }
        catch (SQLException ex) {
            LOGGER.warn("DB not available");
            return Health.down().withDetails(details).withDetail("DB Test", ex.getLocalizedMessage()).build();
        }
        try {
            URL url = new URL(proposalUrl);
            int port = url.getPort();
            if (port == -1) {
                port = url.getDefaultPort();
            }

            try (Socket socket = new Socket(url.getHost(), port)) {
            }
            catch (IOException ex) {
                LOGGER.warn("Failed to open socket to " + proposalUrl);
                return Health.down().withDetails(details).withDetail("PROPOSAL Test", ex.getLocalizedMessage()).build();
            }
        }
        catch (MalformedURLException ex1) {
            LOGGER.warn("Malformed URL: " + proposalUrl);
            return Health.down().withDetails(details).withDetail("URL Test", ex1.getLocalizedMessage()).build();
        }

        return Health.status(Status.UP).withDetails(details).build();
    }
    
}
