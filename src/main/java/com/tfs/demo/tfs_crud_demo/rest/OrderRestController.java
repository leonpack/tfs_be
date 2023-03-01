package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.OrderDetailRepository;
import com.tfs.demo.tfs_crud_demo.dto.RefundDTO;
import com.tfs.demo.tfs_crud_demo.entity.*;
import com.tfs.demo.tfs_crud_demo.library.vn.zalopay.crypto.HMACUtil;
import com.tfs.demo.tfs_crud_demo.service.EventService;
import com.tfs.demo.tfs_crud_demo.service.FoodService;
import com.tfs.demo.tfs_crud_demo.service.OrderService;
import com.tfs.demo.tfs_crud_demo.service.PromotionService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderRestController {

    //config for implementing ZalopayAPI
    private static final Map<String, String> config = new HashMap<String, String>(){{
        put("appid", "554");
        put("key1", "8NdU5pG5R2spGHGhyO99HN1OhD8IQJBn");
        put("key2", "uUfsWgfLkRLzq6W2uNXTCxrfxs51auny");
        put("endpoint", "https://sandbox.zalopay.com.vn/v001/tpe/createorder");
        put("endpointstatus","https://sandbox.zalopay.com.vn/v001/tpe/getstatusbyapptransid");
        put("refundendpoint","https://sandbox.zalopay.com.vn/v001/tpe/partialrefund");
        put("refundstatusendpoint","https://sandbox.zalopay.com.vn/v001/tpe/getpartialrefundstatus");
    }};

    //get current time in GMT+7 to sync ordertime with ZaloServer
    public static final String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private OrderService orderService;
    private PromotionService promotionService;
    private FoodService foodService;
    private final OrderDetailRepository orderDetailRepository;

    private EventService eventService;

    @Autowired
    public OrderRestController(OrderService theOrderService,
                               OrderDetailRepository orderDetailRepository,
                               PromotionService thePromotionService,
                               FoodService theFoodService,
                               EventService theEventService){
        orderService = theOrderService;
        this.orderDetailRepository = orderDetailRepository;
        promotionService = thePromotionService;
        foodService = theFoodService;
        eventService = theEventService;
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
    @PostMapping("/orders")
    public Order addNewOrder(@RequestBody Order order) throws ParseException {

        if(order.getPromotionCode()!=null){
            Promotion promotion = promotionService.getPromotionByCode(order.getPromotionCode());
            //        check if promotion is valid to use
            Event event = eventService.getEventById(promotion.getEventId());
            Date today = sdf.parse(LocalDate.now().toString());
            if(!today.after(event.getFromDate()) && today.before(event.getToDate())){
                throw new RuntimeException("This promotion can't be use in this time!");
            }
            //check promotion code availability
            if(promotion.isStatus()==false){
                throw new RuntimeException("Promotion" +order.getPromotionCode() + " is unusable!");
            }
        }

        if(orderService.CheckDuplicateOrderId(order.getId())){
            throw new RuntimeException("Order with id -" +order.getId() + " already exist, please try again!");
        }

        orderService.saveOrder(order);

//        auto increase num of purchase if order has been successfully saved
        for(OrderDetail item : order.getItemList()){
            Food food = foodService.getFoodById(item.getId());
            food.setPurchaseNum(food.getPurchaseNum()+item.getQuantity());
        }

        return order;
    }

    //addNewOrder with ZaloPay intergrated
    @PostMapping("/orders/zaloPay")
    public Map<String, Object> createZaloPayOrder(@RequestBody Order orderBody) throws IOException {
//        Map<String, Object> normalReturn = new HashMap<>();
//        normalReturn.put("message","Tạo đơn thành công");

        //from now on is for implementing ZaloAPI
            final Map embeddata = new HashMap(){{
                put("merchantinfo", "embeddata123");
            }};

            final Map[] item = {
                    new HashMap(){{
                        put("itemid", orderBody.getId());
                        put("itemname", "Đơn hàng " + orderBody.getId());
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
            Map<String, Object> returnValue = new HashMap<>();
            returnValue.put("apptransid", order.get("apptransid"));
            returnValue.put("zptranstoken", result.get("zptranstoken"));
            returnValue.put("zaloUrl", result.get("orderurl"));

//            return "apptransid: " + order.get("apptransid")+ " - zptranstoken: " +order.get("zptranstoken") + " - zaloUrl: " + result.get("orderurl").toString() ;
            return returnValue;
    }

    @PutMapping("/orders")
    public Order updateOrder(@RequestBody Order order) throws ParseException {
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

        //TODO cho phép khách huỷ tối đa trước 1 ngày giao hàng
        Date today = sdf.parse(LocalDate.now().toString());

        order.setItemList(order.getItemList());
        orderService.saveOrder(order);
        return order;
    }


    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable int orderId){
        orderService.deleteOrder(orderId);
        return "Deleted order " + orderId;
    }

    @GetMapping("/orders/checkPayment/{appstranId}")
    public Map<String, Object> checkZaloPaymentStatus(@PathVariable String appstranId) throws URISyntaxException, IOException {
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

        Map<String, Object> returnMessage = new HashMap<>();
        returnMessage.put("returnCode",result.get("returncode"));
        returnMessage.put("returnMessage",result.get("returnmessage"));
        returnMessage.put("isProcessing?",result.get("isprocessing"));
        returnMessage.put("zptransid",result.get("zptransid"));

        return returnMessage;
    }

    @GetMapping("/orders/refund")
    public Map<String, Object> refundOrder(@RequestBody RefundDTO refundDTO) throws IOException {
        String appid = config.get("appid");
        Random rand = new Random();
        long timestamp = System.currentTimeMillis(); // miliseconds
        String uid = timestamp + "" + (111 + rand.nextInt(888)); // unique id

        Map<String, Object> order = new HashMap<String, Object>(){{
            put("appid", appid);
            put("zptransid", refundDTO.getZptransid());
            put("mrefundid", getCurrentTimeString("yyMMdd") +"_"+ appid +"_"+uid);
            put("timestamp", timestamp);
            put("amount", refundDTO.getAmount());
            put("description", "ZaloPay Intergration Demo");
        }};

        // appid|zptransid|amount|description|timestamp
        String data = order.get("appid") +"|"+ order.get("zptransid") +"|"+ order.get("amount")
                +"|"+ order.get("description") +"|"+ order.get("timestamp");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.get("refundendpoint"));

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

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

        Map<String, Object> returnMessage = new HashMap<>();
        returnMessage.put("returncode",result.get("returncode"));
        returnMessage.put("returnMessage",result.get("returnmessage"));
        returnMessage.put("refundId",result.get("refundid"));
        returnMessage.put("mrefundid",order.get("mrefundid"));

        return returnMessage;
    }

    @GetMapping("/orders/refundStatus/{id}")
    public Map<String, Object> getRefundStatus(@PathVariable String id) throws URISyntaxException, IOException {
        String mrefundid = id;
        String timestamp = Long.toString(System.currentTimeMillis()); // miliseconds
        String data = config.get("appid") +"|"+ mrefundid  +"|"+ timestamp; // appid|mrefundid|timestamp
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", config.get("appid")));
        params.add(new BasicNameValuePair("mrefundid", mrefundid));
        params.add(new BasicNameValuePair("timestamp", timestamp));
        params.add(new BasicNameValuePair("mac", mac));

        URIBuilder uri = new URIBuilder(config.get("refundstatusendpoint"));
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

        Map<String, Object> returnMessage = new HashMap<>();
        returnMessage.put("returnCode",result.get("returncode"));
        returnMessage.put("returnMessage",result.get("returnmessage"));

        return returnMessage;
    }

    @PutMapping("/orders/{orderId}")
    public Order denyOrder(@PathVariable int orderId){
        Order order = orderService.getOrderById(orderId);
        for(OrderDetail item : order.getItemList()){
            if(item.getPartyId()!=null || !item.getPartyId().toString().isEmpty()){
                LocalDate today = LocalDate.parse(LocalDate.now().toString());
                LocalDate deliDay = LocalDate.parse(order.getDeliveryDate().toString());
                long diffDays= ChronoUnit.DAYS.between(today,deliDay);
                if(diffDays>1){
                    order.setStatus("DENY");
                    orderService.saveOrder(order);
                } else {
                    throw new RuntimeException("Order can't be deny right now");
                }
            } else {
                order.setStatus("DENY");
                orderService.saveOrder(order);
            }
        }
        return order;
    }

}
