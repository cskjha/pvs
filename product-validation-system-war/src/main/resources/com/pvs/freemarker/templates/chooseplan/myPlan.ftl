<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM} </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
    	 	<#include "../base/header.ftl"><br>
    	 	<#if companyPlanName??>
	 			${MY_PLAN_MESSAGE1} ${companyPlanName} ${PLAN}.<br>
    			${MY_PLAN_MESSAGE2} <a href="registerproducttemplate">${HERE} </a><br>
    			${MY_PLAN_MESSAGE5} <a href="displayplan?action=change">[${HERE}]</a>
    			  
			<#else>
		 		${MY_PLAN_MESSAGE3} 
		 		<br>${MY_PLAN_MESSAGE4} <a href="displayplan">${HERE}</a>&nbsp;|&nbsp;
	  		</#if>  	
    	</center>
    </body>
    </html>