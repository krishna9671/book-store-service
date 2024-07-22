# book-store-service
This app handles all back-end requests for bookstore app

Steps to run the application.
1. Run the app as Java application
2. By default, books are assumed to be available in database. Please go to h2 database and add the below sample records.
   INSERT INTO BOOK(TITLE,AUTHOR,PRICE) VALUES('Finance','Peter',20.00);
    INSERT INTO BOOK(TITLE,AUTHOR,PRICE) VALUES('Economics','Lynch',30.00);
3. In home page, first go to register and give user name and password. Once successful, it will redirect to login page.
4. In login page, enter credentials and it will be navigated to books page.
5. Select books available and click on add to cart, the unique elemments will be added to cart on top.
6. Click on cart icon, to proceed to cart page and then total of the cart would be displayed.
7. On clicking of place order, order popup will showup and then redirected to books page.
