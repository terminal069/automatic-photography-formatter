package es.tml.apf.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ApfMessageResolver {
	
	@Autowired
	private MessageSource messageSource;

	public String getMessage(String property, Object[] args) {
		
		return messageSource.getMessage(property, args, Locale.getDefault());
	}
}
