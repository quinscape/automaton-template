package de.quinscape.automatontemplate;

import de.quinscape.automatontemplate.runtime.AutomatonTemplateApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
    // prevent websocket errors
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(classes = {
    AutomatonTemplateApplication.class
})
//@EnabledIf(
//    expression ="#{ 'integration-test'.equals(systemProperties['spring.profiles.active']) }"
//)

//XXX: deactivated because it is slow and we have no context-requiring tests yet but keep this as example of how to do it.
@Disabled
public class AutomatonTemplateApplicationTests
{

    @Test
    public void contextLoads()
    {
    }

}
