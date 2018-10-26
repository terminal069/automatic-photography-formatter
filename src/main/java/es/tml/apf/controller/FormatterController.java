package es.tml.apf.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.tml.apf.controller.dto.ConversionType;
import es.tml.apf.controller.dto.FormatterRequest;
import es.tml.apf.controller.transformer.FormatterControllerTransformer;
import es.tml.apf.controller.validator.FormatterControllerValidator;
import es.tml.apf.service.dto.FormatterServiceIDTO;
import es.tml.apf.util.ApfTemplates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormatterController extends CommonController {
	
	@Autowired
	private FormatterControllerValidator formatterControllerValidator;
	
	@Autowired
	private FormatterControllerTransformer formatterControllerTransformer;

	@ModelAttribute("conversionType")
	public Map<String, String> getConversionType() {
		
		return ConversionType.getConversionTypeMapped();
	}
	
	@RequestMapping("/init")
	public String init(Model model) {
		
		log.info("Mapping '/init'");
		
		model.addAttribute(FormatterRequest.builder().build());
		
		return ApfTemplates.FORMATTER_TEMPLATE;
	}
	
	@PostMapping("/format")
	public String format(@ModelAttribute FormatterRequest formatterRequest, Model model) {
		
		log.info("Mapping '/format'");
		
		formatterControllerValidator.validateRequest(formatterRequest);
		
		FormatterServiceIDTO serviceIDTO = formatterControllerTransformer.toServiceIDTO(formatterRequest);
		
		List<String> results = Arrays.asList(
				"Total: 12",
				"Total OK: 8",
				"Total KO: 4");
		
		List<String> errors = Arrays.asList(
				"File DSCN12321 not found",
				"File DSCN53532 not found",
				"File DSCN78478 not found",
				"File DSCN12783 not found");
		
		formatterRequest.setResults(results);
		formatterRequest.setErrors(errors);
		
		return ApfTemplates.FORMATTER_TEMPLATE;
	}
	
}
