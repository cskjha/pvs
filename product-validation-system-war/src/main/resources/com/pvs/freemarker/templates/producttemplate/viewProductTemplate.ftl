<!DOCTYPE html>
	<html>
    <head>
		<title>${REGISTER} ${PRODUCT}</title>
 		<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
    </head>
    
    <body>
    	<center>
    	 <#include "../base/header.ftl">
        <div class="clear"></div>
        <br><br>
        <h2>${VIEW_PROD_TEMPLATE_MSG1}</h2>
        <#if productTemplateList??>
        <table>
        <tr>
        	<td>${PRODUCT} ${TEMPLATE} ${NAME}</td>
        	<td>${REGISTRATION} ${LINK} </td>
        	<td>${DOWNLOAD} ${PRODUCT} ${FORMAT} ${LINK} </td>
        	<td>${UPLOAD} ${PRODUCT} ${LINK}</td>
        </tr>
        <#list productTemplateList as productTemplate>
        	<tr>
        		<td>${productTemplate.productTemplateName}</td>
        		<td><a href="registerproduct?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${REGISTER} ${PRODUCT}</a></td>
        		<td><a href="downloadproductformat?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${DOWNLOAD} ${PRODUCT} ${FORMAT}</a></td>
        		<td><a href="uploadproduct?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${UPLOAD} ${PRODUCT}</a></td>
        	</tr>
        </#list>
        <#else>
        	${VIEW_PROD_TEMPLATE_MSG2}.<br>
            ${REGISTER} ${PRODUCT} ${TEMPLATE} <a href="registerproducttemplate">${HERE}</a>
        </#if>
        </table>
       </center>
    </body>
    </html>