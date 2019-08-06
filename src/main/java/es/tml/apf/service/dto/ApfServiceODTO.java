package es.tml.apf.service.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApfServiceODTO {

	private List<String> results;
	
	private List<String> errors;
}
