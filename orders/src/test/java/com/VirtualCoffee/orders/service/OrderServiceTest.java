@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private BeverageClient beverageClient; // simula la API de bebidas

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder_WhenBeverageAvailable_ShouldCreateOrder() {
        // Arrange
        OrderRequest request = new OrderRequest("Latte", "M");
        when(beverageClient.isBeverageAvailable("Latte", "M")).thenReturn(true);

        Order savedOrder = new Order(1L, "Latte", "M");
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        Order result = orderService.createOrder(request);

        // Assert
        assertNotNull(result);
        assertEquals("Latte", result.getName());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testCreateOrder_WhenBeverageNotAvailable_ShouldThrowException() {
        OrderRequest request = new OrderRequest("Mocha", "L");

        when(beverageClient.isBeverageAvailable("Mocha", "L")).thenReturn(false);

        assertThrows(BeverageUnavailableException.class, () -> {
            orderService.createOrder(request);
        });

        verify(orderRepository, never()).save(any());
    }

    @Test
    void testCreateOrder_InvalidName_ShouldThrowException() {
        OrderRequest request = new OrderRequest("", "M");

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createOrder(request);
        });
    }

    @Test
    void testGetAllOrders_ShouldReturnList() {
        List<Order> orders = Arrays.asList(
                new Order(1L, "Latte", "M"),
                new Order(2L, "Espresso", "S")
        );

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.getAllOrders();

        assertEquals(2, result.size());
        assertEquals("Latte", result.get(0).getName());
    }


}
