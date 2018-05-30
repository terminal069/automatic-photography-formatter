package es.tml.apf.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import es.tml.apf.enums.ConversionType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormatterController extends CommonController {

	@ModelAttribute("conversionTypes")
	public Map<String, String> getConversionTypes() {
		
		return ConversionType.getConversionTypesMapped();
	}
	
	@RequestMapping("/init")
	public String init(Model model) {
		
		log.info("Mapping '/init'");
		
		initFormatterTemplate(model);
		
		return "formatter";
	}
	
	@RequestMapping("/format")
	public String format(Model model) {
		
		log.info("Mapping '/format'");
		
		initFormatterTemplate(model);
		
		List<String> results = Arrays.asList(
				"Total: 12",
				"Total OK: 8",
				"Total KO: 4");
		
		List<String> errors = Arrays.asList(
				"File DSCN12321 not found",
				"File DSCN53532 not found",
				"File DSCN78478 not found",
				"File DSCN12783 not found");
		
		model.addAttribute("results", results);
		model.addAttribute("errors", errors);
		
		return "formatter";
	}
	
	private void initFormatterTemplate(Model model) {

		model.addAttribute("results", null);
		model.addAttribute("errors", null);
	}
}
