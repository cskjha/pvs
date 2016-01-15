<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM} </title>
		<link type="text/css" rel="stylesheet" href="styles/common.css">
		<script src="scripts/jquery/jquery.js" type="text/javascript"></script>
		<script src="scripts/common.js" type="text/javascript"></script>
		<script src="scripts/product-registration.js" type="text/javascript"></script>
		<script>
		function showExpirationDate() {
			document.getElementById('expirationDateBox').style.display='block';
		}
		function hideExpirationDate() {
			document.getElementById('expirationDateBox').style.display='none';
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
    		<div id="main">
			<br /><br />
        
			<div class="clear">
			</div>
        	<section>
            	<div class="my-form"> 
                	<#if productTemplateId??>
                		<form method="post" role="form" action="editproducttemplate" enctype="multipart/form-data">
						
							<h3>${PRODUCT_TEMPLATE_REG_MSG1} ${TEMPLATE}</h3>
	                		${PRODUCT} ${NAME}</td><td> <input type="text" name="productName" placeholder="${PRODUCT} ${NAME}" value="${productName}" required/><br><br>
	                		${MANUFACTURER} ${NAME}</td><td> <input type="text" name="manufacturerName" placeholder="${MANUFACTURER} ${NAME}" value="${manufacturerName}" required/><br><br>
	                		${PRODUCT} ${IMAGE} <input type="file" name="file" size="500000" value="${imageURL}" required/> <img src="${imageURL}"><br><br>
	                		${PRODUCT} ${TYPE} </td><td><#if productType=='FP'> <input type="radio" name="productType" id="ProductTypeFP" value="FP" checked />${FOOD} <input type="radio" name="productType" id="ProductTypeNFP" value="NFP" disabled/>${NON_FOOD}<#else><input type="radio" name="productType" id="ProductTypeFP" value="FP"  disabled/>${FOOD} <input type="radio" name="productType" id="ProductTypeNFP" value="NFP" checked />${NON_FOOD}</#if><br><br>
	                        <h3>${PRODUCT_TEMPLATE_REG_MSG2}</h3>
	                        <#if productType == 'FP'>
		                        <p class="text-box" id="expirationDateBox" >
									<label for="box0">${FIELD} <span class="box-number">0</span></label>
									<input type="text" name="expirationDate" placeholder="${EXPIRATION} ${DATE}" value="Expiration Date" readonly/>
									<input type="button" class="add-box" value="${ADD_MORE}" disabled/>
								</p>
							</#if>
							 <p class="text-box" id="manufacturedOnBox">
								<label for="box1">${FIELD} <span class="box-number">1</span></label>
								<input type="text" name="manufacturedOn" placeholder="${MANUFACTURED} ${ON}" value="Manufactured On" readonly/>
								<input type="button" class="add-box" value="${ADD_MORE}" disabled/>
							</p>
							<#list fieldMap?keys as field>
								<p class="text-box">
										<label for="box2">${FIELD} <span class="box-number">2</span></label>
										<input type="text" id="box2" name="field2" placeholder="${fieldMap[field]}" value="${fieldMap[field]}" />
										<input type="button" class="add-box" value="${ADD_MORE}" disabled/>	
								</p>
								</#list> 
							
							<p class="text-box">
								<label for="box3">${FIELD} <span class="box-number">3</span></label>
								<input type="text" id="box3" name="field3" placeholder="${FIELD} ${NAME}" />
								<input type="button" class="add-box" value="${ADD_MORE}" />
							</p>	
							<p>
								<input type="hidden" name="productTemplateId" value="${productTemplateId}"/>
								<input class="button" type="submit" name="submit" value="${SAVE}" />
							</p>
	                    </form>
                	<#else>
	                	<form method="post" role="form" action="registerproducttemplate" enctype="multipart/form-data">
						
							<h3>${PRODUCT_TEMPLATE_REG_MSG1} ${TEMPLATE}</h3>
	                		${PRODUCT} ${NAME}</td><td> <input type="text" name="productName" placeholder="${PRODUCT} ${NAME}" required/><br><br>
	                		${MANUFACTURER} ${NAME}</td><td> <input type="text" name="manufacturerName" placeholder="${MANUFACTURER} ${NAME}" required/><br><br>
	                		${PRODUCT} ${IMAGE} <input type="file" name="file" size="500000" required/><br><br>
	                		${PRODUCT} ${TYPE} </td><td> <input type="radio" name="productType" id="ProductTypeFP" value="FP" onclick="showExpirationDate()" required/>${FOOD} <input type="radio" name="productType" id="ProductTypeNFP" value="NFP" onclick="hideExpirationDate()" required/>${NON_FOOD}<br><br>
	                        <h3>${PRODUCT_TEMPLATE_REG_MSG2}</h3>
	                        <p class="text-box" id="expirationDateBox" style="display:none">
								<label for="box0">${FIELD} <span class="box-number">0</span></label>
								<input type="text" name="expirationDate" placeholder="${EXPIRATION} ${DATE}" value="Expiration Date" readonly/>
								<input type="button" class="add-box" value="${ADD_MORE}" disabled/>
							</p>
							 <p class="text-box" id="manufacturedOnBox">
								<label for="box1">${FIELD} <span class="box-number">1</span></label>
								<input type="text" name="manufacturedOn" placeholder="${MANUFACTURED} ${ON}" value="Manufactured On" readonly/>
								<input type="button" class="add-box" value="${ADD_MORE}" disabled/>
							</p>
							<p class="text-box">
								<label for="box2">${FIELD} <span class="box-number">2</span></label>
								<input type="text" id="box2" name="field2" placeholder="${FIELD} ${NAME}" />
								<input type="button" class="add-box" value="${ADD_MORE}" />
							</p>
							
								<p>
									<input class="button" type="submit" name="submit" value="${SAVE}" />
								</p>
	                    </form>
	                    </div>
            			<#include "productList.ftl">
	               	</#if>
                	
			</section>
			</div>   	
    	</center>
    </body>
    </html>