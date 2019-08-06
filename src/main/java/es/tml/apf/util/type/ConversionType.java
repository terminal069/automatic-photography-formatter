package es.tml.apf.util.type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ConversionType {

	BY_NAME("name"),
	EXIF("EXIF");
	
	private String propertyDescription;
	
	private ConversionType(String propertyDescription) {
		
		this.propertyDescription = propertyDescription;
	}
	
	public String getPropertyDescription() {
		
		return this.propertyDescription;
	}
	
	public static Map<String, String> getConversionTypeMapped() {
		
		Map<String, String> conversionTypeMap = new HashMap<>();
		
		Arrays.stream(ConversionType.values()).forEach(conversionType ->
				conversionTypeMap.put(conversionType.name(), conversionType.getPropertyDescription()));

		return conversionTypeMap;
	}
}
