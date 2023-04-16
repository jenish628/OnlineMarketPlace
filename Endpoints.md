# Followings are the Endpoints for Shopping Cart

Shopping Carts Related Products
```
GET         /shoppping-cart                             Get Product List from Shopping Cart
            return List
            
POST        /shoppping-cart?productId=4&quantity=4      Add Quantity from Cart Page
            return boolean
            
PUT         /shoppping-cart?productId=4&quantity=4      Add Product to Shopping Cart from product/details page
            return boolean
            
DELETE      /shoppping-cart?productId=4                 Remove Product From Shopping Cart
            return boolean

```
