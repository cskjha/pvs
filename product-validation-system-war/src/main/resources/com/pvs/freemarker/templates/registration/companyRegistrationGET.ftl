<!DOCTYPE html>
	<html>
    <head>
		<title>Welcome to Product Validation System </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
        <#include "../base/header.ftl">
        <br /><br />
        <section>
				<ul>
					<h2 class="register">REGISTER</h2>
					<br><br>
							
					<form method="post" action="companyregister">
						<table>
							<tr><td>Company Name</td><td> <input type="text" name="companyName" placeholder="Company Name" /></td></tr>
							<tr><td>Email</td><td> <input type="email" name="email" placeholder="Email" /></td></tr>
							<tr><td>Password</td><td> <input type="password" name="password" placeholder="Password" /></td></tr>
						</table>
						<br><br>
						<center><input class="button" type="submit" name="submit" value="Register" /></center>
					</form>
					
					
				</ul>
        </section></center>
    </body>
    </html>