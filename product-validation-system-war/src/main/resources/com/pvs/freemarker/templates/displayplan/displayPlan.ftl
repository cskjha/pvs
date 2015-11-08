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
        <#if planList??>
        <#list planList as plan>
        	<section>
            	<p>${plan.allowedRecordCount} Records Free</p>
                <p>${plan.allowedScanCount} Scans Free</p>
                <p> ${plan.planName} </p>
                <p> ${plan.planPrice} ${plan.currencyCode} </p>
                <form method="post" action="myplan">
                	<input class="text" type="hidden" name="companyPlanId" value="${plan.planId}">
                	<input class="button" type="submit" name="free_plan" value="Choose">
                </form>
            </section>
            <div class="content"></div>
        </#list>
        <#else>
        	There is no plan registered.<br>
            Please register your plan <a href="planregister">Here</a>
        </#if>
       </center>
    </body>
    </html>