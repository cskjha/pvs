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
					<h2 class="register">REGISTER A Plan</h2>
					<br><br>
							
					<form method="post" action="planregister">
						<table>
							<tr><td>Plan Name</td><td> <input type="text" name="planName" placeholder="Plan Name" /></td></tr>
							<tr><td>Allowed Record Count</td><td> <input type="text" name="allowedRecordCount" placeholder="Allowed Record Count" /></td></tr>
							<tr><td>Allowed Scan Count</td><td> <input type="text" name="allowedScanCount" placeholder="Allowed Scan Count" /></td></tr>
							<tr><td>Plan Price</td><td> <input type="text" name="planPrice" placeholder="Plan price" /></td></tr>
							<tr><td>Plan Price</td><td> <input type="text" name="currencyCode" placeholder="Currency Code ex. USD" /></td></tr>
							<tr><td>Activate</td><td> <input type="checkbox" name="planState"/></td></tr>
						</table>
						<br>
						<center><input class="button" type="submit" name="submit" value="Register" /></center>
					</form>
					
					
				</ul>
        </section></center>
    </body>
    </html>