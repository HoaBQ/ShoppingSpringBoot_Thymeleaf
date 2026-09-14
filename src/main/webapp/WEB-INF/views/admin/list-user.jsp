<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Người dùng</title></head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-3 gap-3 flex-wrap">
        <a href="${pageContext.request.contextPath}/admin/user/add" class="btn btn-success">Thêm User</a>
        <form action="${pageContext.request.contextPath}/admin/user/list" class="d-flex flex-grow-1 justify-content-end">
            <input type="text" name="keyword" value="${keyword}" class="form-control me-2" placeholder="Tìm theo username">
            <button type="submit" class="btn btn-primary">Tìm</button>
        </form>
    </div>
    <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-bordered table-striped mb-0">
                <thead class="table-dark"><tr><th>ID</th><th>Username</th><th>Email</th><th>Quyền</th><th>Trạng thái</th><th>Thao tác</th></tr></thead>
                <tbody>
                    <c:forEach items="${users}" var="u">
                        <tr>
                            <td>${u.id}</td><td>${u.username}</td><td>${u.email}</td>
                            <td>${u.role == 1 ? 'Admin' : 'User'}</td><td>${u.status == 1 ? 'Mở' : 'Khóa'}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/user/edit/${u.id}" class="btn btn-sm btn-warning">Sửa</a>
                                <a href="${pageContext.request.contextPath}/admin/user/delete/${u.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa người dùng này?');">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty users}">
                        <tr><td colspan="6" class="text-center text-muted py-4">Không tìm thấy người dùng nào.</td></tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <c:if test="${totalPages > 1}">
        <nav class="mt-3">
            <ul class="pagination justify-content-center">
                <c:forEach begin="1" end="${totalPages}" var="p">
                    <li class="page-item ${p == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?keyword=${keyword}&page=${p}">${p}</a>
                    </li>
                </c:forEach>
            </ul>
        </nav>
    </c:if>
</body>