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
    	 	<#if category == 'admin'>
    	 		<#include "../base/admin_header.ftl">
    		<h4>Plan Registration Successful</h4>
    		<h5> Register another Plan <a href="planregister"> ${HERE} </a></h5>
    		<#else>
    	 		<#include "../base/header.ftl">
    	 		<br /><br /><h3>Company users can't access this Page.</h3>
    	 	</#if>
    	</center>
    </body>
    </html>