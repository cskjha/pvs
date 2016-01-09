<!DOCTYPE html>
	<html>
    <head>
		<title>${REGISTER} ${PRODUCT}</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
 		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
    		<#if category == 'company'>
    	 		<#include "../base/header.ftl">
    	 	<#else>
    	 		<#include "../base/admin_header.ftl">
    	 	</#if>
         <#include "productList.ftl">
       </center>
    </body>
    </html>