package es.tml.apf.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import es.tml.apf.service.FormatterService;
import es.tml.apf.service.dto.FormatterServiceIDTO;
import es.tml.apf.service.dto.FormatterServiceODTO;
import es.tml.apf.service.exception.ApfFileException;
import es.tml.apf.util.ApfMessageResolver;
import es.tml.apf.util.type.ConversionType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FormatterServiceImpl implements FormatterService {

	private static final String EXIF_DATE_TIME_TAG = "Date/Time";
	
	private static final String MESSAGE_ERROR_FILE_RENAME = "error.fileRename";
	private static final String MESSAGE_ERROR_EXIF_DATE_TIME_NOT_FOUND = "error.exifDateTimeTag.NotFound";
	
	@Autowired
	private ApfMessageResolver messageResolver;
	
	@Override
	public FormatterServiceODTO format(FormatterServiceIDTO idto) {

		fixData(idto);
		return executeFormatter(idto);
	}
	
	
	private void fixData(FormatterServiceIDTO idto)
	{
		// Directorios
		if (!idto.getInputFolder().endsWith("\\"))
		{
			idto.setInputFolder(idto.getInputFolder().concat("\\"));
		}
		
		if (!idto.getOutputFolder().endsWith("\\"))
		{
			idto.setOutputFolder(idto.getOutputFolder().concat("\\"));
		}
	}
	
	private FormatterServiceODTO executeFormatter(FormatterServiceIDTO idto)
	{
		FormatterServiceODTO odto = FormatterServiceODTO.builder()
				.results(new ArrayList<>())
				.errors(new ArrayList<>())
				.build();
		int total = 0;
		int ok = 0;
		int ko = 0;
		
		// Se obtienen los formatos de las fotos
		SimpleDateFormat inputFormat = new SimpleDateFormat(idto.getInputFormat());
		SimpleDateFormat outputFormat = new SimpleDateFormat(idto.getOutputFormat());
		
		// Se cargan las fotos
		String[] photoFiles = new File(idto.getInputFolder()).list();
		
		String newFileName = "";
		Date conversionDate = null;
		
		for (String fileName : photoFiles)
		{
			try
			{
				total++;
				
				log.debug("Procesando el fichero '{}'", fileName);
				
				// Si se hace conversion con la fecha de la informacion EXIF de la foto
				if (ConversionType.EXIF.equals(idto.getConversionType()))
				{
					conversionDate = getExifDate(inputFormat, idto.getInputFolder() + fileName);
				}
				// Si se hace la conversion por el formato de la fecha en el nombre de fichero
				else
				{
					conversionDate = getNameFormatDate(inputFormat, fileName);
				}
				
				newFileName = outputFormat.format(conversionDate);
				File file = new File(idto.getInputFolder() + fileName);
				
				// Se renombra y se comprueba que se haya renombrado correctamente
				if (!file.renameTo(new File(idto.getOutputFolder() + newFileName)))
				{
					throw new ApfFileException(messageResolver.getMessage(MESSAGE_ERROR_FILE_RENAME, new Object[] { fileName, newFileName }));
				}
				
				log.debug("Fichero '{}' renombrado correctamente a '{}'", fileName, newFileName);
				ok++;
			}
			catch(ApfFileException e)
			{
				odto.getErrors().add(String.format("%s: %s", fileName, e.getMessage()));
				log.error("Error renombrando el fichero '{}' con el formato '{}'", fileName, idto.getInputFormat(), e);
				ko++;
			}
		}
		
		odto.getResults().add("Total: ".concat(String.valueOf(total)));
		odto.getResults().add("Total OK: ".concat(String.valueOf(ok)));
		odto.getResults().add("Total KO: ".concat(String.valueOf(ko)));
		
		return odto;
	}
	
	/**
	 * Obtiene la fecha de la foto a partir del formato del nombre del fichero
	 * 
	 * @param sdfEntrada Formato de entrada
	 * @param nombreFichero Nombre del fichero
	 * @return Fecha de la foto
	 * @throws ApfFileException 
	 */
	private Date getNameFormatDate(SimpleDateFormat sdfEntrada, String nombreFichero) throws ApfFileException
	{
		try {
			return sdfEntrada.parse(nombreFichero);
		} catch (ParseException e) {
			throw new ApfFileException(e.getMessage());
		}
	}

	/**
	 * Obtiene la fecha de la foto a partir de la información EXIF de la misma
	 * 
	 * @param sdfEntrada Formato de entrada
	 * @param rutaNombreFichero Ruta del fichero
	 * @return Fecha de la foto
	 * @throws Exception
	 */
	private Date getExifDate(SimpleDateFormat sdfEntrada, String rutaNombreFichero) throws ApfFileException
	{
		Date fechaConversion = null;
		boolean continuar = true;
		
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(new File(rutaNombreFichero));
	
			for (Directory directory : metadata.getDirectories())
			{
				for (Tag tag : directory.getTags())
				{
					if (tag.getTagName().equals(EXIF_DATE_TIME_TAG))
					{
						fechaConversion = sdfEntrada.parse(tag.getDescription());
						continuar = false;
						break;
					}
				}
				
				if (!continuar)
				{
					break;
				}
			}
		}
		catch (ImageProcessingException | IOException | ParseException e) {
			throw new ApfFileException(e.getMessage());
		}
		
		if (fechaConversion == null)
		{
			throw new ApfFileException(messageResolver.getMessage(MESSAGE_ERROR_EXIF_DATE_TIME_NOT_FOUND, null));
		}
		
		return fechaConversion;
	}
	
}
