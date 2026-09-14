<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<head><title>Trang chủ</title></head>
<body>
    <h1 class="text-center">Sản phẩm nổi bật</h1>
    <div class="row mt-4">
        <c:forEach items="${products}" var="p">
            <div class="col-md-3 mb-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${p.productName}</h5>
                        <p class="card-text text-danger fw-bold">${p.price} VNĐ</p>
                        <span class="badge bg-secondary">${p.category.categoryName}</span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</body>