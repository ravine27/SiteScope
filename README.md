# SiteScope — Website Health & SEO Analyzer 🚀

**Author & Developer**: Radha Agarwal  
**SiteScope** is a modern, full-stack web application designed to quickly analyze the technical health, SEO readiness, accessibility, and performance metrics of any public website in real time.

Built with a **Spring Boot 3.x** REST API backend and a **React 19 + Vite** responsive dashboard frontend.

---

## 🌟 Key Features

- 🔍 **URL Validation**: Validates HTTP/HTTPS URL formats and prevents loopback/internal requests.
- ⚡ **Performance Measurement**: Measures HTTP response time (in milliseconds) and verifies status codes.
- 📄 **HTML Content Parsing (Jsoup)**: Extracts `<title>`, `<meta name="description">`, `<h1>` heading count, images missing `alt` attributes, and total body word count.
- 📊 **Health Score Calculation**: Algorithmic scoring (0–100) and status mapping (*Excellent*, *Good*, *Needs Improvement*, *Poor*).
- 💡 **Actionable Optimization Engine**: Generates targeted recommendations for SEO, speed, and WCAG accessibility improvements.
- 🎨 **Modern Dashboard UI**: Glassmorphism UI, light/dark mode theme toggle, animated score gauge, responsive 12-column grid layout, and touch-friendly design.

---

## 🛠️ Tech Stack

### **Backend**
- **Framework**: Spring Boot 3.3.5 (Java 17 / Java 21)
- **HTML Parser**: Jsoup 1.18.3
- **Build Tool**: Apache Maven
- **Port**: `8080` (CORS enabled for local React frontend)

### **Frontend**
- **Framework**: React 19 + Vite 5
- **Icons**: Lucide React
- **HTTP Client**: Axios
- **Styling**: Modern CSS Design System (Custom properties, HSL colors, responsive grid)
- **Port**: `5173`

---

## 📂 Project Architecture

```
SiteScope/
├── sitescope-backend/                # Spring Boot REST API
│   ├── src/main/java/com/sitescope/
│   │   ├── config/WebConfig.java     # CORS settings
│   │   ├── controller/AuditController.java # REST API Endpoint (POST /api/v1/audit)
│   │   ├── dto/                      # AuditRequest, AuditResponse, ErrorResponse
│   │   ├── exception/                # GlobalExceptionHandler, Custom Exceptions
│   │   ├── model/                    # AuditResult, Recommendation
│   │   ├── service/                  # AuditService, HtmlParserService, HealthScoreService, RecommendationService
│   │   ├── validator/                # UrlValidator
│   │   ├── util/                     # WordCounter
│   │   └── SiteScopeApplication.java
│   ├── src/test/java/com/sitescope/  # Backend Unit & Integration Tests
│   └── pom.xml                       # Maven Configuration
│
├── sitescope-frontend/               # React + Vite Application
│   ├── src/
│   │   ├── components/               # Navbar, SearchBar, ScoreCard, MetricCard, SuggestionCard, LoadingSpinner, ErrorAlert, Footer
│   │   ├── services/api.js           # Axios API client
│   │   ├── App.jsx                   # Main State Machine & Layout
│   │   ├── main.jsx
│   │   └── index.css                 # Design System & Theme Variables
│   ├── package.json
│   └── vite.config.js
│
└── README.md                         # Instructions & Documentation
```

---

## 📋 Prerequisites

Ensure you have the following installed on your machine:
- **Java Development Kit (JDK)**: Java 17 or Java 21 LTS (`java -version`)
- **Node.js**: Node v18+ and npm (`node -v`, `npm -v`)
- **Maven**: (Optional if using `./mvnw`) (`mvn -version`)

---

## 🚀 Step-by-Step Setup & Running Guide

### **Step 1: Start the Spring Boot Backend**

1. Open a terminal and navigate to the backend directory:
   ```bash
   cd sitescope-backend
   ```

2. Build and run the Spring Boot application:
   - **Using Maven wrapper (Windows)**:
     ```cmd
     .\mvnw spring-boot:run
     ```
   - **Using Maven wrapper (macOS/Linux)**:
     ```bash
     ./mvnw spring-boot:run
     ```
   - **Using installed Maven**:
     ```bash
     mvn spring-boot:run
     ```

3. The backend server will start at: **`http://localhost:8080`**

---

### **Step 2: Start the React Frontend**

1. Open a second terminal window and navigate to the frontend directory:
   ```bash
   cd sitescope-frontend
   ```

2. Install dependencies (if not already installed):
   ```bash
   npm install
   ```

3. Start the Vite development server:
   ```bash
   npm run dev
   ```

4. Open your browser and navigate to: **`http://localhost:5173`**

---

## 🧪 Running Tests

To run the backend unit tests (URL validation, Jsoup HTML parser, Health Score logic, Recommendation Engine):

```bash
cd sitescope-backend
mvn test
```

---

## 📡 REST API Reference

### **Endpoint**: `POST /api/v1/audit`

#### **Request Body**:
```json
{
  "url": "https://example.com"
}
```

#### **Sample Success Response (`200 OK`)**:
```json
{
  "url": "https://example.com",
  "status": 200,
  "responseTime": 142,
  "title": "Example Domain",
  "metaDescription": "Example domain for illustrative examples in documents.",
  "h1Count": 1,
  "imagesMissingAlt": 0,
  "wordCount": 512,
  "healthScore": 100,
  "healthStatus": "Excellent",
  "recommendations": []
}
```

#### **Sample Error Response (`400 Bad Request`)**:
```json
{
  "timestamp": "2026-07-25T13:50:00.123",
  "status": 400,
  "error": "Bad Request",
  "message": "Please enter a valid website URL with http:// or https:// scheme.",
  "path": "/api/v1/audit"
}
```

---

## ⚖️ Health Score Algorithm

Score components (Max Score = 100):
- 🌐 **Website Reachable (200 OK)**: `+20 points`
- 🏷️ **Page Title Present**: `+15 points`
- 📝 **Meta Description Present**: `+15 points`
- 🏷️ **H1 Heading Present**: `+15 points`
- ♿ **Images with ALT Text**: `+15 points`
- 📚 **Word Count > 300**: `+20 points`

**Status Thresholds**:
- `90 – 100`: **Excellent** 🌟
- `70 – 89`: **Good** 👍
- `50 – 69`: **Needs Improvement** ⚠️
- `0 – 49`: **Poor** ❌

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).
