@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testCreateOrderEndpoint_ShouldReturn201() throws Exception {
        OrderRequest request = new OrderRequest("Latte", "M");
        Order response = new Order(1L, "Latte", "M");

        when(orderService.createOrder(any())).thenReturn(response);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Latte\", \"size\":\"M\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Latte"));
    }

    @Test
    void testGetOrdersEndpoint_ShouldReturnList() throws Exception {
        List<Order> orders = List.of(new Order(1L, "Latte", "M"));
        when(orderService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
