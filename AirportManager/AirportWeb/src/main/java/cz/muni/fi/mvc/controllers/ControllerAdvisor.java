/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.mvc.controllers;

import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author Sebastian Kupka
 */
@ControllerAdvice
public class ControllerAdvisor {
    
    public ControllerAdvisor() {
        LoggerFactory.getLogger(ControllerAdvisor.class).debug("Controller Advisor Initialized");
    }

     @ExceptionHandler(Exception.class)
     public String handle(Exception ex) {
        return "error";//this is view name
    }
}
