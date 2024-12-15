const apiUrl = "http://localhost:8080/";
globalThis.role = "customer";
globalThis.apiUrl = apiUrl;
globalThis.userId = 3;

document.getElementById('registerButton').addEventListener('click', function() {
    document.querySelector('.login-container').style.display = 'none';
    document.querySelector('.register-container').style.display = 'block';
});
document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止表单默认提交行为
    const username = document.getElementById('regUsername').value;
    const email = document.getElementById('regEmail').value;
    const phoneNumber = document.getElementById('regPhoneNumber').value;
    const password = document.getElementById('regPassword').value;
    const roleName = document.getElementById('regRoleName').value;
    
    // 模拟发送请求到后端进行注册
    fetch( 'http://localhost:8080/api/users/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            "username" : username,
            "email" : email,
            "phoneNumber" : phoneNumber,
            "password" : password,
            "roleName" : roleName })
    })
    .then(response => response.json())
    .then(data => {
        if (data.code === 1) {
            alert('注册成功');
            document.querySelector('.register-container').style.display = 'none';
            document.querySelector('.login-container').style.display = 'block';
        } else {
            alert(data.errorMessage)
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('注册过程中发生错误');
    });
});
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 阻止表单默认提交行为
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // 模拟发送请求到后端进行身份验证
    fetch(`${apiUrl}api/users/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    })
    .then(response => response.json())
    .then(data => {
        // localStorage.setItem('token', data.token);
        console.log(data);
        if (data.code === 1) {
            globalThis.userId = data.data.id;
            globalThis.role = data.data.role;
            switch (data.data.role) {
                case 'customer':
                    window.location.href = '/user_dashboard.html'; // 用户跳转页面
                    break;
                case 'merchant':
                    window.location.href = '/merchant_dashboard.html'; // 商家跳转页面
                    break;
                case 'admin':
                    window.location.href = '/admin_dashboard.html'; // 管理员跳转页面
                    break;
                default:
                    alert('未知身份');
            }
        } else {
            alert('登录失败，请检查用户名和密码');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('登录过程中发生错误');
    });
});
const express = require('express');
const jwt = require('jsonwebtoken');
const bodyParser = require('body-parser');

const app = express();
app.use(bodyParser.json());

const users = [
    { id: 1, username: 'user', password: 'password', role: 'user' },
    { id: 2, username: 'merchant', password: 'password', role: 'merchant' },
    { id: 3, username: 'admin', password: 'password', role: 'admin' }
];

app.post('/api/login', (req, res) => {
    const { username, password } = req.body;
    const user = users.find(u => u.username === username && u.password === password);
    
    if (user) {
        const token = jwt.sign({ id: user.id, role: user.role }, 'your_jwt_secret', { expiresIn: '1h' });
        res.json({ success: true, token, role: user.role });
    } else {
        res.status(401).json({ success: false, message: 'Invalid credentials' });
    }
});

app.listen(3000, () => {
    console.log('Server is running on port 3000');
});

fetch('/api/protected-resource', {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
    }
})
.then(response => response.json())
.then(data => {
    console.log(data);
})
.catch(error => {
    console.error('Error:', error);
});
