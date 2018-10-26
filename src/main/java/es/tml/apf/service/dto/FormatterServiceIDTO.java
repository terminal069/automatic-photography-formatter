package es.tml.apf.service.dto;

import es.tml.apf.controller.dto.ConversionType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormatterServiceIDTO {

	private ConversionType conversionType;

	private String inputFolder;

	private String inputFormat;

	private String outputFolder;

	private String outputFormat;
}
