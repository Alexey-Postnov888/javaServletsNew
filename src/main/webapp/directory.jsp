<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<head>
    <base href="${pageContext.request.contextPath}/">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" />
    <title>Explorer</title>
</head>
<body>
<c:set var="title" value="Проводник" />
<div class="container shadow p-4 mt-4 bg-white rounded">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>${title}</h1>
        <div>
            <span class="me-3">Вы вошли как: ${username}</span>
            <a href="logout" class="btn btn-outline-danger">
                <i class="bi bi-box-arrow-right"></i> Выйти
            </a>
        </div>
    </div>
    <div class="content">
        <p>
            <i class="bi bi-clock"></i>
            Текущее время: ${currentTime}
        </p>
        <p>
            <i class="bi bi-folder"></i>
            Текущий путь: ${currentPath.replace(":\\", " > ").replace("\\", " > ")}
        </p>
        <p>
            <a class="btn btn-outline-primary" href="files?path=${URLEncoder.encode(parentPath, 'UTF-8')}">
                <i class="bi bi-arrow-90deg-up"></i>
                Подняться на уровень выше
            </a>
        </p>
        <ul class="list-unstyled">
            <c:forEach items="${files}" var="file">
                <li class="mb-2">
                    <div class="card">
                        <div class="card-body">
                            <c:choose>
                                <c:when test="${file.isDirectory()}">
                                    <i class="bi bi-folder2-open me-2"></i>
                                    <a class="text-decoration-none" href="files?path=${URLEncoder.encode(file.getAbsolutePath(), 'UTF-8')}">${file.getName()}</a>
                                </c:when>
                                <c:otherwise>
                                    <i class="bi bi-file-earmark-code me-2"></i>
                                    <a class="text-decoration-none" href="download?fileDownloadPath=${URLEncoder.encode(file.getAbsolutePath(), 'UTF-8')}">${file.getName()}</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<footer class="mt-4 text-center text-muted">
    <p>&copy; 2025 ${title}. JavaServlets lab.</p>
</footer>
</body>
</html>