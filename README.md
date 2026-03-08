# 🍕 NextPizza Frontend

NextPizza is the **frontend layer** of an online pizza ordering system,
built with **Spring MVC + Thymeleaf** and integrated with the backend REST API via **OpenFeign**.

---

## 🚀 Features

### 👤 User
- Register and login with JWT (stored in HttpOnly cookie)
- Browse and filter pizzas and drinks by category
- Add products to cart and manage quantities (AJAX, no page reload)
- Checkout with delivery address
- View order history with status tracking
- Cancel orders (CREATED / COOKING status only)
- View and edit profile

### 🧑‍🍳 Admin
- Dashboard with statistics (orders, revenue, users)
- Full CRUD for pizzas and drinks (with image upload)
- View and manage all orders
- Update order status

---

## 🏗 Tech Stack

| Technology | Purpose |
|---|---|
| **Java 21** | Programming language |
| **Spring Boot 3.2** | Application framework |
| **Spring MVC** | Web layer (MVC pattern) |
| **Thymeleaf 3** | Server-side template engine (SSR) |
| **OpenFeign** | Declarative REST client |
| **Bootstrap 5.3** | Responsive UI framework |
| **JavaScript ES6** | AJAX interactions |
| **Maven** | Build tool |
| **Docker** | Containerization |
| **GitHub Actions** | CI/CD pipeline |
| **Railway** | Cloud hosting |

---

## 🔐 Security

- **JWT stored in HttpOnly cookie** — protected from XSS attacks
- **AuthInterceptor** — redirects unauthenticated users to login
- **RoleInterceptor** — restricts `/admin/**` to ADMIN roles
- **Role-based UI rendering** — different menus per role
```
Public:    /  /login  /register
User:      /pizzas  /drinks  /cart  /checkout  /my-orders  /profile
Admin:     /admin/dashboard  /admin/pizzas  /admin/drinks  /admin/orders
Owner:     /admin/users  (+ all admin pages)
```

---

## 🔄 Key Flows

### Login Flow:
```
POST /login → Backend JWT → HttpOnly Cookie → Redirect by role
  USER  → /pizzas
  ADMIN → /admin/dashboard
  OWNER → /admin/dashboard
```

### Cart & Checkout:
```
Add to cart → AJAX update quantity → Checkout form
→ POST /checkout → Order created → Balance deducted → Cart cleared
```

### CI/CD Pipeline:
```
git push (master)
        ↓
GitHub Actions
  - Build with Maven
  - Build Docker image
  - Push to Docker Hub
        ↓
Railway auto-redeploy
```

---

## ⚙️ Configuration
```properties
server.port=4000
api.base-url=${API_BASE_URL:http://localhost:3000/api}
```

---

## 🌍 Live Demo
```
Frontend:  https://nextpizza-frontend-production.up.railway.app
Backend:   https://nextpizza-backend-production.up.railway.app
```

**Test accounts:**
```
USER:   user@test.com   / password123
ADMIN:  admin@test.com  / admin123
```

---

## 👨‍💻 Author

**Javohir** – Java Developer

- GitHub: [@Javohir004](https://github.com/Javohir004)

---

🍕 Built with Spring Boot • Thymeleaf • OpenFeign • Bootstrap • Docker
