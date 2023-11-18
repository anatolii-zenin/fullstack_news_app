package com.mjc.school.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.mjc.school.service", "com.mjc.school.controller"})
public class ControllerSpringConfig {
}
