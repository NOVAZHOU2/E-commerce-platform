let char=0
let merchantId = 3;
let profile=document.getElementById('profile');
const productList = document.getElementById('productList');
const cartBody = document.getElementById('cartBody');
const orderList = document.getElementById('orderList');
const orderHistoryBody =document.getElementById('orderSummaryBody')
products = [
    // 可以添加更多商品数据
];
Productorders=[
    {orderID:1,name:'衣服1',price:'99.00',quantity:1,situation:'prepare',orderItem:1}
];

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
            "id" : merchantId,
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
    // 账户提交表单POST：用户名，邮箱，手机号，密码username，email，phoneNumber，password
}
function Forlogin(){
    document.getElementById('login').style.display='block';

}
function Forhome(){
    document.getElementById('home').style.display='block';
    const productList = document.getElementById('List');
    productList.innerHTML = '';
    document.getElementById('home').style.display='none';
    fetch("http://localhost:8080/api/products/merchant/"+merchantId,{
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if(data.code === 1){
                products = []
                for(let i=0;i<data.data.length;i++){
                    data.data[i].quantity = data.data[i].stock;
                    products.push(data.data[i]);
                }
                products.forEach(product => {
                    const productDiv = document.createElement('div');
                    productDiv.innerHTML = `
                    <h3>${product.name}</h3>
                    <p>价格: ${product.price}</p>
                    <p>数量: ${product.quantity}</p>
               `;
                    productList.appendChild(productDiv);
                });
                console.log(products);
            }else{
                alert(data.errorMessage);
            }
        }).catch(
        error => {
            alert(error.message);
        }
    ).finally(() => {
        document.getElementById('home').style.display = 'block';  // 请求结束后显示页面
    });
    // 商品列表GET:product.imageUrl，product.name，product.quantity，product，price
}
function Forcart(){
    document.getElementById('cart').style.display='block';
    fetch;
    // 添加商品按钮：POST，名称，价格,URL，数量，name,price,url,quantity
    // 查询商品按钮：GET，名称，价格，数量，name，price，quantity
    // 删除商品按钮：POST，之后调用查询一次GET
}
function Fororders(){
    document.getElementById('orders').style.display='block';
    orderHistoryBody.innerHTML='';
    fetch('http://localhost:8080/api/order-query/merchant/'+merchantId,{
        method: 'GET',
    }).then(response => response.json())
        .then(data => {
            if(data.code === 1){
                Productorders = []
                console.log(data)
                for (let index in data.data){
                    Productorders.push(data.data[index]);
                    for (let i = 0;i<products.length;i++){
                        if(products[i].id === Productorders[index].productId){
                            Productorders[index].name = products[i].name;
                            break;
                        }
                    }
                }
                Productorders.forEach((Productorders, index) => {
                    if(Productorders.status === "Prepare" || Productorders.status === "PENDING"){
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
        <td>${Productorders.orderId}</td>
        <td>${Productorders.name}</td>
        <td>${parseFloat(Productorders.price).toFixed(2)}</td>
        <td>${Productorders.quantity}</td>
        <td>${(parseFloat(Productorders.price) * Productorders.quantity).toFixed(2)}</td>
        <td>${Productorders.status} <br><button class="cancel" data-index="${Productorders.orderItemId}">取消</button> <button class="finish" data-index="${Productorders.orderItemId}">完成</button></td>
        `;
                        const cancelButton = tr.querySelector('.cancel');
                        const finishButton = tr.querySelector('.finish');

                        // 为取消按钮添加点击事件监听
                        cancelButton.addEventListener('click', (event) => {
                            const orderItemId = event.target.getAttribute('data-index');
                            updateOrderItem("canel",orderItemId);
                            console.log(`取消按钮被点击，订单下标是: ${index}`);
                            // 你可以在这里执行其他操作，知道是哪个订单被取消
                        });

                        // 为完成按钮添加点击事件监听
                        finishButton.addEventListener('click', (event) => {
                            const index = event.target.getAttribute('data-index');
                            updateOrderItem("finish",index);
                            console.log(`完成按钮被点击，订单下标是: ${index}`);
                            // 你可以在这里执行其他操作，知道是哪个订单被完成
                        });
                        orderHistoryBody.appendChild(tr); // 将订单信息添加到订单历史表格中
                    }else{
                        const tr = document.createElement('tr');
                        tr.innerHTML = `
          <td>${Productorders.orderId}</td>
         <td>${Productorders.name}</td>
        <td>${parseFloat(Productorders.price).toFixed(2)}</td>
        <td>${Productorders.quantity}</td>
        <td>${(parseFloat(Productorders.price) * Productorders.quantity).toFixed(2)}</td>
        <td>${Productorders.status} 
        `;

                        orderHistoryBody.appendChild(tr); // 将订单信息添加到订单历史表格
                    }

                });


                orderHistoryBody.addEventListener('click',function(event){
                    if(event.target.classList.contains('cancel')){
                        let inner=event.target.parentElement;
                        inner.innerHTML='canceled';
                    }
                })
                orderHistoryBody.addEventListener('click',function(event){
                    if(event.target.classList.contains('finish')){
                        let inner=event.target.parentElement;
                        inner.innerHTML='finished';
                    }
                })
                console.log(Productorders);
            }else{
                console.log(data.errorMessage);
            }
        }).catch(error => {
        console.log(error);
    })
    // 商家更改订单POST：返回成功更改订单状态
}
function Forcontact(){
    document.getElementById('contact').style.display='block';
}
// 绑定退出登录按钮点击事件
document.getElementById('logoutBtn').addEventListener('click', function() {
    window.location.href = '/Users/asus/Desktop/online-shopping%EF%BC%88%E5%89%8D%E7%AB%AF%EF%BC%89/index.html'; // 跳转到登出界面
});

document.addEventListener('DOMContentLoaded', function() {
    // 模拟从后端获取的数据

    let orderIdCounter=1;
    let cart = []; // 用于存储购物车中的商品

    document.getElementById('addProductBtn').addEventListener('click', function() {
        // 显示表单容器
        document.getElementById('formContainer').style.display = 'block';
    });

    document.getElementById('productForm').addEventListener('submit', function(event) {
        event.preventDefault(); // 阻止表单的默认提交行为

        // 获取用户输入的值
        const name = document.getElementById('name').value;
        const price = document.getElementById('price').value;
        const imageUrl = document.getElementById('imageUrl').value;
        const quantity = parseInt(document.getElementById('quantity').value, 10);

        //加一个description，然后加到下面
        // 创建新的对象
        let Product = {
            name: name,
            price: price,
            stock: quantity,
            merchantId: merchantId,
        };

        fetch("http://localhost:8080/api/products/add", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Product)
        }).then(response => response.json())
        .then(data => {
            console.log(data);
            if(data.code === 1){
                Product = data.data
            }else {
                console.log(data.errorMessage);
            }
        })

        // 将新的对象添加到数组中
        products.push(Product);
        console.log(products);
        window.location.reload();
        // 清空表单并隐藏表单容器
        document.getElementById('productForm').reset();
        document.getElementById('formContainer').style.display = 'none';
        });



        document.getElementById('delete').addEventListener('click', function() {
            // 获取用户输入的要删除的商品名称
            document.getElementById('fordelete').style.display='block';
            const deleteId = document.getElementById('deleteName').value;

            fetch("http://localhost:8080/api/products/"+deleteId, {
                method: 'DELETE',
            }).then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.code === 1){
                    console.log("delete success");
                    window.location.reload();
                }else{
                    console.log(data.errorMessage);
                }
            })

            // 查找并删除对应的商品
            const index = products.findIndex(product => product.name === deleteName);
            if (index !== -1) {
                products.splice(index, 1);
                console.log(`已删除商品: ${deleteName}`);
            } else {
                console.log(`未找到商品: ${deleteName}`);
            }

            // 清空输入框并重新查询商品列表以更新显示
            document.getElementById('deleteName').value = '';
            document.getElementById('query').click();
        });
    // 动态生成商品列表项
    fetch("http://localhost:8080/api/products/merchant/"+merchantId,{
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
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
                products.forEach((product, index) => {
                    const li = document.createElement('li');
                    li.className = 'product-item';
                    li.innerHTML = `
            <img src="${product.imageUrl}" alt="${product.name}">
            <h3>${product.name}</h3>
            <span>库存:${product.quantity}</span>
            <p>${product.price}</p>
        `;
                    productList.appendChild(li);
                });
            }else{
                alert(data.errorMessage);
            }
        }).catch(
            error => {
                alert(error.message);
            }
        );

    // 动态生成订单列表项

    // 绑定添加到购物车的按钮点击事件
    // productList.addEventListener('click', function(event) {
    //     if (event.target.classList.contains('add-to-cart')) {
    //         const index = event.target.getAttribute('data-index');
    //         addToCart(products[index]);
    //     }
    // });

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

function updateOrderItem(Status,orderItemId) {
    fetch("http://localhost:8080/api/orders/orderItems/update/"+orderItemId,{
        method: 'Put',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "orderItemId": orderItemId,
            "status" : Status,
        })
    }).then(response => response.json())
        .then(data => {
            console.log(data);
            if(data.code === 1){
                alert("cancel success");
            }else{
                console.log(data.errorMessage)
            }
        }).catch(
        error => {
            alert(error.message);
        }
    )
}