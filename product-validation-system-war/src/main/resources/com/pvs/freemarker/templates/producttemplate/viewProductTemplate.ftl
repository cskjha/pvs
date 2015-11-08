<!DOCTYPE html>
	<html>
    <head>
		<title>Register a Product</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl">
        <div class="clear"></div>
        <br><br>
        <h2>Register a Product</h2>
        <#if productTemplateList??>
        <table>
        <tr>
        	<td>Product Template Name</td>
        	<td>Registration Link </td>
        </tr>
        <#list productTemplateList as productTemplate>
        	<tr>
        		<td>${productTemplate.productTemplateName}</td>
        		<td><a href="registerproduct">Register Product</a></td>
        	</tr>
        </#list>
        <#else>
        	There is no product template registered.<br>
            Please register some product templates <a href="registerproducttemplate">Here</a>
        </#if>
        </table>
       </center>
    </body>
    </html>