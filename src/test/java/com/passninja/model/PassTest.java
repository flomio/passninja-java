package com.passninja.model;

import com.passninja.Passninja;
import com.passninja.exception.AuthenticationException;
import com.passninja.net.PassninjaResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


public class PassTest {

    @BeforeEach
    void setup() {
        Passninja.init("69d78c80-1e8e-482b-8f17-15027f28facc", "zIyRpESRkD7Ny1jxiNKYs3qHthW0EtSm9hKWL8G5");
    }


    @Test
    @DisplayName("Should create a new pass")
    public void pass_is_created_successfully() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        Pass pass = response.getResponseBody();
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        assertThat(pass.getUrls()).as("response contains urls").containsKeys("landing", "google", "apple");
    }

    @Test
    @DisplayName("Should retrieve a pass by serial number")
    public void pass_can_be_retrieved_by_serial_number() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        PassninjaResponse<Pass> value = Pass.get("Name.a", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    @DisplayName("Should put new details into a pass")
    public void putPass() throws Exception {
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
    @DisplayName("Should delete a pass")
    public void deletePass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        Pass.delete("Name.a", responseBody.getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("Name.a", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    @DisplayName("Should force delete a pass")
    public void deleteForcePass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        Pass responseBody = response.getResponseBody();
        Pass.deleteForce("Name.a", responseBody.getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("Name.a", responseBody.getSerialNumber());
        assertThat(responseBody.getSerialNumber()).isEqualTo(value.getResponseBody().getSerialNumber());
    }

    @Test
    @DisplayName("Should fail when supplied with invalid pass credentials")
    public void notValidKey() {
        Passninja.init("", "");
        Assertions.assertThrows(AuthenticationException.class, () -> Pass.create("Name.a", new HashMap<>()));
    }
}
