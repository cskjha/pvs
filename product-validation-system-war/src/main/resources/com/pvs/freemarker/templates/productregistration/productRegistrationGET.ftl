<!DOCTYPE html>
	<html>
    <head>
		<title>Welcome to Product Validation System </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/jquery/jquery.js" type="text/javascript"></script>
		<script src="scripts/product-registration.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl">
    		<div id="main">
			<br /><br />
        
			<div class="clear">
			</div>
        	<section>
        		<h3>ADD A ${productName}</h3>
            	<div class="my-form">                	           
                	<form method="post" role="form" action="registerproduct">
					<table>
					<input type="hidden" name="productTemplateId" value="${productTemplateId}" />
					<input type="hidden" name="companyId" value="${companyId}" />
					<#list fieldMap?keys as field>
						<tr>
							<td>${fieldMap[field]} <input type="hidden" name="${field}Name" value="${fieldMap[field]}" /></td>
							<td><input type="text" name="${field}Value" placeholder="${fieldMap[field]}" /><td>
						</tr>
					</#list> 
					</table>
					<p><input class="button" type="submit" name="submit" value="Save" /></p>
                    </form>
                </div>
            </section>
			
			</div>   	
    	</center>
    </body>
    </html>