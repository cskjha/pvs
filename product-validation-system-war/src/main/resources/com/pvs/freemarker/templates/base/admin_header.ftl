<h2>${PRODUCT} ${VALIDATION} ${SYSTEM}</h2>
<link type="text/css" rel="stylesheet" href="styles/common.css">

<#--
<nav class="header-style-language">

<a href="changelanguage?language=en">${ENGLISH}</a> | <a href="changelanguage?language=de">${GERMAN}</a>
| <a href="changelanguage?language=hi">${HINDI}</a> | <a href="changelanguage?language=it">${ITALIAN}</a>
</nav>

-->

<nav class="header-style">
	<#if companyName??>
	  	${WELCOME} ${companyName!GUEST} &nbsp;|&nbsp;
	  	
	  	<a href="displayuserlist">${USER_LIST}</a>&nbsp;|&nbsp;
	  	 
	  	<#if REGISTER_PLAN??>
	  	<a href="planregister">${REGISTER_PLAN}</a>&nbsp;|&nbsp;
	  	</#if>
		<#if LOGOUT??>
	  	<a href="logout">${LOGOUT}</a>&nbsp;|&nbsp;
		</#if>
		<a href="" onclick="printWebPageFunction()">Print this Page</a>	 
	<#else>
	${WELCOME} ${GUEST} &nbsp;|&nbsp;
	<a href="companylogin">${LOGIN}</a>
	</#if>
</nav>
<br><br><br>