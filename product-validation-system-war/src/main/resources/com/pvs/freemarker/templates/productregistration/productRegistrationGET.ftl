<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM} </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/jquery/jquery.js" type="text/javascript"></script>
		<script src="scripts/common.js" type="text/javascript"></script>
		<script src="scripts/product-registration.js" type="text/javascript"></script>
    </head>    
    <body>
    	<center>
    	 <#if category == 'admin'>
    	 		<#include "../base/admin_header.ftl">
    	 	<#else>
    	 		<#include "../base/header.ftl">
    	 	</#if>
    		<div id="main">
			<br /><br />
        
			<div class="clear">
			</div>
        	<section>
        		<h3>${PRODUCT_REG_MESSAGE1} ${productName}</h3>
            	<div class="my-form">                	           
                	<form method="post" role="form" action="registerproduct">
					<table>
					<input type="hidden" name="productTemplateId" value="${productTemplateId}" />
					<input type="hidden" name="companyId" value="${companyId}" />
					<input type="hidden" name="productName" value="${productName}" />
					<input type="hidden" name="productType" value="${productType}" />
					<#list fieldMap?keys as field>
						<#if field == 'expirationDate'>
						  		<tr>
									<td>${fieldMap[field]} <input type="hidden" name="${field}Name" value="${fieldMap[field]}" /></td>
									<td><input type="date" name="expirationDate" placeholder="${fieldMap[field]}" /></td>
								</tr> 
						<#else>
								<tr>
									<td>${fieldMap[field]} <input type="hidden" name="${field}Name" value="${fieldMap[field]}" /></td>
									<td><input type="text" name="${field}Value" placeholder="${fieldMap[field]}" /></td>
								</tr> 
						</#if>
					</#list> 
					<tr>
						<td>${MANUFACTURED} ${ON} </td>
						<td><input type="date" name="manufacturedOn" placeholder="${MANUFACTURED} ${ON}" required/></td>					
					</tr>
		<#--		<#if foodProduct??>
					<tr>
						<td>${EXPIRATION} ${DATE} </td>
						<td><input type="date" name="expirationDate" placeholder="${EXPIRATION} ${DATE}" required/></td>					
					</tr>
				</#if>
		-->
					
					</table>
					<p><input class="button" type="submit" name="submit" value="${SAVE}" /></p>
                    </form>
                </div>
            </section>
			
			</div>   	
    	</center>
    </body>
    </html>