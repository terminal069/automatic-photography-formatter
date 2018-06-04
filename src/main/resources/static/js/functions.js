/**
 * After loading the page
 */
$(document).ready(function()
{
	checkConversionType();
	checkResults();
	checkErrors();
});

/**
 * Checks if conversion type is by EXIF label to set its output pattern automatically
 */
function checkConversionType()
{
	if ($("#conversionTypeId").val() == "EXIF")
	{
		$("#inputFormatId").val("yyyy:MM:dd HH:mm:ss");
	}
}

/**
 * Checks if there are results to show
 */
function checkResults()
{
	if ($("#resultContainerId").height() > 300)
	{
		$("#resultId").height(300);
		$("#resultId").css('overflow','auto')
	}
	
	if ($("#resultId").html().trim() != "")
	{
		$("#resultContainerId").fadeIn();
	}
}

/**
 * Checks if there are errors to show
 */
function checkErrors()
{
	if ($("#errorContainerId").height() > 300)
	{
		$("#errorId").height(300);
		$("#errorId").css('overflow','auto')
	}
	
	if ($("#errorId").html().trim() != "")
	{
		$("#errorContainerId").fadeIn();
	}
}

/**
 * Shows format pattern help popup
 *  
 * @param formatId Id of the format pattern
 */
function showFormatterHelp(formatId)
{
	$("#formatterHelpId").val(formatId);
	$("#formatterHelpContainerId").fadeIn();
}

/**
 * Hides pattern format help popup
 */
function hideFormatterHelp()
{
	$("#formatterHelpContainerId").fadeOut();
	$("#formatterHelpId").val("");
	$("#patternLoaderSelectId").val("0");
	$("#selectedPatternId").val("");
	ocultarCuerpoTabla('datePatternTableHeaderId', 'datePatternTableBodyId', 'datePatternTableHeaderTextId', 'Patrones de fecha');
	ocultarCuerpoTabla('datePatternExamplesTableHeaderId', 'datePatternExamplesTableBodyId', 'datePatternExamplesTableHeaderTextId', 'Ejemplos de patrones de fecha');
}

/**
 * Shows the body of the table and changes properties to hide the body when header is clicked
 * 
 * @param cabeceraId Header id
 * @param cuerpoId Body id
 * @param textoId Text id
 * @param texto Header text
 */
function showTableBody(cabeceraId, cuerpoId, textoId, texto)
{
	$("#" + cuerpoId).fadeIn();
	$("#" + textoId).html("&#9650; " + texto + " &#9650;");
	document.getElementById(cabeceraId).onclick = function() {
		ocultarCuerpoTabla(cabeceraId, cuerpoId, textoId, texto);
	};
}

/**
 * Hides the body of the table and changes properties to show the body when header is clicked
 * 
 * @param cabeceraId Header id
 * @param cuerpoId Body id
 * @param textoId Text id
 * @param texto Header text
 */
function ocultarCuerpoTabla(cabeceraId, cuerpoId, textoId, texto)
{
	$("#" + cuerpoId).fadeOut();
	$("#" + textoId).html("&#9660; " + texto + " &#9660;");
	document.getElementById(cabeceraId).onclick = function() {
		showTableBody(cabeceraId, cuerpoId, textoId, texto);
	};
}

/**
 * Loads pattern selected
 *  
 * @param tipoPatron Pattern type
 */
function loadPatternPopup(tipoPatron)
{
	var patron;
	
	if (tipoPatron == "EXIF")
	{
		patron = "yyyy:MM:dd HH:mm:ss";
	}
	else if (tipoPatron == "AND_EST")
	{
		patron = "'IMG'_yyyyMMdd_HHmmss'.jpg'";
	}
	else if (tipoPatron == "AND_MILI")
	{
		patron = "'IMG'_yyyyMMdd_HHmmssSSS'.jpg'";
	}
	else if (tipoPatron == "IPHONE_ESP")
	{
		patron = "'Foto' dd-M-yy HH mm ss'.jpg'";
	}
	else if (tipoPatron == "IPHONE_PUN")
	{
		patron = "yyyy-MM-dd HH.mm.ss'.jpg'";
	}
	else if (tipoPatron == "SALIDA_EST")
	{
		patron = "yyyyMMdd_HHmmss'.jpg'";
	}
	else
	{
		patron = "";
	}
	
	$("#selectedPatternId").val(patron);
}

/**
 * Loads the pattern into the selected pattern format 
 */
function loadPattern()
{
	$("#" + $("#formatterHelpId").val()).val($("#selectedPatternId").val());
	hideFormatterHelp();
}
