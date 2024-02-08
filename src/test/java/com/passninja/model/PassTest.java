package com.passninja.model;

import com.passninja.Passninja;
import com.passninja.exception.AuthenticationException;
import com.passninja.net.PassninjaResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PassTest {

    @BeforeEach
    void setup() {
        Passninja.init("69d78c80-1e8e-482b-8f17-15027f28facc", "zIyRpESRkD7Ny1jxiNKYs3qHthW0EtSm9hKWL8G5");
    }


    @Test
    public void should_create_a_new_pass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        Pass pass = response.getResponseBody();
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        assertThat(pass.getUrls()).as("response contains urls").containsKeys("landing", "google", "apple");
    }

    @Test
    public void should_retrieve_a_pass_by_serial_number() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        PassninjaResponse<Pass> value = Pass.get("Name.a", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    public void should_retrieve_passes() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        PassninjaResponse<Pass[]> value = Pass.findPasses("Name.a");
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody()[0].getSerialNumber());
    }

    @Test
    public void should_decrypt_pass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        PassninjaResponse<Pass> value = Pass.decryptPass("Name.a",
            "55166a9700250a8c51382dd16822b0c763136090b91099c16385f2961b7d9392d31b386cae"
            + "133dca1b2faf10e93a1f8f26343ef56c4b35d5bf6cb8cd9ff45177e1ea070f0d4fe88887");
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    public void should_put_new_details_into_a_pass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Map<String, Object> pass = new HashMap<>();
        pass.put("logoText", "Put Example Loyalty");
        pass.put("organizationName", "Put my org");
        pass.put("description", "Put this is a loyalty card");
        pass.put("expiration", "2025-12-01T23:59:59Z");
        pass.put("memberName", "Put Victoria");
        pass.put("specialOffer", "Put Free Drinks at 4:30PM!");
        pass.put("loyaltyLevel", "put level one");
        pass.put("barcode", "www.put.com");
        Pass originalResponseBody = response.getResponseBody();
        Pass newPassResponseBody = Pass.put("Name.a", originalResponseBody.getSerialNumber(), pass).getResponseBody();
        assertThat(originalResponseBody.getSerialNumber()).isEqualTo(newPassResponseBody.getSerialNumber());
        assertThat(newPassResponseBody.getPass()).containsAllEntriesOf(pass);
    }

    @Test
    public void should_delete_a_pass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        Pass.delete("Name.a", responseBody.getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("Name.a", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    public void should_force_delete_a_pass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        Pass.deleteForce("Name.a", responseBody.getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("Name.a", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    public void should_fail_with_invalid_pass_credentials() {
        Passninja.init("", "");
        Assertions.assertThrows(AuthenticationException.class, () -> Pass.create("Name.a", new HashMap<>()));
    }
}
