<!DOCTYPE html>
	<html>
    <head>
		<title>Product Validation System </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
    </head>
    
    <body>
    	<center>
    	 	<#include "../base/header.ftl"><br>
    	 	<#if companyPlanName??>
	 			You have selected ${companyPlanName} plan.<br>
    			You can register your products <a href="registerproducttemplate">Here </a>  
			<#else>
		 		You have not selected a plan.
		 		<br>Please select your plan <a href="displayplan">Here</a>&nbsp;|&nbsp;
	  		</#if>  	
    	</center>
    </body>
    </html>