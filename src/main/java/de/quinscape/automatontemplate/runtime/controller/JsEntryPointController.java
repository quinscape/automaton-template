package de.quinscape.automatontemplate.runtime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JsEntryPointController
{
    // TODO: change application base URI and entry point name (see webpack.config.js)
    @RequestMapping("/myapp/**")
    public String serveTodo()
    {
        return "myapp";
    }
}
