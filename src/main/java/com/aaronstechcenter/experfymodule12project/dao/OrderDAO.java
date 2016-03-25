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

import com.aaronstechcenter.experfymodule12project.data.OrderItem;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderDAO {
    private Session session;
    
    public OrderDAO(Session _session) {
        session = _session;
    }
    
    public void createOrder(List<OrderItem> _order) {
        //Tip: DO NOT USE A BATCH STATEMENT HERE!!!!
        String strCQL = ;

        PreparedStatement statement = session.prepare(strCQL);
        BoundStatement boundStatement = new BoundStatement(statement);

        try {
            for (OrderItem item : _order) {
                boundStatement.bind(;

                session.execute(boundStatement);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public List<OrderItem> getAllOrdersByEmail(String _email) {
        List<OrderItem> returnVal = new ArrayList<OrderItem>();

        try {


            
            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {
                OrderItem item = new OrderItem();
                item.setOrderID(row.getUUID("orderid"));


                
                returnVal.add(item);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
        return returnVal;
    }
    
    //Note: If the PRIMARY KEY on serenity_books.order_items was built correctly,
    //      then NO MODIFICATIONS SHOULD BE REQUIRED FOR THIS METHOD.
    public void updateOrderStatus(String _newStatus, List<OrderItem> _order) {
        String strCQL = "INSERT INTO serenity_books.order_items "
            + "(orderid,email,isbn,status,orderDate) "
            + "VALUES (?,?,?,?,?)";

        PreparedStatement statement = session.prepare(strCQL);
        BoundStatement boundStatement = new BoundStatement(statement);

        try {
            for (OrderItem item : _order) {
                boundStatement.bind(item.getOrderID(),item.getEmail(),item.getIsbn(),
                    _newStatus,item.getOrderDate());

                session.execute(boundStatement);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }
    
    public void closeConnection() {
        session.close();
    }
}