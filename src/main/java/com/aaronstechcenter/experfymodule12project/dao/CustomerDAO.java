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

import com.aaronstechcenter.experfymodule12project.data.Customer;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
       
import java.util.Date;

public class CustomerDAO {
    private Session session;
            
    public CustomerDAO(Session _session) {
        session = _session;
    }
    
    public void createNewCustomer(Customer _customer) {
        try {
                String strCQL = ;

                PreparedStatement statement = session.prepare(strCQL);
                BoundStatement boundStatement = new BoundStatement(statement);
                boundStatement.bind(;
                
                session.execute(boundStatement);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    public Customer getCustomer(String _email) {
        Customer returnVal = new Customer();

        String strCQL = ;
        
        try {
            PreparedStatement statement = session.prepare(strCQL);
            BoundStatement boundStatement = new BoundStatement(statement);
            boundStatement.bind(_email);

            ResultSet results = session.execute(boundStatement);
            for (Row row : results) {

            }
        } catch (Exception ex) {
            System.out.println("Customer not found for this email!");
        }

        return returnVal;
    }
    
    public void updateExistingAddress(Customer _customer) {
        String strCQL = ;
        
        try {
        PreparedStatement statement = session.prepare(strCQL);
        BoundStatement boundStatement = new BoundStatement(statement);
        boundStatement.bind(;

        session.execute(boundStatement);
        } catch (Exception ex) {
            System.out.println(ex.toString());
            returnVal = false;
        }
    }
    
    public void closeConnection() {
        session.close();
    }
}
