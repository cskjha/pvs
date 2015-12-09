<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM}  </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
        <#include "../base/header.ftl">
        <br /><br />
        <section>
				<ul>
					<h2 class="register">${PRODUCT} ${UPLOAD}</h2>
					<br><br>
					<center>		
					<form action="uploadproduct?productType=${productType}&productTemplateId=${productTemplateId}" method="post"  enctype="multipart/form-data">
						<input type="file" name="file" size="500000" />
						<br/><br/>
						<input type="submit" value="${UPLOAD} ${FILE}" />
					</form>	
					</center>				
				</ul>
        </section></center>
    </body>
    </html>