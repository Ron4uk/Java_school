package tests.controller;

import com.task.controller.TariffController;
import com.task.dto.ClientDto;
import com.task.dto.ContractDto;
import com.task.dto.OrderDto;
import com.task.dto.TariffDto;
import com.task.service.ContractService;
import com.task.service.OptionService;
import com.task.service.TariffService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TariffControllerTest {
    @InjectMocks
    private TariffController tariffController;
    private MockMvc mockMvc;
    @Mock
    private TariffService tariffService;
    @Mock
    private OptionService optionService;
    @Mock
    private ContractService contractService;

    private static ContractDto contractDto = new ContractDto();
    private static OrderDto orderDto = new OrderDto();
    private static ClientDto clientDto = new ClientDto();
    private static TariffDto tariffDto = new TariffDto();
    private static String[] optionsId = new String[3];
    private static List<TariffDto> tariffDtoList = new ArrayList<>();

    @BeforeClass
    public static void initialize() {
        orderDto.setTariffDto(tariffDto);
        tariffDtoList.add(tariffDto);

        optionsId[0] = "1";
        optionsId[1] = "2";
        optionsId[2] = "3";

        tariffDto.setId(2);
        tariffDto.setTariff("Tariff");
        tariffDto.setDeleted(false);
        tariffDto.setPrice(BigDecimal.valueOf(10));
        tariffDto.setContracts(new HashSet<>());
        tariffDto.getContracts().add(contractDto);

        clientDto.setId(1);
        clientDto.setAddress("USA");
        clientDto.setFirstName("John");
        clientDto.setLastName("Black");
        clientDto.setContracts(new HashSet<>());
        clientDto.getContracts().add(contractDto);

        contractDto.setId(1);
        contractDto.setClientDto(clientDto);
        contractDto.setTariffDto(tariffDto);
    }

    @Before
    public void setup() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver(); //Reconfigure the view resolver in test
        resolver.setPrefix("");
        resolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(tariffController)
                .setViewResolvers(resolver)
                .build();
    }


    @Test
    public void getAllTariffs() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/employee/tariffs"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("employee"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("employee.jsp"))
                .andReturn();
        Assert.assertEquals("employee", result.getModelAndView().getViewName());

    }

    @Test
    public void editTariff() throws Exception {
        when(tariffService.findByIdDto(1)).thenReturn(tariffDto);
        MvcResult result = mockMvc.perform(get("/employee/edittariff").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("edittariff"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("edittariff.jsp"))
                .andReturn();
        Assert.assertEquals("edittariff", result.getModelAndView().getViewName());


    }

    @Test
    public void newTariff() throws Exception {
        MvcResult result = mockMvc.perform(post("/employee/newTariff")
                .sessionAttr("tariffDto", tariffDto)
                .requestAttr("opt", optionsId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("edittariff"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("edittariff.jsp"))
                .andReturn();
        Assert.assertEquals("edittariff", result.getModelAndView().getViewName());

    }

    @Test
    public void deleteTariffGet() throws Exception {
        when(tariffService.findByIdDto(1)).thenReturn(tariffDto);
        MvcResult result = this.mockMvc.perform(get("/employee/deletetariff").param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("deletetariff"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("deletetariff.jsp"))
                .andReturn();
        Assert.assertEquals("deletetariff", result.getModelAndView().getViewName());
    }

    @Test
    public void deleteTariffPost() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/employee/deletetariff")
                .sessionAttr("tariffDto", tariffDto)
                .param("newtariff", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("deletetariff"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("deletetariff.jsp"))
                .andReturn();
        Assert.assertEquals("deletetariff", result.getModelAndView().getViewName());
    }

    @Test
    public void chooseNewTariff() throws Exception {
        when(contractService.checkContract(contractDto, orderDto)).thenReturn(true);
        MvcResult result = this.mockMvc.perform(get("/user/userchoosenewtariff")
                .sessionAttr("contractDto", contractDto)
                .sessionAttr("orderDto", orderDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("userchoosenewtariff"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userchoosenewtariff.jsp"))
                .andReturn();

        Assert.assertEquals("userchoosenewtariff", result.getModelAndView().getViewName());
        when(contractService.checkContract(contractDto, orderDto)).thenReturn(false);
        MvcResult result2 = this.mockMvc.perform(get("/user/userchoosenewtariff")
                .sessionAttr("contractDto", contractDto)
                .sessionAttr("orderDto", orderDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("user"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user.jsp"))
                .andReturn();
        Assert.assertEquals("user", result2.getModelAndView().getViewName());
    }

    @Test
    public void addTariffToOrder() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/user/addtarifftoorder")
                .sessionAttr("orderDto", orderDto))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("userchoosenewoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userchoosenewoption.jsp"))
                .andReturn();
        Assert.assertEquals("userchoosenewoption", result.getModelAndView().getViewName());
    }

    @Test
    public void getTariffs() throws Exception {
        when(tariffService.getAllDtoWithReq()).thenReturn(tariffDtoList);
        MvcResult result = this.mockMvc.perform(get("/tariff"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].tariff", is("Tariff")))
                .andExpect(jsonPath("$[0].price").value(BigDecimal.valueOf(10)))
                .andExpect(jsonPath("$[0].deleted").value(false))
                .andReturn();

        verify(tariffService, times(1)).getAllDtoWithReq();
        verifyNoMoreInteractions(tariffService);
    }
}
