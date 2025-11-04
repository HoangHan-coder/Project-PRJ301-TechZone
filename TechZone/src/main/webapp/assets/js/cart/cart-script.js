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

                        if (cb.checked) {
                            checkedCount++;
                            totalQuantity += quantity;
                            totalPrice += price * quantity;

                            // Thêm input ẩn để gửi lên servlet
                            if (hiddenInputs) {
                                hiddenInputs.insertAdjacentHTML('beforeend', `
                        <input type="hidden" name="selectedCartItemIds" value="${id}">
                        <input type="hidden" name="quantity_${id}" value="${quantity}">
                    `);
                            }
                        } else {
                            allChecked = false;
                        }
                    });

                    // Hiển thị lại tổng cộng
                    document.getElementById('tong-san-pham').textContent = totalQuantity;
                    document.getElementById('tong-tien').innerHTML = formatCurrency(totalPrice) + '<sup>đ</sup>';

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

                    // 3️⃣ Sự kiện Tăng / Giảm / Xóa trong container
                    document.querySelector('#cart-items-container').addEventListener('click', e => {
                        const btn = e.target.closest('.btn-quantity-plus, .btn-quantity-minus, .btn-delete-item');
                        if (!btn)
                            return;

                        const form = btn.closest('.form-cart-item');
                        const input = form?.querySelector('.input-quantity');
                        if (!form || !input)
                            return;

                        // --- Xử lý tăng/giảm số lượng ---
                        if (btn.classList.contains('btn-quantity-plus') || btn.classList.contains('btn-quantity-minus')) {
                            let quantity = parseInt(input.value);
                            if (btn.classList.contains('btn-quantity-plus'))
                                quantity++;
                            else if (quantity > 1)
                                quantity--;

                            input.value = quantity;
                            updateItemTotal(form.querySelector('.product-row'));
                            updateCartSummary();
                            form.submit();
                        }
                        

                    });

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

                    // 5️⃣ Khi vừa tải trang
                    document.querySelectorAll('.product-row').forEach(updateItemTotal);
                    updateCartSummary();
                });