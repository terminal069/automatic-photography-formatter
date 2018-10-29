package es.tml.apf.controller.transformer;

import es.tml.apf.controller.dto.ApfRequest;
import es.tml.apf.service.dto.ApfServiceIDTO;
import es.tml.apf.service.dto.ApfServiceODTO;

public interface ApfControllerTransformer {

	ApfServiceIDTO toServiceIDTO(ApfRequest request);
	
	ApfRequest toApfRequest(ApfServiceODTO odto);
}
