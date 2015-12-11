<!DOCTYPE html>
	<html>
    <head>
		<title>${PRODUCT} ${VALIDATION} ${SYSTEM} </title>
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
            	<div class="my-form"> 
                
                	<form method="post" role="form" action="registerproducttemplate" enctype="multipart/form-data">
					
						<h3>${PRODUCT_TEMPLATE_REG_MSG1} ${TEMPLATE}</h3>
                		${PRODUCT} ${NAME}</td><td> <input type="text" name="productName" placeholder="${PRODUCT} ${NAME}" required/><br><br>
                		${MANUFACTURER} ${NAME}</td><td> <input type="text" name="manufacturerName" placeholder="${MANUFACTURER} ${NAME}" required/><br><br>
                		${PRODUCT} ${IMAGE} <input type="file" name="file" size="500000" /><br><br>
                		${PRODUCT} ${TYPE} </td><td> <input type="radio" name="productType" value="FP"/>${FOOD} <input type="radio" name="productType" value="NFP"/>${NON_FOOD}<br><br>
                        <h3>${PRODUCT_TEMPLATE_REG_MSG2}</h3>
						<p class="text-box">
							<label for="box1">${FIELD} <span class="box-number">1</span></label>
							<input type="text" id="box1" name="field1" placeholder="${FIELD} ${NAME}" />
							<input type="button" class="add-box" value="${ADD_MORE}" />
						</p>
						
							<p>
								<input class="button" type="submit" name="submit" value="${SAVE}" />
							</p>
                    </form>
                </div>
            </section>
			
			</div>   	
    	</center>
    </body>
    </html>