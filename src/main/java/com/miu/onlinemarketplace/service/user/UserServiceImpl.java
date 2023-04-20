package com.miu.onlinemarketplace.service.user;

import com.miu.onlinemarketplace.common.dto.AddressDto;
import com.miu.onlinemarketplace.common.dto.CardInfoDto;
import com.miu.onlinemarketplace.common.dto.UserCardInfoDto;
import com.miu.onlinemarketplace.entities.Address;
import com.miu.onlinemarketplace.entities.CardInfo;
import com.miu.onlinemarketplace.entities.ShoppingCart;
import com.miu.onlinemarketplace.entities.User;
import com.miu.onlinemarketplace.repository.AddressRepository;
import com.miu.onlinemarketplace.repository.CardInfoRepository;
import com.miu.onlinemarketplace.repository.ShoppingCartRepository;
import com.miu.onlinemarketplace.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CardInfoRepository cardInfoRepository;

    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserCardInfoDto findInfoById(Long id) {

        UserCardInfoDto dto = new UserCardInfoDto();
        Optional<ShoppingCart> shoppingCartOpt = shoppingCartRepository.findByUserUserId(id);
        if(shoppingCartOpt.isPresent()) {
           ShoppingCart shoppingCart = shoppingCartOpt.get();
           dto.setUserId(id);
           dto.setFullName(shoppingCart.getUser().getFullName());
           dto.setQuantity(shoppingCart.getQuantity());
//           dto.setPrice(shoppingCart.getProduct().getP);
           Optional<CardInfo> cardInfoOpt = cardInfoRepository.findByUserUserId(id);
           System.out.println("----------address :::: "+cardInfoOpt.toString());
           cardInfoOpt.ifPresent(cardInfo -> dto.setCardInfoDto(modelMapper.map(cardInfo, CardInfoDto.class)));

           Optional<Address> addressOpt = addressRepository.findByUser(shoppingCart.getUser());
           addressOpt.ifPresent(address -> dto.setAddressDto(modelMapper.map(address, AddressDto.class)));
           System.out.println(addressOpt.toString());
        }
        return dto;
    }
}
