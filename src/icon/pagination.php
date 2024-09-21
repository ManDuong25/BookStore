<?php

use backend\bus\ProductBUS;
// Prevent caching
header("Cache-Control: no-cache, must-revalidate"); // HTTP 1.1
header("Pragma: no-cache"); // HTTP 1.0
header("Expires: Sat, 26 Jul 1997 05:00:00 GMT"); // Date in the past
// Số sản phẩm trên một trang
$items_per_page = 12;

// Trang hiện tại (mặc định là trang 1 nếu không có biến trang)
$current_page = isset($_POST['page']) ? intval($_POST['page']) : 1;


// Tính vị trí bắt đầu
$start_from = ($current_page - 1) * $items_per_page;

$total = count(ProductBUS::getInstance()->getAllModels());
echo $total;
$total_pages = ceil($row["total"] / $items_per_page);

// Chuẩn bị câu lệnh SQL sử dụng prepared statements
$sql = "SELECT * FROM products LIMIT ? OFFSET ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("ii", $items_per_page, $start_from);
$stmt->execute();
$result = $stmt->get_result();

// Xử lý dữ liệu trả về
$htmlproducts = '';
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $htmlproducts .= '
            <div class="pitem">
                <div class="imgitem">
                    <img src="' . $row['image'] . '" alt="">
                </div>
                <div class="content">
                    <div class="name">' . $row['name'] . '</div>
                    <div class="price">' . $row['price'] . '<sup>đ</sup></div>
                    <button class="see_product">
                        <span></span>
                        <span></span>
                        <span></span>
                        <span></span>
                        <a href="?module=indexphp&action=singleproduct&id=' . $row['id'] . '">SEE MORE</a>
                    </button>
                </div>
            </div>
            ';
    }
}

// Đóng kết nối
echo $htmlproducts;
