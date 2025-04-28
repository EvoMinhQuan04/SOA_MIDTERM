if (localStorage.getItem("loggedIn") === "true") {
    // Nếu đã đăng nhập, chuyển hướng tới trang home
    document.getElementById("loggedUsername").textContent = "Logged in as: " + localStorage.getItem("username");
    
    let tableShowId = document.getElementById("tableShowId");
    let orderShowId = document.getElementById("orderShowId");
    let kitchenShowId = document.getElementById("kitchenShowId");
    let menuShowId = document.getElementById("menuShowId");
    let billingShowId = document.getElementById("billingShowId");

    const role = localStorage.getItem("role");

    if(role === "KITCHEN") {
        hiddenElement(tableShowId);
        hiddenElement(orderShowId);
        hiddenElement(billingShowId);
    }

    if(role === "EMPLOYEE") {
        hiddenElement(kitchenShowId);
        hiddenElement(menuShowId);
        hiddenElement(billingShowId);
    }

    
} else {
    // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
    window.location.href = "login.html";  // Thay "login.html" bằng trang đăng nhập của bạn
}

function hiddenElement(element) {
    element.remove();
}

function logout() {
    localStorage.removeItem('loggedIn');  // Xóa dữ liệu 'username' khỏi localStorage
    localStorage.removeItem('username');  
    localStorage.removeItem('role');  
    window.location.href = "login.html";  // Thay "login.html" bằng trang đăng nhập của bạn
}

