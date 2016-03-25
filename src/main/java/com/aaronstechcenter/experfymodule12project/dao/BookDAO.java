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
        String strCQL = ;
        
        try {
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_isbn);

            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {
                returnVal.setAuthor(row.getString("author")); 
                
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("No books found for this ISBN");
        }
        
        return returnVal;
    }

    public List<Book> getBooksByCategory(String _category) {
        List<Book> returnVal = new ArrayList<Book>();

        String strCQL = ;
        
        try {
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_category);

            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {
                Book book = new Book();
                
                
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

        String strCQL = ;
        
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
    
    public void closeConnection() {
        session.close();
    }
}