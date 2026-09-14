<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Form User</title></head>
<body>
    <div class="card shadow-sm mx-auto" style="max-width: 760px;">
        <div class="card-body">
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
            <form action="${pageContext.request.contextPath}/admin/user/save" method="post">
                <input type="hidden" name="id" value="${user.id}">
                <div class="mb-3">
                    <label class="form-label">Tên đăng nhập</label>
                    <input type="text" name="username" value="${user.username}" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mật khẩu</label>
                    <input type="password" name="password" class="form-control" placeholder="${user.id > 0 ? 'Để trống nếu không đổi mật khẩu' : 'Nhập mật khẩu'}">
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" name="email" value="${user.email}" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Quyền</label>
                    <select name="role" class="form-control">
                        <option value="1" ${user.role == 1 ? 'selected' : ''}>Admin</option>
                        <option value="0" ${user.role == 0 ? 'selected' : ''}>User</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Trạng Thái</label>
                    <select name="status" class="form-control">
                        <option value="1" ${user.status == 1 ? 'selected' : ''}>Mở</option>
                        <option value="0" ${user.status == 0 ? 'selected' : ''}>Khóa</option>
                    </select>
                </div>
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                    <a href="${pageContext.request.contextPath}/admin/user/list" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</body>