<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>${pageTitle} - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/libs/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css">
</head>
<body class="bg-light">
    <div class="container-fluid">
        <div class="row min-vh-100">
            <aside class="col-md-3 col-lg-2 bg-dark text-white p-0">
                <div class="p-4 border-bottom border-secondary">
                    <h4 class="mb-0">Admin Panel</h4>
                </div>
                <nav class="nav flex-column p-3">
                    <a class="nav-link ${fn:contains(pageContext.request.requestURI, '/admin/category/') ? 'active bg-secondary rounded' : 'text-white-50'}" href="${pageContext.request.contextPath}/admin/category/list">Danh mục</a>
                    <a class="nav-link ${fn:contains(pageContext.request.requestURI, '/admin/product/') ? 'active bg-secondary rounded' : 'text-white-50'}" href="${pageContext.request.contextPath}/admin/product/list">Sản phẩm</a>
                    <a class="nav-link ${fn:contains(pageContext.request.requestURI, '/admin/user/') ? 'active bg-secondary rounded' : 'text-white-50'}" href="${pageContext.request.contextPath}/admin/user/list">Người dùng</a>
                </nav>
                <div class="mt-auto p-3 border-top border-secondary">
                    <c:if test="${not empty sessionScope.loggedUser}">
                        <div class="text-white-50 small">Đăng nhập:</div>
                        <div class="fw-semibold">${sessionScope.loggedUser.username}</div>
                    </c:if>
                    <a class="btn btn-outline-light btn-sm mt-3 w-100" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                </div>
            </aside>

            <main class="col-md-9 col-lg-10 p-4">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <h2 class="mb-0">Quản trị</h2>
                    </div>
                </div>
                <jsp:include page="${contentView}"/>
            </main>
        </div>
    </div>
</body>
</html>