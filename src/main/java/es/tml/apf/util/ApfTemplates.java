package es.tml.apf.util;

public abstract class ApfTemplates {

	public static final String FORMATTER_TEMPLATE = "formatter";
	public static final String WELCOME_TEMPLATE = "welcome";
	public static final String ERROR_TEMPLATE = "error";
	
	private ApfTemplates() {
		
		throw new IllegalStateException("Utility class");
	}
}
