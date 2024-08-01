<h1>Course Management System</h1>

<p>This project is for practice purposes.</p>
<p>
This is a Spring Boot project that provides a RESTful API for managing a university's departments, teachers, students, and courses. The project includes role-based access control using Spring Security, and it persists data using JPA and Hibernate with a PostgreSQL database.
</p>

<h2>Technologies Used</h2>

<ul>
  <li>Spring Boot - Version 2.5.2</li>
  <li>Spring Security - For securing the APIs with role-based access control</li>
  <li>JPA & Hibernate - For ORM and data persistence</li>
  <li>PostgreSQL - For the database</li>
  <li>Java - Version 17</li>
</ul>


<h2>Project Setup</h2>

<h3>Prerequisites</h3>

Before running the project, ensure you have the following installed:

<ul>
<li>JDK 17</li>
<li>IntelliJ IDEA (or any preferred IDE)</li>
<li>PostgreSQL</li>
</ul>

<h3>Database Setup</h3>

<ol>
    <li>Create a PostgreSQL database with a name of your choice (e.g., course_management).</li>
    <li>Add an initial ADMIN record manually to your database to start using the APIs. For example:
        <pre><code>INSERT INTO users (username, password, role) VALUES ('admin', 'password', 'ROLE_ADMIN');</code></pre>
    </li>
    <li>Update the application.properties file with your database configuration:
        <pre><code>
spring.datasource.url=jdbc:postgresql://localhost:5432/course_management
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
        </code></pre>
    </li>
</ol>


<h3>Running the Project</h3>

<p>To run the project using IntelliJ IDEA:</p>

<ol>
    <li>Open the project in IntelliJ IDEA.</li>
    <li>Make sure the correct SDK (JDK 17) is selected.</li>
    <li>Navigate to the <code>CourseManagementSystemApplication</code> class, which contains the <code>main</code> method.</li>
    <li>Right-click on the class and select <code>Run 'CourseManagementSystemApplication'</code>.</li>
</ol>


<h3>Security Configuration</h3>

<p>The project is secured with Spring Security. Access to the APIs is restricted based on user roles: ADMIN, HEAD, TEACHER, and STUDENT.</p>

<h3>API Endpoints</h3>

<h4>Course APIs</h4>
<ul>
    <li><code>POST /api/course</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code></li>
    <li><code>GET /api/course</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>GET /api/course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>PUT /api/course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code></li>
    <li><code>DELETE /api/course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code></li>
</ul>


<h4>Department APIs</h4>
<ul>
    <li><code>POST /api/department</code> - Accessible by <code>ADMIN</code></li>
    <li><code>GET /api/department</code> - Accessible by <code>ADMIN</code></li>
    <li><code>GET /api/department/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code></li>
    <li><code>GET /api/department/{id}/avg</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code></li>
    <li><code>PUT /api/department/{id}</code> - Accessible by <code>ADMIN</code></li>
    <li><code>DELETE /api/department/{id}</code> - Accessible by <code>ADMIN</code></li>
</ul>


<h4>Provided Course APIs</h4>
<ul>
    <li><code>POST /api/provided-course</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>GET /api/provided-course</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code>, <code>STUDENT</code></li>
    <li><code>GET /api/provided-course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code>, <code>STUDENT</code></li>
    <li><code>GET /api/provided-course/{id}/avg</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>PUT /api/provided-course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>DELETE /api/provided-course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
</ul>


<h4>Selected Course APIs</h4>
<ul>
    <li><code>POST /api/selected-course</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>STUDENT</code></li>
    <li><code>GET /api/selected-course</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code>, <code>STUDENT</code></li>
    <li><code>GET /api/selected-course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code>, <code>STUDENT</code></li>
    <li><code>PUT /api/selected-course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>DELETE /api/selected-course/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>STUDENT</code></li>
</ul>


<h4>Student APIs</h4>
<ul>
    <li><code>POST /api/student</code> - Accessible by <code>ADMIN</code></li>
    <li><code>GET /api/student</code> - Accessible by <code>ADMIN</code></li>
    <li><code>GET /api/student/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>STUDENT</code></li>
    <li><code>GET /api/student/{id}/avg</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>STUDENT</code></li>
    <li><code>PUT /api/student/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>STUDENT</code></li>
    <li><code>DELETE /api/student/{id}</code> - Accessible by <code>ADMIN</code></li>
</ul>


<h4>Teacher APIs</h4>
<ul>
    <li><code>POST /api/teacher</code> - Accessible by <code>ADMIN</code></li>
    <li><code>GET /api/teacher</code> - Accessible by <code>ADMIN</code></li>
    <li><code>GET /api/teacher/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>PUT /api/teacher/{id}</code> - Accessible by <code>ADMIN</code>, <code>HEAD</code>, <code>TEACHER</code></li>
    <li><code>DELETE /api/teacher/{id}</code> - Accessible by <code>ADMIN</code></li>
</ul>


<h2>Authentication</h2>
<p>
This project uses basic authentication for API access. Roles must be correctly assigned to users to grant appropriate access levels.
</p>
<h2>Author</h2>

<p>Arvin Aryaei Azad</p>
