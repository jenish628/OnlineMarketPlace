package com.miu.onlinemarketplace.service.domain.shopping;

import com.miu.onlinemarketplace.common.dto.ShoppingCartDto;
import com.miu.onlinemarketplace.entities.Product;
import com.miu.onlinemarketplace.entities.ShoppingCart;
import com.miu.onlinemarketplace.exception.DataNotFoundException;
import com.miu.onlinemarketplace.exception.QuantityInsufficientException;
import com.miu.onlinemarketplace.repository.ProductRepository;
import com.miu.onlinemarketplace.repository.ShoppingCartRepository;
import com.miu.onlinemarketplace.utils.UserUtils;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService{

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ShoppingCartDto> getCartItems() {
        Long userId = UserUtils.getCurrentUserId();
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUser_UserId(userId);
        return shoppingCarts.stream().map(shoppingCart -> modelMapper.map(shoppingCart, ShoppingCartDto.class)).collect(Collectors.toList());


    }

    @Override
    public boolean addQty(Long productId, Integer qty) {
        Product product = productRepository.findById(productId).orElseThrow(()->{
            log.error("Product Not found for id:"+productId);
            return new DataNotFoundException("Product Not found for id:"+productId);
        });
        Long userId = UserUtils.getCurrentUserId();
        System.out.println("#######User####### "+ userId);
        System.out.println("#######Product####### "+ productId);
        ShoppingCart shoppingCart = shoppingCartRepository.findByProduct_ProductIdAndUser_UserId(productId, userId)
                .orElseThrow(() -> new DataNotFoundException("Product not found in cart for userId:"+ userId + ", ProductId:"+productId));
//        if(shoppingCartOptional.isPresent()){
//            ShoppingCart shoppingCart = shoppingCartOptional.get();
            if(qty>shoppingCart.getProduct().getQuantity()){
                log.error("Insufficient Product Quantity for ProductId:"+productId+"Qty Entered:"+qty);
                throw new QuantityInsufficientException("Insufficient Product Quantity");
            } else {
                shoppingCart.setQuantity(qty);
            }
//        } else {
//            log.error("Product not found in cart for userId:"+ userId + ", ProductId:"+productId);
//            throw new DataNotFoundException("Product not found in cart for userId:"+ userId + ", ProductId:"+productId);
//        }
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

    @Override
    public boolean add(Long productId, Integer quantity) {
        Product product = productRepository.findById(productId).orElseThrow(()->{
            log.error("Product Not found for id:"+productId);
            return new DataNotFoundException("Product Not found for id:"+productId);
        });
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByProduct_ProductIdAndUser_UserId(UserUtils.getCurrentUserId(), productId);
        ShoppingCart shoppingCart = shoppingCartOptional.orElse(new ShoppingCart());
        if(shoppingCartOptional.isPresent()){
                // if product is already in cart // then increase qty
            if(quantity+shoppingCart.getQuantity()>shoppingCart.getProduct().getQuantity()){
                // TODO: throw error
                log.error("Insufficient Product Quantity for ProductId:"+productId+"Qty Entered:"+quantity);
                throw new QuantityInsufficientException("Insufficient Product Quantity");
            }else{
                shoppingCart.setQuantity(shoppingCart.getQuantity()+quantity);
            }
        } else {
            //else add new records
            shoppingCart.setProduct(product);
            shoppingCart.setQuantity(quantity);
            //shoppingCart.setUser(UserUtils.getCurrentUser()); // TODO: add User
        }
        shoppingCartRepository.save(shoppingCart);

        return true;

    }

    @Override
    public boolean remove(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()->{
            log.error("Product Not found for id:"+productId);
            return new DataNotFoundException("Product Not found for id:"+productId);
        });
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findByProduct_ProductIdAndUser_UserId(UserUtils.getCurrentUserId(), productId);
        if(shoppingCartOptional.isPresent()){
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            shoppingCartRepository.delete(shoppingCart);
            return true;
        }
        return false;

    }
}
