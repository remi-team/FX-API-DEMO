package com.fx.api.test;

import com.fx.api.client.ApiClient;
import com.fx.api.client.ApiConfig;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static ApiClient apiClient;

    protected static String BASE_URL = "https://api.remifx-test.ai"; // Replace with actual host
    protected static String API_KEY = "your-api-key";
    protected static String SECRET_KEY = "your-secret-key";

    @BeforeAll
    public static void setUp() {
        ApiConfig config = new ApiConfig(BASE_URL, API_KEY, SECRET_KEY);
        apiClient = new ApiClient(config);
    }
}
