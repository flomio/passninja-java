package com.passninja.model;

import com.passninja.BaseTest;
import com.passninja.exception.InvalidRequestException;
import com.passninja.net.PassninjaResponse;
import org.junit.Test;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PassTest extends BaseTest {

    //@Test
    public void createPass() throws Exception {
        PassninjaResponse<Pass> response = new Pass.RequestBuilder()
              .setDescription("description").create();

        Pass pass = response.getResponseBody();

        assertEquals(200, response.getResponseCode());
    }
}
