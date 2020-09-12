package tests.service;

import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import com.task.service.GenericMapper;
import com.task.service.impl.OptionServiceImpl;
import com.task.service.impl.OptionValidationAndTools;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class OptionServiceImplTest extends GenericMapper {
    @Mock
    private OptionDao optionDao;
    @InjectMocks
    private OptionServiceImpl optionServiceImpl;
    @InjectMocks
    private ModelMapper modelMapper;
    @Mock
    private OptionValidationAndTools validationAndTools;
    @Mock
    private TariffDao tariffDao;


    private static List<Option> optionList = new ArrayList<>();
    private static List<OptionDto> optionDtoList = new ArrayList<>();


    @BeforeClass
    public static void initialize() {


        Option option = new Option();
        Option option2 = new Option();
        Set<Option> requiredOption = new HashSet<>();
        requiredOption.add(option2);
        option.setId(1);
        option.setDeleted(false);
        option.setConnectionCost(BigDecimal.valueOf(10));
        option.setPrice(BigDecimal.valueOf(10));
        option.setName("opt1");
        option.setContracts(null);
        option.setTariffs(null);
        option.setRequiredOptions(requiredOption);

        option2.setId(2);
        option2.setDeleted(false);
        option2.setConnectionCost(BigDecimal.valueOf(12));
        option2.setPrice(BigDecimal.valueOf(12));
        option2.setName("opt2");
        option2.setContracts(null);
        option2.setTariffs(null);
        optionList.add(option);
        optionList.add(option2);

        OptionDto optionDto = new OptionDto();
        OptionDto optionDto2 = new OptionDto();
        Set<OptionDto> requiredOptionDto = new HashSet<>();
        requiredOptionDto.add(optionDto2);
        optionDto.setId(1);
        optionDto.setDeleted(false);
        optionDto.setConnectionCost(BigDecimal.valueOf(10));
        optionDto.setPrice(BigDecimal.valueOf(10));
        optionDto.setName("opt1");
        optionDto.setContracts(null);
        optionDto.setTariffs(null);
        optionDto.setRequiredOptions(requiredOptionDto);

        optionDto2.setId(2);
        optionDto2.setDeleted(false);
        optionDto2.setConnectionCost(BigDecimal.valueOf(12));
        optionDto2.setPrice(BigDecimal.valueOf(12));
        optionDto2.setName("opt2");
        optionDto2.setContracts(null);
        optionDto2.setTariffs(null);
        optionDtoList.add(optionDto);
        optionDtoList.add(optionDto2);


    }

    @Before
    public void setModelMapper() {
        optionServiceImpl.setModelMapper(modelMapper);
    }

    @Test
    public void getAll() {
        when(optionDao.getAll()).thenReturn(optionList);

        assertEquals(optionList, optionServiceImpl.getAll());
    }

    @Test
    public void getAllDto() {
        when(optionDao.getAll()).thenReturn(optionList);
        assertEquals(optionDtoList, optionServiceImpl.getAllDto());
    }

    @Test
    public void getAllDtoWithReqId() {
        when(optionDao.getAll()).thenReturn(optionList);
        assertEquals(optionDtoList, optionServiceImpl.getAllDtoWithReqId());
        optionDtoList.forEach(e->verify(validationAndTools, times(1)).setExclusions(e));
        optionDtoList.forEach(e->verify(validationAndTools, times(1)).setRequirements(e));
        verifyNoMoreInteractions(validationAndTools);
    }
    @Test
    public void getAllDtoWithReqIdWithDeleted() {
        when(optionDao.getAllWithDeleted()).thenReturn(optionList);
        assertEquals(optionDtoList, optionServiceImpl.getAllDtoWithReqIdWithDeleted());
        optionDtoList.forEach(e->verify(validationAndTools, times(1)).setExclusions(e));
        optionDtoList.forEach(e->verify(validationAndTools, times(1)).setRequirements(e));
        verifyNoMoreInteractions(validationAndTools);
    }


    @Test
    public void getAllWithoutDtoTest() {
        List<Option> list = new ArrayList<>(optionList);
        list.removeIf(e -> e.getId().equals(2));
        when(optionDao.getAllWithout(2)).thenReturn(list);
        List<OptionDto> listDto = new ArrayList<>(optionDtoList);
        listDto.removeIf(e -> e.getId().equals(2));
        assertEquals(listDto, optionServiceImpl.getAllWithoutDto(2));

    }

    @Test
    public void findById() {
        when(optionDao.findById(1)).thenReturn(optionList.get(0));
        assertEquals(optionList.get(0), optionServiceImpl.findById(1));


    }

    @Test(expected = IllegalStateException.class)
    public void findByIdTestException() {
        when(optionDao.findById(null)).thenThrow(new IllegalStateException());
        optionServiceImpl.findById(null);

    }

    @Test
    public void findByIdDto() {
        when(optionDao.findById(1)).thenReturn(optionList.get(0));
        assertEquals(optionDtoList.get(0), optionServiceImpl.findByIdDto(1));
    }

    @Test
    public void update() {
        when(validationAndTools.globalCheck(new String[]{}, new String[]{}, optionDtoList.get(0))).
                thenReturn(optionDtoList.get(0));
        when(optionDao.update(optionList.get(0))).thenReturn(optionList.get(0));
        assertEquals("Changes successful.", optionServiceImpl.update(new String[]{}, new String[]{},
                optionDtoList.get(0)));
    }

    @Test
    public void deleteById() {
        when(optionDao.findById(2)).thenReturn(optionList.get(1));
        assertEquals("Changes successful.", optionServiceImpl.deleteById(2, optionDtoList.get(1)));
        OptionDto optionDto = optionDtoList.get(1);
        optionDto.setDeleted(true);
        assertEquals("Option was deleted", optionServiceImpl.deleteById(2, optionDto));
    }
}
