
document.getElementById('profileForm').addEventListener('submit', function(event) { // 监听表单提交事件
    event.preventDefault(); // 阻止默认提交行为
    const formData = new FormData(this); // 获取表单数据
    fetch('/api/upload', { // 使用fetch API进行异步请求
        method: 'POST',
        body: formData, // 将表单数据作为请求体发送
        headers: {
            'Accept': 'application/json'
        }
    })
    .then(response => response.json()) // 解析JSON响应
    .then(data => { // 处理成功响应
        console.log('Success:', data);
    })
    .catch((error) => { // 处理错误响应
        console.error('Error:', error);
    });
});

// 绑定退出登录按钮点击事件
document.getElementById('logoutBtn').addEventListener('click', function() {
    window.location.href = '/index.html'; // 跳转到登出界面
});

document.addEventListener('DOMContentLoaded', function() {
    // 模拟从后端获取的数据
    const products = [
        { name: '商品名称', price: '¥99.00', imageUrl: 'https://via.placeholder.com/150' },
        { name: '商品名称', price: '¥199.00', imageUrl: 'https://via.placeholder.com/150' },
        { name: '商品名称', price: '¥299.00', imageUrl: 'https://via.placeholder.com/150' }
        // 可以添加更多商品数据
    ];

    const productList = document.getElementById('productList');
    const orderList = document.getElementById('orderList');
    let cart = []; // 用于存储购物车中的商品

    // 动态生成商品列表项
    products.forEach((product, index) => {
        const li = document.createElement('li');
        li.className = 'product-item';
        li.innerHTML = `
            <img src="${product.imageUrl}" alt="${product.name}">
            <h3>${product.name} ${index + 1}</h3>
            <p>${product.price}</p>
            <button class="add-to-cart" data-index="${index}">添加到购物车</button>
        `;
        productList.appendChild(li);
    });

    // 绑定添加到购物车的按钮点击事件
    productList.addEventListener('click', function(event) {
        if (event.target.classList.contains('add-to-cart')) {
            const index = event.target.getAttribute('data-index');
            addToCart(products[index]);
        }
    });

    // 添加到购物车函数
    function addToCart(product) {
        let found = false;
        cart.forEach(item => {
            if (item.name === product.name) {
                item.quantity += 1;
                found = true;
            }
        });
        if (!found) {
            product.quantity = 1;
            cart.push(product);
        }
        updateOrderList();
    }
    // 更新订单列表函数
    function updateOrderList() {
        const orderSummaryBody = document.getElementById('orderSummaryBody');
        orderSummaryBody.innerHTML = ''; // 清空当前订单列表
        let totalAmount = 0;
        cart.forEach((product, index) => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.quantity}</td>
                <td>¥${parseFloat(product.price.replace('¥', '')) * product.quantity}</td>
            `;
            orderSummaryBody.appendChild(tr);
            totalAmount += parseFloat(product.price.replace('¥', '')) * product.quantity;
        });
        document.getElementById('totalAmount').textContent = `¥${totalAmount.toFixed(2)}`;
    }
});

// // 模拟一些已选择的商品数据（实际应从服务器获取）
// const selectedProducts = [
//     { id: 1, name: '商品名称1', price: 99 },
//     { id: 2, name: '商品名称2', price: 199 }
// ];
// const orderList = document.querySelector('#orders .order-list'); // 获取订单商品列表容器
// const orderSummaryBody = document.getElementById('orderSummaryBody'); // 获取订单摘要表体容器
// const totalAmountElement = document.getElementById('totalAmount'); // 获取总计金额元素
// let totalAmount = 0; // 初始化总计金额为0
// selectedProducts.forEach(product => { // 遍历已选择的商品数据并生成订单商品项和订单摘要行
//     const productItem = document.createElement('li'); // 创建订单商品项容器
//     productItem.className = 'product-item'; // 设置类名以应用样式
//     productItem.innerHTML = `
//         <label><input type="checkbox" checked disabled> ${product.name}</label>
//         <p>¥${product.price}</p>
//     `; // 设置订单商品项内容（包含确认框和商品信息）
//     orderList.appendChild(productItem); // 将订单商品项添加到订单商品列表中
//     const summaryRow = document.createElement('tr'); // 创建订单摘要行容器
//     summaryRow.innerHTML = `
//         <td>${product.name}</td>
//         <td>¥${product.price}</td>
//         <td>1</td>
//         <td>¥${product.price}</td>
//     `; // 设置订单摘要行内容（包含商品名称、单价、数量和小计）
//     orderSummaryBody.appendChild(summaryRow); // 将订单摘要行添加到订单摘要表中
//     totalAmount += product.price; // 累加总计金额
// });
// totalAmountElement.textContent = `¥${totalAmount}`; // 更新总计金额显示内容
// document.getElementById('payButton').addEventListener('click', function() { // 监听付款按钮点击事件
//     window.location.href = '/payment'; // 跳转到付款页面（实际应实现付款逻辑）
// });