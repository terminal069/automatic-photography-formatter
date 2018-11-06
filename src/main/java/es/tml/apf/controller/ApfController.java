package es.tml.apf.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.tml.apf.controller.dto.ApfRequest;
import es.tml.apf.controller.dto.ConversionType;
import es.tml.apf.controller.transformer.ApfControllerTransformer;
import es.tml.apf.controller.validator.ApfControllerValidator;
import es.tml.apf.service.ApfService;
import es.tml.apf.service.dto.ApfServiceIDTO;
import es.tml.apf.service.dto.ApfServiceODTO;
import es.tml.apf.util.ApfTemplates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ApfController extends CommonController {
	
	@Autowired
	private ApfControllerValidator validator;
	
	@Autowired
	private ApfControllerTransformer transformer;
	
	@Autowired
	private ApfService service;

	@ModelAttribute("conversionType")
	public Map<String, String> getConversionType() {
		
		return ConversionType.getConversionTypeMapped();
	}
	
	@RequestMapping("/init")
	public String init(Model model) {
		
		log.info("Mapping '/init'");
		
		model.addAttribute("apfRequest", ApfRequest.builder().build());
		
		return ApfTemplates.FORMATTER_TEMPLATE;
	}
	
	@PostMapping("/format")
	public String format(@ModelAttribute ApfRequest apfRequest, Model model) {
		
		log.info("Mapping '/format'");
		
		validator.validateRequest(apfRequest);
		
		ApfServiceIDTO serviceIDTO = transformer.toServiceIDTO(apfRequest);
		
		ApfServiceODTO serviceODTO = service.format(serviceIDTO);
		
		model.addAttribute("apfRequest", transformer.toApfRequest(serviceODTO, apfRequest));
		
		return ApfTemplates.FORMATTER_TEMPLATE;
	}
	
}
