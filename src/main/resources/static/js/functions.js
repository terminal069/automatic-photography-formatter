/**
 * After loading the page
 */
$(document).ready(function() {
	
	checkIOFormat();
	checkResults();
	checkErrors();
});

/**
 * Checks if conversion type is by EXIF label to set its output pattern automatically
 */
function checkIOFormat() {
	
	if ($("#conversionTypeId").val() == "EXIF") {
		$("#inputFormatId").val("yyyy:MM:dd HH:mm:ss");
	}
	
	if ($("#outputFormatId").val() == "") {
		$("#outputFormatId").val("yyyyMMdd_HHmmss'.jpg'")
	}
}

/**
 * Checks if there are results to show
 */
function checkResults() {
	
	if ($("#resultContainerId").height() > 300) {
		$("#resultId").height(300);
		$("#resultId").css('overflow','auto')
	}
	
	if ($("#resultId").html().trim() != "") {
		$("#resultContainerId").fadeIn();
	}
}

/**
 * Checks if there are errors to show
 */
function checkErrors() {
	
	if ($("#errorContainerId").height() > 300) {
		$("#errorId").height(300);
		$("#errorId").css('overflow','auto')
	}
	
	if ($("#errorId").html().trim() != "") {
		$("#errorContainerId").fadeIn();
	}
}

/**
 * Shows format pattern help popup
 *  
 * @param formatId Id of the format pattern
 */
function showFormatterHelp(formatId) {
	
	$("#formatterHelpId").val(formatId);
	$("#formatterHelpContainerId").fadeIn();
}

/**
 * Hides pattern format help popup
 */
function hideFormatterHelp() {
	
	$("#formatterHelpContainerId").fadeOut();
	$("#formatterHelpId").val("");
	$("#patternLoaderSelectId").val("0");
	$("#selectedPatternId").val("");
	hideTableBody('datePatternTableHeaderId', 'datePatternTableBodyId', 'datePatternTableText');
	hideTableBody('datePatternExamplesTableHeaderId', 'datePatternExamplesTableBodyId', 'datePatternExamplesTableText');
}

/**
 * Shows the body of the table and changes properties to hide the body when header is clicked
 * 
 * @param headerId Header id
 * @param bodyId Body id
 * @param textId Text id
 */
function showTableBody(headerId, bodyId, textId) {
	
	$("#" + bodyId).fadeIn();
	$("#" + textId + "AId").html("&#9650;");
	$("#" + textId + "BId").html("&#9650;");
	document.getElementById(headerId).onclick = function() {
		hideTableBody(headerId, bodyId, textId);
	};
}

/**
 * Hides the body of the table and changes properties to show the body when header is clicked
 * 
 * @param headerId Header id
 * @param bodyId Body id
 * @param textId Text id
 */
function hideTableBody(headerId, bodyId, textId) {
	
	$("#" + bodyId).fadeOut();
	$("#" + textId + "AId").html("&#9660;");
	$("#" + textId + "BId").html("&#9660;");
	document.getElementById(headerId).onclick = function() {
		showTableBody(headerId, bodyId, textId);
	};
}

/**
 * Loads pattern selected
 *  
 * @param patternType Pattern type
 */
function loadPatternPopup(patternType) {
	
	var pattern;
	
	if (patternType == "EXIF") {
		pattern = "yyyy:MM:dd HH:mm:ss";
	}
	else if (patternType == "AND_EST") {
		pattern = "'IMG'_yyyyMMdd_HHmmss'.jpg'";
	}
	else if (patternType == "AND_MILI") {
		pattern = "'IMG'_yyyyMMdd_HHmmssSSS'.jpg'";
	}
	else if (patternType == "IPHONE_ESP") {
		pattern = "'Foto' dd-M-yy HH mm ss'.jpg'";
	}
	else if (patternType == "IPHONE_PUN") {
		pattern = "yyyy-MM-dd HH.mm.ss'.jpg'";
	}
	else if (patternType == "SALIDA_EST") {
		pattern = "yyyyMMdd_HHmmss'.jpg'";
	}
	else {
		pattern = "";
	}
	
	$("#selectedPatternId").val(pattern);
}

/**
 * Loads the pattern into the selected pattern format 
 */
function loadPattern() {
	
	$("#" + $("#formatterHelpId").val()).val($("#selectedPatternId").val());
	hideFormatterHelp();
}
