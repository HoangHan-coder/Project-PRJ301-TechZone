/* =========================================================
 PHẦN 1: HÀM TIỆN ÍCH CHUNG
 ========================================================= */

/**
 * Định dạng số thành chuỗi tiền tệ kiểu Việt Nam.
 * Ví dụ: 1234567 → "1.234.567"
 */
function formatCurrency(num) {
    return num.toLocaleString('vi-VN', {style: 'decimal'});
}

/**
 * Cập nhật tổng tiền cho một dòng sản phẩm.
 * @param {HTMLElement} row - Thẻ cha chứa thông tin sản phẩm (class="product-row")
 */
function updateItemTotal(row) {
    const quantityInput = row.querySelector('.input-quantity');
    const unitPrice = parseFloat(quantityInput.dataset.unitPrice);
    const quantity = parseInt(quantityInput.value);
    const totalEl = row.querySelector('.item-total-price');
    if (totalEl)
        totalEl.textContent = formatCurrency(unitPrice * quantity) + '₫';
}

/* =========================================================
 PHẦN 2: HÀM CHÍNH TÍNH TỔNG GIỎ HÀNG
 ========================================================= */

function updateCartSummary() {
    let totalQuantity = 0, totalPrice = 0, checkedCount = 0;
    let allChecked = true;
    const hiddenInputs = document.getElementById('selected-items-inputs');
    if (hiddenInputs)
        hiddenInputs.innerHTML = '';

    document.querySelectorAll('.cart-item-checkbox').forEach(cb => {
        const row = cb.closest('.product-row, .form-cart-item');
        const qtyInput = row?.querySelector('.input-quantity');
        if (!row || !qtyInput)
            return;

        const quantity = parseInt(qtyInput.value);
        const price = parseFloat(cb.dataset.price);
        const id = cb.dataset.itemId;
        const cartId = cb.dataset.cartId;

        if (cb.checked) {
            checkedCount++;
            totalQuantity += quantity;
            totalPrice += price * quantity;

            // Thêm input ẩn để gửi lên servlet
            if (hiddenInputs) {
                hiddenInputs.insertAdjacentHTML('beforeend', `
                    <input type="hidden" name="view" value="check-out-cart">
                    <input type="hidden" name="cartId" value="${cartId}">
                    <input type="hidden" name="cartItemIds" value="${id}">
                    <input type="hidden" name="quantity-${id}" value="${quantity}">
                `);
            }
        } else {
            allChecked = false;
        }
    });

    // Hiển thị lại tổng cộng
    const tongSP = document.getElementById('tong-san-pham');
    const tongTien = document.getElementById('tong-tien');
    if (tongSP)
        tongSP.textContent = totalQuantity;
    if (tongTien)
        tongTien.innerHTML = formatCurrency(totalPrice) + '<sup>đ</sup>';

    // Bật/tắt nút Mua hàng
    const btn = document.getElementById('btn-mua-hang');
    if (btn)
        btn.disabled = checkedCount === 0;

    // Đồng bộ checkbox "Chọn tất cả"
    ['chon-tat-ca-header', 'chon-tat-ca-footer'].forEach(id => {
        const box = document.getElementById(id);
        if (box)
            box.checked = allChecked;
    });
}

/* =========================================================
 PHẦN 3: XỬ LÝ SỰ KIỆN SAU KHI TRANG TẢI XONG
 ========================================================= */

document.addEventListener('DOMContentLoaded', () => {

    const checkboxes = document.querySelectorAll('.cart-item-checkbox');
    const selectAllBoxes = [
        document.getElementById('chon-tat-ca-header'),
        document.getElementById('chon-tat-ca-footer')
    ].filter(Boolean);

    // 1️⃣ Khi người dùng tick chọn sản phẩm
    checkboxes.forEach(cb => cb.addEventListener('change', updateCartSummary));

    // 2️⃣ Khi tick “Chọn tất cả”
    selectAllBoxes.forEach(box => box.addEventListener('change', e => {
            checkboxes.forEach(cb => cb.checked = e.target.checked);
            updateCartSummary();
        }));

    // 3️⃣ Sự kiện Tăng / Giảm trong container
    const itemsContainer = document.querySelector('#cart-items-container');
    if (itemsContainer) {
        itemsContainer.addEventListener('click', e => {
            const btn = e.target.closest('.btn-quantity-plus, .btn-quantity-minus');
            if (!btn)
                return;

            const form = btn.closest('.form-cart-item');
            const input = form?.querySelector('.input-quantity');
            if (!form || !input)
                return;

            let quantity = parseInt(input.value);
            if (btn.classList.contains('btn-quantity-plus'))
                quantity++;
            else if (quantity > 1)
                quantity--;

            input.value = quantity;
            updateItemTotal(form.querySelector('.product-row'));
            updateCartSummary();
            form.submit();
        });
    }

    // 4️⃣ Khi người dùng nhập số lượng thủ công
    document.querySelectorAll('.input-quantity').forEach(input => {
        input.addEventListener('change', function () {
            let quantity = parseInt(this.value);
            if (isNaN(quantity) || quantity < 1)
                this.value = 1;
            updateItemTotal(this.closest('.product-row'));
            updateCartSummary();
            this.closest('.form-cart-item').submit();
        });
    });

    // 5️⃣ Xóa sản phẩm (xác nhận trước khi gửi)
    document.body.addEventListener('click', function (e) {
        const delBtn = e.target.closest('.btn-delete-item');
        if (!delBtn)
            return;
        e.preventDefault();
        if (!confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ?'))
            return;

        // Gửi POST xóa 1 item theo cartItemId
        const cartItemId = delBtn.getAttribute('data-cart-item-id');
        if (!cartItemId) {
            // fallback: nếu là link, điều hướng
            const href = delBtn.getAttribute('href');
            if (href)
                window.location.href = href;
            return;
        }

        // AI supports: gửi form tối giản để xóa
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = (document.body.getAttribute('data-context-path') || '') + '/cartitem';
        form.innerHTML = '<input type="hidden" name="action" value="delete-item">' +
                '<input type="hidden" name="cartItemId" value="' + cartItemId + '">';
        document.body.appendChild(form);
        form.submit();
    });

    // 6️⃣ Thanh toán: gửi POST với danh sách đã chọn
    const btnBuy = document.getElementById('btn-mua-hang');
    const checkoutForm = document.getElementById('checkout-form');
    if (btnBuy && checkoutForm) {
        btnBuy.addEventListener('click', function () {
            if (btnBuy.disabled)
                return;
            checkoutForm.submit();
        });
    }

    // 6.1️⃣ Xóa các mục đã chọn (bulk)
    const btnDeleteSelected = document.getElementById('btn-delete-selected');
    if (btnDeleteSelected) {
        btnDeleteSelected.addEventListener('click', function () {
            const selected = Array.from(document.querySelectorAll('.cart-item-checkbox:checked'))
                    .map(cb => cb.dataset.itemId);
            if (selected.length === 0)
                return;
            if (!confirm('Xóa ' + selected.length + ' sản phẩm đã chọn?'))
                return;

            // AI supports: gửi form tối giản để xóa nhiều item
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = (document.body.getAttribute('data-context-path') || '') + '/cartitem';
            form.innerHTML = '<input type="hidden" name="action" value="delete-items">';
            selected.forEach(id => {
                const inp = document.createElement('input');
                inp.type = 'hidden';
                inp.name = 'selectedCartItemIds';
                inp.value = id;
                form.appendChild(inp);
            });
            document.body.appendChild(form);
            form.submit();
        });
    }

    // 7️⃣ Khi vừa tải trang
    document.querySelectorAll('.product-row').forEach(updateItemTotal);
    updateCartSummary();
});