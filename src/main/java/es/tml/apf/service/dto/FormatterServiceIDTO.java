package es.tml.apf.service.dto;

import es.tml.apf.util.type.ConversionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormatterServiceIDTO {

	private ConversionType conversionType;

	private String inputFolder;

	private String inputFormat;

	private String outputFolder;

	private String outputFormat;
}
