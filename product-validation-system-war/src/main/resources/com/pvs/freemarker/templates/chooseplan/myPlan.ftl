<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM}m </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
    </head>
    
    <body>
    	<center>
    	 	<#include "../base/header.ftl"><br>
    	 	<#if companyPlanName??>
	 			${MY_PLAN_MESSAGE1} ${companyPlanName} ${PLAN}.<br>
    			${MY_PLAN_MESSAGE2} <a href="registerproducttemplate">${HERE} </a>  
			<#else>
		 		${MY_PLAN_MESSAGE3}.
		 		<br>${MY_PLAN_MESSAGE4} <a href="displayplan">${HERE}</a>&nbsp;|&nbsp;
	  		</#if>  	
    	</center>
    </body>
    </html>