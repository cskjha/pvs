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
            	<div class="my-form">
                
                
                	<form method="post" role="form" action="registerproduct">
					
						<h3>ADD A PRODUCT</h3>
                		Product Name</td><td> <input type="text" name="productName" placeholder="Product Name" />
                        <h3>Enter the fields</h3>
						<p class="text-box">
							<label for="box1">Field <span class="box-number">1</span></label>
							<input type="text" id="box1" name="field1Name" placeholder="Field Name" />
							<input type="text" id="box1" name="field1Value" placeholder="Field Value" />
							<input type="button" class="add-box" value="Add More" />
						</p>
						
							<p>
								<input class="button" type="submit" name="submit" value="Save" />
							</p>
                    </form>
                </div>
            </section>
			
			</div>   	
    	</center>
    </body>
    </html>