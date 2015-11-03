<!DOCTYPE html>
	<html>
    <head>
		<title>Product Validation System </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
    </head>
    
    <body>
    <center>
     <#include "../base/header.ftl">
        <br /><br />
        <section>
				<ul>
					<h2 class="register">Login</h2>
					<br><br>
					<form method="post" action="companylogin">
						<table>
							<tr><td>Email</td><td> <input type="email" name="email" placeholder="Email" /></td></tr>
							<tr><td>Password</td><td> <input type="password" name="password" placeholder="Password" /></td></tr>
						</table>
						<br><br>
						<center><input class="button" type="submit" name="submit" value="Login" /></center>
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