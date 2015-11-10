<h2>Product Validation System</h2>
<link type="text/css" rel="stylesheet" href="styles/common.css">
<nav class="header-style">
	<#if companyName??>
	  Welcome ${companyName!"Guest"} &nbsp;|&nbsp;
	  <a href="planregister">Register Plan</a>&nbsp;|&nbsp;
	  <a href="myplan">My Plan</a>&nbsp;|&nbsp;
	  <a href="registerproducttemplate">Register Product Template</a>&nbsp;|&nbsp;
	  <a href="viewproducttemplates">View Product Templates</a>&nbsp;|&nbsp;
	  <a href="logout">Logout</a>
	<#else>
		 Welcome Guest &nbsp;|&nbsp;
	  	<a href="companyregister">Register</a>&nbsp;|&nbsp;
		<a href="companylogin">Login</a>
	</#if>
</nav>