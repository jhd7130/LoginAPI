package me.flab.loginjoinAPI.service;

import me.flab.loginjoinAPI.data.CommonResponse;
import me.flab.loginjoinAPI.data.ListResponse;
import me.flab.loginjoinAPI.data.SingleResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponseProviderService {
    public <T> SingleResponse<T> getSingleResponse(T data){
        SingleResponse singleResponse = new SingleResponse();
        singleResponse.data = data;
        if(data != null){
            setSuccessResponse(singleResponse);
            return singleResponse;
        }
        setFailResponse(singleResponse);
        return singleResponse;
    }

    public <T> ListResponse<T> getListResponse(List<T> dataList){
        ListResponse listResponse = new ListResponse();
        listResponse.dataList = dataList;

        if(dataList != null){
            setSuccessResponse(listResponse);
            return listResponse;
        }
        setFailResponse(listResponse);
        return listResponse;
    }

    void setSuccessResponse(CommonResponse response){
        response.code = 200;
        response.success = true;
        response.message ="SUCCESS";
    }

    void setFailResponse(CommonResponse response){
        response.code = 400;
        response.success = false;
        response.message = "FAIL";
    }


}
