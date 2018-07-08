<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp" %>
<script src="${ctxStatic}/map/js/selectLocation.js" type="text/javascript"></script>
	<title>医院管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if($("#bannerImage").val().split("|")<1){
						top.$.jBox.tip("banner不能为空，请上传。",'error', { focusId: "bannerImage" });
						
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
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
		<li><a href="${ctx}/sys/office/list?id=${office.parent.id}&parentIds=${office.parentIds}">医院列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">医院<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<input type="hidden" name="parent.id" value="0">
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div class="controls">
                <sys:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">医院名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">LOGO:</label>
			<div class="controls">
				<form:hidden id="logoImage" path="logo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="logoImage" type="images" uploadPath="/hosp/logo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">BANNER:</label>
			<div class="controls">
				<form:hidden id="bannerImage" path="banner" htmlEscape="false" maxlength="255" class="input-xlarge" />
				<sys:ckfinder input="bannerImage" type="images" uploadPath="/hosp/banner" selectMultiple="true" maxNum="4" maxWidth="100" maxHeight="100"/>
				<span class="help-inline"><font color="red">(*必填，最多4张)</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">医院编码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<input type="hidden" name="type" value="1">
		<div class="control-group">
			<label class="control-label">机构级别:</label>
			<div class="controls">
				<form:select path="grade" class="input-medium">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否可用:</label>
			<div class="controls">
				<form:select path="useable">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">主负责人:</label>
			<div class="controls">
				 <sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">副负责人:</label>
			<div class="controls">
				 <sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
					title="用户" url="/sys/office/treeData?type=3" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50"/><input id="selectBtn" class="btn  btn-primary" type="button" value="地图选点" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经纬度:</label>
			<div class="controls">
				<form:input path="lotlat" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人:</label>
			<div class="controls">
				<form:input path="master" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">医院介绍:</label>
			<div class="controls">
				<form:textarea id="content" path="remarks" htmlEscape="false" rows="3" maxlength="1000" class="input-xxlarge"/>
				
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<script type="text/javascript">
	SelectLocation.init({
        id:'selectBtn',     //打开地图窗口按钮的ID
        url:'${ctxStatic}/map/bdMap.html',   //地图页面的地址
//        width:'1024',        //打开地图窗口的宽度,可不传
//        height:'800',        //打开地图窗口的高度,可不传
//        top:'50',            //打开地图窗口距显示器顶部的距离,可不传
//        left:'100',          //打开地图窗口显示器左边的距离,可不传
        callback:function(selectedLocation){
//             var gpsAddr = document.getElementById('address');
//             var location = document.getElementById('lotlat');

          $("#address").val(selectedLocation.gpsAddr);
          $("#lotlat").val(selectedLocation.location);
        }
    });
	</script>
</body>
</html>