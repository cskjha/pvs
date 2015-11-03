<h2>Product Validation System</h2>
<nav>
	<#if user??>
	  Welcome ${user!"Guest"} &nbsp;|&nbsp;
	  <a href="logout">Logout</a>
	<#else>
		 Welcome Guest &nbsp;|&nbsp;
	  	<a href="companyregister">Register</a>&nbsp;|&nbsp;
		<a href="companylogin">Login</a>
	</#if>
</nav>