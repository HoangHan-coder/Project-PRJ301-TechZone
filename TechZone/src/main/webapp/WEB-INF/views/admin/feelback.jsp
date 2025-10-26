<%-- 
    Document   : feelback
    Created on : Oct 26, 2025, 2:28:54 PM
    Author     : letan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Danh sách Phản hồi</title>
  <style>
    body {
      font-family: "Segoe UI", Tahoma, sans-serif;
      background: #f8fafc;
      margin: 0;
      padding: 40px;
      color: #333;
    }

    h1 {
      text-align: center;
      color: #222;
      margin-bottom: 30px;
      font-size: 28px;
      font-weight: 600;
    }

    .feedback-container {
      max-width: 1100px;
      margin: 0 auto;
      background: #fff;
      border-radius: 12px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
      padding: 20px 30px;
    }

    .filter-bar {
      display: flex;
      flex-wrap: wrap;
      gap: 10px;
      margin-bottom: 20px;
      justify-content: space-between;
    }

    .filter-bar input,
    .filter-bar select {
      padding: 8px 12px;
      border: 1px solid #ccc;
      border-radius: 6px;
      font-size: 14px;
      flex: 1;
      min-width: 180px;
      transition: 0.2s;
    }

    .filter-bar input:focus,
    .filter-bar select:focus {
      outline: none;
      border-color: #2b90d9;
      box-shadow: 0 0 3px rgba(43, 144, 217, 0.5);
    }

    .filter-bar button {
      background: #16a34a;
      color: white;
      border: none;
      border-radius: 6px;
      padding: 8px 16px;
      font-size: 14px;
      cursor: pointer;
      transition: background 0.2s;
    }

    .filter-bar button:hover {
      background: #15803d;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      font-size: 15px;
    }

    th {
      background: #2b90d9;
      color: white;
      text-align: left;
      padding: 12px 10px;
      font-weight: 500;
    }

    td {
      padding: 10px;
      border-bottom: 1px solid #e5e7eb;
    }

    tr:hover {
      background: #f1f5f9;
      transition: background 0.2s;
    }

    .delete-btn {
      background: none;
      border: none;
      cursor: pointer;
      color: #ef4444;
      font-size: 18px;
      transition: transform 0.2s;
    }

    .delete-btn:hover {
      transform: scale(1.2);
    }

    /* Pagination */
    .pagination {
      display: flex;
      justify-content: center;
      margin-top: 20px;
      gap: 6px;
      flex-wrap: wrap;
    }

    .pagination button {
      background: white;
      border: 1px solid #ccc;
      padding: 6px 12px;
      border-radius: 6px;
      cursor: pointer;
      transition: 0.2s;
    }

    .pagination button:hover {
      background: #e2e8f0;
    }

    .pagination button.active {
      background: #2b90d9;
      color: white;
      border-color: #2b90d9;
      font-weight: 600;
    }

    @media (max-width: 768px) {
      table, thead, tbody, th, td, tr {
        display: block;
      }
      thead {
        display: none;
      }
      tr {
        background: #fff;
        margin-bottom: 15px;
        border-radius: 10px;
        box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
        padding: 10px;
      }
      td {
        border: none;
        display: flex;
        justify-content: space-between;
        padding: 8px;
      }
      td::before {
        content: attr(data-label);
        font-weight: 600;
        color: #555;
      }
    }
  </style>
</head>
<body>
  <h1>Danh sách Phản hồi</h1>

  <div class="feedback-container">
    <div class="filter-bar">
      <input type="text" placeholder="Tìm theo tên sản phẩm hoặc khách hàng..." />
      <select>
        <option>Chọn số sao</option>
        <option>5 sao</option>
        <option>4 sao</option>
        <option>3 sao</option>
        <option>2 sao</option>
        <option>1 sao</option>
      </select>
      <button>Tìm kiếm</button>
    </div>

    <table id="feedbackTable">
      <thead>
        <tr>
          <th>ID</th>
          <th>Tên khách hàng</th>
          <th>Sản phẩm</th>
          <th>Nội dung</th>
          <th>Số sao</th>
          <th>Ngày phản hồi</th>
          <th>Hành động</th>
        </tr>
      </thead>
      <tbody></tbody>
    </table>

    <div class="pagination" id="pagination"></div>
  </div>

  <script>
    const feedbacks = [
      { id: 1, name: "Nguyễn Văn A", product: "Điện thoại XYZ", content: "Sản phẩm tốt, giao hàng nhanh", star: 5, date: "2025-09-20" },
      { id: 2, name: "Trần Thị B", product: "Laptop ABC", content: "Pin hơi yếu", star: 3, date: "2025-09-18" },
      { id: 3, name: "Lê Minh C", product: "Tai nghe Bluetooth", content: "Âm thanh hay", star: 4, date: "2025-09-19" },
      { id: 4, name: "Phạm Thảo D", product: "Chuột gaming", content: "Cầm rất chắc tay", star: 5, date: "2025-09-17" },
      { id: 5, name: "Ngô Đức E", product: "Bàn phím cơ", content: "Phím gõ êm", star: 4, date: "2025-09-16" },
      { id: 6, name: "Lưu Quang F", product: "Màn hình LG", content: "Màu sắc đẹp", star: 5, date: "2025-09-15" },
      { id: 7, name: "Phan Anh G", product: "Ổ cứng SSD", content: "Tốc độ cao", star: 5, date: "2025-09-14" },
      { id: 8, name: "Trần Mỹ H", product: "Máy in HP", content: "Dễ sử dụng", star: 4, date: "2025-09-13" }
    ];

    const rowsPerPage = 3;
    let currentPage = 1;

    function renderTable() {
      const tbody = document.querySelector("#feedbackTable tbody");
      tbody.innerHTML = "";
      const start = (currentPage - 1) * rowsPerPage;
      const end = start + rowsPerPage;
      const pageData = feedbacks.slice(start, end);

      pageData.forEach(fb => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td data-label="ID">${fb.id}</td>
          <td data-label="Tên khách hàng">${fb.name}</td>
          <td data-label="Sản phẩm">${fb.product}</td>
          <td data-label="Nội dung">${fb.content}</td>
          <td data-label="Số sao">⭐ ${fb.star}</td>
          <td data-label="Ngày phản hồi">${fb.date}</td>
          <td data-label="Hành động"><button class="delete-btn">🗑️</button></td>
        `;
        tbody.appendChild(tr);
      });
    }

    function renderPagination() {
      const totalPages = Math.ceil(feedbacks.length / rowsPerPage);
      const pagination = document.getElementById("pagination");
      pagination.innerHTML = "";

      for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement("button");
        btn.textContent = i;
        btn.classList.toggle("active", i === currentPage);
        btn.addEventListener("click", () => {
          currentPage = i;
          renderTable();
          renderPagination();
        });
        pagination.appendChild(btn);
      }
    }

    renderTable();
    renderPagination();
  </script>
</body>
</html>


