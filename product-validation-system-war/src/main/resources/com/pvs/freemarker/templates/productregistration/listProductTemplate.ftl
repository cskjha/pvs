<!DOCTYPE html>
	<html>
    <head>
		<title>${LIST} ${PRODUCT}</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl">
        <div class="clear"></div>
        <br><br>
        <h2></h2>
        <#if productList??>
        <table>
        <tr>
        	<td>Product Id</td>
        	<td>Field</td>
        	<td>Generate QR Code</td>
        </tr>
        <#list productList as product>
        	<tr>
        		<td>${product.productId}</td>
        		<td></td>
        		<td><a href="generateqrcode?productScanCode=${product.productId}">QR Code</a></td>
        	</tr>
        </#list>
        <#else>
        	No products Added yet. Please add one.. follow link</a>
        </#if>
        </table>
       </center>
    </body>
    </html>