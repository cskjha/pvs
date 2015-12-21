<!DOCTYPE html>
	<html>
    <head>
		<title>Product Verification Deatils</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
    </head>
    
    <body>
    	
    		
    		<#if productwebview??>
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
    	</center>
    </body>
    </html>