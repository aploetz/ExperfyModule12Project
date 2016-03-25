/*
 * The MIT License
 *
 * Copyright 2016 Aaron A. Ploetz.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.aaronstechcenter.experfymodule12project.dao;

import com.aaronstechcenter.experfymodule12project.data.Book;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Session session;
    
    public BookDAO(Session _session) {
        session = _session;
    }
    
    public Book getBookByISBN(String _isbn) {
        Book returnVal = new Book();
        String strCQL = "SELECT author,title,publisher,edition,year,category,price "
            + "FROM serenity_books.books_by_isbn "
            + "WHERE isbn=?";
        
        try {
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_isbn);

            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {
                returnVal.setAuthor(row.getString("author")); 
                returnVal.setTitle(row.getString("title"));
                returnVal.setIsbn(_isbn);
                returnVal.setPublisher(row.getString("publisher"));
                returnVal.setYear(row.getString("year"));
                returnVal.setEdition(row.getString("edition"));
                returnVal.setCategory(row.getString("category"));
                returnVal.setPrice(row.getLong("price"));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("No books found for this ISBN");
        }
        
        return returnVal;
    }

    public List<Book> getBooksByCategory(String _category) {
        List<Book> returnVal = new ArrayList<Book>();

        String strCQL = "SELECT author,title,publisher,edition,year,category,price,isbn "
            + "FROM serenity_books.books_by_category "
            + "WHERE category=?";
        
        try {
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_category);

            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {
                Book book = new Book();
                
                book.setAuthor(row.getString("author")); 
                book.setTitle(row.getString("title"));
                book.setIsbn(row.getString("isbn"));
                book.setPublisher(row.getString("publisher"));
                book.setYear(row.getString("year"));
                book.setEdition(row.getString("edition"));
                book.setCategory(row.getString("category"));
                book.setPrice(row.getLong("price"));
                
                returnVal.add(book);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("No books found for this category");
        }
        
        return returnVal;
    }
        
    public List<String> getBookCategories() {
        List<String> returnVal = new ArrayList<String>();

        String strCQL = "SELECT DISTINCT category "
            + "FROM serenity_books.books_by_category ";
        
        try {
            ResultSet results = session.execute(strCQL);
            
            for (Row row : results) {
                returnVal.add(row.getString("category"));
            }
        } catch (Exception ex) {
            System.out.println("No books found for this category");
        }
        
        return returnVal;
    }
    
    public long getPrice(String _isbn) {
        long price = -1;
        
        String strCQL = "SELECT price "
            + "FROM serenity_books.books_by_isbn "
            + "WHERE isbn=?";
        
        try {
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_isbn);

            ResultSet results = session.execute(boundStatement);
            price = results.one().getLong("price");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("No books found for this ISBN");
        }
        
        return price;
    }
    
    public void closeConnection() {
        session.close();
    }
}