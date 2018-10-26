package es.tml.apf.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.tml.apf.util.ApfTemplates;

@Controller
public class WelcomeController extends CommonController {

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		
		return ApfTemplates.WELCOME_TEMPLATE;
	}
}
