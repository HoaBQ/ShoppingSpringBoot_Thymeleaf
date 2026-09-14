<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Form Sản phẩm</title></head>
<body>
    <div class="card shadow-sm mx-auto" style="max-width: 760px;">
        <div class="card-body">
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
            <form action="${pageContext.request.contextPath}/admin/product/save" method="post">
                <input type="hidden" name="id" value="${product.id}">
                <div class="mb-3"><label class="form-label">Tên Sản Phẩm</label><input type="text" name="productName" value="${product.productName}" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Giá</label><input type="number" step="0.01" name="price" value="${product.price}" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Mô tả</label><textarea name="description" class="form-control">${product.description}</textarea></div>
                <div class="mb-3">
                    <label class="form-label">Danh mục</label>
                    <select name="category.id" class="form-control">
                        <c:forEach items="${categories}" var="cat">
                            <option value="${cat.id}" ${product.category != null && product.category.id == cat.id ? 'selected' : ''}>${cat.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Trạng Thái</label>
                    <select name="status" class="form-control">
                        <option value="1" ${product.status == 1 ? 'selected' : ''}>Mở</option>
                        <option value="0" ${product.status == 0 ? 'selected' : ''}>Khóa</option>
                    </select>
                </div>
                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                    <a href="${pageContext.request.contextPath}/admin/product/list" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</body>