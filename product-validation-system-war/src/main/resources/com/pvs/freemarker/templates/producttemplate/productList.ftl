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
			<td>${PRODUCT} ${LIST}</td>
			<td>${DOWNLOAD} ${TEXT} ${FILE}</td>
        </tr>
        <#list productTemplateList as productTemplate>
        	<tr>
        		<td>${productTemplate.productTemplateName} <a href=""> [${EDIT}] </a>  <a href="removeproducttemplate?productType=${productTemplate.productType}&productTemplateName=${productTemplate.productTemplateName}&companyName=${companyName}">[${REMOVE}]</a></td>
        		<td><a href="registerproduct?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${REGISTER} ${PRODUCT}</a></td>
        		<td><a href="downloadproductformat?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}&templateName=${productTemplate.productTemplateName}">${DOWNLOAD} ${PRODUCT} ${FORMAT}</a></td>
        		<td><a href="uploadproduct?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${UPLOAD} ${PRODUCT}</a></td>
        		<td><a href="listproducts?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${VIEW} ${PRODUCT}</a></td>
				<td><a target="_blank" href="textfiledownload?productTemplateId=${productTemplate.productTemplateId}&productType=${productTemplate.productType}">${DOWNLOAD}</a></td>
			</tr>
        </#list>
        <#else>
        	${VIEW_PROD_TEMPLATE_MSG2}.<br>
            ${REGISTER} ${PRODUCT} ${TEMPLATE} <a href="registerproducttemplate">${HERE}</a>
        </#if>
        </table>