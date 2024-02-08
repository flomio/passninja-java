package com.passninja.model;

import com.passninja.Passninja;
import com.passninja.exception.AuthenticationException;
import com.passninja.net.PassninjaResponse;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PassTest {

    @BeforeEach
    void setup() {
        Passninja.init("aid_0x2", "d5247644c316194d9089e23766e08ea9");
    }

    @Test
    public void should_create_a_new_pass() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);
        Pass pass = response.getResponseBody();
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        assertThat(pass.getUrls()).as("response contains urls").containsKeys("landing");
    }

    @Test
    public void should_retrieve_a_pass_by_serial_number() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        PassninjaResponse<Pass> value = Pass.get("ptk_0x2", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    public void should_retrieve_passes() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        PassninjaResponse<Passes> value = Pass.find("ptk_0x2");
        assertThat(
                value.getResponseBody().getPasses().stream()
                        .anyMatch(item -> Objects.equals(item.getSerialNumber(), responseBody.getSerialNumber()))).isEqualTo(true);
    }

    @Test
    public void should_decrypt_pass() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        PassninjaResponse<PassPayload> value = Pass.decrypt("ptk_0x2",
            "55166a9700250a8c51382dd16822b0c763136090b91099c16385f2961b7d9392d31b386cae"
            + "133dca1b2faf10e93a1f8f26343ef56c4b35d5bf6cb8cd9ff45177e1ea070f0d4fe88887");
        assertThat(value.getResponseBody().getDecrypted()).startsWith("founder-id:");
    }

    @Test
    public void should_put_new_details_into_a_pass() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Map<String, Object> pass = new HashMap<>();
        pass.put("nfc-message", "fill me in");
        pass.put("member-name", "fill me in");
        Pass originalResponseBody = response.getResponseBody();
        Pass newPassResponseBody = Pass.put("ptk_0x2", originalResponseBody.getSerialNumber(), pass).getResponseBody();
        assertThat(originalResponseBody.getSerialNumber()).isEqualTo(newPassResponseBody.getSerialNumber());
        assertThat(newPassResponseBody.getPass()).isNull();
    }

    @Test
    @Disabled
    public void should_delete_a_pass() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        Pass.delete("ptk_0x2", responseBody.getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("ptk_0x2", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    @Disabled
    public void should_force_delete_a_pass() throws Exception {
        Map<String, Object> inputPass = new HashMap<>();
        inputPass.put("nfc-message", "fill me in");
        inputPass.put("member-name", "fill me in");
        PassninjaResponse<Pass> response = Pass.create("ptk_0x2", inputPass);
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        Pass.deleteForce("ptk_0x2", responseBody.getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("ptk_0x2", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    public void should_fail_with_invalid_pass_credentials() {
        Passninja.init("", "");
        Assertions.assertThrows(AuthenticationException.class, () -> Pass.create("ptk_0x2", new HashMap<>()));
    }
}
