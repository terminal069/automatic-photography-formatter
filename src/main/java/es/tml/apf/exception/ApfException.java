package es.tml.apf.exception;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class ApfException extends RuntimeException {

	private static final long serialVersionUID = -6174743605879258163L;

	private final List<String> errors;
	
	public ApfException(List<String> errors) {
		
		super();
		this.errors = errors;
	}
}
