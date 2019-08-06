package es.tml.apf.service;

import es.tml.apf.service.dto.FormatterServiceIDTO;
import es.tml.apf.service.dto.FormatterServiceODTO;

public interface FormatterService {
	
	FormatterServiceODTO format(FormatterServiceIDTO idto);
}
