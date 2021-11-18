HOTEL-BOOKING-BACKEND-SERVICE
-----------------------------


----------


Below are the Admin APIs available :
----------------------------------

1. **GET**  :   */admin/room*

       This API returns all the rooms with there booking records


2. **POST** : */admin/room*

       This API accepts below JSON and after validation it will create a new Room in database

           {
              "roomNo": Integer(ex. 10, 100),
              "price": double(ex. 10.10, 100.25)
           }

       ex :

           {
               "roomNo": 10,
               "price": 200.0
           }

3. **PUT**  :   */admin/room*

       This API accepts below JSON and finds room by Room Id and updates the room

           {
               "roomId" : "8d6f1461-ea67-4aa5-b823-f9923d242056"  (UUID),
               "roomNo" : 10 (Integer),
               "price" : 200.0 (Double)
           }

       ex :

            {
               "roomId" : "8d6f1461-ea67-4aa5-b823-f9923d242056",
               "roomNo" : 10,
               "price" : 200.0
           }

4. **DELETE**  : */admin/room/{roomId}*

       This API accepts valid roomId(UUID) as path variable and deletes the room from database

5. **GET**  :  */admin/user*

       This API return all the users present in the database

6.  **GET**  :  */admin/room/booked*

    This API returns all the rooms which are the booked

7. **GET**  :  */admin/room/available*

       This API returns all the rooms which are not booked rooms

8. **GET**  :  */admin/room/totalRevenue*

       This API fetches total revenue



Below are the User APIs :
------------------------

1. **GET**  :  */user/room/booking*

       This API returns all the rooms and there bookings and booked dates to user

2. **POST**  :  */user/room/booking*

       This API accepts below JSON does the bookings for users

       examples :
       1.
       {
           "userId" : "8d6f1461-ea67-4aa5-b823-f9923d242056",
           "nameOfUser" : "Sansa Stark",
           "singleBookingDtos" :   [ {
               "bookingDates" : "2021-10-28, 2021-10-29, 2021-12-01",
               "roomNumbers" : [10, 100, 5]
           },
           {
               "bookingDates" : "2021-10-28",
               "roomNumbers" : [1, 2, 5]
           },
           {
               "bookingDates" : "2021-10-28, 2021-10-29",
               "roomNumbers" : [1]
           }
           ]
       }


       2.
       {
           "userId" : "8d6f1461-ea67-4aa5-b823-f9923d242056",
           "nameOfUser" : "Daenerys Targaryen",
           "singleBookingDtos" :   [ {
               "bookingDates" : "2021-10-28",
               "roomNumbers" : [10]
           },
           {
               "bookingDates" : "2021-10-25, 2021-10-26, 2021-10-28, 2021-10-29, 2021-12-01",
               "roomNumbers" : [1, 2, 5, 4, 6]
           },
           {
               "bookingDates" : "2021-10-28, 2021-10-29",
               "roomNumbers" : [1, 2, 5]
           }
           ]
       }



Application Notes :
-----------------

- Use Java 11 or higher and the default port is 8080
- In the Bootstrap class startup code is written to add the initial Users, Rooms and Bookings
- Once you start the application call  **GET**  :   */admin/room*  and   **GET**  :   */admin/user*  APIs to get the rooms and user details present in the database
- You can also access the database on **GET**  :   */h2-console* API and for the database credentials go once through *application.yaml*
- While booking rooms for multiple number of dates enter dates in double quotation("") and with comma(,) separated and in *YYYY-MM-DD* format ex. *"2021-10-25, 2021-10-26, 2021-10-28, 2021-10-29, 2021-12-01"* and enter room numbers in Array format ex.  *[1, 2, 5, 4, 6]*