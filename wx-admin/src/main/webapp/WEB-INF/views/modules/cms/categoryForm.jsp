<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 <%@ include file="/WEB-INF/views/include/head.jsp" %>
	<title>栏目管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/category/">栏目列表</a></li>
		<li class="active"><a href="${ctx}/cms/category/form?id=${category.id}&parent.id=${category.parent.id}">栏目<shiro:hasPermission name="cms:category:edit">${not empty category.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:category:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="category" action="${ctx}/cms/category/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">所属医院:</label>
			<div class="controls">
                <sys:treeselect id="office" name="office.id" value="${category.office.id}" labelName="office.name" labelValue="${category.office.name}"
					title="机构" url="/sys/office/treeData" cssClass="required"/>
			</div>
		</div>
		<form:hidden path="parent.id" value="0"/>
		
		<div class="control-group">
			<label class="control-label">栏目名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">缩略图:</label>
			<div class="controls">
				<form:hidden path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="image" type="thumb" uploadPath="/cms/category"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
				<span class="help-inline">栏目的排列次序</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		
		
		
		
		<div class="form-actions">
			<shiro:hasPermission name="cms:category:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>