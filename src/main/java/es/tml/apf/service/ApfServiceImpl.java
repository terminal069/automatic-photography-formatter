package es.tml.apf.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import es.tml.apf.exception.ApfException;
import es.tml.apf.service.dto.ApfServiceIDTO;
import es.tml.apf.service.dto.ApfServiceODTO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApfServiceImpl implements ApfService {
	
	private static final Integer OK = 1;
	private static final Integer KO = -1;

	@Override
	public ApfServiceODTO format(ApfServiceIDTO serviceIDTO) {

		SimpleDateFormat inputSdf = new SimpleDateFormat(serviceIDTO.getInputFormat());
		SimpleDateFormat outputSdf = new SimpleDateFormat(serviceIDTO.getOutputFormat());
		
		String[] fileNames = new File(serviceIDTO.getInputFolder()).list();
		
		List<Integer> results = new ArrayList<>();
		
		Arrays.stream(fileNames).forEach(fileName -> {
			
			log.debug("Formatting file: '{}'", fileName);
			
			String newFileName = "";
			Date conversionDate = null;
			
			try {
				switch(serviceIDTO.getConversionType()) {
					case BY_NAME: {
						conversionDate = getDateByName(inputSdf, fileName);
						break;
					}
					case EXIF: {
						conversionDate = getDateByExif(inputSdf, serviceIDTO.getInputFolder().concat(fileName));
						break;
					}
					default: {
						throw new ApfException("");
					}
				}
			}
			catch(Exception e) {
				
			}
		});
		
		return ApfServiceODTO.builder().build();
	}
	
	@SneakyThrows
	private Date getDateByName(SimpleDateFormat inputSdf, String fileName) {
		
		return inputSdf.parse(fileName);
	}
	
	private Date getDateByExif(SimpleDateFormat inputSdf, String filePath) {
		
		return null;
	}
}
