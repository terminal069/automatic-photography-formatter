package es.tml.apf.service;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import es.tml.apf.exception.ApfException;
import es.tml.apf.service.dto.ApfServiceIDTO;
import es.tml.apf.service.dto.ApfServiceODTO;
import es.tml.apf.util.ApfMessageResolver;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ApfServiceImpl implements ApfService {
	
	private static final String DATE_TIME_TAG = "Date/Time";
    private static final Integer OK = 1;
	private static final Integer KO = -1;
	
	@Autowired
	private ApfMessageResolver messageResolver;

	@Override
	public ApfServiceODTO format(ApfServiceIDTO serviceIDTO) {

		SimpleDateFormat inputSdf = new SimpleDateFormat(serviceIDTO.getInputFormat());
		SimpleDateFormat outputSdf = new SimpleDateFormat(serviceIDTO.getOutputFormat());
		
		String[] fileNames = new File(serviceIDTO.getInputFolder()).list();
		
		List<Pair<Integer, String>> results = new ArrayList<>();
		
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
						conversionDate = getDateByExif(inputSdf, serviceIDTO.getInputFolder(), fileName);
						break;
					}
					default: {
						throw new ApfException(messageResolver.getMessage(
						        "error.unknownConversionType",
						        new Object[] { serviceIDTO.getConversionType().name() }));
					}
				}
				
				newFileName = outputSdf.format(conversionDate);
				File file = new File(serviceIDTO.getInputFolder().concat(fileName));
				
				if (!file.renameTo(new File(serviceIDTO.getOutputFolder().concat(newFileName)))) {
				    throw new ApfException(messageResolver.getMessage(
				            "error.renameFile",
				            new Object[] { fileName, newFileName }));
				}
				
				log.debug("File '{}' properly renamed to '{}'", fileName, newFileName);
				
				results.add(Pair.of(OK, null));
			}
			catch(Exception e) {
				log.error(e.getMessage());
				results.add(Pair.of(KO, e.getMessage()));
			}
		});
		
		return ApfServiceODTO.builder()
		        .results(getResults(results))
		        .errors(getErrors(results))
		        .build();
	}
	
    @SneakyThrows
	private Date getDateByName(SimpleDateFormat inputSdf, String fileName) {
		
		return inputSdf.parse(fileName);
	}
	
	@SneakyThrows
	private Date getDateByExif(SimpleDateFormat inputSdf, String filePath, String fileName) {
		
	    Metadata metadata = ImageMetadataReader.readMetadata(new File(filePath.concat(fileName)));
	    
	    for (Directory directory : metadata.getDirectories()) {
	        for (Tag tag : directory.getTags()) {
	            if (DATE_TIME_TAG.equals(tag.getTagName())) {
	                return inputSdf.parse(tag.getDescription());
	            }
	        }
	    }
	            
		throw new ApfException(messageResolver.getMessage("error.exifTag.NotFound", new Object[] { fileName }));
	}
	
	private List<String> getResults(List<Pair<Integer, String>> results) {

	    return Arrays.asList(
	            messageResolver.getMessage("results.totalOK", new Object[] {
	                results.stream()
                        .filter(result -> result.getKey().equals(OK))
                        .count() }),
	            messageResolver.getMessage("results.totalKO", new Object[] {
                    results.stream()
                        .filter(result -> result.getKey().equals(KO))
                        .count() }),
	            messageResolver.getMessage("results.totalProcessed", new Object[] {
                    results.size() }));
    }
	
	private List<String> getErrors(List<Pair<Integer, String>> results) {

	    return results.stream()
	            .filter(result -> result.getKey().equals(KO))
	            .map(Pair::getValue)
	            .collect(Collectors.toList());
    }
}
