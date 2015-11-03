<!DOCTYPE html>
	<html>
    <head>
		<title>Select a Plan</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl">
        <div class="clear"></div>
        <br><br>
        <h2>Select a Plan</h2>
        	<section>
            	<p>100 Free</p>
                <p>Api calls</p>
                <p> Free Plan </p>
                <form method="post" action="chooseplan">
                	<input class="text" type="hidden" name="plan" value="Free Plan">
                	<input class="button" type="submit" name="free_plan" value="Choose">
                </form>
            </section>
            <div class="content">
        	<section>
            	<p>100000 Free</p>
                <p>Api calls</p>
                <p>100 UDS/Month</p>
                <form method="post" action="chooseplan">
                	<input class="text" type="hidden" name="plan" value="100 UDS/Month">
                	<input class="button" type="submit" name="free_plan" value="Choose">
                </form>
            </section>
            <div class="content">
        	<section>
            	<p>1000000 Free</p>
                <p>Api calls</p>
                <p>200 USD/Month</p>
               <form method="post" action="chooseplan">
                	<input class="text" type="hidden" name="plan" value="200 USD/Month">
                	<input class="button" type="submit" name="free_plan" value="Choose">
                </form>
            </section>
            <div class="content">
        	<section>
            	<p>10000000 Free</p>
                <p>Api calls</p>
                <p>500 USD/Month</p>
               <form method="post" action="chooseplan">
                	<input class="text" type="hidden" name="plan" value="500 USD/Month">
                	<input class="button" type="submit" name="free_plan" value="Choose">
                </form>
            </section>
       </center>
    </body>
    </html>