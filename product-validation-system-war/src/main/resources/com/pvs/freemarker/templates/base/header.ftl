<h2>Product Validation System</h2>
<nav>
	<#if companyName??>
	  Welcome ${companyName!"Guest"} &nbsp;|&nbsp;
	  <a href="logout">Logout</a>
	<#else>
		 Welcome Guest &nbsp;|&nbsp;
	  	<a href="companyregister">Register</a>&nbsp;|&nbsp;
		<a href="companylogin">Login</a>
	</#if>
</nav>