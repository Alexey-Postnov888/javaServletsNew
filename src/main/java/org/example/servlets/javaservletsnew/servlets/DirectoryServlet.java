package org.example.servlets.javaservletsnew.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class DirectoryServlet extends HttpServlet {
    private static final String BASE_DIR = "C:\\Users\\adpos\\Documents\\GitHub\\Code\\CSU projects\\2nd course\\OOP\\UsersForLab5\\";
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = (String) session.getAttribute("username");
        String userHomeDir = BASE_DIR + username + "\\";

        String path = request.getParameter("path");
        if (path == null || path.isEmpty()) {
            path = userHomeDir;
        }

        if (!path.startsWith(userHomeDir)) {
            path = userHomeDir;
        }

        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File[] files = directory.listFiles();

        request.setAttribute("currentPath", path);
        request.setAttribute("files", files);
        request.setAttribute("parentPath", directory.getParent());
        request.setAttribute("currentTime", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date()));
        request.setAttribute("username", username);

        request.getRequestDispatcher("/directory.jsp").forward(request, response);
    }
}