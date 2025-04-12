package org.example.servlets.javaservletsnew.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/download")
public class DownloadFilesServlet extends HttpServlet {
    private static final String BASE_DIR = "C:\\Users\\adpos\\Documents\\GitHub\\Code\\CSU projects\\2nd course\\OOP\\UsersForLab5\\";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }

        String username = (String) session.getAttribute("username");
        String userHomeDir = BASE_DIR + username + '\\';
        String fileDownloadPath = request.getParameter("fileDownloadPath");

        if (fileDownloadPath == null || !fileDownloadPath.startsWith(userHomeDir)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещён");
            return;
        }

        File file = new File(fileDownloadPath);

        response.setContentType("application/octet-stream");
        String encodedFileName = URLEncoder.encode(file.getName(), StandardCharsets.UTF_8.toString());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + "\"");
        response.setContentLength((int) file.length());

        try (FileInputStream fileInputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}