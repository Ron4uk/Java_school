package tests.controller;

import com.task.controller.OptionController;
import com.task.dto.ContractDto;
import com.task.dto.OptionDto;
import com.task.dto.OrderDto;
import com.task.service.ContractService;
import com.task.service.OptionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(MockitoJUnitRunner.class)
public class OptionControllerTest {
    @InjectMocks
    private OptionController optionController;
    private MockMvc mockMvc;
    @Mock
    private OptionService optionService;
    @Mock
    private ContractService contractService;

    private static ContractDto contractDto = new ContractDto();
    private static OptionDto optionDto = new OptionDto();
    private static OrderDto orderDto = new OrderDto();


    @Before
    public void setup() {

        InternalResourceViewResolver resolver = new InternalResourceViewResolver(); //Reconfigure the view resolver in test
        resolver.setPrefix("");
        resolver.setSuffix(".jsp");
        mockMvc = MockMvcBuilders.standaloneSetup(optionController)
                .setViewResolvers(resolver)
                .build();
    }


    @Test
    public void getAllOptions() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/employee/options"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("employee"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("employee.jsp"))
                .andReturn();
        Assert.assertEquals("employee", result.getModelAndView().getViewName());
    }

    @Test
    public void editOption() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/employee/editoption"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("editoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("editoption.jsp"))
                .andReturn();
        verify(optionService, times(1)).getAllDto();
        verifyNoMoreInteractions(optionService);
        Assert.assertEquals("editoption", result.getModelAndView().getViewName());

        MvcResult result2 = this.mockMvc.perform(get("/employee/editoption")
                .param("id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("editoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("editoption.jsp"))
                .andReturn();
        Assert.assertEquals("editoption", result2.getModelAndView().getViewName());
        verify(optionService, times(1)).findByIdDto(1);
        verify(optionService, times(1)).getAllWithoutDto(1);
        verifyNoMoreInteractions(optionService);
    }

    @Test
    public void updateOrDeleteOption() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/employee/updateOrDeleteOption"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("editoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("editoption.jsp"))
                .andReturn();
        Assert.assertEquals("editoption", result.getModelAndView().getViewName());
        verify(optionService, times(1)).update(null, null, new OptionDto());
        verify(optionService, times(1)).getAllWithoutDto(null);
        verifyNoMoreInteractions(optionService);

        MvcResult result2 = this.mockMvc.perform(post("/employee/updateOrDeleteOption")
                .sessionAttr("optionDto", new OptionDto())
                .param("delete", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("editoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("editoption.jsp"))
                .andReturn();
        Assert.assertEquals("editoption", result2.getModelAndView().getViewName());
        verify(optionService, times(1)).deleteById(null, new OptionDto());
        verify(optionService, times(2)).getAllWithoutDto(null);
        verifyNoMoreInteractions(optionService);
    }

    @Test
    public void chooseNewOption() throws Exception {
        when(contractService.checkContract(new ContractDto(), new OrderDto())).thenReturn(true);
        MvcResult result = this.mockMvc.perform(get("/user/userchoosenewoption")
                .sessionAttr("orderDto", new OrderDto()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("userchoosenewoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("userchoosenewoption.jsp"))
                .andReturn();
        Assert.assertEquals("userchoosenewoption", result.getModelAndView().getViewName());

        when(contractService.checkContract(new ContractDto(), new OrderDto())).thenReturn(false);
        MvcResult result2 = this.mockMvc.perform(get("/user/userchoosenewoption")
                .sessionAttr("orderDto", new OrderDto()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("user"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user.jsp"))
                .andReturn();
        Assert.assertEquals("user", result2.getModelAndView().getViewName());

    }

    @Test
    public void addOptionToTariff() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/user/addoptiontotariff")
                .sessionAttr("orderDto", new OrderDto()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("user"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user.jsp"))
                .andReturn();
        Assert.assertEquals("user", result.getModelAndView().getViewName());

    }

    @Test
    public void manageOption() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/user/usermanageoption"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("usermanageoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("usermanageoption.jsp"))
                .andReturn();
        Assert.assertEquals("usermanageoption", result.getModelAndView().getViewName());

    }

    @Test
    public void connectedNewOptionByUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/user/connectedoptionbyuser")
                .sessionAttr("contractDto", new ContractDto())
                .param("optid", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("usermanageoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("usermanageoption.jsp"))
                .andReturn();
        Assert.assertEquals("usermanageoption", result.getModelAndView().getViewName());
        verify(contractService, times(1)).addConnectedOption(new ContractDto(), 1);
        verifyNoMoreInteractions(contractService);

    }

    @Test
    public void disconnectOptionByUser() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/user/disconnectoptionbyuser")
                .sessionAttr("contractDto", new ContractDto())
                .param("optid", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("usermanageoption"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("usermanageoption.jsp"))
                .andReturn();
        Assert.assertEquals("usermanageoption", result.getModelAndView().getViewName());
        verify(contractService, times(1)).disconnectOption(new ContractDto(), 1);
        verifyNoMoreInteractions(contractService);
    }
}
