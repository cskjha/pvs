<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM}</title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/jquery/jquery.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
    	 	<#if category == 'admin'>
    	 		<#include "../base/admin_header.ftl">
    	 	<#else>
    	 		<#include "../base/header.ftl">
    	 	</#if>
    		<h4>${PRODUCT_UPLOAD_EXCEL_MSG1}</h4><br>
    		<br><br>
    		<h5> ${PRODUCT_UPLOAD_EXCEL_MSG2} <a href="viewproducttemplates"> ${HERE} </a></h5>
    	</center>
    </body>
    </html>