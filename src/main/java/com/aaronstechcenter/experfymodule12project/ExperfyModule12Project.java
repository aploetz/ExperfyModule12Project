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
package com.aaronstechcenter.experfymodule12project;

import com.aaronstechcenter.experfymodule12project.dao.BookDAO;
import com.aaronstechcenter.experfymodule12project.dao.CartDAO;
import com.aaronstechcenter.experfymodule12project.dao.CassandraDAO;
import com.aaronstechcenter.experfymodule12project.dao.CustomerDAO;
import com.aaronstechcenter.experfymodule12project.dao.OrderDAO;
import com.aaronstechcenter.experfymodule12project.data.Book;
import com.aaronstechcenter.experfymodule12project.data.CartBook;
import com.aaronstechcenter.experfymodule12project.data.Customer;
import com.aaronstechcenter.experfymodule12project.data.OrderItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ExperfyModule12Project {
    
    public static void main(String[] args) {
        //prepare connection
        String[] nodeList = {"192.168.0.100"};
        String username = "serenity";
        String password = "bacon";
        String dataCenter = "AaronsLab";
        CassandraDAO cassDao = CassandraDAO.getInstance(nodeList, username, password, dataCenter);

        //prepare specific DAOs
        BookDAO bookDao = new BookDAO(cassDao.getSession());
        CartDAO cartDao = new CartDAO(cassDao.getSession());
        CustomerDAO custDao = new CustomerDAO(cassDao.getSession());
        OrderDAO orderDao = new OrderDAO(cassDao.getSession());
        
        //**************QUERIES TO SOLVE FOR**************//
        
        //Life will be easier if you set your email address here:
        String myEmailAddress = "aaronploetz@gmail.com";

        //insert yourself as a customer
//        public Customer(String _firstName, String _lastName, String _email, String _password,
//                        String _street,    String _city,     String _state, String _postal, String _country)
        Customer customer = 
            new Customer("Aaron","Ploetz",myEmailAddress,"flynnLives", 
                         "33 S. 6th Street","Minneapolis","MN","55402","United States");
        custDao.createNewCustomer(customer);

        //Create a new customer object, and update your address
        Customer myAddressUpdate = new Customer();
        myAddressUpdate.setEmail(myEmailAddress);
        myAddressUpdate.setStreet("9347 Magnolia Lane N.");
        myAddressUpdate.setCity("Maple Grove");
        myAddressUpdate.setState("MN");
        myAddressUpdate.setPostal("55369");
        myAddressUpdate.setCountry("United States");
        custDao.updateExistingAddress(myAddressUpdate);
        
        //retreive your customer data by email, and save to a new Customer object
        Customer customerTest = custDao.getCustomer(myEmailAddress);
        //verify your new address
        System.out.format("%15s %15s %25s %15s %2s %s \n",
                customerTest.getFirstName(),
                customerTest.getLastName(),
                customerTest.getEmail(),
                customerTest.getCity(),
                customerTest.getState(),
                customerTest.getCountry());
        
        //get the list book categories
        List<String> categories = bookDao.getBookCategories();
        
        System.out.println();
        
        //Iterate through each category, and list all books under it
        for (String category : categories) {
            System.out.println(category + " books:");
            
            List<Book> books = bookDao.getBooksByCategory(category);
            for (Book book : books) {
                System.out.format("%20s %40s %13s %25s %s \n",
                    book.getAuthor(), 
                    book.getTitle(),
                    book.getIsbn(),
                    book.getPublisher(),
                    book.getYear());
            }
        }

        System.out.println();
        
        //pick 3 books...
        Book book1 = bookDao.getBookByISBN("9781783989126");
        Book book2 = bookDao.getBookByISBN("9781430229704");
        Book book3 = bookDao.getBookByISBN("9781418836290");
        
        //...convert them to CartBook objects...
        CartBook cartLine1 = new CartBook(myEmailAddress, book1, 1);
        CartBook cartLine2 = new CartBook(myEmailAddress, book2, 1);
        CartBook cartLine3 = new CartBook(myEmailAddress, book3, 1);

        //...and add them to your shopping cart.
        cartDao.AddItem(cartLine1);
        cartDao.AddItem(cartLine2);
        cartDao.AddItem(cartLine3);
        
        //retrieve the contents of your shopping cart.
        List<CartBook> shoppingCart = cartDao.getCart(myEmailAddress);
        
        //display cart
        for (CartBook cartLine : shoppingCart) {
            Long price = cartLine.getPrice();
            double dblPrice = Double.parseDouble(price.toString()) * .01;
            System.out.format("%13s %40s %d $%.2f\n",
                cartLine.getIsbn(),
                cartLine.getTitle(),
                cartLine.getQty(),
                dblPrice);
        }
        
        System.out.println();
        
        //convert your cart to an order
        List<OrderItem> order = convertCartToOrder(myEmailAddress,shoppingCart);
        
        //place your order into the system
        orderDao.createOrder(order);
        
        //update your order status
        orderDao.updateOrderStatus("SHIPPED", order);
        
        //view all orders for your email address
        List<OrderItem> orders = orderDao.getAllOrdersByEmail(myEmailAddress);
        viewMyOrders(orders);
        
        custDao.closeConnection();
        bookDao.closeConnection();
        cartDao.closeConnection();
        orderDao.closeConnection();
        cassDao.closeConnection();
    }
    
    private static List<OrderItem> convertCartToOrder(String _email, List<CartBook> cart) {
        List<OrderItem> returnVal = new ArrayList<OrderItem>();
        
        UUID orderid = UUID.randomUUID();
        Date orderDate = new Date();
        
        for (CartBook cartLine : cart) {
            OrderItem orderLine = new OrderItem(orderid,_email,orderDate,"SUBMITTED",cartLine);
            returnVal.add(orderLine);
        }
        
        return returnVal;
    }
    
    private static void viewMyOrders(List<OrderItem> _orders) {
        UUID orderID = UUID.fromString("00000000-0000-0000-0000-000000000000");
        UUID lastOrderID = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Double dblTotal = 0d;
        Date orderDate = new Date();
        String status = "";
        boolean first = true;
        
        System.out.format("%36s %28s %10s %s\n",
                "OrderID","Order Date","Status","Total");
        for (OrderItem item : _orders) {
            //iterate order items, compute totals
            orderID = item.getOrderID();
            if (!(lastOrderID.equals(orderID)) && !first) {
                System.out.format("%36s %28s %10s %s\n",
                    lastOrderID,orderDate,status,String.format("%1$,.2f",dblTotal));
                //reset display variables
                dblTotal = 0d;
            }
            
            Long totalItemPrice = item.getPrice() * item.getQty();
            dblTotal += Double.parseDouble(totalItemPrice.toString()) * .01;
            lastOrderID = orderID;
            orderDate = item.getOrderDate();
            status = item.getStatus();
            first = false;
        }
        
        //print the very last order
        if (!first) {
            System.out.format("%36s %28s %10s %s\n",
                lastOrderID,orderDate,status,String.format("%1$,.2f",dblTotal));
        }
    }
}
