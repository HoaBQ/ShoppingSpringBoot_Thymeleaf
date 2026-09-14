<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Đăng ký</title></head>
<body>
    <div class="w-50 mx-auto mt-5">
        <h3 class="text-center">Đăng ký tài khoản</h3>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <form action="${pageContext.request.contextPath}/register" method="post">
            <div class="mb-3"><label>Username</label><input type="text" name="username" class="form-control" required></div>
            <div class="mb-3"><label>Mật khẩu</label><input type="password" name="password" class="form-control" required></div>
            <div class="mb-3"><label>Email</label><input type="email" name="email" class="form-control" required></div>
            <button type="submit" class="btn btn-success w-100">Đăng ký</button>
        </form>
    </div>
</body>