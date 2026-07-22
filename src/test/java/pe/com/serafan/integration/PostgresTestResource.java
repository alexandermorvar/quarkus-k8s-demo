package pe.com.serafan.integration;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

public class PostgresTestResource implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgres;

    private static final DockerImageName POSTGRES_IMAGE =
            DockerImageName.parse("postgres:17");

    @Override
    public Map<String, String> start() {

        postgres = new PostgreSQLContainer<>(POSTGRES_IMAGE);

        postgres.start();

        Map<String, String> config = new HashMap<>();

        config.put("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        config.put("quarkus.datasource.username", postgres.getUsername());
        config.put("quarkus.datasource.password", postgres.getPassword());

        return config;
    }

    @Override
    public void stop() {

        if (postgres != null) {
            postgres.stop();
        }

    }

}