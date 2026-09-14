<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đăng nhập - ShoppingSpringBoot</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/libs/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/libs/bootstrap-icons/bootstrap-icons.css">
    <style>
        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #172554 0%, #1d4ed8 55%, #38bdf8 100%);
        }
        .login-shell {
            max-width: 430px;
        }
        .login-card {
            border: 0;
            border-radius: 1rem;
            box-shadow: 0 1rem 3rem rgba(15, 23, 42, .25);
        }
        .brand-mark {
            width: 64px;
            height: 64px;
            border-radius: 1rem;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            background: #dbeafe;
            color: #1d4ed8;
            font-size: 2rem;
        }
        .form-control {
            min-height: 48px;
        }
        .btn-login {
            min-height: 48px;
            font-weight: 600;
        }
    </style>
</head>
<body>
    <main class="container d-flex align-items-center justify-content-center min-vh-100 py-4">
        <div class="login-shell w-100">
            <div class="card login-card">
                <div class="card-body p-4 p-md-5">
                    <div class="text-center mb-4">
                        <div class="brand-mark mb-3">
                            <i class="bi bi-shop"></i>
                        </div>
                        <h1 class="h3 fw-bold mb-2">ShoppingSpringBoot</h1>
                        <p class="text-secondary mb-0">Đăng nhập trang quản trị</p>
                    </div>

                    <c:if test="${param.error == 'access_denied'}">
                        <div class="alert alert-warning d-flex align-items-center" role="alert">
                            <i class="bi bi-shield-exclamation me-2"></i>
                            <span>Vui lòng đăng nhập bằng tài khoản Admin.</span>
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger d-flex align-items-center" role="alert">
                            <i class="bi bi-exclamation-circle me-2"></i>
                            <span>${error}</span>
                        </div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/login" method="post">
                        <div class="mb-3">
                            <label for="username" class="form-label fw-semibold">Tên đăng nhập</label>
                            <div class="input-group">
                                <span class="input-group-text bg-white"><i class="bi bi-person"></i></span>
                                <input id="username" type="text" name="username" class="form-control"
                                       placeholder="Nhập tên đăng nhập" autocomplete="username" required>
                            </div>
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label fw-semibold">Mật khẩu</label>
                            <div class="input-group">
                                <span class="input-group-text bg-white"><i class="bi bi-lock"></i></span>
                                <input id="password" type="password" name="password" class="form-control"
                                       placeholder="Nhập mật khẩu" autocomplete="current-password" required>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary btn-login w-100">
                            <i class="bi bi-box-arrow-in-right me-2"></i>Đăng nhập
                        </button>
                    </form>
                </div>
            </div>
            <p class="text-center text-white-50 small mt-4 mb-0">Hệ thống quản trị cửa hàng</p>
        </div>
    </main>
</body>
</html>
