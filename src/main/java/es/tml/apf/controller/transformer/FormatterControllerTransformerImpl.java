package es.tml.apf.controller.transformer;

import org.springframework.stereotype.Component;

import es.tml.apf.controller.dto.FormatterRequest;
import es.tml.apf.service.dto.FormatterServiceIDTO;

@Component
public class FormatterControllerTransformerImpl implements FormatterControllerTransformer {

	@Override
	public FormatterServiceIDTO toServiceIDTO(FormatterRequest formatterRequest) {

		return FormatterServiceIDTO.builder()
				.conversionType(formatterRequest.getConversionType())
				.inputFolder(formatterRequest.getInputFolder())
				.inputFormat(formatterRequest.getInputFormat())
				.outputFolder(formatterRequest.getOutputFolder())
				.outputFormat(formatterRequest.getOutputFormat())
				.build();
	}

}
