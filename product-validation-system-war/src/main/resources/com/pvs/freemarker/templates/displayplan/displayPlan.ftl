<!DOCTYPE html>
	<html>
    <head>
		<title>${DISPLAY_PLAN_MESSAGE1}</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
		<script src="scripts/common.js" type="text/javascript"></script>
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl">
        <div class="clear"></div>
        <br><br>
        <h2>${DISPLAY_PLAN_MESSAGE1}</h2>
        <#if planList??>
        <#list planList as plan>
        	<section>
            	<p>${plan.allowedRecordCount} ${DISPLAY_PLAN_MESSAGE2}</p>
                <p>${plan.allowedScanCount} ${DISPLAY_PLAN_MESSAGE3}</p>
                <p> ${plan.planName} </p>
                <p> ${plan.planPrice} ${plan.currencyCode} </p>
                <form method="post" action="myplan">
                	<input class="text" type="hidden" name="companyPlanId" value="${plan.planId}">
                	<input class="button" type="submit" name="free_plan" value="${CHOOSE}">
                </form>
            </section>
            <div class="content"></div>
        </#list>
        <#else>
        	${MY_PLAN_MESSAGE3}.<br>
        </#if>
       </center>
    </body>
    </html>