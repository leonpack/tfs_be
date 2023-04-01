package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.dao.ZalopayDetailRepository;
import com.tfs.demo.tfs_crud_demo.entity.ZalopayDetail;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ZalopayDetailServiceImplementation implements ZalopayDetailService{

    private ZalopayDetailRepository zalopayDetailRepository;

    public ZalopayDetailServiceImplementation(ZalopayDetailRepository theZalopayDetailRepository){
        zalopayDetailRepository = theZalopayDetailRepository;
    }

    @Override
    public ZalopayDetail getDetailByOrderId(int orderId) {
        ZalopayDetail zalopayDetail = zalopayDetailRepository.getZalopayDetailByOrderId(orderId);
        if(zalopayDetail==null){
            throw new RuntimeException("This order is not using zalopay payment method");
        }
        return zalopayDetail;
    }

    @Override
    public ZalopayDetail save(ZalopayDetail zalopayDetail) {
        ZalopayDetail zalopayDetail1 = zalopayDetailRepository.save(zalopayDetail);
        return zalopayDetail1;
    }

}
