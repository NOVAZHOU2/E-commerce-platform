
let userId = 1;
let profile=document.getElementById('profile');
const usernameInput = document.querySelector('input[name="username"]');
const emailInput = document.querySelector('input[name="email"]');
const phoneInput = document.querySelector('input[name="phoneNumber"]');
const passwordInput = document.querySelector('input[name="password"]');


profile.addEventListener('submit', function(event) { // 监听表单提交事件

    event.preventDefault(); // 阻止默认提交行为
    const username = usernameInput.value;
    const email = emailInput.value;
    const phoneNumber = phoneInput.value;
    const password = passwordInput.value;

    console.log(username, email, phoneNumber, password, globalThis.role);
    fetch(`http://localhost:8080/api/users/update`, { // 使用fetch API进行异步请求
        method: 'POST',
        body: JSON.stringify({
            "id" : 3,
            "username" : username,
            "email" : email,
            "password" : password,
            "phoneNumber" : phoneNumber,
        }), // 将表单数据作为请求体发送
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json()) // 解析JSON响应
    .then(data => { // 处理成功响应
        if (data.code === 1){
            console.log("success update");
        }else{
            console.log(data.errorMessage)
        }
    })
    .catch((error) => { // 处理错误响应
        console.error('Error:', error);
    });
});
let checkaccount=document.querySelector('.checkaccount');
let checklogin=document.querySelector('.checklogin');
let checkhome=document.querySelector('.checkhome');
let checkcart=document.querySelector('.checkcart');
let checkorders=document.querySelector('.checkorders');
let checkcontact=document.querySelector('.checkcontact');
checkaccount.addEventListener('click',function(){
    let sections=document.querySelectorAll('.section');
    sections.forEach((section,index) => {
        section.style.display='none';
    })
    Foraccount();
    })
checklogin.addEventListener('click',function(){
    let sections=document.querySelectorAll('.section');
    sections.forEach((section,index) => {
        section.style.display='none';
    })
    Forlogin();
    })
checkhome.addEventListener('click',function(){
    let sections=document.querySelectorAll('.section');
    sections.forEach((section,index) => {
        section.style.display='none';
    })
    Forhome();
    })
checkcart.addEventListener('click',function(){
    let sections=document.querySelectorAll('.section');
    sections.forEach((section,index) => {
        section.style.display='none';
    })
    Forcart();
    })
checkorders.addEventListener('click',function(){
    let sections=document.querySelectorAll('.section');
    sections.forEach((section,index) => {
        section.style.display='none';
    })
    Fororders();
    })
checkcontact.addEventListener('click',function(){
    let sections=document.querySelectorAll('.section');
    sections.forEach((section,index) => {
        section.style.display='none';
    })
    Forcontact();
    })
function Foraccount(){
    document.getElementById('account').style.display='block';
}
function Forlogin(){
    document.getElementById('login').style.display='block';
}
function Forhome(){
    document.getElementById('home').style.display='block';
    fetch("http://localhost:8080/api/products/products",{
        method: 'GET',
    })
        .then(response => response.json())
        .then(data => {
            if(data.code === 1){
                console.log(data);
                products = []
                for(let i=0;i<data.data.length;i++){
                    data.data[i].quantity = data.data[i].stock;
                    products.push(data.data[i]);
                }
                console.log(products);
            }else{
                alert(data.errorMessage);
            }
        }).catch(
        error => {
            alert(error.message);
        }
    );
}
function Forcart(){
    document.getElementById('cart').style.display='block';
}
function Fororders(){
    document.getElementById('orders').style.display='block';
    fetch('http://localhost:8080/api/orders/search/'+userId,{
        method: 'GET',
    }).then(response => response.json())
        .then(data => {
            if(data.code === 1){
                Productorders = []
                for (let index in data.data){
                    Productorders.push(data.data[index]);
                }
                console.log(Productorders);
            }else{
                console.log("error");
            }
        }).catch(error => {
        console.log("error");
    })
}
function Forcontact(){
    document.getElementById('contact').style.display='block';
}
// 绑定退出登录按钮点击事件
document.getElementById('logoutBtn').addEventListener('click', function() {
    window.location.href = "file:///C:/Users/asus/Desktop/online-shopping%EF%BC%88%E5%89%8D%E7%AB%AF%EF%BC%89/index.html"; // 跳转到登出界面
});

document.addEventListener('DOMContentLoaded', function() {
    // 模拟从后端获取的数据
    const products = [
        { name: '衣服1', price: '¥99.00', imageUrl: '',quantity:11, merchantId:1,productId:1},
        // 可以添加更多商品数据
    ];
    const Productorders=[
        {orderID:1,name:'衣服1',price:'99.00',quantity:1,situation:'prepared'}
    ];
    const productList = document.getElementById('productList');
    const cartBody = document.getElementById('cartBody');
    const orderList = document.getElementById('orderList');
    const orderHistoryBody =document.getElementById('orderSummaryBody')
    let orderIdCounter=1;
    let cart = []; // 用于存储购物车中的商品

    // 动态生成商品列表项
    products.forEach((product, index) => {
        const li = document.createElement('li');
        li.className = 'product-item';
        li.innerHTML = `
            <img src="${product.imageUrl}" alt="${product.name}">
            <h3>${product.name}</h3>
            <span>库存:${product.quantity}</span>
            <p>${product.price}</p>
            <button class="add-to-cart" data-index="${index}">添加到购物车</button>
        `;
        productList.appendChild(li);
    });
    // 动态生成订单列表项
    Productorders.forEach((Productorders, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
        <td>${Productorders.orderID}</td>
        <td>${Productorders.name}</td>
        <td>${parseFloat(Productorders.price.replace('¥', '')).toFixed(2)}</td>
        <td>${Productorders.quantity}</td>
        <td>${(parseFloat(Productorders.price.replace('¥', '')) * Productorders.quantity).toFixed(2)}</td>
        <td>${Productorders.situation} <br><button class="cancel">取消</button></td>
        `;
    orderHistoryBody.appendChild(tr); // 将订单信息添加到订单历史表格中
    });


    orderHistoryBody.addEventListener('click',function(event){
        if(event.target.classList.contains('cancel')){
            let inner=event.target.parentElement;
            inner.innerHTML='canceled';
        }
    })
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
    function updateOrderList() {
        cartBody.innerHTML = ''; // 清空当前订单列表
        let totalAmount = 0;
        cart.forEach((product, index) => {
            const tr = document.createElement('tr');
            tr.classList.add(`number${index}`)
            tr.innerHTML = `
                <td>${product.name}</td>
                <td>${product.quantity}</td>
                <td>¥${parseFloat(product.price.replace('¥', ''))}</td>
                <td>¥${parseFloat(product.price.replace('¥', '')) * product.quantity}</td>
                <td><input type="checkbox" data-index="${index}" checked></td>
            `;
            cartBody.appendChild(tr);
            totalAmount += parseFloat(product.price.replace('¥', '')) * product.quantity;
        });
        let total=document.querySelector('#totalAmount');
        total.textContent = `¥${totalAmount.toFixed(0)}`;
        attachCheckboxChangeListeners(); // 附加复选框变化监听器
    }
    function attachCheckboxChangeListeners() {
        document.querySelectorAll('#cartBody input[type="checkbox"]').forEach(checkbox => {
            checkbox.addEventListener('change', function(e){
              let flag=e.target.getAttribute('data-index');
              let count=document.querySelector(`tr.number${flag}`);
              let tdcounts = count.querySelectorAll('td');
              let tdcount = tdcounts[3].innerHTML.match(/\d+/g);
              let total=document.querySelector('#totalAmount');
              if(e.target.checked)
              {
                total.textContent=`￥${parseInt(total.textContent.match(/\d+/g))+parseInt(tdcount)}`;
              }
              else{total.textContent=`￥${parseInt(total.textContent.match(/\d+/g))-parseInt(tdcount)}`;}
            });
        });
    }

    // 监听提交订单按钮点击事件
    submitOrder.addEventListener('click', function() {
        const selectedProducts = cart.filter((item, index) => {
            return document.querySelector(`input[type="checkbox"][data-index="${index}"]`).checked;
        });
        jsonBody = []
        for (let i = 0; i < selectedProducts.length; i++) {
            var s = JSON.stringify(
                {
                    name: selectedProducts[i].name,
                    price: selectedProducts[i].price,
                    quantity: selectedProducts[i].quantity,
                    productId: selectedProducts[i].productId,
                    merchantId: selectedProducts[i].merchantId,
                }
            );
            jsonBody.push(s);
        }

        console.log(selectedProducts);
        // 这里的3实际运行需要改成userId
        fetch('http:localhost:8080/api/orders/addOrder/' + 3, {
            method: 'POST',
            body: jsonBody,
        })
        .then(response => response.json())
        .then(data => {
            if (data.code === 1) {
                // displayOrderHistory(selectedProducts); // 显示订单历史记录
                console.log('Selected Products:', selectedProducts); // 这里可以处理提交订单的逻辑，比如发送请求到服务器等
                alert('订单提交成功');
            } else {
                alert('订单提交失败，请重新提交');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('提交过程中发生错误');
        });


    });

    // function displayOrderHistory(selectedProducts) {
    //     const orderId = orderIdCounter++; // 生成订单ID并递增计数器
    //     selectedProducts.forEach(product => {
    //         const tr = document.createElement('tr');
    //         tr.innerHTML = `
    //             <td>${orderId}</td>
    //             <td>${product.name}</td>
    //             <td>${parseFloat(product.price.replace('¥', '')).toFixed(2)}</td>
    //             <td>${product.quantity}</td>
    //             <td>${(parseFloat(product.price.replace('¥', '')) * product.quantity).toFixed(2)}</td>
    //         `;
    //         orderHistoryBody.appendChild(tr); // 将订单信息添加到订单历史表格中
    //     });
    // }

});

 