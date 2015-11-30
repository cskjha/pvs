<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM}</title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/jquery/jquery.js" type="text/javascript"></script>
		<script src="scripts/product-registration.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl"><br>
    		<h4>${PRODUCT_REG_MESSAGE2}</h4><br>
    	<#--	<h4>Quick Response Code Image</h4><br><br>
    		<img src="${qrCodeImagefilePath}" alt="Quick Response Code Image" style="width:200px;height:200px;">    	
    	-->	
    		<br><br>
    		<h5> ${PRODUCT_REG_MESSAGE3} <a href="viewproducttemplates"> ${HERE} </a></h5>
    	</center>
    </body>
    </html>