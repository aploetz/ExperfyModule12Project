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
package com.aaronstechcenter.experfymodule12project.data;

import com.aaronstechcenter.experfymodule12project.data.CartBook;

import java.util.Date;
import java.util.UUID;

public class OrderItem {
    private UUID orderID;
    private String email;
    private Date orderDate;
    private String status;
    private String isbn;
    private String title;
    private long price;
    private long qty;
    
    public OrderItem() {
        
    }
    
    public OrderItem(UUID _orderID, String _email, Date _orderDate, String _status, 
            CartBook _cartBook) {
        this.orderID = _orderID;
        this.orderDate = _orderDate;
        this.email = _email;
        this.status = _status;
        this.price = _cartBook.getPrice();
        this.qty = _cartBook.getQty();
        this.isbn = _cartBook.getIsbn();
        this.title = _cartBook.getTitle();
    }
    
    public UUID getOrderID() {
        return this.orderID;
    }
    
    public void setOrderID(UUID value) {
        this.orderID = value;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String value) {
        this.email = value;
    }
    
    public Date getOrderDate() {
        return this.orderDate;
    }
    
    public void setOrderDate(Date value) {
        this.orderDate = value;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String value) {
        this.status = value;
    }

    public String getIsbn() {
        return this.isbn;
    }
    
    public void setIsbn(String value) {
        this.isbn = value;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public long getPrice() {
        return this.price;
    }
    
    public void setPrice(long value) {
        this.price = value;
    }
    
    public long getQty() {
        return this.qty;
    }
    
    public void setQty(long value) {
        this.qty = value;
    }
}
