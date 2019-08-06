package es.tml.apf.controller.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApfRequest {

	private ConversionType conversionType;
	
	private String inputFolder;
	
	private String inputFormat;
	
	private String outputFolder;
	
	private String outputFormat;
	
	private List<String> results;
	
	private List<String> errors;
}
