/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const selectVoucher = document.getElementById("selectVoucher");
const discountValue = document.getElementById("discountValue");
const subtotalValRaw = document.getElementById("subtotalVal").textContent;
const summaryTotal = document.getElementById("summaryTotal");
const btnVoucher = document.getElementById("btnVoucher");
const voucherVal = document.getElementById("voucherVal");
console.log(discountValue.value);
var subtotal = parseInt(subtotalValRaw.replace(/\./g, ""), 10);
var discountAmount;
selectVoucher.addEventListener("change", function () {
    const opt = selectVoucher.options[selectVoucher.selectedIndex];
    var type = opt.dataset.type;
    var discountVal = parseFloat(opt.dataset.value);
    console.log(opt.textcontent);

    console.log(discountPercent); // => "PERCENT" hoặc "FIXED"
    console.log(subtotal);

    if (type === "PERCENT") {
        var discountPercent = discountVal;
        discountAmount = subtotal * (discountPercent / 100);
    } else {
        discountAmount = discountVal;
    }

    discountValue.innerHTML = "-" + discountAmount.toLocaleString("vi-VN") + "₫";


});

btnVoucher.addEventListener("click", function () {
    console.log(discountAmount); // => "PERCENT" hoặc "FIXED"
    console.log("------------");
    console.log(subtotal - discountAmount);
    const totalAfterDiscount = subtotal - discountAmount;

    voucherVal.innerHTML = "-" + discountAmount.toLocaleString("vi-VN") + "₫";
    summaryTotal.innerHTML = totalAfterDiscount.toLocaleString("vi-VN") + "₫";
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

document.getElementById("checkoutForm").addEventListener("submit", (e) => {
    if (!validateAddress()) {
        e.preventDefault(); // chặn submit
    }
});
