package com.tfs.demo.tfs_crud_demo.service;

import com.tfs.demo.tfs_crud_demo.entity.ZalopayDetail;

public interface ZalopayDetailService {

    ZalopayDetail getDetailByOrderId(int orderId);

    ZalopayDetail save(ZalopayDetail zalopayDetail);

}
