package es.tml.apf.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.tml.apf.controller.dto.FormatterRequest;
import es.tml.apf.controller.transformer.FormatterControllerTransformer;
import es.tml.apf.controller.validator.FormatterControllerValidator;
import es.tml.apf.service.FormatterService;
import es.tml.apf.service.dto.FormatterServiceIDTO;
import es.tml.apf.service.dto.FormatterServiceODTO;
import es.tml.apf.util.ApfTemplates;
import es.tml.apf.util.type.ConversionType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormatterController extends CommonController {
	
	@Autowired
	private FormatterControllerValidator formatterControllerValidator;
	
	@Autowired
	private FormatterControllerTransformer formatterControllerTransformer;
	
	@Autowired
	private FormatterService formatterService;

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
		
		FormatterServiceIDTO idto = formatterControllerTransformer.toServiceIDTO(formatterRequest);
		
		FormatterServiceODTO odto = formatterService.format(idto);
		
		formatterRequest.setResults(odto.getResults());
		formatterRequest.setErrors(odto.getErrors());
		
		return ApfTemplates.FORMATTER_TEMPLATE;
	}
	
}
