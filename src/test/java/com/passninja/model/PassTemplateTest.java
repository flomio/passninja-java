package com.passninja.model;

import com.passninja.Passninja;
import com.passninja.net.PassninjaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PassTemplateTest {

    @BeforeEach
    void setup() {
        Passninja.init("aid_0x2", "d5247644c316194d9089e23766e08ea9");
    }

    @Test
    void find_pass_template() throws Exception {
        PassninjaResponse<PassTemplate> response = PassTemplate.find("ptk_0x2");
        PassTemplate passTemplate = response.getResponseBody();
        assertThat(response.getResponseCode()).as("check successful response").isEqualTo(200);
        assertThat(passTemplate.getName()).as("response name").isEqualTo("Starbucks Rewards");
        assertThat(passTemplate.getPlatform()).as("response platform").isEqualTo("apple");
    }
}
