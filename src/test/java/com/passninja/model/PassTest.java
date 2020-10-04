package com.passninja.model;

import com.passninja.BaseTest;
import com.passninja.Passninja;
import com.passninja.exception.AuthenticationException;
import com.passninja.net.PassninjaResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class PassTest extends BaseTest {

    @Test
    public void createPass() throws Exception {
        super.beforeClass();
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        Pass pass = response.getResponseBody();

        assertEquals(200, response.getResponseCode());
        assertThat(pass.getUrls(), instanceOf(Map.class));
    }

    @Test
    public void getPass() throws Exception {
        super.beforeClass();
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertEquals(200, response.getResponseCode());
        PassninjaResponse<Pass> value = Pass.get("Name.a", response.getResponseBody().getSerialNumber());
        assertEquals(response.getResponseBody().getSerialNumber(), value.getResponseBody().getSerialNumber());
    }

    @Test
    public void putPass() throws Exception {
        super.beforeClass();
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        assertEquals(200, response.getResponseCode());
        Map<String, Object> pass = new HashMap<>();
        pass.put("logoText", "Put Example Loyalty");
        pass.put("organizationName", "Put my org");
        pass.put("description", "Put this is a loyalty card");
        pass.put("expiration", "2025-12-01T23:59:59Z");
        pass.put("memberName", "Put Victoria");
        pass.put("specialOffer", "Put Free Drinks at 4:30PM!");
        pass.put("loyaltyLevel", "put level one");
        pass.put("barcode", "www.put.com");
        PassninjaResponse<Pass> value = Pass.put("Name.a", response.getResponseBody().getSerialNumber(), pass);
        assertEquals(response.getResponseBody().getSerialNumber(), value.getResponseBody().getSerialNumber());
        assertEquals("Put Free Drinks at 4:30PM!", value.getResponseBody().getPass().get("specialOffer"));
    }

    @Test
    public void deletePass() throws Exception {
        super.beforeClass();
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());
        assertEquals(200, response.getResponseCode());
        Pass.delete("Name.a", response.getResponseBody().getSerialNumber());
        PassninjaResponse<Pass> value = Pass.get("Name.a", response.getResponseBody().getSerialNumber());
        assertEquals(response.getResponseBody().getSerialNumber(), value.getResponseBody().getSerialNumber());
    }

    @Test
    public void notValidKey() throws Exception {
        Passninja.init("", "");
        try
        {
            Pass.create("Name.a", new HashMap<>());
            fail("AuthenticationException is expected");
        } catch (AuthenticationException expected) {
            expected.getStatusCode();
            expected.getMessage();
        }
    }

}
