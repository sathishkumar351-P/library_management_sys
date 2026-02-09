package com.wipro.book.servlet;

import java.io.IOException;

import com.wipro.book.bean.AuthorBean;
import com.wipro.book.bean.BookBean;
import com.wipro.book.dao.AuthorDAO;
import com.wipro.book.service.Administrator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("AddBook".equalsIgnoreCase(operation)) {

            BookBean book = buildBook(request);

            if (book == null) {
                response.sendRedirect("Invalid.html");
                return;
            }

            Administrator admin = new Administrator();
            String result = admin.add(book);

            if ("SUCCESS".equals(result)) {
                HttpSession session = request.getSession();
                session.setAttribute("book", book);
                response.sendRedirect("ViewServlet");
            } else {
                response.sendRedirect("Failure.html");
            }
        }

        else if ("ViewBook".equalsIgnoreCase(operation)) {

            String isbn = request.getParameter("isbn");

            Administrator admin = new Administrator();
            BookBean book = admin.viewBook(isbn);

            if (book == null) {
                response.sendRedirect("Failure.html");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("book", book);
            response.sendRedirect("ViewServlet");
        }
    }

    private BookBean buildBook(HttpServletRequest request) {

        String isbn = request.getParameter("isbn");
        String bookName = request.getParameter("bookname");
        String bookType = request.getParameter("booktype");
        String authorName = request.getParameter("author");
        String costStr = request.getParameter("bookcost");

        
        try {
            float cost = Float.parseFloat(costStr);

            AuthorDAO adao = new AuthorDAO();
            AuthorBean author = adao.getAuthorByName(authorName);

            if (author == null) {
				return null;
			}

            BookBean book = new BookBean();
            book.setIsbn(isbn);
            book.setBookName(bookName);
            book.setBookType(bookType.charAt(0));
            book.setCost(cost);
            book.setAuthor(author);

            return book;

        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
}
