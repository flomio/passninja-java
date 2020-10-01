package com.passninja.model;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.passninja.BaseTest;
import com.passninja.net.PassninjaResponse;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;

public class PassTest extends BaseTest {

    @Test
    public void createPass() throws Exception {
        PassninjaResponse<Pass> response = Pass.create("Name.a", new HashMap<>());

        Pass pass = response.getResponseBody();

        assertEquals(200, response.getResponseCode());
        assertThat(pass.getUrls(), instanceOf(Map.class));
    }

    @Test(expected = ValueInstantiationException.class)
    public void getPass() throws Exception {
        PassninjaResponse<Pass> response = new Pass.RequestBuilder()
              .setPassType("Name.a").setPass(new HashMap<>()).create();

        assertEquals(200, response.getResponseCode());
        PassninjaResponse<PassCollection> value = Pass.get();
    }

}
