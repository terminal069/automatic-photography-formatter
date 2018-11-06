package es.tml.apf.controller.transformer;

import org.springframework.stereotype.Component;

import es.tml.apf.controller.dto.ApfRequest;
import es.tml.apf.service.dto.ApfServiceIDTO;
import es.tml.apf.service.dto.ApfServiceODTO;

@Component
public class ApfControllerTransformerImpl implements ApfControllerTransformer {

	@Override
	public ApfServiceIDTO toServiceIDTO(ApfRequest apfRequest) {

		return ApfServiceIDTO.builder()
				.conversionType(apfRequest.getConversionType())
				.inputFolder(apfRequest.getInputFolder().endsWith("\\") ?
						apfRequest.getInputFolder() : apfRequest.getInputFolder().concat("\\"))
				.inputFormat(apfRequest.getInputFormat())
				.outputFolder(apfRequest.getOutputFolder().endsWith("\\") ?
						apfRequest.getOutputFolder() : apfRequest.getOutputFolder().concat("\\"))
				.outputFormat(apfRequest.getOutputFormat())
				.build();
	}

	@Override
	public ApfRequest toApfRequest(ApfServiceODTO odto, ApfRequest apfRequest) {

		return ApfRequest.builder()
		        .conversionType(apfRequest.getConversionType())
		        .inputFolder(apfRequest.getInputFolder())
		        .inputFormat(apfRequest.getInputFormat())
		        .outputFolder(apfRequest.getOutputFolder())
		        .outputFormat(apfRequest.getOutputFormat())
				.results(odto.getResults())
				.errors(odto.getErrors())
				.build();
	}
}
