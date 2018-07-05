<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function page(n,s){
		if(n) $("#pageNo").val(n);
		if(s) $("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/sys/office/deptList");
		$("#searchForm").submit();
    	return false;
    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/office/deptList?type=2&id=${office.id}&parentIds=${office.parentIds}">科室列表</a></li>
		<shiro:hasPermission name="sys:office:edit"><li><a href="${ctx}/sys/office/deptForm?parent.id=${office.id}">科室添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/office/deptList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<sys:message content="${message}"/>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>所属医院</th><th>科室名称</th><th>科室编码</th><th>负责人</th><th>电话</th><shiro:hasPermission name="sys:office:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="row">
			<tr id="${row.id}" >
			<td>${row.parent.name}</td>
			<td><a href="${ctx}/sys/office/form?id=${row.id}">${row.name}</a></td>
			<td>${row.code}</td>					
			<td>${row.master}</td>
			<td>${row.phone}</td>
			<shiro:hasPermission name="sys:office:edit"><td>
				<a href="${ctx}/sys/office/deptForm?id=${row.id}">修改</a>
				<a href="${ctx}/sys/office/delete?id=${row.id}&type=2" onclick="return confirmx('要删除该科室及所有子科室项吗？', this.href)">删除</a>
			</td></shiro:hasPermission>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>