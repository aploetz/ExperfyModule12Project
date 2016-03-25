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

import com.aaronstechcenter.experfymodule12project.data.CartBook;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    private Session session;
    
    public CartDAO(Session _session) {
        session = _session;
    }
    
    public List<CartBook> getCart(String _email) {
        List<CartBook> returnVal = new ArrayList<CartBook>();
        
        try {
            String strCQL = "SELECT email,isbn,title,price,qty "
                + "FROM serenity_books.cart_books WHERE email = ?";
            
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_email);

            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {
                CartBook book = new CartBook();
                
                book.setEmail(row.getString("email")); 
                book.setTitle(row.getString("title"));
                book.setIsbn(row.getString("isbn"));
                book.setQty(row.getLong("qty"));
                book.setPrice(row.getLong("price"));
                
                returnVal.add(book);
            }
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return returnVal;
    }

    public void AddItem(CartBook book) {
        AddItem(book.getIsbn(),book.getQty(),book.getPrice(), book.getTitle(), book.getEmail());
    }
    
    public void AddItem(String _isbn, long _qty, long _price, String _title, String _email) {
        try {
            String insertItemCQL = "INSERT INTO serenity_books.cart_books "
                + "(email,isbn,title,price,qty) "
                + "VALUES(?,?,?,?,?)";
            PreparedStatement ps = session.prepare(insertItemCQL);
            BoundStatement boundStatement = new BoundStatement(ps);
            boundStatement.bind(_email,_isbn,_title,_price,_qty);

            session.execute(boundStatement);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public boolean removeItem(String _isbn, String _email) {
        boolean success = true;
        
        try {
                String removeItemCQL = "DELETE FROM serenity_books.cart_books ";
                removeItemCQL += "WHERE email=? AND isbn=?";

                PreparedStatement ps = session.prepare(removeItemCQL);
                BoundStatement boundStatement = new BoundStatement(ps);
                boundStatement.bind(_email,_isbn);

                session.execute(boundStatement);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            success = false;
        }
        
        return success;
    }
    
    
    public boolean removeAllItems(String _email) {
        boolean success = true;
        
        try {
                String removeItemCQL = "DELETE FROM serenity_books.cart_books ";
                removeItemCQL += "WHERE email=? AND isbn=?";

                PreparedStatement ps = session.prepare(removeItemCQL);
                BoundStatement boundStatement = new BoundStatement(ps);
                boundStatement.bind(_email);

                session.execute(boundStatement);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            success = false;
        }
        
        return success;
    }
    
    public void closeConnection() {
        session.close();
    }
}