package es.tml.apf.controller.transformer;

import es.tml.apf.controller.dto.FormatterRequest;
import es.tml.apf.service.dto.FormatterServiceIDTO;

public interface FormatterControllerTransformer {

	FormatterServiceIDTO toServiceIDTO(FormatterRequest request);
}
