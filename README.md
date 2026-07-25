# SiteScope — Website Health & SEO Analyzer 🚀

**Author & Developer**: Radha Agarwal  
**Live Backend API**: `https://sitescope-backend-am1d.onrender.com`  
**GitHub Repository**: `https://github.com/ravine27/SiteScope`

**SiteScope** is a modern, full-stack web application designed to evaluate the technical health, SEO readiness, WCAG accessibility compliance, and performance metrics of any public website in real time.

Built with a **Spring Boot 3.x** REST API backend and a **React 19 + Vite** responsive dashboard frontend.

---

## 🏗️ 1. Architectural Design Decisions & Reasoning

### **Decision 1: Stateless On-Demand Real-Time Architecture (No Database in MVP)**
- **Design**: The backend analyzes requested URLs dynamically in real time without persisting audit history to a database.
- **Reasoning**: Website health, latency, and SEO metrics represent a real-time snapshot of public websites. Introducing a database for the MVP would add schema migrations, persistence overhead, and storage costs without adding user value prior to authentication. A stateless backend allows instant horizontal scaling on cloud platforms like Render, lower memory consumption, and zero cold-storage latency.

### **Decision 2: Decoupled Single-Responsibility Services (`HtmlParser`, `HealthScore`, `Recommendation`)**
- **Design**: Separated HTML parsing (`HtmlParserService`), health score calculation (`HealthScoreService`), and recommendation generation (`RecommendationService`) into dedicated Spring `@Service` components.
- **Reasoning**: Decoupling HTML extraction from evaluation ensures high maintainability and unit testability. If scoring rules change (e.g., adding Lighthouse integration or modifying point weights), or if new recommendation rules are introduced for accessibility, developers can edit the relevant service independently without touching Jsoup parsing logic or REST controller handlers.

### **Decision 3: Two-Stage Docker Containerization for Cloud Deployment**
- **Design**: Created a 2-stage `Dockerfile` (`maven:3.9.6-eclipse-temurin-17` for compilation and `eclipse-temurin:17-jre` for runtime execution).
- **Reasoning**: Compiling Spring Boot inside Docker guarantees environment consistency across developer OS and cloud environments. Separating the build stage from the runtime stage strips out Maven source artifacts and compiler tools, reducing the production Docker image size from ~700MB to ~200MB. This drastically speeds up deployment build times and improves container security on Render.

---

## 🛠️ 2. Setup Guide

### Prerequisites
- **Java Development Kit (JDK)**: Java 17 or Java 21 LTS (`java -version`)
- **Node.js**: Node v18+ and npm (`node -v`, `npm -v`)
- **Git**: Installed on system

---

### **Backend Setup (Spring Boot 3.x)**

1. Navigate to the backend directory:
   ```bash
   cd sitescope-backend
   ```

2. Run unit tests to verify system health:
   ```bash
   mvn test
   ```
   *(Or on Windows: `.\mvnw.cmd test`)*

3. Start the Spring Boot server:
   ```bash
   mvn spring-boot:run
   ```
   *(Or on Windows: `.\mvnw.cmd spring-boot:run`)*

4. The backend API will start at **`http://localhost:8080`**.

---

### **Frontend Setup (React 19 + Vite)**

1. Open a new terminal window and navigate to the frontend directory:
   ```bash
   cd sitescope-frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the Vite local development server:
   ```bash
   npm run dev
   ```

4. Open your browser and navigate to **`http://localhost:5173`**.

---

### **Docker Setup (Local & Production)**

Build and run the backend Docker container locally:
```bash
cd sitescope-backend
docker build -t sitescope-backend .
docker run -p 8080:8080 sitescope-backend
```

---

## 📡 3. API Contract Specification

### **Root Health Endpoint**
- **URL**: `GET /`
- **Description**: Verifies backend server health and API route status.
- **Response (`200 OK`)**:
  ```json
  {
    "project": "SiteScope - Website Health & SEO Analyzer API",
    "status": "UP",
    "auditEndpoint": "POST /api/v1/audit"
  }
  ```

---

### **Website Audit Endpoint**
- **URL**: `POST /api/v1/audit` *(also mapped to `POST /audit`)*
- **Content-Type**: `application/json`

#### **Request Body Schema (`AuditRequest`)**:
| Field | Type | Required | Constraint | Description |
| :--- | :--- | :--- | :--- | :--- |
| `url` | `String` | Yes | Non-blank, HTTP/HTTPS format | Target website URL to audit |

**Example Request**:
```json
{
  "url": "https://example.com"
}
```

#### **Success Response Schema (`200 OK` - `AuditResponse`)**:
| Field | Type | Description |
| :--- | :--- | :--- |
| `url` | `String` | Target website URL audited |
| `status` | `Integer` | HTTP status code returned by target site (e.g. 200) |
| `responseTime` | `Long` | Target site latency in milliseconds |
| `title` | `String` | Extracted page title |
| `metaDescription` | `String` | Extracted SEO meta description |
| `h1Count` | `Integer` | Total `<h1>` heading elements found |
| `imagesMissingAlt` | `Integer` | Count of `<img>` tags missing `alt` attributes |
| `wordCount` | `Integer` | Total body word count extracted via Jsoup |
| `healthScore` | `Integer` | Algorithmic health score (0 – 100) |
| `healthStatus` | `String` | Health category (`Excellent`, `Good`, `Needs Improvement`, `Poor`) |
| `recommendations` | `List<String>` | Array of actionable optimization suggestions |

**Example Success Response (`200 OK`)**:
```json
{
  "url": "https://example.com",
  "status": 200,
  "responseTime": 1015,
  "title": "Example Domain",
  "metaDescription": "Example Domain for illustrative documents.",
  "h1Count": 1,
  "imagesMissingAlt": 0,
  "wordCount": 512,
  "healthScore": 100,
  "healthStatus": "Excellent",
  "recommendations": []
}
```

#### **Error Response Schema (`ErrorResponse`)**:
```json
{
  "timestamp": "2026-07-25T17:30:00.123",
  "status": 400,
  "error": "Bad Request",
  "message": "Please enter a valid website URL with http:// or https:// scheme.",
  "path": "/api/v1/audit"
}
```

#### **Error HTTP Status Mapping**:
- `400 Bad Request`: Invalid URL format, non-HTTP/HTTPS schemes, or loopback/localhost requests.
- `415 Unsupported Media Type`: Non-HTML content types (e.g., PDFs, images, JSON).
- `502 Bad Gateway`: Target website DNS resolution failure or network connection refused.
- `504 Gateway Timeout`: Target website response timeout exceeding 10,000 ms.

---

## 📄 License
This project is open-source and maintained by **Radha Agarwal** under the [MIT License](LICENSE).
