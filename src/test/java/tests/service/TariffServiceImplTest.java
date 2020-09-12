package tests.service;

import com.task.dao.ContractDao;
import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.OptionDto;
import com.task.dto.TariffDto;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.service.TariffSender;
import com.task.service.impl.OptionValidationAndTools;
import com.task.service.impl.TariffServiceImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class TariffServiceImplTest {

    @InjectMocks
    private TariffServiceImpl tariffService;
    @InjectMocks
    private ModelMapper modelMapper;
    @Mock
    private OptionValidationAndTools validationAndTools;
    @Mock
    private TariffDao tariffDao;
    @Mock
    private OptionDao optionDao;
    @Mock
    private TariffSender tariffSender;
    @Mock
    private ContractDao contractDao;



    private static List<Tariff> tariffs = new ArrayList<>();
    private static List<TariffDto> tariffDtoList = new ArrayList<>();
    private static Option option;

    @BeforeClass
    public static void initialize(){
        Tariff tariff = new Tariff();
        Tariff tariff2 = new Tariff();
        tariff.setId(1);
        tariff.setTariff("tar1");
        tariff.setPrice(BigDecimal.valueOf(10));
        tariff.setDeleted(false);
        tariff.setOptions(new HashSet<>());
        tariff.setContracts(new HashSet<>());

        tariff2.setId(2);
        tariff2.setTariff("tar2");
        tariff2.setPrice(BigDecimal.valueOf(20));
        tariff2.setDeleted(false);
        tariff2.setOptions(new HashSet<>());
        tariff2.setContracts(new HashSet<>());
        tariffs.add(tariff);
        tariffs.add(tariff2);

        TariffDto tariffDto = new TariffDto();
        TariffDto tariffDto2 = new TariffDto();
        tariffDto.setId(1);
        tariffDto.setTariff("tar1");
        tariffDto.setPrice(BigDecimal.valueOf(10));
        tariffDto.setDeleted(false);
        tariffDto.setOptions(new HashSet<>());
        tariffDto.setContracts(new HashSet<>());
        tariffDto2.setId(2);
        tariffDto2.setTariff("tar2");
        tariffDto2.setPrice(BigDecimal.valueOf(20));
        tariffDto2.setDeleted(false);
        tariffDto2.setOptions(new HashSet<>());
        tariffDto2.setContracts(new HashSet<>());
        tariffDtoList.add(tariffDto);
        tariffDtoList.add(tariffDto2);

        option = new Option();
        option.setId(1);
        option.setDeleted(false);
        option.setConnectionCost(BigDecimal.valueOf(10));
        option.setPrice(BigDecimal.valueOf(10));
        option.setName("opt1");
        option.setContracts(null);
        option.setTariffs(null);


    }

    @Before
    public void setModelMapper() {
        tariffService.setModelMapper(modelMapper);
    }

    @Test
    public void getAll() {
        when(tariffDao.getAll()).thenReturn(tariffs);
        assertEquals(tariffs, tariffService.getAll());
    }

    @Test
    public void getAllDto() {
        when(tariffDao.getAll()).thenReturn(tariffs);
        assertEquals(tariffDtoList, tariffService.getAllDto());
    }

    @Test
    public void getAllDtoWithReq() {
        when(tariffDao.getAll()).thenReturn(tariffs);
        assertEquals(tariffDtoList, tariffService.getAllDtoWithReq());
    }

    @Test
    public void getAllWithoutDto() {
        List<Tariff> list = new ArrayList<>(tariffs);
        list.removeIf(e -> e.getId().equals(2));
        when(tariffDao.getAllWithout(2)).thenReturn(list);
        List<TariffDto> listDto = new ArrayList<>(tariffDtoList);
        listDto.removeIf(e -> e.getId().equals(2));
        assertEquals(listDto, tariffService.getAllWithoutDto(2));
    }

    @Test
    public void findById() {
        when(tariffDao.findById(1)).thenReturn(tariffs.get(0));
        assertEquals(tariffs.get(0), tariffService.findById(1));
    }

    @Test
    public void findByIdDto() {
        when(tariffDao.findById(1)).thenReturn(tariffs.get(0));
        assertEquals(tariffDtoList.get(0), tariffService.findByIdDto(1));
    }

    @Test
    public void merge() {
        Tariff tariff = tariffs.get(0);
        when(tariffDao.update(tariff)).thenReturn(tariff);
        assertEquals("changes successful", tariffService.merge(tariffDtoList.get(0), null));
        Set<Option> optionSet = new HashSet<>();
        optionSet.add(option);
        when(optionDao.findById(1)).thenReturn(option);
        when(validationAndTools.checkOptions(optionSet)).thenReturn(false);
        assertEquals("Changes failed. Check requirements", tariffService.merge(tariffDtoList.get(0),
                new String[]{"1"}));
    }

    @Test
    public void createRequirementsForEmbeddedOptions() {
        assertEquals(tariffDtoList.get(0), tariffService.createRequirementsForEmbeddedOptions(tariffDtoList.get(0)));
    }

    @Test
    public void remove() {
        when(tariffDao.findById(1)).thenReturn(tariffs.get(0));
        assertEquals(tariffDtoList.get(0), tariffService.remove(tariffDtoList.get(0), 1));
    }

    @Test
    public void sendTariffsToQueue() {
        when(tariffDao.getAll()).thenReturn(tariffs);
        tariffService.sendTariffsToQueue();
        verify(tariffSender, times(1)).sendTariffs(new HashSet<>(tariffDtoList));
    }
}
