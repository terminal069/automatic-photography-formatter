package es.tml.apf.exception;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import es.tml.apf.controller.CommonController;
import es.tml.apf.util.ApfTemplates;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApfErrorController extends CommonController {

	@ExceptionHandler(value = ApfException.class)
	public String handleApfException(ApfException e, Model model) {
		
		Optional.of(e)
			.map(ApfException::getErrors)
			.orElse(Collections.emptyList())
			.forEach(log::error);
		
		model.addAttribute("errors", e.getErrors());
		setFooter(model);
		
		return ApfTemplates.ERROR_TEMPLATE;
	}
	
	@ExceptionHandler(value = Exception.class)
	public String handleGenericException(Exception e, Model model) {
		
		log.error("Error: ", e);
		
		model.addAttribute("errors", Arrays.asList(e.getMessage()));
		setFooter(model);
		
		return ApfTemplates.ERROR_TEMPLATE;
	}
	
	private void setFooter(Model model) {
		
		model.addAttribute("footerYear", getFooterYear());
	}
}
