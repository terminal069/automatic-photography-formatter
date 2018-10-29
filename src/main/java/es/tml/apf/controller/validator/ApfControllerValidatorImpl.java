package es.tml.apf.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.tml.apf.controller.dto.ApfRequest;
import es.tml.apf.exception.ApfException;
import es.tml.apf.util.ApfMessageResolver;

@Component
public class ApfControllerValidatorImpl implements ApfControllerValidator {

	private static final String MESSAGE_ERROR_NOT_EMPTY = "error.notEmpty";
	private static final String MESSAGE_LABEL_CONVERSION_TYPE = "formatter.label.conversionType";
	private static final String MESSAGE_LABEL_INPUT_FOLDER = "formatter.label.inputFolder";
	private static final String MESSAGE_LABEL_INPUT_FORMAT = "formatter.label.inputFormat";
	private static final String MESSAGE_LABEL_OUTPUT_FOLDER = "formatter.label.outputFolder";
	private static final String MESSAGE_LABEL_OUTPUT_FORMAT= "formatter.label.outputFormat";
	
	@Autowired
	private ApfMessageResolver messageResolver;
	
	@Override
	public void validateRequest(ApfRequest request) {

		List<String> errors = new ArrayList<>();
		
		if (Objects.isNull(request.getConversionType())) {
			addError(errors, MESSAGE_ERROR_NOT_EMPTY, MESSAGE_LABEL_CONVERSION_TYPE);
		}
		
		if (StringUtils.isBlank(request.getInputFolder())) {
			addError(errors, MESSAGE_ERROR_NOT_EMPTY, MESSAGE_LABEL_INPUT_FOLDER);
		}
		
		if (StringUtils.isBlank(request.getInputFormat())) {
			addError(errors, MESSAGE_ERROR_NOT_EMPTY, MESSAGE_LABEL_INPUT_FORMAT);
		}
		
		if (StringUtils.isBlank(request.getOutputFolder())) {
			addError(errors, MESSAGE_ERROR_NOT_EMPTY, MESSAGE_LABEL_OUTPUT_FOLDER);
		}
		
		if (StringUtils.isBlank(request.getOutputFormat())) {
			addError(errors, MESSAGE_ERROR_NOT_EMPTY, MESSAGE_LABEL_OUTPUT_FORMAT);
		}
		
		if (!errors.isEmpty()) {
			throw new ApfException(errors);
		}
	}

	private void addError(List<String> errors, String error, String field) {
		
		errors.add(messageResolver.getMessage(error,
				field == null ? null : new Object[] { messageResolver.getMessage(field, null) }));
	}
}
