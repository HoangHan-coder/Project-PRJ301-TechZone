<%-- 
    Document   : create-product
    Created on : Nov 1, 2025, 5:07:37 PM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../includes/navbar-admin.jsp" %>
<style>
    .container{
        transform: translateX(150px);
        width: 80%;
    }
</style>
<div class="container my-5">
    <div class="card shadow-lg p-3 p-md-5">
        <h1 class="mb-4 text-primary border-bottom pb-2">Them San Pham</h1>

        <form onsubmit="return validatesubmit()" class="product-form" action="http://localhost:8080/TechZone/admin/product" method="post" enctype="multipart/form-data">
            <input type="hidden" id="categorypro" name="action" value="createproduct">
            <div class="card mb-4 bg-light">
                <div class="card-body">
                    <h5 class="card-title text-info mb-3">1. Thông tin Cơ bản</h5>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="productName" class="form-label">Ten San Pham<span class="text-danger">(*)</span></label>
                            <input type="text" class="form-control" id="productName" name="productname" required>
                        </div>
                        <div class="col-md-6">
                            <label for="category" class="form-label">Danh mục <span class="text-danger">(*)</span></label>
                            <select id="category" class="form-select" name="CategoryID" onchange="saveCategoryValue(this.value)" required>
                                <option value="">Please choose option</option>
                                <option value="1">Laptop</option>
                                <option value="2">Phone</option>
                                <option value="3">Accessories</option>
                            </select>


                        </div>
                        <div class="col-md-6">
                            <label for="brand" class="form-label">Thuong hieu</label>
                            <input type="text" class="form-control" id="brand" name="brand" required>
                        </div>
                        <div class="col-md-6">
                            <label for="price" class="form-label">Giá</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="price" onchange="updatePrice(this.value);" name="price" required>
                                <span class="input-group-text">VND</span>
                            </div>
                        </div>

                        <div class="col-md-6">
                            <label for="model" class="form-label">Model</label>
                            <input type="text" class="form-control" id="model" name="model" required>
                        </div>
                        <div class="col-12">
                            <label for="description" class="form-label">Mô tả sản phẩm</label>
                            <textarea class="form-control" id="description" rows="5" name="descriptionproduct" required></textarea>
                        </div>
                    </div>
                </div>
            </div>

            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title text-info mb-3">2. Thông số Kỹ thuật</h5>
                    <div class="row g-3">
                        <div class="col-lg-3 col-md-6" id="cpu">
                            <label for="cpu" class="form-label">CPU</label>
                            <input type="text" class="form-control" id="cpu1" name="cpu" required>
                        </div>
                        <div class="col-lg-3 col-md-6" id="ram">
                            <label for="ram" class="form-label">RAM</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="ram1" name="ram" required>
                                <span class="input-group-text">GB</span>
                            </div>
                        </div>
                        <div class="col-lg-3 col-md-6" id="storage">
                            <label for="storage" class="form-label">Ổ cứng (Storage)</label>
                            <input type="text" class="form-control" id="storage1" name="storage" required>
                        </div>
                        <div class="col-lg-3 col-md-6" id="os">
                            <label for="os" class="form-label">Hệ điều hành</label>
                            <input type="text" class="form-control" id="os1" name="os" required>
                        </div>

                        <div class="col-md-6">
                            <label for="weight" class="form-label">Trọng lượng</label>
                            <div class="input-group">
                                <input type="text" class="form-control" id="weight" name="weight" required>
                                <span class="input-group-text">kg</span>
                            </div>
                        </div>
                        <!--                        <div class="col-md-6">
                                                    <label for="color" class="form-label">Màu sắc sản phẩm</label>
                                                    <input type="text" class="form-control" id="color" placeholder="Bạc, Xanh đen, Đỏ..." name="color">
                                                </div>-->

                        <div class="col-md-6" id="camerainput">

                        </div>

                    </div>
                </div>
            </div>

            <div class="card mb-4 bg-light">
                <div class="card-body">
                    <h5 class="card-title text-info mb-3">3. Kho hàng & Hình ảnh</h5>
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label for="quantity" class="form-label">Số lượng tồn kho</label>
                            <input type="text" class="form-control" id="quantity" min="0" name="stock" required>
                        </div>
                        <div class="col-md-6">
                            <label for="status" class="form-label">Tình trạng</label>
                            <select id="status" class="form-select" name="status">
                                <option value="in-stock">Còn hàng</option>
                                <option value="out-stock">Hết hàng</option>
                            </select>
                        </div>

                        <div class="col-12">
                            <label class="form-label">Hình ảnh sản phẩm <span class="text-danger">(*)</span></label>
                            <div class="border border-primary border-2 p-5 text-center bg-white rounded-3 upload-area" onclick="document.getElementById('fileUpload').click();">
                                <p class="mb-0 text-primary fw-bold">Kéo và thả ảnh vào đây, hoặc <span class="text-decoration-underline">Chọn tệp</span></p>
                                <input type="file" id="fileUpload" multiple accept="image/*" class="d-none" name="img" required>
                            </div>
                            <div class="mt-3 image-preview">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-grid gap-2 d-md-flex justify-content-md-end pt-3">
                <button type="submit" class="btn btn-primary btn-lg">
                    <i class="bi bi-plus-circle me-2"></i> Thêm sản phẩm
                </button>
            </div>

        </form>
    </div>
</div>
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const category = document.getElementById("category");

//       
        [category].forEach(el => el.addEventListener("change", fetchinput));

        function fetchinput() {
            const containercam = document.getElementById("camerainput");
            const containercpu = document.getElementById("cpu");
            const containerram = document.getElementById("ram");
            const containerstorage = document.getElementById("storage");
            const containeros = document.getElementById("os");
            const cat = category.value;
            console.log(cat);
            if (cat === '2') {
                containercam.innerHTML = "";
                let htmlcpu = `<label for="cpu" class="form-label">CPU</label>
                            <input type="text" class="form-control" id="cpu1" name="cpu" required>`;
                containercpu.innerHTML = "";
                containercpu.innerHTML = htmlcpu;

                let htmlram = `<label for="ram" class="form-label">RAM</label>
                            <div class="input-group">
                            <input type="text" class="form-control" id="ram1" name="ram" required>
                                <span class="input-group-text">GB</span>
                            </div>`;
                containerram.innerHTML = "";
                containerram.innerHTML = htmlram;

                let htmlstorage = `<label for="storage" class="form-label">Ổ cứng (Storage)</label>
                            <input type="text" class="form-control" id="storage1" name="storage" required>`;
                containerstorage.innerHTML = "";
                containerstorage.innerHTML = htmlstorage;
                let htmlos = `<label for="os" class="form-label">Hệ điều hành</label>
                            <input type="text" class="form-control" id="os1" name="os" required>`;
                containeros.innerHTML = "";
                containeros.innerHTML = htmlos;

                let html = `<label for="color" class="form-label">camera</label>
                            <input type="text" class="form-control" id="camera" name="cam" required> `;
                containercam.innerHTML = html;
            } else if (cat === '3') {
                containercam.innerHTML = "";
                let htmltype = `<label for="type" class="form-label">Type</label>
                            <input type="text" class="form-control" id="type" name="type" required>`;
                containercpu.innerHTML = "";
                containercpu.innerHTML = htmltype;

                let htmlconnectivity = `<label for="connectivity" class="form-label">Connectivity</label>
                            <input type="text" class="form-control" id="connectivity" name="connectivity" required>`;
                containerram.innerHTML = "";
                containerram.innerHTML = htmlconnectivity;

                let htmlcolor = `<label for="color" class="form-label">Color</label>
                            <input type="text" class="form-control" id="color" name="color" required>`;
                containerstorage.innerHTML = "";
                containerstorage.innerHTML = htmlcolor;
                let htmlcompatibility = `<label for="compatibility" class="form-label">Compatibility</label>
                            <input type="text" class="form-control" id="compatibility" name="compatibility" required>`;
                containeros.innerHTML = "";
                containeros.innerHTML = htmlcompatibility;
            } else if (cat === '1') {
                containercam.innerHTML = "";
                let htmlcpu = `<label for="cpu" class="form-label">CPU</label>
                            <input type="text" class="form-control" id="cpu1" name="cpu" required>`;
                containercpu.innerHTML = "";
                containercpu.innerHTML = htmlcpu;

                let htmlram = `<label for="ram" class="form-label">RAM</label>
                            <div class="input-group">
                            <input type="text" class="form-control" id="ram1" name="ram" required>
                                <span class="input-group-text">GB</span>
                            </div>`;
                containerram.innerHTML = "";
                containerram.innerHTML = htmlram;

                let htmlstorage = `<label for="storage" class="form-label">Ổ cứng (Storage)</label>
                            <input type="text" class="form-control" id="storage1" name="storage" required>`;
                containerstorage.innerHTML = "";
                containerstorage.innerHTML = htmlstorage;
                let htmlos = `<label for="os" class="form-label">Hệ điều hành</label>
                            <input type="text" class="form-control" id="os1" name="os" required>`;
                containeros.innerHTML = "";
                containeros.innerHTML = htmlos;
            }
        }
    });



    let selectedCategory = "";

    function saveCategoryValue(value) {
        selectedCategory = value;
        console.log("Đã chọn category:", selectedCategory);
    }

    function validatesubmit() {
        const productName = document.getElementById("productName").value.trim();
        const brand = document.getElementById("brand").value.trim();
        const price = document.getElementById("price").value.trim();
        const model = document.getElementById("model").value.trim();
        const description = document.getElementById("description").value.trim();

        const weight = document.getElementById("weight").value.trim();
        const stock = document.getElementById("quantity").value.trim();



        if (!selectedCategory) {
            alert("Vui lòng chọn danh mục sản phẩm!");
            return false;
        }

        if (selectedCategory === "1") {
            const cpu = document.getElementById("cpu1").value.trim();
            const ram = document.getElementById("ram1").value.trim();
            const storage = document.getElementById("storage1").value.trim();
            const os = document.getElementById("os1").value.trim();
            if (productName === "" || brand === "" || price === "" || model === "" || description === "" || cpu === "" || ram === "" || storage === "" || os === "" || weight === "" || stock === "") {
                alert("Khpng duoc de trong o nhap");
                return false;
            }

            const nameRegex = /^[\p{L}\s\d\-()]+$/u;         // Tên, Model, Brand: cho phép chữ, số, khoảng trắng, dấu - ()
            const numberRegex = /^\d+(\.\d+)?$/;             // Giá, cân nặng, số lượng: chỉ số (cho phép thập phân)
            const ramStorageRegex = /^\d+\s?(GB|TB|MB)$/i;
            const osRegex = /^[A-Za-z0-9\s.]+$/;             // Hệ điều hành: "Windows 11", "macOS 14.2", ...
            const ramStorageRegexLaptop = /^\d+\s?(GB|TB|MB)(\s?SSD)?$/i;
            const weightRegex = /^\d+(\.\d+)?(KG)$/i;

            if (!nameRegex.test(productName)) {
                alert("Tên sản phẩm chứa ký tự không hợp lệ!");
                return false;
            }
            if (!nameRegex.test(brand)) {
                alert("Tên thương hiệu không hợp lệ!");
                return false;
            }
            if (!numberRegex.test(price)) {
                alert("Giá sản phẩm phải là số!");
                return false;
            }
            if (!nameRegex.test(model)) {
                alert("Model không hợp lệ!");
                return false;
            }
            if (!ramStorageRegex.test(ram)) {
                alert("RAM phải có dạng như '8GB' hoặc '16 GB'!");
                return false;
            }
            if (!ramStorageRegexLaptop.test(storage)) {
                alert("Dung lượng lưu trữ phải có dạng như '256GB SSD' hoặc '1 TB SSD'!");
                return false;
            }
            if (!osRegex.test(os)) {
                alert("Hệ điều hành chứa ký tự không hợp lệ!");
                return false;
            }
            if (!weightRegex.test(weight)) {
                alert("Cân nặng phải là số!");
                return false;
            }
            if (!/^\d+$/.test(stock)) {
                alert("Số lượng tồn kho phải là số nguyên!");
                return false;
            }

            if (!osRegex.test(cpu)) {
                alert("Cpu chứa ký tự không hợp lệ!");
                return false;
            }

            return true;


        } else if (selectedCategory === "2") {
            const cpu = document.getElementById("cpu1").value.trim();
            const ram = document.getElementById("ram1").value.trim();
            const storage = document.getElementById("storage1").value.trim();
            const os = document.getElementById("os1").value.trim();
            const cam = document.getElementById("camera").value.trim();
            const ramStorageRegex = /^\d+\s?(GB|TB|MB)$/i;
            const nameRegex = /^[\p{L}\s\d\-()]+$/u;         // Tên, Model, Brand: cho phép chữ, số, khoảng trắng, dấu - ()
            const numberRegex = /^\d+(\.\d+)?$/;             // Giá, cân nặng, số lượng: chỉ số (cho phép thập phân)
            const osRegex = /^[A-Za-z0-9\s.]+$/;
            const ramCamRegex = /^\d+\s?(MB)$/i;
            const weightRegex = /^\d+\s?(KG)$/i;

            if (productName === "" || brand === "" || price === "" || model === "" || description === "" || cpu === "" || ram === "" || storage === "" || os === "" || weight === "" || stock === "" || cam === "") {
                alert("Khpng duoc de trong o nhap");
                return false;
            }




            if (!nameRegex.test(productName)) {
                alert("Tên sản phẩm chứa ký tự không hợp lệ!");
                return false;
            }
            if (!nameRegex.test(brand)) {
                alert("Tên thương hiệu không hợp lệ!");
                return false;
            }
            if (!numberRegex.test(price)) {
                alert("Giá sản phẩm phải là số!");
                return false;
            }
            if (!nameRegex.test(model)) {
                alert("Model không hợp lệ!");
                return false;
            }
            if (!ramStorageRegex.test(ram)) {
                alert("RAM phải có dạng như '8GB' hoặc '16 GB'!");
                return false;
            }
            if (!ramStorageRegex.test(storage)) {
                alert("Dung lượng lưu trữ phải có dạng như '256GB' hoặc '1 TB'!");
                return false;
            }
            if (!osRegex.test(os)) {
                alert("Hệ điều hành chứa ký tự không hợp lệ!");
                return false;
            }
            if (!weightRegex.test(weight)) {
                alert("Cân nặng phải là số!, Hoac la vi du 1.8 KG");
                return false;
            }
            if (!/^\d+$/.test(stock)) {
                alert("Số lượng tồn kho phải là số nguyên!");
                return false;
            }

            if (!osRegex.test(cpu)) {
                alert("cpu chứa ký tự không hợp lệ!");
                return false;
            }

            if (!ramCamRegex.test(cam)) {
                alert("Cam phải có dạng như '8GB' hoặc '16 GB'!");
                return false;
            }

            return true;
        } else if (selectedCategory === "3") {
            const type = document.getElementById("type").value.trim();
            const connectivity = document.getElementById("connectivity").value.trim();
            const color = document.getElementById("color").value.trim();
            const compatibility = document.getElementById("compatibility").value.trim();
            const ramStorageRegex = /^\d+\s?(GB|TB|MB)$/i;
            const nameRegex = /^[\p{L}\s\d\-()]+$/u;         // Tên, Model, Brand: cho phép chữ, số, khoảng trắng, dấu - ()
            const numberRegex = /^\d+(\.\d+)?$/;             // Giá, cân nặng, số lượng: chỉ số (cho phép thập phân)

            const osRegex = /^[A-Za-z0-9\s.]+$/;
            const ramCamRegex = /^\d+\s?(MB)$/i;
            const weightRegex = /^\d+\s?(KG)$/i;
            const colorRegex = /^[A-Za-z]+$/;

            if (productName === "" || brand === "" || price === "" || model === "" || description === "" || type === "" || connectivity === "" || color === "" || compatibility === "" || weight === "" || stock === "") {
                alert("Khpng duoc de trong o nhap");
                return false;
            }




            if (!nameRegex.test(productName)) {
                alert("Tên sản phẩm chứa ký tự không hợp lệ!");
                return false;
            }
            if (!nameRegex.test(brand)) {
                alert("Tên thương hiệu không hợp lệ!");
                return false;
            }
            if (!numberRegex.test(price)) {
                alert("Giá sản phẩm phải là số!");
                return false;
            }
            if (!nameRegex.test(model)) {
                alert("Model không hợp lệ!");
                return false;
            }
            if (!osRegex.test(type)) {
                alert("Type co ky tu khong hop le!, hop le phai la vi du: Charger hoac Charger 02");
                return false;
            }
            if (!nameRegex.test(connectivity)) {
                alert("Connectivity khong duoc co ky tu dat biet, chi cho phep - , (), hoac dau cach, vi du : USB-C, USB 4.0");
                return false;
            }
            if (!colorRegex.test(color)) {
                alert("Color chi cho phep nhap chu");
                return false;
            }
            if (!weightRegex.test(weight)) {
                alert("Cân nặng phải là số!");
                return false;
            }
            if (!/^\d+$/.test(stock)) {
                alert("Số lượng tồn kho phải là số nguyên!");
                return false;
            }

            if (!colorRegex.test(compatibility)) {
                alert("compatibility chứa ký tự không hợp lệ!, chi cho phep nhap chu");
                return false;
            }

            return true;
        }
        return true;
    }
    
   function updatePrice(value) {
        value = value.replace(/[.,]/g, "");
        document.getElementById("price").value = value;

    }


</script>
</body>
</html>
