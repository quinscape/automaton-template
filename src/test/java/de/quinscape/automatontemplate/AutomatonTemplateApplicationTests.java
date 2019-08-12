package de.quinscape.automatontemplate;

import de.quinscape.automatontemplate.runtime.AutomatonTemplateApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
    AutomatonTemplateApplication.class
})

//XXX: deactivated because it is slow and we have no context-requiring tests yet but keep this as example of how to do it.
@Ignore
public class AutomatonTemplateApplicationTests
{

    @Test
    public void contextLoads()
    {
    }

}
