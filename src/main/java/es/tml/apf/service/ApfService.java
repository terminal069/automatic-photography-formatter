package es.tml.apf.service;

import es.tml.apf.service.dto.ApfServiceIDTO;
import es.tml.apf.service.dto.ApfServiceODTO;

public interface ApfService {
	
	ApfServiceODTO format(ApfServiceIDTO serviceIDTO);
}
