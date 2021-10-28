package servlets;

import dataaccess.UserDB;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;

/**
 *
 * @author Dynamic Duo
 */
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        UserDB udb = new UserDB();

        try {
            List<User> usersList = udb.getAll();
            request.setAttribute("users", usersList);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        String action = request.getParameter("action");

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);  //loads Shopping list page
        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        UserDB udb = new UserDB();
        
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String fName = request.getParameter("fName");
        String lName = request.getParameter("lName");
        String password = request.getParameter("password");
        String role = request.getParameter("roles");
        
        User user = new User (email, true, fName, lName, password, role);
        
        try {
            switch (action) {
                case "add":
                    udb.insert(user);
                    udb.getAll();
                    break;
//                case "update":
//                    ns.update(Integer.parseInt(noteId), title, contents, email);
//                    break;
//                case "delete":
//                    ns.delete(Integer.parseInt(noteId));
            }
            request.setAttribute("message", action);
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "error");
        }

        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);  //loads Shopping list page
        return;
    }

}
