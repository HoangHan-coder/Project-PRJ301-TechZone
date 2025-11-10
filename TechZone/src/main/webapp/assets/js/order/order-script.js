/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const selectVoucher = document.getElementById("selectVoucher"); // dropdown chọn voucher
const discountValue = document.getElementById("discountValue"); // hiển thị giảm giá tạm tính
const subtotalValRaw = document.getElementById("subtotalVal").textContent; // tổng phụ dạng chuỗi có định dạng
const summaryTotal = document.getElementById("summaryTotal"); // tổng thanh toán hiển thị cuối
const btnVoucher = document.getElementById("btnVoucher"); // nút áp dụng voucher
const voucherVal = document.getElementById("voucherVal"); // hiển thị giảm giá đã áp dụng
const totalAmount = document.getElementById("totalAmount"); // input ẩn tổng tiền gửi lên server
const shippingFee = parseInt("150000"); // phí vận chuyển cố định 150,000đ
var subtotal =  parseInt(subtotalValRaw.replace(/[^\d]/g, ""), 10); // chuyển tổng phụ về số (bỏ ký tự không phải số)
var discountAmount; // số tiền giảm giá sau khi chọn voucher

selectVoucher.addEventListener("change", function () { // khi chọn voucher mới
    const opt = selectVoucher.options[selectVoucher.selectedIndex]; // option đang chọn
    var type = opt.dataset.type; // loại voucher: PERCENT hoặc AMOUNT
    var discountVal = parseFloat(opt.dataset.value); // giá trị giảm (phần trăm hoặc số tiền)

    if (type === "PERCENT") { // giảm theo phần trăm
        var discountPercent = discountVal;
        discountAmount = subtotal * (discountPercent / 100);
    } else { // giảm theo số tiền cố định
        discountAmount = discountVal;
    }

    discountValue.innerHTML = "-" + discountAmount.toLocaleString("vi-VN") + "₫"; // cập nhật hiển thị giảm tạm tính


});

btnVoucher.addEventListener("click", function () { // khi bấm áp dụng voucher
    const totalAfterDiscount = subtotal + shippingFee - discountAmount; // tính tổng sau giảm
    totalAmount.value = totalAfterDiscount; // set giá trị gửi lên server
    voucherVal.innerHTML = "-" + discountAmount.toLocaleString("vi-VN") + "₫"; // hiển thị giảm cố định
    summaryTotal.innerHTML = totalAfterDiscount.toLocaleString("vi-VN") + "₫"; // cập nhật tổng cộng
});
const addressInput = document.getElementById("address");
const addressError = document.getElementById("addressError");

function validateAddress() {
    const address = addressInput.value.trim();

    // 1. Không để trống
    if (address.length === 0) {
        addressError.textContent = "Vui lòng nhập địa chỉ giao hàng.";
        return false;
    }

    // 2. Tối thiểu 5 ký tự, tối đa 200 ký tự
    if (address.length < 5 || address.length > 200) {
        addressError.textContent = "Địa chỉ phải từ 5 đến 200 ký tự.";
        return false;
    }

    // 3. Không chỉ toàn ký tự đặc biệt
    if (!/[a-zA-Z0-9]/.test(address)) {
        addressError.textContent = "Địa chỉ không hợp lệ (chỉ chứa ký tự đặc biệt).";
        return false;
    }

    // 4. Không chứa ký tự cấm
    if (/[<>]/.test(address)) {
        addressError.textContent = "Địa chỉ chứa ký tự không hợp lệ.";
        return false;
    }

    // 5. (Tùy chọn) Có ít nhất 1 chữ + 1 số
    if (!/[a-zA-ZÀ-ỹ]/.test(address) || !/\d/.test(address)) {
        addressError.textContent = "Địa chỉ cần bao gồm cả số và chữ (ví dụ: 123 Nguyễn Trãi).";
        return false;
    }

    addressError.textContent = "";
    return true;
}

// Gọi khi blur hoặc trước khi submit
addressInput.addEventListener("blur", validateAddress);

document.getElementById("checkoutForm").addEventListener("submit", (e) => { // kiểm tra trước khi gửi form
    if (!validateAddress()) {
        e.preventDefault(); // chặn submit nếu địa chỉ không hợp lệ
    }
});
