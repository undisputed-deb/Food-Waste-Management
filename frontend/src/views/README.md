# 🌍 Food Waste Management System

A comprehensive full-stack web application designed to track, manage, and reduce food waste through efficient monitoring of donors, waste items, collection centers, and processing facilities.

![Winter Theme Login](https://img.shields.io/badge/Theme-Winter%20Night-blue?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=for-the-badge&logo=springboot)
![Vue.js](https://img.shields.io/badge/Vue.js-3.x-42b883?style=for-the-badge&logo=vue.js)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [User Roles](#user-roles)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Environment Variables](#environment-variables)
- [Contributing](#contributing)
- [License](#license)

---

## 🎯 Overview

The Food Waste Management System helps organizations and individuals track food waste from generation to processing. With an intuitive interface and robust backend, users can monitor waste donors, track individual waste items, manage collection centers, and coordinate with processing facilities.

### Why This Project?

- 🌱 **1/3 of all food produced globally is wasted**
- 💰 **Food waste costs $1 trillion annually**
- 🔥 **Rotting food produces methane, a potent greenhouse gas**
- ♻️ **This system helps reduce waste and promote sustainability**

---

## ✨ Features

### Core Functionality
- ✅ **User Authentication & Authorization** - Secure JWT-based login system
- ✅ **Role-Based Access Control** - Admin, Donor, Operator, and Processor roles
- ✅ **Donor Management** - Track restaurants, stores, and food donors
- ✅ **Waste Item Tracking** - Monitor food waste by type, weight, and expiration
- ✅ **Collection Centers** - Manage temporary storage facilities
- ✅ **Processor Management** - Coordinate with composting/processing facilities
- ✅ **Real-time Dashboard** - Visual analytics and reporting
- ✅ **Responsive Design** - Works on desktop, tablet, and mobile

### Beautiful UI/UX
- 🎨 **Winter-Themed Design** - Stunning glassmorphism effects
- ❄️ **Animated Snow Effects** - Interactive background elements
- 🌙 **Dark Mode Optimized** - Easy on the eyes
- 📱 **Mobile Responsive** - Seamless experience across devices

---

## 🛠️ Tech Stack

### Backend
- **Spring Boot 3.x** - Java framework for REST API
- **Spring Security** - JWT authentication & authorization
- **Spring Data JPA** - Database ORM
- **PostgreSQL 15** - Relational database
- **Maven** - Dependency management

### Frontend
- **Vue.js 3** - Progressive JavaScript framework
- **Vue Router** - Client-side routing
- **Axios** - HTTP client
- **CSS3** - Glassmorphism, animations, and modern styling

### DevOps
- **Docker & Docker Compose** - Containerization
- **PostgreSQL Container** - Database in Docker

---

## 📦 Prerequisites

Before running this project, ensure you have:

- **Java 17+** installed ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Node.js 16+** and npm ([Download](https://nodejs.org/))
- **PostgreSQL 15** (or Docker for containerized setup)
- **Maven** (included via wrapper)
- **Git** for cloning the repository

---

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/foodwaste-management.git
cd foodwaste-management
```

### 2. Backend Setup

#### Option A: Using Local PostgreSQL
```bash
# Navigate to backend directory
cd foodwaste

# Update application.properties with your database credentials
# src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/foodwaste
spring.datasource.username=postgres
spring.datasource.password=your_password

# Build the project
./mvnw clean package -DskipTests

# Run the application
./mvnw spring-boot:run
```

#### Option B: Using Docker (Recommended)
```bash
cd foodwaste

# Start PostgreSQL container
docker run -d --name foodwaste-postgres \
  -e POSTGRES_DB=foodwaste \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  postgres:15

# Build and run backend
./mvnw clean package -DskipTests
./mvnw spring-boot:run
```

Backend will start at: **http://localhost:8080**

### 3. Frontend Setup
```bash
# Navigate to frontend directory
cd ../foodwaste-frontend

# Install dependencies
npm install

# Run development server
npm run dev
```

Frontend will start at: **http://localhost:5173**

---

## 🎮 Running the Application

### Using Docker Compose (Full Stack)
```bash
# In the root directory
docker-compose up --build
```

This will start:
- PostgreSQL database on port **5432**
- Spring Boot backend on port **8080**

Then separately run the frontend:
```bash
cd foodwaste-frontend
npm run dev
```

### Default Login Credentials

| Username | Password | Role |
|----------|----------|------|
| `admin` | `admin123` | ADMIN |
| `deb212` | `admin123` | DONOR |

---

## 📚 API Documentation

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

### Donors API
```http
GET    /api/donors              # Get all donors (requires authentication)
GET    /api/donors/{id}         # Get donor by ID
POST   /api/donors              # Create donor (ADMIN only)
PUT    /api/donors/{id}         # Update donor (ADMIN only)
DELETE /api/donors/{id}         # Delete donor (ADMIN only)
```

### Waste Items API
```http
GET    /api/waste-items         # Get all waste items
POST   /api/waste-items         # Create waste item
PUT    /api/waste-items/{id}    # Update waste item
DELETE /api/waste-items/{id}    # Delete waste item
```

### Collection Centers API
```http
GET    /api/centers             # Get all centers
POST   /api/centers             # Create center (ADMIN/OPERATOR)
PUT    /api/centers/{id}        # Update center (ADMIN/OPERATOR)
DELETE /api/centers/{id}        # Delete center (ADMIN/OPERATOR)
```

### Processors API
```http
GET    /api/processors          # Get all processors
POST   /api/processors          # Create processor (ADMIN only)
PUT    /api/processors/{id}     # Update processor (ADMIN only)
DELETE /api/processors/{id}     # Delete processor (ADMIN only)
```

**All protected endpoints require:**
```http
Authorization: Bearer {your-jwt-token}
```

---

## 👥 User Roles

| Role | Permissions |
|------|-------------|
| **ADMIN** | Full access - manage all entities, view everything |
| **DONOR** | Create waste items, view donors and waste items |
| **OPERATOR** | Manage collection centers, update waste item status |
| **PROCESSOR** | View and process waste items, mark as processed |

---

## 📁 Project Structure
```
foodwaste-management/
├── foodwaste/                          # Backend (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/foodwaste/foodwaste/
│   │   │   │   ├── config/            # Security & CORS configuration
│   │   │   │   ├── controller/        # REST API endpoints
│   │   │   │   ├── dto/               # Data Transfer Objects
│   │   │   │   ├── exception/         # Global exception handling
│   │   │   │   ├── model/             # JPA entities
│   │   │   │   ├── repository/        # Database access layer
│   │   │   │   ├── security/          # JWT utilities & filters
│   │   │   │   └── service/           # Business logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── pom.xml
│
├── foodwaste-frontend/                 # Frontend (Vue.js)
│   ├── src/
│   │   ├── assets/                    # Images, styles
│   │   ├── components/                # Reusable Vue components
│   │   ├── views/                     # Page components
│   │   │   ├── Login.vue
│   │   │   ├── Register.vue
│   │   │   ├── Dashboard.vue
│   │   │   ├── Donors.vue
│   │   │   ├── WasteItems.vue
│   │   │   ├── Centers.vue
│   │   │   └── Processors.vue
│   │   ├── router/                    # Vue Router config
│   │   ├── services/                  # API services
│   │   │   └── api.js
│   │   ├── App.vue
│   │   └── main.js
│   ├── package.json
│   └── vite.config.js
│
└── README.md
```

---

## 🖼️ Screenshots

### Login Page
Beautiful winter-themed login with glassmorphism effects and animated snow.

### Dashboard
Real-time statistics and quick access to all features.

### Waste Items Management
Track food waste by type, weight, expiration date, and processing status.

### Donor Management
Maintain a database of food donors with contact information.

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
spring.jpa.properties.hibernate.format_sql=true

# Server Configuration
server.port=8080

# JWT Secret (Change in production!)
jwt.secret=your-super-secret-key-that-is-at-least-256-bits-long
```

### Frontend (`.env`)
```env
VITE_API_BASE_URL=http://localhost:8080/api
```

---

## 🔐 Security Features

- **JWT Authentication** - Stateless token-based auth
- **Password Encryption** - BCrypt hashing (10 rounds)
- **Role-Based Access Control** - Fine-grained permissions
- **CORS Protection** - Configurable allowed origins
- **SQL Injection Prevention** - JPA parameterized queries
- **XSS Protection** - Input sanitization

---

## 📊 Database Schema

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

---

## 🧪 Testing

### Backend Tests
```bash
cd foodwaste
./mvnw test
```

### Frontend Tests
```bash
cd foodwaste-frontend
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

## 🚀 Deployment

### Backend Deployment (Heroku/Railway)
```bash
# Build JAR
./mvnw clean package -DskipTests

# Deploy JAR file to your platform
```

### Frontend Deployment (Vercel/Netlify)
```bash
# Build production
npm run build

# Deploy dist/ folder
```

### Docker Deployment
```bash
# Build and run with Docker Compose
docker-compose -f docker-compose.prod.yml up -d
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 Future Enhancements

- [ ] Email notifications for waste collection
- [ ] Mobile app (React Native)
- [ ] Advanced analytics dashboard with charts
- [ ] QR code generation for waste items
- [ ] Blockchain integration for waste tracking
- [ ] AI-powered waste prediction
- [ ] Multi-language support
- [ ] Export reports as PDF/Excel
- [ ] Real-time notifications using WebSockets
- [ ] Integration with Google Maps for route optimization

---

## 🐛 Known Issues

- **Issue:** First-time Docker setup may take 2-3 minutes
  - **Solution:** Wait for "Started FoodwasteApplication" message
  
- **Issue:** CORS errors on different ports
  - **Solution:** Update `CorsConfig.java` with your frontend URL

---

## 📞 Contact

**Your Name** - [@yourtwitter](https://twitter.com/yourtwitter) - your.email@example.com

**Project Link:** [https://github.com/yourusername/foodwaste-management](https://github.com/yourusername/foodwaste-management)

---

## 🙏 Acknowledgments

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Vue.js Documentation](https://vuejs.org/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [JWT.io](https://jwt.io/) for token visualization
- Food waste statistics from [FAO](http://www.fao.org/)

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## ⭐ Show Your Support

Give a ⭐️ if this project helped you learn full-stack development!

---

<div align="center">
  <p>Made with ❤️ and ♻️ for a sustainable future</p>
  <p>
    <a href="#top">Back to Top ↑</a>
  </p>
</div>