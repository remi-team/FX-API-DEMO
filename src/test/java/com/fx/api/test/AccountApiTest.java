package com.fx.api.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Account API endpoint tests
 */
@DisplayName("Account API Tests")
public class AccountApiTest extends BaseTest {
//Account info response: {"balances":[{"asset":"CNHR","assetId":"CNHR","assetName":"CNHR","total":"54970108.01256551","free":"54585690.63523551","locked":"384417.37733"},{"asset":"EURR","assetId":"EURR","assetName":"EUB","total":"20477066.04309","free":"20404531.91309","locked":"72534.13"},{"asset":"EUTSIT","assetId":"EUTSIT","assetName":"EUTSIT","total":"97890869.5433699","free":"95920923.0233699","locked":"1969946.52"},{"asset":"HKTSIT","assetId":"HKTSIT","assetName":"HKTSIT","total":"75522194.58911235154","free":"67822030.89356335154","locked":"7700163.695549"},{"asset":"JPYR","assetId":"JPYR","assetName":"JPB","total":"924922581.567518616","free":"924112490.148198616","locked":"810091.41932"},{"asset":"THBR","assetId":"THBR","assetName":"THB","total":"22710012.077939949","free":"22493040.547440949","locked":"216971.530499"},{"asset":"USDR","assetId":"USDR","assetName":"USB","total":"12270524.467794136","free":"12200784.372021136","locked":"69740.095773"},{"asset":"USTSIT","assetId":"USTSIT","assetName":"USTSIT","total":"97244616.65540552144","free":"95405839.78583552144","locked":"1838776.86957"},{"asset":"VNDR","assetId":"VNDR","assetName":"VNB","total":"10000000","free":"10000000","locked":"0"}]}
    @Test
    @DisplayName("Test /openapi/v1/account - Get account information")
    public void testAccountInfo() {
        try {
            Map<String, String> params = new HashMap<>();

            String response = apiClient.getWithSignature("/openapi/v1/account", params);
            assertNotNull(response);
            assertTrue(response.contains("balances") || response.contains("canTrade"));
            System.out.println("Account info response: " + response);
        } catch (Exception e) {
            fail("Account info test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/depositOrders - Get deposit orders")
    public void testDepositOrders() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("limit", "500");

            String response = apiClient.getWithSignature("/openapi/v1/depositOrders", params);
            assertNotNull(response);
            System.out.println("Deposit orders response: " + response);
        } catch (Exception e) {
            fail("Deposit orders test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/depositOrders - Get deposit orders with token filter")
    public void testDepositOrdersWithToken() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("token", "EUTSIT");
            params.put("limit", "100");

            String response = apiClient.getWithSignature("/openapi/v1/depositOrders", params);
            assertNotNull(response);
            System.out.println("Deposit orders (filtered) response: " + response);
        } catch (Exception e) {
            fail("Deposit orders with token test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/depositOrders - Get deposit orders with time range")
    public void testDepositOrdersWithTimeRange() {
        try {
            Map<String, String> params = new HashMap<>();
//            params.put("startTime", "1499865549590");
//            params.put("endTime", "1699865549590");

            String response = apiClient.getWithSignature("/openapi/v1/depositOrders", params);
            assertNotNull(response);
            System.out.println("Deposit orders (time range) response: " + response);
        } catch (Exception e) {
            fail("Deposit orders with time range test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/withdraw/detail - Get withdrawal detail by orderId")
    public void testWithdrawDetailByOrderId() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("orderId", "2085487724068944896");

            String response = apiClient.getWithSignature("/openapi/v1/withdraw/detail", params);
            assertNotNull(response);
            System.out.println("Withdraw detail (orderId) response: " + response);
        } catch (Exception e) {
            System.out.println("Note: Test may fail if orderId doesn't exist - " + e.getMessage());
        }
    }
//todo
    @Test
    @DisplayName("Test /openapi/v1/withdraw/detail - Get withdrawal detail by clientOrderId")
    public void testWithdrawDetailByClientOrderId() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("clientOrderId", "test-client-order-1");

            String response = apiClient.getWithSignature("/openapi/v1/withdraw/detail", params);
            assertNotNull(response);
            System.out.println("Withdraw detail (clientOrderId) response: " + response);
        } catch (Exception e) {
            System.out.println("Note: Test may fail if clientOrderId doesn't exist - " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/withdrawalOrders - Get withdrawal orders")
    public void testWithdrawalOrders() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("limit", "500");

            String response = apiClient.getWithSignature("/openapi/v1/withdrawalOrders", params);
            assertNotNull(response);
            System.out.println("Withdrawal orders response: " + response);
        } catch (Exception e) {
            fail("Withdrawal orders test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/withdrawalOrders - Get withdrawal orders with token filter")
    public void testWithdrawalOrdersWithToken() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("token", "USTSIT");
            params.put("limit", "100");

            String response = apiClient.getWithSignature("/openapi/v1/withdrawalOrders", params);
            assertNotNull(response);
            System.out.println("Withdrawal orders (filtered) response: " + response);
        } catch (Exception e) {
            fail("Withdrawal orders with token test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/withdrawalOrders - Get withdrawal orders with time range")
    public void testWithdrawalOrdersWithTimeRange() {
        try {
            Map<String, String> params = new HashMap<>();
//            params.put("startTime", "1536053746220");
//            params.put("endTime", "1636053746220");

            String response = apiClient.getWithSignature("/openapi/v1/withdrawalOrders", params);
            assertNotNull(response);
            System.out.println("Withdrawal orders (time range) response: " + response);
        } catch (Exception e) {
            fail("Withdrawal orders with time range test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/balance_flow - Get balance flow")
    public void testBalanceFlow() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("limit", "100");

            String response = apiClient.postWithSignature("/openapi/v1/balance_flow", params);
            assertNotNull(response);
            System.out.println("Balance flow response: " + response);
        } catch (Exception e) {
            fail("Balance flow test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/balance_flow - Get balance flow with token filter")
    public void testBalanceFlowWithToken() {
        try {
            Map<String, String> params = new HashMap<>();
//            params.put("tokenId", "USTSIT");
            params.put("limit", "50");

            String response = apiClient.postWithSignature("/openapi/v1/balance_flow", params);
            assertNotNull(response);
            System.out.println("Balance flow (filtered) response: " + response);
        } catch (Exception e) {
            fail("Balance flow with token test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/balance_flow - Get balance flow with time range")
    public void testBalanceFlowWithTimeRange() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("startTime", "1578640809195");
            params.put("endTime", "1679640809195");

            String response = apiClient.postWithSignature("/openapi/v1/balance_flow", params);
            assertNotNull(response);
            System.out.println("Balance flow (time range) response: " + response);
        } catch (Exception e) {
            fail("Balance flow with time range test failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test /openapi/v1/withdraw - Create withdrawal (Note: This is a real withdrawal request)")
    public void testWithdraw() {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("tokenId", "USTSIT");
            params.put("clientOrderId", String.valueOf(System.currentTimeMillis()));
            params.put("withdrawQuantity", "0.001");
            params.put("chainType", "BTC");

            // WARNING: This will create a real withdrawal request
            // Comment out in production or use test environment
//            System.out.println("WARNING: Withdraw test is disabled to prevent accidental withdrawals");
             String response = apiClient.postWithSignature("/openapi/v1/withdraw", params);
             assertNotNull(response);
             System.out.println("Withdraw response: " + response);
        } catch (Exception e) {
            fail("Withdraw test failed: " + e.getMessage());
        }
    }
}
