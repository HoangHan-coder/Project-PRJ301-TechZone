<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../Includes/navbar-admin.jsp" %>

<!-- Content -->
<div class="content">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <form action="${pageContext.request.contextPath}/admin?action=profile" method="post">
                    
                    <h3 class="mb-4">Hồ sơ cá nhân</h3>
                    <div class="card shadow-sm">
                        <div class="card-body">
                            <form>
                                <div class="mb-3">
                                    <label class="form-label">Họ và tên</label>
                                    <input type="text" class="form-control" value="Nguyễn Văn A" readonly>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Email</label>
                                    <input type="email" class="form-control" value="nguyenvana@example.com" readonly>
                                </div>
                                <div class="row">
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Số điện thoại</label>
                                        <input type="text" class="form-control" value="0123456789" readonly>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <label class="form-label">Địa chỉ</label>
                                        <input type="text" class="form-control" value="123 Đường ABC, Quận 1, TP. Hồ Chí Minh" readonly>
                                    </div>
                                </div>
                                <div class="d-flex gap-2">
                                    <button type="button" class="btn btn-primary">Chỉnh sửa thông tin</button>
                                    <button type="button" class="btn btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#changePasswordModal">Đổi mật khẩu</button>
                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- Modal đổi mật khẩu -->
                    <div class="modal fade" id="changePasswordModal" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Đổi mật khẩu</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <div class="mb-3">
                                            <label class="form-label">Mật khẩu cũ</label>
                                            <input type="password" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Mật khẩu mới</label>
                                            <input type="password" class="form-control">
                                        </div>
                                        <div class="mb-3">
                                            <label class="form-label">Xác nhận mật khẩu</label>
                                            <input type="password" class="form-control">
                                        </div>
                                        <button type="submit" class="btn btn-primary">Lưu mật khẩu</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
