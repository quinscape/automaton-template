package de.quinscape.automatontemplate;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class TestCase
{
    private final static Logger log = LoggerFactory.getLogger(TestCase.class);

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @Test
    public void testPW() throws Exception
    {
        pw("admin");
        pw("user_a");
        pw("user_b");
        pw("user_c");
    }

    private void pw(String name)
    {
        log.info("INSERT INTO app_user (id,login,password,roles,created, last_login) VALUES ('{}', '{}', '{}', '{}', now(), now());", UUID.randomUUID().toString(), name, encoder.encode(name), "ROLE_" + name.toUpperCase());
    }
}
