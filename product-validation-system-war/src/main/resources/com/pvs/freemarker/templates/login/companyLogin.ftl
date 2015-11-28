<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM} </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
    </head>
    
    <body>
    <center>
     <#include "../base/header.ftl">
        <br /><br />
        <section>
				<ul>
					<h2 class="register">${LOGIN}</h2>
					<br><br>
					<form method="post" action="companylogin">
						<table>
							<tr><td>${EMAIL}</td><td> <input type="email" name="email" placeholder="${EMAIL}" /></td></tr>
							<tr><td>${PASSWORD}</td><td> <input type="password" name="password" placeholder="${PASSWORD}" /></td></tr>
						</table>
						<br><br>
						<center><input class="button" type="submit" name="submit" value="${LOGIN}" /></center>
					</form>
					
					
				</ul>
        </section>
        <section>
        <#if errorMessage??>
	 		<font color="#FF2222">${errorMessage}</font>
		</#if>
        </section>
        </center>
    </body>
    </html>