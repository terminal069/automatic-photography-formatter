package es.tml.apf.controller.validator;

import es.tml.apf.controller.dto.ApfRequest;

public interface ApfControllerValidator {

	void validateRequest(ApfRequest request);
}
