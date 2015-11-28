<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM}</title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
        <#include "../base/header.ftl">
        <br /><br />
        <section>
				<ul>
					<h2 class="register">${REGISTER}</h2>
					<br><br>
							
					<form method="post" action="companyregister">
						<table>
							<tr><td>${COMPANY} ${NAME}</td><td> <input type="text" name="companyName" placeholder="${COMPANY} ${NAME}" /></td></tr>
							<tr><td>${EMAIL}</td><td> <input type="email" name="email" placeholder="${EMAIL}" /></td></tr>
							<tr><td>${PASSWORD}</td><td> <input type="password" name="password" placeholder="${PASSWORD}" /></td></tr>
						</table>
						<br><br>
						<center><input class="button" type="submit" name="submit" value="${REGISTER}" /></center>
					</form>
					
					
				</ul>
        </section></center>
    </body>
    </html>