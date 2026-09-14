<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Sản phẩm</title></head>
<body>
    <div class="d-flex justify-content-between align-items-center mb-3 gap-3 flex-wrap">
        <a href="${pageContext.request.contextPath}/admin/product/add" class="btn btn-success">Thêm Sản phẩm</a>
        <form action="${pageContext.request.contextPath}/admin/product/list" class="d-flex flex-grow-1 justify-content-end">
            <input type="text" name="keyword" value="${keyword}" class="form-control me-2" placeholder="Tìm theo tên sản phẩm">
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
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Danh mục</th>
                        <th>Giá</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${products}" var="p">
                        <tr>
                            <td>${p.id}</td>
                            <td>${p.productName}</td>
                            <td>${p.category != null ? p.category.categoryName : ''}</td>
                            <td>${p.price}</td>
                            <td>${p.status == 1 ? 'Mở' : 'Khóa'}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/product/edit/${p.id}" class="btn btn-sm btn-warning">Sửa</a>
                                <a href="${pageContext.request.contextPath}/admin/product/delete/${p.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty products}">
                        <tr><td colspan="6" class="text-center text-muted py-4">Chưa có sản phẩm nào.</td></tr>
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
