<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/head.jsp" %>
    <title>医院二维码管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
           
           
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
		<li class="active">${office.name}二维码信息</li>
		
	</ul>
<br/>

    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">二维码:</label>
        <div class="controls">
        	<c:if test="${empty office.ercode}">
        		请点击“生成”按钮生成二维码
        	</c:if>
        	<c:if test="${not empty office.ercode}">
        		<img alt="医院二维码" src="${office.ercode}">
        	</c:if>
            	
        </div>
    </div>
    <form id="inputForm" action="${ctx}/wx/ercode" method="post" class="form-horizontal">
    	<input type="hidden" name="flag" value="true"/>
    	<div class="form-actions">
        	<shiro:hasPermission name="sys:office:edit">
        		<input id="btnSubmit" class="btn btn-primary" type="submit"  value='<c:if test="${not empty office.ercode}">重新</c:if>生成'/>                                               
            </shiro:hasPermission>
        	
    	</div>
    </form>
</body>
</html>