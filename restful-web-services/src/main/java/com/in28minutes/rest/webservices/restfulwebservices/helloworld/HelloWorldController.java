package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    //@GetMapping - I can directly use this too instead of Request mapping. (Much more readable.)
    //@RequestMapping(method = RequestMethod.GET, path = "/Hello-world")
    @GetMapping(path = "/Hello-world")
    public String helloworld(){
        return "Hello world";
    }

    // using bean.
    @GetMapping(path = "/Hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");
    }

    // path parameter
    @GetMapping(path = "/Hello-world/Path-variable/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable String name){
        return new HelloWorldBean(String.format("hello world, %s",name));
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/Hello-world")
    @GetMapping(path = "/Hello-world-Internationalized")
    public String helloWorldInternationalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message",null,"default message",locale);
        //return " Hello world V2";
    }

}
