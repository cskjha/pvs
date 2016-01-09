<!DOCTYPE html>
	<html>
    <head>
		<title>${LIST} ${PRODUCT}</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
		<script src="scripts/common.js" type="text/javascript"></script>
		<script> 
		function showSelection(){
			var e = document.getElementById("product_response_code");
			var selectedOp = e.options[e.selectedIndex].text;
			alert(selectedOp);
		}
		</script>
    </head>
    
    <body>
    	<center>
    	 	<#if category == 'admin'>
    	 		<#include "../base/admin_header.ftl">
    	 	<#else>
    	 <#include "../base/header.ftl">
    	 	</#if>
        <div class="clear"></div>
        <br><br>
        <h2></h2>
        <#if productList??>
        <table>
        <tr>
        	<td>Product Id</td>
        	<td>Field</td>
        	<td>Generate QR Code</td>
        	<#if category == 'admin'>
        		<td>Product Status</td>
        		<td>Action</td>
        	</#if>
        </tr>
        <#list productList as product>
        	<tr><form method="post" action="updatestolenresponsecode">
        		<td>${product.fullproductId}<input type="hidden" name="productId" value="${product.productId}" placeholder="${PRODUCT} ${ID}" /><input type="hidden" name="productType" value="${product.productType}" placeholder="${PRODUCT} ${TYPE}" /><input type="hidden" name="productTemplateId" value="${product.productTemplateId}"/></td>
        		<td></td>
        		<td><a href="generateqrcode?productScanCode=${product.fullproductId}">QR Code</a></td>
        		<#if category == 'admin'>
        		<td>
        			<#if product.responseCode == '200'>
        				<select id="product_response_code" name="product_response_code" onchange="showSelection();">
        				<option value="200" selected>ok</option>
        				<option value="205">stolen</option>
        			<#else>
        				<select id="product_response_code" name="product_response_code" onchange="showSelection();">
        				<option value="205" selected>stolen</option>
        				<option value="200">ok</option>
        			</#if>
  				</td>
  				<td align="center"><input class="button" type="submit" name="submit" value="${SAVE}" /></td>
        		</#if>
        		</form>
        	</tr>
        </#list>
        <#else>
        	No products Added yet. Please add one.. follow link</a>
        </#if>
        </table>
       </center>
    </body>
    </html>