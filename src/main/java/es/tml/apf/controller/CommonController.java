package es.tml.apf.controller;

import java.util.Calendar;

import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class CommonController {

	private static final String FOOTER = "\u00A9 %s - ";
	
	@ModelAttribute("footerYear")
	protected String getFooterYear() {
		
		return String.format(FOOTER, Calendar.getInstance().get(Calendar.YEAR));
	}
}
