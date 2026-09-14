<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Form Danh mục</title></head>
<body>
    <div class="card shadow-sm mx-auto" style="max-width: 760px;">
        <div class="card-body">
            <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
            <form action="${pageContext.request.contextPath}/admin/category/save" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${category.id}">
                <input type="hidden" name="existingImage" value="${category.image}">

                <div class="mb-3">
                    <label class="form-label">Tên danh mục</label>
                    <input type="text" name="categoryName" value="${category.categoryName}" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Trạng thái</label>
                    <select name="status" class="form-control">
                        <option value="1" ${category.status == 1 ? 'selected' : ''}>Hoạt động</option>
                        <option value="0" ${category.status == 0 ? 'selected' : ''}>Khóa</option>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Ảnh danh mục</label>
                    <c:if test="${not empty category.image}">
                        <div class="mb-2">
                            <img src="${pageContext.request.contextPath}/image?fname=${category.image}" alt="ảnh hiện tại" style="width:80px;height:80px;object-fit:cover;">
                            <div class="form-text">Ảnh hiện tại — chọn ảnh mới bên dưới nếu muốn thay đổi.</div>
                        </div>
                    </c:if>
                    <input type="file" name="imageFile" class="form-control" accept="image/*">
                </div>

                <div class="d-flex gap-2">
                    <button type="submit" class="btn btn-primary">Lưu</button>
                    <a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn-secondary">Hủy</a>
                </div>
            </form>
        </div>
    </div>
</body>
