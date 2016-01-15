<!DOCTYPE html>
	<html>
    <head>
		<title>Product Verification Deatils</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
    </head>
    
    <body>
    	
    		
    		<#if productwebview??>
    			<#if productwebview.responseCode=='200'>
    			<div align="left">
    				<h1 align="center">${productwebview.productName}</h1>
    				<h2 align="left">Manufactured By:  ${productwebview.manufacturerName}</h2><br>
                    <h2 align="left">Manufactured Date:  ${productwebview.manufacturedOn}</h2><br>
    				<img align="right" height="450" width="325" src="${productwebview.image}">
                    
	    			    <#list productwebview.dynamicValues as dynamicvalue>
	    				   <h2 align="left">${dynamicvalue.fieldName}:  ${dynamicvalue.fieldValue}</h2><br>
	    			    </#list>
                        <h3>Thanks for Checking</h3>
                 </div>
                 </#if> 
             	<#if productwebview.responseCode=='205'>
             		<div align="center">
             			<h2>This product is stolen</h2>
             			<br><br><img src="${productwebview.stolen_image}"> 	
             		</div>
             	</#if>
    		</#if>
    	</center>
    </body>
    </html>