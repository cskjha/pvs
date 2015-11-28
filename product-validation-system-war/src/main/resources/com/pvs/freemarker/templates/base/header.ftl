<h2>${PRODUCT} ${VALIDATION} ${SYSTEM}</h2>
<link type="text/css" rel="stylesheet" href="styles/common.css">
<nav class="header-style">
	<#if companyName??>
	  ${WELCOME} ${companyName!GUEST} &nbsp;|&nbsp;
	  <#if REGISTER_PLAN??>
	  <a href="planregister">${REGISTER_PLAN}</a>&nbsp;|&nbsp;
	  </#if>
	 <#if MY_PLAN??>
	 <a href="myplan">${MY_PLAN}</a>&nbsp;|&nbsp;
	  </#if>
	<#if REGISTER_PRODUCT_TEMPLATE??>
	  <a href="registerproducttemplate">${REGISTER_PRODUCT_TEMPLATE}</a>&nbsp;|&nbsp;
	  </#if>
	<#if VIEW_PRODUCT_TEMPLATES??>
	   <a href="viewproducttemplates">${VIEW_PRODUCT_TEMPLATES}</a>&nbsp;|&nbsp;
	  </#if>
	<#if LOGOUT??>
	  <a href="logout">${LOGOUT}</a>
	  </#if>	 
	<#else>
		${WELCOME} ${GUEST} &nbsp;|&nbsp;
	  	<a href="companyregister">${REGISTER}</a>&nbsp;|&nbsp;
		<a href="companylogin">${LOGIN}</a>
	</#if>
</nav>