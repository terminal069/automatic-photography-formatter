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
		$("#formatoEntradaId").val("yyyy:MM:dd HH:mm:ss");
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
 * Checks if there are errors to show them
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
 * Shows pattern format help popup
 *  
 * @param idFormato Id of the pattern format
 */
function mostrarAyudaFormato(idFormato)
{
	$("#ayudaFormatoId").val(idFormato);
	$("#contenedorAyudaFormatoId").fadeIn();
}

/**
 * Hides pattern format help popup
 */
function ocultarAyudaFormato()
{
	$("#contenedorAyudaFormatoId").fadeOut();
	$("#ayudaFormatoId").val("");
	$("#selectCargarPatronId").val("0");
	$("#patronSeleccionadoId").val("");
	ocultarCuerpoTabla('cabeceraTablaPatronesFechaId', 'cuerpoTablaPatronesFechaId', 'textoCabeceraTablaPatronesFechaId', 'Patrones de fecha');
	ocultarCuerpoTabla('cabeceraTablaEjemplosPatronesFechaId', 'cuerpoTablaEjemplosPatronesFechaId', 'textoCabeceraTablaEjemplosPatronesFechaId', 'Ejemplos de patrones de fecha');
}

/**
 * Shows the body of the table and changes properties to hide the body when header is clicked
 * 
 * @param cabeceraId Header id
 * @param cuerpoId Body id
 * @param textoId Text id
 * @param texto Header text
 */
function mostrarCuerpoTabla(cabeceraId, cuerpoId, textoId, texto)
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
		mostrarCuerpoTabla(cabeceraId, cuerpoId, textoId, texto);
	};
}

/**
 * Loads pattern selected
 *  
 * @param tipoPatron Pattern type
 */
function cargarPatronPopup(tipoPatron)
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
	
	$("#patronSeleccionadoId").val(patron);
}

/**
 * Loads the pattern into the selected pattern format 
 */
function cargarPatron()
{
	$("#" + $("#ayudaFormatoId").val()).val($("#patronSeleccionadoId").val());
	ocultarAyudaFormato();
}
