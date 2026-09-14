<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Danh mục</title></head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-3 gap-3 flex-wrap">
        <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-success">Thêm Danh mục</a>
        <form action="${pageContext.request.contextPath}/admin/category/list" class="d-flex flex-grow-1 justify-content-end">
            <input type="text" name="keyword" value="${keyword}" class="form-control me-2" placeholder="Tìm theo tên danh mục">
            <button type="submit" class="btn btn-primary">Tìm</button>
        </form>
    </div>

    <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>

    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-bordered table-striped mb-0">
                <thead class="table-dark">
                    <tr>
                        <th style="width:70px">ID</th>
                        <th style="width:90px">Ảnh</th>
                        <th>Tên danh mục</th>
                        <th style="width:140px">Trạng thái</th>
                        <th style="width:170px">Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${categories}" var="c">
                        <tr>
                            <td>${c.id}</td>
                            <td>
                                <c:if test="${not empty c.image}">
                                    <img src="${pageContext.request.contextPath}/image?fname=${c.image}" alt="${c.categoryName}" style="width:48px;height:48px;object-fit:cover;">
                                </c:if>
                            </td>
                            <td>${c.categoryName}</td>
                            <td>${c.status == 1 ? 'Hoạt động' : 'Khóa'}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/category/edit/${c.id}" class="btn btn-sm btn-warning">Sửa</a>
                                <a href="${pageContext.request.contextPath}/admin/category/delete/${c.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa danh mục này?');">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty categories}">
                        <tr><td colspan="5" class="text-center text-muted py-4">Không tìm thấy danh mục nào.</td></tr>
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
