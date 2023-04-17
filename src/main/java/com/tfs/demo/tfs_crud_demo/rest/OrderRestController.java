package com.tfs.demo.tfs_crud_demo.rest;

import com.tfs.demo.tfs_crud_demo.dao.OrderComboDetailRepository;
import com.tfs.demo.tfs_crud_demo.dto.*;
import com.tfs.demo.tfs_crud_demo.entity.*;
import com.tfs.demo.tfs_crud_demo.library.vn.zalopay.crypto.HMACUtil;
import com.tfs.demo.tfs_crud_demo.service.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class OrderRestController {

    //config for implementing ZalopayAPI
    private static final Map<String, String> config = new HashMap<String, String>(){{
        //version 1 key
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

    private final OrderService orderService;
    private final PromotionService promotionService;
    private final FoodService foodService;
    private final StaffService staffService;
    private final EventService eventService;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    private final NotificationService notificationService;
    private final CartService cartService;
    private final ZalopayDetailService zalopayDetailService;
    private final ComboService comboService;
    private final OrderComboDetailRepository orderComboDetailRepository;

    @Autowired
    public OrderRestController(OrderService theOrderService,
                               PromotionService thePromotionService,
                               FoodService theFoodService,
                               EventService theEventService,
                               StaffService theStaffService,
                               RestaurantService theRestaurantService,
                               CustomerService theCustomerService,
                               NotificationService theNotificationService,
                               CartService theCartService,
                               ZalopayDetailService theZalopayDetailService,
                               ComboService theComboService,
                               OrderComboDetailRepository orderComboDetailRepository){
        orderService = theOrderService;
        promotionService = thePromotionService;
        foodService = theFoodService;
        eventService = theEventService;
        staffService = theStaffService;
        restaurantService = theRestaurantService;
        customerService = theCustomerService;
        notificationService = theNotificationService;
        cartService = theCartService;
        zalopayDetailService = theZalopayDetailService;
        comboService = theComboService;
        this.orderComboDetailRepository = orderComboDetailRepository;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrderById(@PathVariable int orderId){
        return orderService.getOrderById(orderId);
    }

    @GetMapping("/orders/customer/{cusId}")
    public List<Order> getAllOrderByCustomer(@PathVariable int cusId){
        return orderService.getAllOrderByCustomerId(cusId);
    }

    @GetMapping("/orders/staff/{staffId}")
    public List<Order> getAllOrderByStaff(@PathVariable int staffId){
        return orderService.getAllOrderByStaffId(staffId);
    }

    @GetMapping("/orders/restaurant/{resId}")
    public List<Order> getAllOrderByRestaurant(@PathVariable int resId){
        return orderService.getAllOrderByRestaurantId(resId);
    }

    //original working addNewOrder method without ZaloAPI
    @PostMapping("/orders")
    public Order addNewOrder(@RequestBody Order order) throws ParseException {

        //remove party from cart before saving new order
        Customer customer = customerService.getCustomerById(order.getCustomerId());
        if(customer.getCart()!=null){
            Cart cart = customer.getCart();
            cart.setParty(null);
            cart.setNumberCart(0);
            cart.getCartItems().removeAll(cart.getCartItems());
            cart.setTotalPrice((double) 0);
            cart.getComboList().removeAll(cart.getComboList());
            cartService.saveCart(cart);
        }

        //auto add customer information if json is null
        if(order.getCustomerName()==null){
            order.setCustomerName(customer.getCustomerName());
        }

        if(order.getCustomerPhoneNumber()==null){
            order.setCustomerPhoneNumber(customer.getTheAccount().getPhoneNumber());
        }

        //check valid promotion
        if(order.getPromotionId()!=null){
            Promotion promotion = promotionService.getPromotionById(order.getPromotionId());
            //        check if promotion is valid to use
            Event event = eventService.getEventById(promotion.getEventId());
            Date today = sdf.parse(LocalDate.now().toString());
            if(!today.after(event.getFromDate()) && today.before(event.getToDate())){
                throw new RuntimeException("This promotion can't be use in this time!");
            }
            //check promotion code availability
            if(!promotion.getStatus()){
                throw new RuntimeException("Promotion" +order.getPromotionId() + " is unusable!");
            }
        }

        if(orderService.CheckDuplicateOrderId(order.getId())){
            throw new RuntimeException("Order with id - " +order.getId() + " already exist, please try again!");
        }

        //check if the party is in another order
        if(order.getParty()!=null){
            Order order1 = orderService.getByParty(order.getParty());
            if( order1!=null && !order.equals(order1) ) {
                throw new RuntimeException("This party is already belong to order " +order1.getId());
            }
        }

        //try catch restaurant null value
        if(order.getRestaurantId()==null || order.getRestaurantId().toString().isEmpty()){
            throw new RuntimeException("Restaurant Id can't be null");
        }

        //auto assign order staff
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurantId());
        if(order.getStatus().equals("accept") && (order.getStaffId()==null || order.getStaffId().toString().isEmpty())){
//            for(Staff item: restaurant.getStaffList()){
//                if(item.getStaffActivityStatus().equals("available")){
//                    order.setStaffId(item.getStaffId());
//                    Notification staffNoti = new Notification("Bạn có một đơn hàng mới cần xử lý, mã đơn hàng là " +order.getId(),item.getTheAccountForStaff().getAccountId());
//                    notificationService.save(staffNoti);
//                    staffService.getStaffById(item.getStaffId()).setStaffActivityStatus("busy");
//                }
//            }
//            if(order.getStaffId()==null || order.getStaffId().toString().isEmpty()){
                List<Staff> availableStaff = new ArrayList<>();
                for(Staff item: restaurant.getStaffList()){
                    if(item.getTheAccountForStaff().getRoleId().toString().equals("4")){
                        availableStaff.add(item);
                    }
                }
                if(availableStaff.isEmpty()){
                    throw new RuntimeException("This restaurant don't have any staff to proceed this order");
                }
                Random rd = new Random();
                int randomDude = rd.nextInt(availableStaff.size());
                order.setStaffId(availableStaff.get(randomDude).getStaffId());
                Notification staffNoti = new Notification("Bạn có một đơn hàng mới cần xử lý, mã đơn hàng là " +order.getId()
                        , restaurant.getStaffList().get(randomDude).getTheAccountForStaff().getAccountId());
                notificationService.save(staffNoti);
//                staffService.getStaffById(restaurant.getStaffList().get(randomDude).getStaffId()).setStaffActivityStatus("busy");
//            }
        }

        String managerId = "";
        for(Staff item: restaurant.getStaffList()){
            if(item.getTheAccountForStaff().getRoleId().toString().equals("3")){
                managerId = item.getTheAccountForStaff().getAccountId();
            }
        }

        orderService.saveOrder(order);
        Notification userNoti = new Notification("Đơn hàng " +order.getId() + " vừa được tạo thành công", customerService.getCustomerById(order.getCustomerId()).getTheAccount().getAccountId());
        Notification managerNoti = new Notification("Khách hàng " + customerService.getCustomerById(order.getCustomerId()).getCustomerName() + " vừa đặt đơn hàng " +order.getId(), managerId);
        notificationService.save(userNoti);
        notificationService.save(managerNoti);

        return order;
    }

    @PostMapping("/orders/forstaff")
    public Order createOrderForStaff(@RequestBody Order order){

        //get list to get the latest item id + 1 to generate new order id;
        List<Order> additionalList = orderService.getAllOrders();
        if(order.getId()==0){
            order.setId(additionalList.get(additionalList.size()-1).getId()+1);
        }
        if(order.getDeliveryAddress()==null || order.getDeliveryAddress().isBlank()){
            order.setDeliveryAddress(restaurantService.getRestaurantById(order.getRestaurantId()).getRestaurantName());
        }
        if(order.getNote()==null || order.getNote().isBlank()){
            order.setNote("");
        }
        if(order.getReason()==null || order.getReason().isBlank()){
            order.setReason("");
        }
        order.setFeedbackStatus(false);
        if(order.getStatus()==null || order.getStatus().isBlank()){
            order.setStatus("done");
        }
        if(order.getDeliveryMethod()==null || order.getDeliveryMethod().isBlank()){
            order.setDeliveryMethod("takeaway");
        }
        if(order.getItemList()!=null || !order.getItemList().isEmpty()){
            Integer quantity = 0;
            Double price = (double) 0;
            List<OrderDetail> itemList = order.getItemList();
            for(OrderDetail item: itemList){
                quantity += item.getQuantity();
                price += item.getSubTotal();
            }
            order.setTotalPrice(price);
            order.setTotalQuantity(quantity);
        }
        order.setDeliveryDate(LocalDateTime.now());
        order.setReceiveTime(LocalDateTime.now());
        Order order2 = orderService.saveOrder(order);

        for(OrderDetail item: order2.getItemList()){
            Food food = foodService.getFoodById(item.getId());
            food.setPurchaseNum(food.getPurchaseNum()+item.getQuantity());
            foodService.saveFood(food);
        }
        return order2;

    }

    //addNewOrder with ZaloPay intergrated for mobile app
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
            if(zalopayDetailService.getDetailByOrderId(orderBody.getId())==null){
                zalopayDetailService.save(new ZalopayDetail(orderBody.getId(),order.get("apptransid").toString(), "0"));
            }
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

        if(order.getStatus()==null){
            order.setStatus(theTempOrder.getStatus());
        }

        if(order.getNote()==null){
            order.setNote(theTempOrder.getNote());
        }

        if(order.getReason()==null){
            order.setReason(theTempOrder.getReason());
        }

        if(order.getPromotionId()==null){
            order.setPromotionId(theTempOrder.getPromotionId());
        }

        if(order.getCustomerName()==null){
            order.setCustomerName(theTempOrder.getCustomerName());
        }

        if(order.getCustomerPhoneNumber()==null){
            order.setCustomerPhoneNumber(theTempOrder.getCustomerPhoneNumber());
        }

        if(order.getItemList()==null || order.getItemList().isEmpty()){
            order.setItemList(theTempOrder.getItemList());
        }

        if(order.getServiceList()==null || order.getServiceList().isEmpty()){
            order.setServiceList(theTempOrder.getServiceList());
        }

        if(order.getComboList()==null || order.getComboList().isEmpty()){
            order.setComboList(theTempOrder.getComboList());
        }

        orderService.saveOrder(order);
        return order;
    }

    @PutMapping("/orders/status")
    public Order updateOrderStatus(@RequestBody AssignOrderDTO assignOrderDTO){

        if(assignOrderDTO.getReason()==null || assignOrderDTO.getReason().isBlank()){
            assignOrderDTO.setReason("");
        }

        Order order = orderService.getOrderById(assignOrderDTO.getOrderId());
        Staff staff = staffService.getStaffById(assignOrderDTO.getStaffId());
        Restaurant restaurant = restaurantService.getRestaurantById(order.getRestaurantId());
        String managerId = "";
        for(Staff item: restaurant.getStaffList()){
            if(item.getTheAccountForStaff().getRoleId().toString().equals("3")){
                managerId = item.getTheAccountForStaff().getAccountId();
            }
        }

//        if(restaurantService.getRestaurantById(order.getRestaurantId()).getStaffList().stream().anyMatch(s -> s.equals(staff))){
//            System.out.println("good");
//        } else {
//            throw new RuntimeException("This staff is not belong to this restaurant!");
//        }

        if(assignOrderDTO.getStatus().toLowerCase().equals("deny")){
            order.setStatus(assignOrderDTO.getStatus());
            order.setStaffId(null);
            order.setReason(assignOrderDTO.getReason());
            orderService.saveOrder(order);

            //create notification to in-form user about denied's order
            Notification userNoti = new Notification("Đơn hàng " +order.getId() + " đã bị huỷ", customerService.getCustomerById(order.getCustomerId()).getTheAccount().getAccountId());
            notificationService.save(userNoti);

            return order;
        }
        else if(assignOrderDTO.getStatus().toLowerCase().equals("accept")){
            order.setStatus(assignOrderDTO.getStatus());
            order.setStaffId(assignOrderDTO.getStaffId());

            orderService.saveOrder(order);

            //create notification for customer to inform that their order has been accepted
            Notification userNoti = new Notification("Đơn hàng " +order.getId() + " của quý khách đã được xác nhận", customerService.getCustomerById(order.getCustomerId()).getTheAccount().getAccountId());
            notificationService.save(userNoti);

            //create notification for staff to inform that an order has been assigned for them
            Notification staffNoti = new Notification("Bạn có một đơn hàng mới cần xử lý, mã đơn hàng là " +order.getId(), staff.getTheAccountForStaff().getAccountId());
            notificationService.save(staffNoti);

            return order;
        }
        else if(assignOrderDTO.getStatus().toLowerCase().equals("delivery")){
            order.setStatus(assignOrderDTO.getStatus());
            order.setDeliveryDate(LocalDateTime.now());
            order.setStaffId(staff.getStaffId());
            orderService.saveOrder(order);

            //create notification for customer to inform that their order are being deliver
            Notification userNoti = new Notification("Đơn hàng " + order.getId() + " của bạn đang được giao đến bạn bởi nhân viên " +staff.getStaffFullName()
                    , customerService.getCustomerById(order.getCustomerId()).getTheAccount().getAccountId());
            notificationService.save(userNoti);

            //create notification for manager to inform that a staff are delivery an order to customer
            Notification managerNoti = new Notification("Đơn hàng " + order.getId() + " đang được giao bởi nhân viên " +staff.getStaffFullName(), managerId);
            notificationService.save(managerNoti);

            return order;
        }
        else if(assignOrderDTO.getStatus().toLowerCase().equals("done")){
            order.setReceiveTime(LocalDateTime.now());
            order.setStatus(assignOrderDTO.getStatus());
            orderService.saveOrder(order);

            //create notification for customer to inform that their order has been finished
            Notification userNoti = new Notification("Đơn hàng " +order.getId() + " đã được giao thành công, chúc bạn ngon miệng"
                    , customerService.getCustomerById(order.getCustomerId()).getTheAccount().getAccountId());
            notificationService.save(userNoti);

            Notification managerNoti = new Notification("Đơn hàng " +order.getId() + " đã được giao thành công", managerId);
            notificationService.save(managerNoti);

            for(OrderDetail item: order.getItemList()){
                Food food = foodService.getFoodById(item.getId());
                food.setPurchaseNum(food.getPurchaseNum()+item.getQuantity());
                foodService.saveFood(food);
            }

            return order;
        }
        else
        {
            order.setStatus(assignOrderDTO.getStatus());
            order.setStaffId(assignOrderDTO.getStaffId());
            orderService.saveOrder(order);
            return order;
        }
    }

    @PostMapping("/orders/changerestaurant")
    public ResponseEntity<String> changeRestaurantForOrder(@RequestBody ChangeRestaurantDTO restaurantDTO){
        Order order = orderService.getOrderById(restaurantDTO.getOrderId());
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantDTO.getRestaurantId());
        if(!order.getStatus().equals("pending") || !order.getStatus().equals("accept")){
            throw new RuntimeException("This order is already in process and can't be move to another restaurant");
        }
        List<Staff> staffList = restaurant.getStaffList();
        for(Staff item: staffList){
            if(item.getTheAccountForStaff().getRoleId().toString().equals("3")){
                order.setRestaurantId(restaurantDTO.getRestaurantId());
                order.setStaffId(null);
                order.setStatus("pending");
                orderService.saveOrder(order);
                Notification noti = new Notification("Đơn hàng " +restaurantDTO.getOrderId() + " vừa được chuyển sang nhà hàng của bạn", item.getTheAccountForStaff().getAccountId());
                Notification noti2 = new Notification("Đơn hàng " +restaurantDTO.getOrderId() + " của bạn vùa được chuyển sang cho nhà hàng "
                        +restaurant.getRestaurantName() + " xử lý", customerService.getCustomerById(order.getCustomerId()).getTheAccount().getAccountId());
                notificationService.save(noti);
                notificationService.save(noti2);
                break;
            }
        }
        return ResponseEntity.ok("Đơn hàng được chuyển nhà hàng thành công");
    }

    @DeleteMapping("/orders/{orderId}")
    public String deleteOrder(@PathVariable int orderId){
        orderService.deleteOrder(orderId);
        return "Deleted order " + orderId;
    }

    @GetMapping("/orders/checkPayment/{appstranId}")
    public Map<String, Object> checkZaloPaymentStatus(@PathVariable String appstranId) throws URISyntaxException, IOException {
        String data = config.get("appid") +"|"+ appstranId  +"|"+ config.get("key1"); // appid|apptransid|key1
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", config.get("appid")));
        params.add(new BasicNameValuePair("apptransid", appstranId));
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

    //new method for checking zalopayPayment
    @GetMapping("/orders/checkZalopayPayment/{orderId}")
    public Map<String, Object> checkZaloPayPaymentStatusVersion2(@PathVariable int orderId) throws URISyntaxException, IOException {
        ZalopayDetail zalopayDetail = zalopayDetailService.getDetailByOrderId(orderId);
        if(zalopayDetail==null){
            throw new RuntimeException("This order is not found/ not using zalopay payment method");
        }

        String apptransid = zalopayDetail.getApptransid();
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

        Long value = (Long) result.get("zptransid");

        Map<String, Object> returnMessage = new HashMap<>();
        returnMessage.put("returnCode",result.get("returncode"));
        returnMessage.put("returnMessage",result.get("returnmessage"));
        returnMessage.put("isProcessing?",result.get("isprocessing"));
        returnMessage.put("zptransid",result.get("zptransid"));
        zalopayDetail.setZptransid(value.toString());
        zalopayDetailService.save(zalopayDetail);

        return returnMessage;
    }

    @PostMapping("/orders/refund")
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

    //new method to create refund from using zalopay payment method
    @PostMapping("/orders/refundZalopay")
    public Map<String, Object> refundOrderVersion2(@RequestBody NewRefundDTO refundDTO) throws IOException {

        ZalopayDetail zalopayDetail = zalopayDetailService.getDetailByOrderId(refundDTO.getOrderId());

        if(zalopayDetail==null){
            throw new RuntimeException("This order is not capable of refund");
        }

        String appid = config.get("appid");
        Random rand = new Random();
        long timestamp = System.currentTimeMillis(); // miliseconds
        String uid = timestamp + "" + (111 + rand.nextInt(888)); // unique id

        Map<String, Object> order = new HashMap<String, Object>(){{
            put("appid", appid);
            put("zptransid", zalopayDetail.getZptransid());
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

    @GetMapping("/orders/refundStatus/{mrefundid}")
    public Map<String, Object> getRefundStatus(@PathVariable String mrefundid) throws URISyntaxException, IOException {
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

    @PutMapping("/orders/feedback/status")
    public ResponseEntity<String> updateOrderFeedback(@RequestBody FeedbackStatusDTO feedbackStatusDTO){
        Order order = orderService.getOrderById(feedbackStatusDTO.getOrderId());
        order.setFeedbackStatus(feedbackStatusDTO.getFeedbackStatus());
        orderService.saveOrder(order);
        return ResponseEntity.ok("Lưu feedback status của order thành công");
    }

    @PutMapping("/orders/note")
    public ResponseEntity<String> setNoteForOrder(@RequestBody OrderNoteDTO noteDTO){
        Order order = orderService.getOrderById(noteDTO.getOrderId());
        order.setNote(noteDTO.getNote());
        orderService.saveOrder(order);
        return ResponseEntity.ok("Lưu note thành công");
    }

}
