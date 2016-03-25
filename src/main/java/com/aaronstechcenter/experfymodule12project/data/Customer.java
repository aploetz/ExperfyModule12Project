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

import java.util.Date;

public class Customer {
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String street;
    private String city;
    private String state;
    private String postal;
    private String country;

    public Customer() {
        
    }
    
    public Customer(String _firstName, String _lastName, String _email, String _password,
            String _street, String _city, String _state, String _postal, String _country) {
        
        this.firstName = _firstName;
        this.lastName = _lastName;
        this.email = _email;
        this.password = _password;
        this.street = _street;
        this.city = _city;
        this.state = _state;
        this.postal = _postal;
        this.country = _country;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String value) {
        this.firstName = value;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String value) {
        this.lastName = value;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String value) {
        this.email = value;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String value) {
        this.password = value;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String value) {
        this.street = value;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String value) {
        this.city = value;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(String value) {
        this.state = value;
    }
    
    public String getPostal() {
        return this.postal;
    }
    
    public void setPostal(String value) {
        this.postal = value;
    }
    
    public String getCountry() {
        return this.country;
    }
    
    public void setCountry(String value) {
        this.country = value;
    }
}