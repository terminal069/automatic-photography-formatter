package es.tml.apf.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController extends CommonController {

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		
		return "welcome";
	}
}
