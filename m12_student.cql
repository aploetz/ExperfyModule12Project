#Note:  This CQL will not function on its own.  Parts of it have been left incomplete, requiring you to further define them.

CREATE KEYSPACE serenity_books WITH replication = {'class': 'NetworkTopologyStrategy', <YOUR DATACENTER NAME GOES HERE>: <RF>}  AND durable_writes = true;

use serenity_books;

CREATE TABLE books_by_isbn (
    isbn TEXT,
    title TEXT,
    edition TEXT,
    year TEXT,
    category TEXT,
    price BIGINT,
    publisher TEXT,
    author TEXT,
    PRIMARY KEY ()
);

CREATE TABLE books_by_category (
    isbn TEXT,
    title TEXT,
    edition TEXT,
    year TEXT,
    category TEXT,
    price BIGINT,
    publisher TEXT,
    author TEXT,
    PRIMARY KEY ()
);

CREATE TABLE customer_by_email (
    firstName TEXT,
    lastName TEXT,
    email TEXT,
    password TEXT,
    regDate TIMESTAMP,
    street TEXT,
    city TEXT,
    state TEXT,
    postal TEXT,
    country TEXT,
    PRIMARY KEY ()
);

CREATE TABLE order_items (
    orderID UUID,
    email TEXT,
    orderDate TIMESTAMP,
    status TEXT,
    isbn TEXT,
    title TEXT,
    price BIGINT,
    qty BIGINT,
    PRIMARY KEY ()
) WITH CLUSTERING ORDER BY ();

CREATE TABLE cart_books (
    email TEXT,
    isbn TEXT,
    titie TEXT,
    price BIGINT,
    QTY BIGINT,
    PRIMARY KEY ()
);

