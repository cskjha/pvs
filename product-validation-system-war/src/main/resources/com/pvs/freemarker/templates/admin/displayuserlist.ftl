<html>
<head><title>${USER} ${LIST}</title>
 	<link type="text/css" rel="stylesheet" href="styles/displayPlan.css">
	<script src="scripts/common.js" type="text/javascript"></script>
	<script> function showSelection(){
		var e = document.getElementById("status_select");
		var selectedOp = e.options[e.selectedIndex].text;
		alert(selectedOp);
	}</script>
</head>
<body>	
		<center>
    	 	<#include "../base/admin_header.ftl">	
			<div class="clear"></div>
	        <br><br>
	        <h2>${VIEW_USERLIST_MSG1}</h2>
	        <#if userList??>
	        <table>
	        <tr>
	        	<td align="center">${COMPANY_NAME}</td><br>
	        	<td align="center">${USERNAME}</td><br>
	        	<td align="center">${STATUS}</td>
	        	<td align="center">${ACTION}</td>
	        </tr>
	        
	        <#list userList as userlist>
	        	<tr><form method="post" action="displayuserlist">
	        		<td align="center">${userlist.companyName}<input type="hidden" name="companyName" value="${userlist.companyName}" placeholder="${COMPANY} ${NAME}" /></td><br>
	        		<td align="center"><a href="userproducttemplate?username=${userlist.companyEmail}">${userlist.companyEmail}</a><input type="hidden" name="companyEmail" value="${userlist.companyEmail}" placeholder="${COMPANY} ${EMAIL}" /> <a href="removeuser?companyname=${userlist.companyName}&username=${userlist.companyEmail}">[${REMOVE}]</a></td><br>
	        			<td align="center">
	        			<#if userlist.status == 'enabled'>
			        		
			        				<select id="status_select" name="status_select" onchange="showSelection();">
		  							<option value="disabled">  disabled  </option>
		  							<option value="enabled" selected>  enabled  </option>
  						<#else>
			        				<select id="status_select" name="status_select" onchange="showSelection();">
		  							<option value="disabled" selected>  disabled  </option>
		  							<option value="enabled">  enabled  </option>
  						</#if></td>
  					<td align="center"><input class="button" type="submit" name="submit" value="${SAVE}" /></td>
  					</form>
	        	</tr>
	        </#list>
	        <#else>
	        	${VIEW_USERLIST_MSG2}.<br>
	        </#if>
	        </table>
        </center>
</body>
</html>