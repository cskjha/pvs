<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM}  </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
        <#if category == 'admin'>
    	 		<#include "../base/admin_header.ftl">
    	 	
    	 	
        <br /><br />
        <section>
				<ul>
					<h2 class="register">${REGISTER_PLAN}</h2>
					<br><br>
							
					<form method="post" action="planregister">
						<table>
							<tr><td>${PLAN} ${NAME}</td><td> <input type="text" name="planName" placeholder="${PLAN} ${NAME}" /></td></tr>
							<tr><td>${PLAN_REGISTRATION_MESSAGE1}</td><td> <input type="text" name="allowedRecordCount" placeholder="${PLAN_REGISTRATION_MESSAGE1}" /></td></tr>
							<tr><td>${PLAN_REGISTRATION_MESSAGE2}</td><td> <input type="text" name="allowedScanCount" placeholder="${PLAN_REGISTRATION_MESSAGE2}" /></td></tr>
							<tr><td>${PLAN_REGISTRATION_MESSAGE3}</td><td> <input type="text" name="planPrice" placeholder="${PLAN_REGISTRATION_MESSAGE3}" /></td></tr>
							<tr><td>${PLAN_REGISTRATION_MESSAGE4}</td><td> <input type="text" name="currencyCode" placeholder="${PLAN_REGISTRATION_MESSAGE4}" /></td></tr>
							<tr><td>${PLAN_REGISTRATION_MESSAGE5}</td><td> <input type="checkbox" name="planState"/></td></tr>
						</table>
						<br>
						<center><input class="button" type="submit" name="submit" value="${REGISTER}" /></center>
					</form>					
				</ul>
        </section>
        <#else>
    	 		<#include "../base/header.ftl">
    	 		<br /><br /><h3>Company users can't access this Page.</h3>
    	 </#if>
        </center>
    </body>
    </html>