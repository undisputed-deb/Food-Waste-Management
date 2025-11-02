# 🌱 Food Waste Management System

<div align="center">

![Food Waste Banner](https://img.shields.io/badge/Sustainability-Green_Future-4ade80?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-3.x-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)

**A comprehensive full-stack solution for tracking and managing food waste from generation to processing.**

[View Demo](#-demo) · [Report Bug](https://github.com/undisputed-deb/Food-Waste-Management/issues) · [Request Feature](https://github.com/undisputed-deb/Food-Waste-Management/issues)

</div>

---

## 📖 About The Project

The Food Waste Management System is a full-stack web application designed to combat food waste through intelligent tracking and management. With real-time monitoring of donors, waste items, collection centers, and processing facilities, this system helps organizations reduce waste and promote sustainability.

### 🌍 Why This Matters

- **1/3 of all food produced globally is wasted** - representing massive resource loss
- **Food waste costs $1 trillion annually** worldwide
- **Rotting food produces methane** - a greenhouse gas 25x more potent than CO₂
- **This system helps reduce waste** and creates a sustainable food ecosystem

---

## ✨ Key Features

### 🔐 **Secure Authentication**
- JWT-based authentication with role-based access control
- BCrypt password encryption
- Secure token management

### 📊 **Real-Time Dashboard**
- Live waste tracking and analytics
- Environmental impact metrics
- Beautiful data visualizations with animated charts
- Activity feed with real-time updates

### 👥 **Multi-Role System**
- **ADMIN** - Full system control and management
- **DONOR** - Report and track food waste donations
- **OPERATOR** - Manage collection centers and logistics
- **PROCESSOR** - Handle waste processing and composting

### 🗑️ **Waste Management**
- Track waste by type (Vegetables, Dairy, Grains, Meat)
- Monitor weight, expiration dates, and processing status
- Link waste items to donors and processors

### 🏢 **Organization Management**
- Donor tracking (restaurants, stores, cafeterias)
- Collection center coordination
- Processor facility management

### 🎨 **Beautiful UI/UX**
- Modern glassmorphism design
- Winter-themed aesthetics with animated snow effects
- Fully responsive (mobile, tablet, desktop)
- Dark mode optimized
- Smooth animations and transitions

---

## 🛠️ Tech Stack

### Backend
- **Framework:** Spring Boot 3.5.6
- **Language:** Java 17
- **Security:** Spring Security + JWT
- **Database:** PostgreSQL 15
- **ORM:** Spring Data JPA (Hibernate)
- **Build Tool:** Maven
- **Validation:** Jakarta Validation

### Frontend
- **Framework:** Vue.js 3 (Composition API)
- **Routing:** Vue Router
- **HTTP Client:** Axios
- **Styling:** CSS3 (Custom animations, glassmorphism)
- **Build Tool:** Vite

### DevOps & Tools
- **Containerization:** Docker & Docker Compose
- **Version Control:** Git & GitHub
- **API Testing:** Postman
- **IDE:** VS Code, IntelliJ IDEA

---

## 📁 Project Structure
```
Food-Waste-Management/
├── backend/                           # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/foodwaste/foodwaste/
│   │   │   │   ├── config/           # Security & CORS configuration
│   │   │   │   ├── controller/       # REST API endpoints
│   │   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── exception/        # Global exception handling
│   │   │   │   ├── model/            # JPA entities (database models)
│   │   │   │   ├── repository/       # Database access layer
│   │   │   │   ├── security/         # JWT utilities & filters
│   │   │   │   └── service/          # Business logic layer
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/                     # Unit & integration tests
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── pom.xml
│
└── frontend/                          # Vue.js Application
    ├── src/
    │   ├── assets/                   # Images & global styles
    │   ├── components/               # Reusable Vue components
    │   ├── views/                    # Page components
    │   │   ├── Login.vue            # Authentication page
    │   │   ├── Register.vue         # User registration
    │   │   ├── Dashboard.vue        # Main dashboard with analytics
    │   │   ├── Donors.vue           # Donor management
    │   │   ├── WasteItems.vue       # Waste tracking
    │   │   ├── Centers.vue          # Collection centers
    │   │   └── Processors.vue       # Processing facilities
    │   ├── router/                   # Vue Router configuration
    │   ├── services/                 # API service layer
    │   │   └── api.js               # Axios configuration & endpoints
    │   ├── App.vue
    │   └── main.js
    ├── package.json
    └── vite.config.js
```

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed:

- **Java 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Node.js 16+** & npm - [Download](https://nodejs.org/)
- **PostgreSQL 15** (or use Docker)
- **Docker** (optional, for containerized setup) - [Download](https://www.docker.com/products/docker-desktop)
- **Git** - [Download](https://git-scm.com/)

---

### Installation

#### 1️⃣ **Clone the Repository**
```bash
git clone https://github.com/undisputed-deb/Food-Waste-Management.git
cd Food-Waste-Management
```

---

#### 2️⃣ **Backend Setup**

##### Option A: Using Docker (Recommended)
```bash
cd backend

# Start PostgreSQL in Docker
docker run -d --name foodwaste-postgres \
  -e POSTGRES_DB=foodwaste \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15

# Build and run Spring Boot
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

##### Option B: Using Local PostgreSQL
```bash
cd backend

# Update application.properties with your database credentials
# src/main/resources/application.properties

# Build and run
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

**Backend will start at:** `http://localhost:8080`

---

#### 3️⃣ **Frontend Setup**
```bash
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

**Frontend will start at:** `http://localhost:5173`

---

### 🐳 Docker Deployment (Full Stack)

For a complete containerized setup:
```bash
# In the backend directory
cd backend
docker-compose up --build
```

Then separately run the frontend:
```bash
cd frontend
npm run dev
```

---

## 🎮 Usage

### Default Login Credentials

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | ADMIN |
| `deb212` | `admin123` | DONOR |

### Creating a New User

1. Navigate to the **Register** page
2. Fill in your details:
   - Username
   - Email
   - Password
   - Role (DONOR, OPERATOR, PROCESSOR)
3. Click **Create Account**
4. Login with your new credentials

---

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john",
  "email": "john@example.com",
  "password": "password123",
  "role": "DONOR"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "role": "ADMIN"
}
```

### Protected Endpoints

All protected endpoints require JWT token in the Authorization header:
```http
Authorization: Bearer {your-jwt-token}
```

#### Donors API
```http
GET    /api/donors              # Get all donors (requires authentication)
GET    /api/donors/{id}         # Get donor by ID
POST   /api/donors              # Create donor (ADMIN only)
PUT    /api/donors/{id}         # Update donor (ADMIN only)
DELETE /api/donors/{id}         # Delete donor (ADMIN only)
```

#### Waste Items API
```http
GET    /api/waste-items                    # Get all waste items
POST   /api/waste-items                    # Create waste item (DONOR/OPERATOR/ADMIN)
PUT    /api/waste-items/{id}               # Update waste item
PATCH  /api/waste-items/{id}/process/{pid} # Mark as processed (PROCESSOR/OPERATOR/ADMIN)
DELETE /api/waste-items/{id}               # Delete waste item (ADMIN only)
```

#### Collection Centers API
```http
GET    /api/centers             # Get all centers
POST   /api/centers             # Create center (ADMIN/OPERATOR)
PUT    /api/centers/{id}        # Update center (ADMIN/OPERATOR)
DELETE /api/centers/{id}        # Delete center (ADMIN)
```

#### Processors API
```http
GET    /api/processors          # Get all processors
POST   /api/processors          # Create processor (ADMIN only)
PUT    /api/processors/{id}     # Update processor (ADMIN only)
DELETE /api/processors/{id}     # Delete processor (ADMIN only)
```

---

## 🔒 Security Features

- **JWT Authentication** - Stateless, secure token-based authentication
- **BCrypt Password Hashing** - Industry-standard password encryption (10 rounds)
- **Role-Based Access Control (RBAC)** - Fine-grained permission management
- **CORS Protection** - Configurable allowed origins
- **SQL Injection Prevention** - JPA parameterized queries
- **XSS Protection** - Input sanitization and validation

---

## 👥 User Roles & Permissions

| Action | ADMIN | DONOR | OPERATOR | PROCESSOR |
|--------|-------|-------|----------|-----------|
| **View All Data** | ✅ | ✅ | ✅ | ✅ |
| **Create Donor** | ✅ | ❌ | ❌ | ❌ |
| **Manage Donors** | ✅ | ❌ | ❌ | ❌ |
| **Create Waste Item** | ✅ | ✅ | ✅ | ❌ |
| **Update Waste Item** | ✅ | ✅ | ✅ | ❌ |
| **Delete Waste Item** | ✅ | ❌ | ❌ | ❌ |
| **Mark as Processed** | ✅ | ❌ | ✅ | ✅ |
| **Manage Centers** | ✅ | ❌ | ✅ | ❌ |
| **Create Processor** | ✅ | ❌ | ❌ | ❌ |
| **Manage Processors** | ✅ | ❌ | ❌ | ❌ |

---

## 🗄️ Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(50) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE
);
```

### Food Donors Table
```sql
CREATE TABLE food_donors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(500),
    contact_info VARCHAR(255)
);
```

### Food Waste Items Table
```sql
CREATE TABLE food_waste_items (
    id BIGSERIAL PRIMARY KEY,
    weight DOUBLE PRECISION,
    waste_type VARCHAR(100),
    expiration_date DATE,
    processed BOOLEAN DEFAULT FALSE,
    donor_id BIGINT REFERENCES food_donors(id),
    processor_id BIGINT REFERENCES processors(id)
);
```

### Collection Centers Table
```sql
CREATE TABLE collection_centers (
    id BIGSERIAL PRIMARY KEY,
    location VARCHAR(500),
    max_capacity DOUBLE PRECISION,
    current_capacity DOUBLE PRECISION,
    processor_id BIGINT REFERENCES processors(id)
);
```

### Processors Table
```sql
CREATE TABLE processors (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(500),
    max_processing_capacity DOUBLE PRECISION
);
```

---

## 🌐 Environment Variables

### Backend (`application.properties`)
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/foodwaste
spring.datasource.username=postgres
spring.datasource.password=postgres

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Server Configuration
server.port=8080
```

### Frontend (`.env`)
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

---

## 🧪 Testing

### Backend Tests
```bash
cd backend
./mvnw test
```

### Frontend Tests
```bash
cd frontend
npm run test
```

### API Testing with cURL
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","email":"test@test.com","password":"test123","role":"DONOR"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test123"}'

# Get Donors (with token)
curl http://localhost:8080/api/donors \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## 📊 Features Showcase

### 🎨 Beautiful UI Components

- **Winter-themed Login** - Glassmorphism with animated snow effects
- **Interactive Dashboard** - Real-time statistics with animated counters
- **Data Visualizations** - Animated bar charts and impact metrics
- **Activity Feed** - Live updates with status badges
- **Responsive Design** - Seamless experience across all devices

### 📈 Analytics & Insights

- Waste reduction tracking
- Cost savings calculations ($45 per kg waste diverted)
- Carbon footprint metrics (2.5 kg CO₂ per kg waste)
- Water conservation estimates (170L per kg waste)
- Meals saved calculations (2 meals per kg)

---

## 🚧 Roadmap

- [ ] Email notifications for waste collection
- [ ] Mobile app (React Native)
- [ ] Advanced analytics with Chart.js/D3.js
- [ ] QR code generation for waste items
- [ ] Blockchain integration for transparency
- [ ] AI-powered waste prediction
- [ ] Multi-language support (i18n)
- [ ] Export reports as PDF/Excel
- [ ] Real-time notifications (WebSockets)
- [ ] Google Maps integration for route optimization
- [ ] Automated testing (Unit, Integration, E2E)

---

## 🤝 Contributing

Contributions make the open-source community an amazing place to learn and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 🐛 Known Issues

- First-time Docker setup may take 2-3 minutes for database initialization
- CORS errors may occur if frontend URL is not configured in backend
- Session persistence requires manual token refresh after 24 hours

**Found a bug?** Please [open an issue](https://github.com/undisputed-deb/Food-Waste-Management/issues) with detailed steps to reproduce.

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Debashrestha Nayak**

- GitHub: [@undisputed-deb](https://github.com/undisputed-deb)
- Project: [Food Waste Management](https://github.com/undisputed-deb/Food-Waste-Management)

---

## 🙏 Acknowledgments

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Vue.js Documentation](https://vuejs.org/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [JWT.io](https://jwt.io/) - JWT visualization and debugging
- [FAO](http://www.fao.org/) - Food waste statistics
- [Shields.io](https://shields.io/) - README badges
- Inspired by the global movement to reduce food waste and promote sustainability

---

## 📞 Support

Give a ⭐️ if this project helped you!

For support, email [your-email@example.com] or open an issue.

---

## 📖 Additional Resources

- [Spring Security JWT Guide](https://spring.io/guides/tutorials/spring-security-and-angular-js/)
- [Vue.js Best Practices](https://vuejs.org/guide/best-practices/production-deployment.html)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [RESTful API Design](https://restfulapi.net/)

---

<div align="center">

**Made with ❤️ and ♻️ for a sustainable future**

[⬆ Back to Top](#-food-waste-management-system)

</div>
