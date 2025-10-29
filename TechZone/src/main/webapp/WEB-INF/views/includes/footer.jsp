<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .hero {
        background-color: #f8f9fa;
    }

    .hero h1 {
        font-weight: 700;
    }

    .category-card {
        border: 1px solid #ddd;
        transition: transform 0.3s;
    }

    .category-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
    }

    .featured-products .card,
    .trending-products .card {
        border: none;
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .featured-products .card:hover,
    .trending-products .card:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 8px rgba(0,0,0,0.15);
    }

    .newsletter {
        background-size: cover;
        background-position: center;
        background-color: #333;
    }
    .carousel-item img {
        height: 500px;         /* chiều cao ảnh */
        object-fit: cover;     /* cắt ảnh vừa khung */
        width: 100%;
    }
    .carousel-caption {
        bottom: 20px;          /* kéo caption lên gần ảnh hơn */
    }
    .carousel-caption h5,
    .carousel-caption p {
        margin-bottom: 5px;    /* giảm khoảng cách chữ */
    }

    .product-card {
        background: #1c1c1c;
        color: white;
        border-radius: 15px;
        padding: 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        height: 250px;
    }
    .product-info h3 {
        font-weight: bold;
    }
    .product-image img {
        max-height: 180px;
    }
    .logo-container {
        display: flex;
        justify-content: space-evenly; /* căn đều các logo */
        align-items: center;
        padding: 20px 0;
        background-color: #f8f8f8;
    }

    .logo-container img {
        max-height: 60px; /* đồng bộ chiều cao logo */
        object-fit: contain;
        transition: transform 0.2s;
    }

    .logo-container img:hover {
        transform: scale(1.1);
    }


    footer {
        background-color:#111;
        color:#fff;
        padding:40px 20px 0;
    }
    .footer-columns {
        display: flex;
        justify-content: space-evenly;
        gap: 40px; /* khoảng cách giữa các cột */
        max-width: 1200px;
        margin: 0 auto 30px;
        text-align: left;
    }
    .footer-column h4 {
        margin-bottom: 15px;
        font-size: 18px;
        font-weight: bold;
    }
    .footer-column ul {
        list-style: none;
        margin: 0;
        padding: 0;
    }
    .footer-column ul li {
        margin-bottom: 8px;
    }
    .footer-column ul li a {
        color: #bbb;
        text-decoration: none;
        transition: color .3s;
    }
    .footer-column ul li a:hover {
        color: #fff;
    }
    .footer-bottom {
        border-top:1px solid #333;
        margin-top:30px;
        padding:20px;
        display:flex;
        flex-wrap:wrap;
        justify-content:space-evenly;
        align-items:center;
        max-width:1200px;
        margin-left:auto;
        margin-right:auto;
    }
    .footer-logo {
        font-size:24px;
        font-weight:bold;
    }
    .footer-bottom-item {
        display:flex;
        align-items:center;
        gap:10px;
        color:#bbb;
    }
    .footer-bottom-item i {
        font-size:20px;
        color:#fff;
    }
    
    .footer-nav{
        transform: translateY(200px);
    }
</style>
<!-- Footer Gizmos -->
<footer style="background-color: #212529 !important;">
    <section class="newsletter" style="background-color: #212529 !important;">
        <div class="container">
            <div class="row align-items-center">

                <!-- Left: Title -->
                <div class="col-md-3">
                    <h3 class="mb-0">Sign up to Newsletter</h3>
                </div>

                <!-- Middle: Email input -->
                <div class="col-md-6">
                    <form class="d-flex">
                        <input type="email" class="form-control me-2" placeholder="Your Email Address">
                        <button type="submit" class="btn btn-primary">
                            <i class="bi bi-envelope"></i>
                        </button>
                    </form>
                </div>

                <!-- Right: Social icons -->
                <div class="col-md-3 text-md-end mt-3 mt-md-0">
                    <span class="fw-bold">Follow us on:</span>
                    <div class="d-inline-flex ms-2">
                        <a href="#" class="text-white me-2"><i class="bi bi-spotify"></i></a>
                        <a href="#" class="text-white me-2"><i class="bi bi-youtube"></i></a>
                        <a href="#" class="text-white me-2"><i class="bi bi-music-note-list"></i></a>
                        <a href="#" class="text-white me-2"><i class="bi bi-twitter"></i></a>
                        <a href="#" class="text-white me-2"><i class="bi bi-dribbble"></i></a>
                        <a href="#" class="text-white me-2"><i class="bi bi-instagram"></i></a>
                        <a href="#" class="text-white"><i class="bi bi-facebook"></i></a>
                    </div>
                </div>

            </div>
        </div>
    </section>
    <div class="footer-columns" style="margin-top: 40px ;background-color: #212529 !important;">
        <!-- Privacy Policy -->
        <div class="footer-column">
            <h4>Privacy Policy</h4>
            <ul>
                <li><a href="#">Returns & Exchanges</a></li>
                <li><a href="#">Payment Terms</a></li>
                <li><a href="#">Delivery Terms</a></li>
                <li><a href="#">Payment & Pricing</a></li>
                <li><a href="#">Terms Of Use</a></li>
                <li><a href="#">Privacy Policy</a></li>
            </ul>
        </div>

        <!-- Quick Links -->
        <div class="footer-column">
            <h4>Quick Links</h4>
            <ul>
                <li><a href="#">Smartphones</a></li>
                <li><a href="#">Headphones</a></li>
                <li><a href="#">Laptop & Tablet</a></li>
                <li><a href="#">Monitors</a></li>
                <li><a href="#">Printers</a></li>
                <li><a href="#">Gadgets</a></li>
            </ul>
        </div>

        <!-- Customer Care -->
        <div class="footer-column">
            <h4>Customer Care</h4>
            <ul>
                <li><a href="#">My Account</a></li>
                <li><a href="#">Store Locator</a></li>
                <li><a href="#">Customer Service</a></li>
                <li><a href="#">Returns/Exchange</a></li>
                <li><a href="#">Product Support</a></li>
                <li><a href="#">FAQs</a></li>
            </ul>
        </div>
    </div>

    <div class="footer-bottom">
        <div class="footer-logo">Gizmos.</div>
        <div class="footer-bottom-item">
            <i class="fas fa-headset"></i>
            +0916 9731 61
        </div>
        <div class="footer-bottom-item">
            <i class="fas fa-truck"></i>
            Amounts over $100
        </div>
        <div class="footer-bottom-item">
            <i class="fas fa-tag"></i>
            Save up to 20%
        </div>
    </div>
</footer>