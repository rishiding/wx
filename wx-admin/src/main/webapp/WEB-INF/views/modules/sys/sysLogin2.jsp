<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>${fns:getConfig('productName')} 登录</title>
    <script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    
    <link href="${ctxStatic}/css/zh.css"  rel="stylesheet" />
    <link href="${ctxStatic}/css/login.css"  rel="stylesheet" />  
    <meta name="decorator" content="blank"/>   
    <script type="text/javascript">
        $(document).ready(function () {
        	var mes='${message}';
        	if(mes){
        		alert(mes);
        	}
        });
        // 如果在框架或在对话框中，则弹出提示并跳转到首页
        if (self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0) {
            alert('未登录或登录超时。请重新登录，谢谢！');
            top.location = "${ctx}";
        }
    </script>
</head>
<body>
<div class="header">
    <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}">
        <button data-dismiss="alert" class="close">×</button>
        <label id="loginError" class="error">${message}</label>
    </div>
</div>
 <div class="bd">
        <div class="title">${fns:getConfig('productName')}</div>
        <!--登录框开始-->
        <div class="login_windows">
            <span class="user_title">用户登录</span>
            <form id="loginForm"  action="${ctx}/login" method="post">
                <div class="text">
                    <input type="text" class="username required" name="username" placeholder="用户名" value="${username}">
                    <input type="password" class="password required" name="password" placeholder="密码">
                </div>
                <div class="remember"><label for="rememberMe" title="下次不需要再登录">
                <input type="checkbox" class="checkbox" id="rememberMe"   name="rememberMe" ${rememberMe ? 'checked' : ''}><span>记住我</span></div>
                <input type="submit" value="立即登录" />
            </form>
        </div>
        <!--登录框结束-->
    </div>
    <div class="footer">Copyright © 2018  ${fns:getConfig('productName')}     |     客服热线：XXXXXXXXX</div>
    </body>



</html>