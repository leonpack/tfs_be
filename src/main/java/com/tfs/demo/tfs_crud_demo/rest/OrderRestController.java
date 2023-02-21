package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.entity.Order;
import com.tfs.demo.tfs_crud_demo.library.vn.zalopay.crypto.HMACUtil;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class OrderRestController {

    //config for implementing ZalopayAPI
    private static final Map<String, String> config = new HashMap<String, String>(){{
        put("appid", "554");
        put("key1", "8NdU5pG5R2spGHGhyO99HN1OhD8IQJBn");
        put("key2", "uUfsWgfLkRLzq6W2uNXTCxrfxs51auny");
        put("endpoint", "https://sandbox.zalopay.com.vn/v001/tpe/createorder");
        put("endpointstatus","https://sandbox.zalopay.com.vn/v001/tpe/getstatusbyapptransid");
    }};

    //get current time in GMT+7 to sync ordertime with ZaloServer
    public static final String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    private OrderService orderService;

    @Autowired
    public OrderRestController(OrderService theOrderService){
        orderService = theOrderService;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable int orderId){
        Order order = orderService.getOrderById(orderId);
        return order;
    }

    @GetMapping("/orders/customer/{cusId}")
    public List<Order> getAllOrderByCustomer(@PathVariable int cusId){
        return orderService.getAllOrderByCustomerId(cusId);
    }

    @GetMapping("/orders/staff/{staffId}")
    public List<Order> getAllOrderByStaff(@PathVariable String staffId){
        return orderService.getAllOrderByStaffId(staffId);
    }

    @GetMapping("/orders/restaurant/{resId}")
    public List<Order> getAllOrderByRestaurant(@PathVariable String resId){
        return orderService.getAllOrderByRestaurantId(resId);
    }

    //original working addNewOrder method without ZaloAPI
//    @PostMapping("/orders")
//    public Order addNewOrder(@RequestBody Order order){
//        orderService.saveOrder(order);
//        return order;
//    }

    //addNewOrder with ZaloPay intergrated
    @PostMapping("/orders")
    public String addNewOrder(@RequestBody Order orderBody) throws IOException {
        orderService.saveOrder(orderBody);

        //from now on is for implementing ZaloAPI
        if(orderBody.getPaymentMethod().equals("ZaloPay")){
            final Map embeddata = new HashMap(){{
                put("merchantinfo", "embeddata123");
            }};

            final Map[] item = {
                    new HashMap(){{
                        put("itemid", orderBody.getId());
                        put("itemname", "Đơn hàng " +orderBody.getId());
                        put("itemprice", orderBody.getTotalPrice().longValue());
                        put("itemquantity", orderBody.getTotalQuantity());
                    }}
            };

            Map<String, Object> order = new HashMap<String, Object>(){{
                put("appid", config.get("appid"));
                put("apptransid", getCurrentTimeString("yyMMdd") +"_"+ orderBody.getId()); // mã giao dich có định dạng yyMMdd_xxxx
                put("apptime", System.currentTimeMillis()); // miliseconds
                put("appuser", "TFSystem");
                put("amount", orderBody.getTotalPrice().longValue());
                put("description", "ZaloPay Intergration Demo");
                put("bankcode", "zalopayapp");
                put("item", new JSONObject(item).toString());
                put("embeddata", new JSONObject(embeddata).toString());
            }};

            // appid +”|”+ apptransid +”|”+ appuser +”|”+ amount +"|" + apptime +”|”+ embeddata +"|" +item
            String data = order.get("appid") +"|"+ order.get("apptransid") +"|"+ order.get("appuser") +"|"+ order.get("amount")
                    +"|"+ order.get("apptime") +"|"+ order.get("embeddata") +"|"+ order.get("item");
            order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(config.get("endpoint"));

            List<NameValuePair> params = new ArrayList<>();
            for (Map.Entry<String, Object> e : order.entrySet()) {
                params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
            }

            // Content-Type: application/x-www-form-urlencoded
            post.setEntity(new UrlEncodedFormEntity(params));

            CloseableHttpResponse res = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
            StringBuilder resultJsonStr = new StringBuilder();
            String line;

            while ((line = rd.readLine()) != null) {
                resultJsonStr.append(line);
            }

            JSONObject result = new JSONObject(resultJsonStr.toString());
            for (String key : result.keySet()) {
                System.out.format("%s = %s\n", key, result.get(key));
            }
            System.out.println(order.get("apptransid"));

            return "apptransid: " + order.get("apptransid")+ " - zaloUrl: " + result.get("orderurl").toString() ;
        }
        else
            return "Tạo đơn hàng thành công";
    }

    @PutMapping("/orders")
    public Order updateOrder(@RequestBody Order order){
        Order theTempOrder = orderService.getOrderById(order.getId());

        //try catch null input for update
        if(order.getCustomerId()==null){
            order.setCustomerId(theTempOrder.getCustomerId());
        }

        if(order.getRestaurantId()==null){
            order.setRestaurantId(theTempOrder.getRestaurantId());
        }

        if(order.getStaffId()==null){
            order.setStaffId(theTempOrder.getStaffId());
        }

        if(order.getItemList()==null){
            order.setItemList(theTempOrder.getItemList());
        }

        if(order.getOrderDate()==null){
            order.setOrderDate(theTempOrder.getOrderDate());
        }

        if(order.getDeliveryAddress()==null){
            order.setDeliveryAddress(theTempOrder.getDeliveryAddress());
        }

        if(order.getPaymentMethod()==null){
            order.setPaymentMethod(theTempOrder.getPaymentMethod());
        }

        if(order.getReceiveTime()==null){
            order.setReceiveTime(theTempOrder.getReceiveTime());
        }

        if(order.getDeliveryDate()==null){
            order.setDeliveryDate(theTempOrder.getDeliveryDate());
        }

        if(order.getTotalPrice()==null){
            order.setTotalPrice(theTempOrder.getTotalPrice());
        }

        if(order.getTotalQuantity()==null){
            order.setTotalQuantity(theTempOrder.getTotalQuantity());
        }

        orderService.saveOrder(order);
        return order;
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable int orderId){
        orderService.deleteOrder(orderId);
        return "Deleted order " + orderId;
    }

    @GetMapping("/orders/checkPayment/{appstranId}")
    public String checkZaloPaymentStatus(@PathVariable String appstranId) throws URISyntaxException, IOException {
        String apptransid = appstranId;
        String data = config.get("appid") +"|"+ apptransid  +"|"+ config.get("key1"); // appid|apptransid|key1
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", config.get("appid")));
        params.add(new BasicNameValuePair("apptransid", apptransid));
        params.add(new BasicNameValuePair("mac", mac));

        URIBuilder uri = new URIBuilder(config.get("endpointstatus"));
        uri.addParameters(params);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri.build());

        CloseableHttpResponse res = client.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        for (String key : result.keySet()) {
            System.out.format("%s = %s\n", key, result.get(key));
        }
        if(result.get("returncode").toString().equals("1") && result.get("isprocessing").toString().equals("false")){
            return "Giao dịch thành công";
        }
        else if(result.get("returncode").toString().equals("-49") || result.get("isprocessing").toString().equals("true")){
            return "Đơn hàng chưa được thanh toán hoặc đang xử lý";
        }
        else if(result.get("returncode").toString().equals("-117")){
            return "Người dùng đã nhập sai mật khẩu khi thanh toán trên ZaloPay App";
        }

        return "Checking";

    }

}
