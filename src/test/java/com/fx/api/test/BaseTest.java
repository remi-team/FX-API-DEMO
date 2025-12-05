package com.fx.api.test;

import com.fx.api.client.ApiClient;
import com.fx.api.client.ApiConfig;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static ApiClient apiClient;

    //    protected static String BASE_URL = "https://api.remifx-test.ai"; // Replace with actual host
//    protected static String API_KEY = "8q0sfK2TyroghFSVEd8P69YO3muC9982WTBG2lQ50V6SEMB1SfMHx1jgvhLyZHbz";
//    protected static String SECRET_KEY = "SAHK97QwR4QAbcAXUrSzWV5rKkYgsiIkKihM4n6FTYN169nAH1yle517YTStdWgG";
    protected static String BASE_URL = "https://api.remifx-test.ai"; // Replace with actual host
    protected static String API_KEY = "47VZdXqaJp9Ym6HLSDA9NBJEkdss9e3Ga5sJf57DIF3oO47Cc9adKNOtmmySFoEH";
    protected static String SECRET_KEY = "dUEgOouyrVpVeaFu7zZHenbkGsP32MVJ5ZUyW7yxbJyNUjcHGNladlisua90tzwK";
    @BeforeAll
    public static void setUp() {
        ApiConfig config = new ApiConfig(BASE_URL, API_KEY, SECRET_KEY);
        apiClient = new ApiClient(config);
    }
}
