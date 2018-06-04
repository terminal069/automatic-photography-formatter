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
	hideTableBody('datePatternTableHeaderId', 'datePatternTableBodyId', 'datePatternTableHeaderTextId', 'Patrones de fecha');
	hideTableBody('datePatternExamplesTableHeaderId', 'datePatternExamplesTableBodyId', 'datePatternExamplesTableHeaderTextId', 'Ejemplos de patrones de fecha');
}

/**
 * Shows the body of the table and changes properties to hide the body when header is clicked
 * 
 * @param headerId Header id
 * @param bodyId Body id
 * @param textId Text id
 * @param headerText Header text
 */
function showTableBody(headerId, bodyId, textId, headerText)
{
	$("#" + bodyId).fadeIn();
	$("#" + textId).html("&#9650; " + headerText + " &#9650;");
	document.getElementById(headerId).onclick = function() {
		hideTableBody(headerId, bodyId, textId, headerText);
	};
}

/**
 * Hides the body of the table and changes properties to show the body when header is clicked
 * 
 * @param headerId Header id
 * @param bodyId Body id
 * @param textId Text id
 * @param headerText Header text
 */
function hideTableBody(headerId, bodyId, textId, headerText)
{
	$("#" + bodyId).fadeOut();
	$("#" + textId).html("&#9660; " + headerText + " &#9660;");
	document.getElementById(headerId).onclick = function() {
		showTableBody(headerId, bodyId, textId, headerText);
	};
}

/**
 * Loads pattern selected
 *  
 * @param patternType Pattern type
 */
function loadPatternPopup(patternType)
{
	var patron;
	
	if (patternType == "EXIF")
	{
		patron = "yyyy:MM:dd HH:mm:ss";
	}
	else if (patternType == "AND_EST")
	{
		patron = "'IMG'_yyyyMMdd_HHmmss'.jpg'";
	}
	else if (patternType == "AND_MILI")
	{
		patron = "'IMG'_yyyyMMdd_HHmmssSSS'.jpg'";
	}
	else if (patternType == "IPHONE_ESP")
	{
		patron = "'Foto' dd-M-yy HH mm ss'.jpg'";
	}
	else if (patternType == "IPHONE_PUN")
	{
		patron = "yyyy-MM-dd HH.mm.ss'.jpg'";
	}
	else if (patternType == "SALIDA_EST")
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
