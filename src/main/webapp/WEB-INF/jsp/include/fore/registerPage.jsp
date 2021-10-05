<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<script>
    $(function () {

        <c:if test="${!empty msg}">
        $("span.errorMessage").html("${msg}");
        $("div.registerErrorMessageDiv").css("visibility", "visible");
        </c:if>

        $(".form-horizontal").submit(function () {
            if (0 == $("#inputU").val().length) {
                $("span.errorMessage").html("Please enter username.");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }
            if (0 == $("#inputPassword3").val().length) {
                $("span.errorMessage").html("Please enter password");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }
            if (0 == $("#inputPassword2").val().length) {
                $("span.errorMessage").html("Re-enter password");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }
            if ($("#inputPassword3").val() != $("#inputPassword2").val()) {
                $("span.errorMessage").html("Passwords not match");
                $("div.registerErrorMessageDiv").css("visibility", "visible");
                return false;
            }

            return true;
        });
    })
</script>
<div align="center">
	<h3>Create account</h3>
</div>

<form class="form-horizontal" method="post" action="foreregister">

	<!--用于显示错误信息-->
	<div class="registerErrorMessageDiv">
		<div class="alert alert-danger" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
			<span class="errorMessage"></span>
		</div>
	</div>

    <!--输入用户名-->
    <div class="form-group">
        <label for="inputU" class="col-sm-2 control-label">Username</label>
        <div class="col-sm-10">
            <input type="text" name="name" class="form-control" id="inputU" placeholder="Your Username">
        </div>
    </div>

    <!--输入密码-->
    <div class="form-group">
        <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
        <div class="col-sm-10">
            <input type="password" name="password" class="form-control" id="inputPassword3" placeholder="Password">
        </div>
    </div>

    <!--再次输入密码-->
    <div class="form-group">
        <label for="inputPassword2" class="col-sm-2 control-label">Re-enter Password</label>
        <div class="col-sm-10">
            <input type="password" class="form-control" id="inputPassword2" placeholder="Re-enter password">
        </div>
    </div>

    <!--注册-->
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
			<a href="registerSuccess.jsp"><button type="submit" class="btn btn-default">Continue</button></a>
        </div>
    </div>
</form>