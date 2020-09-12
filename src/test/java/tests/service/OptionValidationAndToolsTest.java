package tests.service;

import com.task.dao.OptionDao;
import com.task.dao.TariffDao;
import com.task.dto.OptionDto;
import com.task.entity.Option;
import com.task.entity.Tariff;
import com.task.exception.WrongOptionException;
import com.task.service.GenericMapper;
import com.task.service.impl.OptionValidationAndTools;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class OptionValidationAndToolsTest extends GenericMapper {

    @InjectMocks
    private OptionValidationAndTools toolsTest;
    @InjectMocks
    private ModelMapper modelMapper;
    @Mock
    private OptionDao optionDao;
    @Mock
    private TariffDao tariffDao;
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();


    private static List<OptionDto> optionDtoList = new ArrayList<>();
    private static Set<Option> optionList = new HashSet<>();
    private static Set<Integer> requirementsId= new HashSet<>(Arrays.asList(new Integer[]{2, 4}));
    private static Set<Integer> exclusionsId= new HashSet<>(Arrays.asList(new Integer[]{3}));
    private static OptionDto optionDto;
    private static OptionDto optionDto2;
    private static OptionDto optionDto3;
    private static OptionDto optionDto4;
    private static Option option;
    private static Option option2;
    private static Option option3;
    private static Option option4;


    @BeforeClass
    public static void initialize() {
        option =new Option();
        option2 =new Option();
        option3 =new Option();
        option4 =new Option();
        option.setId(1);
        option.setDeleted(false);
        option.setConnectionCost(BigDecimal.valueOf(10));
        option.setPrice(BigDecimal.valueOf(10));
        option.setName("opt1");
        option.setContracts(null);
        option.setTariffs(null);

        option2.setId(2);
        option2.setDeleted(false);
        option2.setConnectionCost(BigDecimal.valueOf(12));
        option2.setPrice(BigDecimal.valueOf(12));
        option2.setName("opt2");
        option2.setContracts(null);
        option2.setTariffs(null);
        option2.setRequiredOptions(new HashSet<>());
        option2.setExclusionOptions(new HashSet<>());


        option3.setId(3);
        option3.setDeleted(false);
        option3.setPrice(BigDecimal.valueOf(13));
        option3.setName("opt3");
        option3.setConnectionCost(BigDecimal.valueOf(13));
        option3.setContracts(null);
        option3.setTariffs(null);
        option3.setRequiredOptions(new HashSet<>());
        option3.setExclusionOptions(new HashSet<>());

        option4.setId(4);
        option4.setDeleted(false);
        option4.setPrice(BigDecimal.valueOf(14));
        option4.setName("opt4");
        option4.setConnectionCost(BigDecimal.valueOf(14));
        option4.setContracts(null);
        option4.setTariffs(null);
        option4.setRequiredOptions(new HashSet<>());
        option4.setExclusionOptions(new HashSet<>());

        Set<Option> requiredOption = new HashSet<>();
        requiredOption.add(option2);
        requiredOption.add(option4);
        Set<Option> exclusionOption = new HashSet<>();
        exclusionOption.add(option3);
        option.setRequiredOptions(requiredOption);
        option.setExclusionOptions(exclusionOption);

        optionList.add(option);
        optionList.add(option2);
        optionList.add(option3);


        optionDto = new OptionDto();
        optionDto2 = new OptionDto();
        optionDto3 = new OptionDto();
        optionDto4 = new OptionDto();


        optionDto.setId(1);
        optionDto.setDeleted(false);
        optionDto.setConnectionCost(BigDecimal.valueOf(10));
        optionDto.setPrice(BigDecimal.valueOf(10));
        optionDto.setName("opt1");
        optionDto.setContracts(null);
        optionDto.setTariffs(null);



        optionDto2.setId(2);
        optionDto2.setDeleted(false);
        optionDto2.setConnectionCost(BigDecimal.valueOf(12));
        optionDto2.setPrice(BigDecimal.valueOf(12));
        optionDto2.setName("opt2");
        optionDto2.setContracts(null);
        optionDto2.setTariffs(null);

        optionDto3.setId(3);
        optionDto3.setDeleted(false);
        optionDto3.setPrice(BigDecimal.valueOf(13));
        optionDto3.setName("opt3");
        optionDto3.setConnectionCost(BigDecimal.valueOf(13));
        optionDto3.setContracts(null);
        optionDto3.setTariffs(null);

        optionDto4.setId(4);
        optionDto4.setDeleted(false);
        optionDto4.setPrice(BigDecimal.valueOf(14));
        optionDto4.setName("opt4");
        optionDto4.setConnectionCost(BigDecimal.valueOf(14));
        optionDto4.setContracts(null);
        optionDto4.setTariffs(null);

        Set<OptionDto> requiredOptionDto = new HashSet<>();
        requiredOptionDto.add(optionDto2);
        requiredOptionDto.add(optionDto4);
        Set<OptionDto> exclusionOptionDto = new HashSet<>();
        exclusionOptionDto.add(optionDto3);
        optionDto.setRequiredOptions(requiredOptionDto);
        optionDto.setExclusionOptions(exclusionOptionDto);

        optionDtoList.add(optionDto);
        optionDtoList.add(optionDto2);
        optionDtoList.add(optionDto3);


    }
    @Before
    public void setModelMapper() {
        toolsTest.setModelMapper(modelMapper);
    }

    @Test
    public void setRequirements() {
        assertEquals(requirementsId, toolsTest.setRequirements(optionDto));
    }

    @Test
    public void setExclusions() {
        assertEquals(exclusionsId, toolsTest.setExclusions(optionDto));
    }

    @Test
    public void checkOptionName() {
        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage("Option with this name already exist");
        OptionDto optionDtoInCheck = new OptionDto();
        optionDtoInCheck.setId(null);
        optionDtoInCheck.setDeleted(false);
        optionDtoInCheck.setConnectionCost(BigDecimal.valueOf(10));
        optionDtoInCheck.setPrice(BigDecimal.valueOf(10));
        optionDtoInCheck.setName("opt1");
        optionDtoInCheck.setContracts(null);
        optionDtoInCheck.setTariffs(null);
        when(optionDao.findByName(optionDtoInCheck.getName())).thenReturn(option);
        toolsTest.checkOptionName(optionDtoInCheck);


    }
    @Test
    public void checkOptionName2() {
        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage("Option with this name already exist");
        OptionDto optionDtoInCheck = new OptionDto();
        optionDtoInCheck.setId(11);
        optionDtoInCheck.setDeleted(false);
        optionDtoInCheck.setConnectionCost(BigDecimal.valueOf(10));
        optionDtoInCheck.setPrice(BigDecimal.valueOf(10));
        optionDtoInCheck.setName("opt1");
        optionDtoInCheck.setContracts(null);
        optionDtoInCheck.setTariffs(null);
        when(optionDao.findByName(optionDtoInCheck.getName())).thenReturn(option);
        toolsTest.checkOptionName(optionDtoInCheck);


    }

    @Test
    public void createOnlyExclusionOptions() {
        Set<OptionDto> chooseRequiredOptions = new HashSet<>();
        chooseRequiredOptions.add(optionDto);
        List<OptionDto> onlyExclusionOptions = new ArrayList<>();
        List<OptionDto> onlyExclusionOptionsResult = new ArrayList<>();
        onlyExclusionOptionsResult.add(optionDto3);
        assertEquals(onlyExclusionOptionsResult,
                toolsTest.createOnlyExclusionOptions(chooseRequiredOptions, onlyExclusionOptions));

    }

    @Test
    public void createReqHierarchy() {
        Set<OptionDto> chooseRequiredOptions = new HashSet<>();
        chooseRequiredOptions.add(optionDto);
        List<OptionDto> allOptions = new ArrayList<>();
        List<OptionDto> allOptionsResult = new ArrayList<>();
        allOptionsResult.add(optionDto4);
        allOptionsResult.add(optionDto2);
        allOptionsResult.add(optionDto);
        toolsTest.createReqHierarchy(allOptions, chooseRequiredOptions);
        assertEquals(allOptionsResult, allOptions);

    }

    @Test
    public void createExclHierarchy() {
        Set<OptionDto> chooseExclusionOptions = new HashSet<>();
        chooseExclusionOptions.add(optionDto);
        List<OptionDto> allOptions = new ArrayList<>();
        List<OptionDto> allOptionsResult = new ArrayList<>();
        allOptionsResult.add(optionDto3);
        toolsTest.createExclHierarchy(allOptions, chooseExclusionOptions);
        assertEquals(allOptionsResult, allOptions);
    }

    @Test
    public void checkLikeParent() {
        OptionDto optionDtoTest1 = new OptionDto();
        OptionDto optionDtoTest2 = new OptionDto();
        optionDtoTest1.setName("optTest1");
        optionDtoTest2.setName("optTest2");
        Set<OptionDto> requiredOptionDto1 = new HashSet<>();
        requiredOptionDto1.add(optionDtoTest2);
        optionDtoTest1.setRequiredOptions(requiredOptionDto1);
        Set<OptionDto> requiredOptionDto2 = new HashSet<>();
        requiredOptionDto1.add(optionDtoTest1);
        optionDtoTest2.setRequiredOptions(requiredOptionDto2);
        List<OptionDto> allOptions = new ArrayList<>();


        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage(optionDtoTest1.getName()+
                "  violates the principle of binding to the selected options");
        toolsTest.checkLikeParent(allOptions, optionDtoTest1, null);


    }

    @Test
    public void checkLikeChildTestRequiredOptions() {
        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage("Chosen options violates the principle of coupling with option " +
                optionDto.getName());
        List<OptionDto> allCheckingOptions = new ArrayList<>();
        allCheckingOptions.add(optionDto);
        List<OptionDto> onlyExclusionOptions = new ArrayList<>();
        onlyExclusionOptions.add(optionDto3);
        List<Option> parentOptions = new ArrayList<>();
        parentOptions.add(option);
        when(optionDao.getAllParent(optionDto2.getId())).thenReturn(parentOptions);
        toolsTest.checkLikeChild(allCheckingOptions, optionDto2, optionDto2, onlyExclusionOptions);

    }

    @Test
    public void checkLikeChildTestExclusionsOptions() {
        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage("Chosen options violates the principle of coupling with option " +
                optionDto.getName());
        List<OptionDto> allCheckingOptions = new ArrayList<>();
        allCheckingOptions.add(optionDto3);
        List<OptionDto> onlyExclusionOptions = new ArrayList<>();
        onlyExclusionOptions.add(optionDto3);
        List<Option> parentOptions = new ArrayList<>();
        parentOptions.add(option);
        when(optionDao.getAllParent(optionDto2.getId())).thenReturn(parentOptions);

        toolsTest.checkLikeChild(allCheckingOptions, optionDto2, optionDto2, onlyExclusionOptions);

    }

    @Test
    public void checkLikeChildTestonlyExclusionOptions() {
        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage("Chosen options violates the principle of coupling with option " +
                optionDto4.getName());
        List<OptionDto> allCheckingOptions = new ArrayList<>();
        List<OptionDto> onlyExclusionOptions = new ArrayList<>();
        onlyExclusionOptions.add(optionDto4);
        List<Option> parentOptions = new ArrayList<>();
        parentOptions.add(option);
        when(optionDao.getAllParent(optionDto2.getId())).thenReturn(parentOptions);
        toolsTest.checkLikeChild(allCheckingOptions, optionDto2, optionDto2, onlyExclusionOptions);

    }

    @Test
    public void getAllParentDto() {
        List<Option> parentOptions = new ArrayList<>();
        parentOptions.add(option);
        List<OptionDto> parentOptionsDto = new ArrayList<>();
        parentOptionsDto.add(optionDto);
        when(optionDao.getAllParent(optionDto2.getId())).thenReturn(parentOptions);
        assertEquals(parentOptionsDto, toolsTest.getAllParentDto(optionDto2));

    }

    @Test
    public void checkOptions() {
        Option optionTest1 = new Option();
        Option optionTest2 = new Option();
        optionTest1.setName("optTest1");
        optionTest2.setName("optTest2");
        optionTest2.setRequiredOptions(new HashSet<>());
        Set<Option> requiredOptionDto1 = new HashSet<>();
        requiredOptionDto1.add(optionTest2);
        optionTest1.setRequiredOptions(requiredOptionDto1);
        Set<Option> options = new HashSet<>();
        options.add(optionTest1);
        assertEquals(false, toolsTest.checkOptions(options));
        options.add(optionTest2);
        assertEquals(true, toolsTest.checkOptions(options));
    }

    @Test
    public void checkContainsRequirementInExclusion() {
        exceptionRule.expect(WrongOptionException.class);
        exceptionRule.expectMessage("Option cannot be both in requirements and exceptions");
        toolsTest.checkContainsRequirementInExclusion(new String[]{"1", "2", "3"}, new String[]{"3"}, null);
    }

    @Test
    public void globalCheck() {
        OptionDto test = new OptionDto();
        test.setId(1);
        test.setDeleted(false);
        test.setConnectionCost(BigDecimal.valueOf(10));
        test.setPrice(BigDecimal.valueOf(10));
        test.setName("opt1");
        test.setContracts(null);
        test.setTariffs(null);
        test.setRequiredOptions(new HashSet<>());
        test.setExclusionOptions(new HashSet<>());

        when(optionDao.findById(Integer.parseInt("2"))).thenReturn(option2);
        when(optionDao.findById(Integer.parseInt("4"))).thenReturn(option4);
        when(optionDao.findById(Integer.parseInt("3"))).thenReturn(option3);
        when(optionDao.findByName(test.getName())).thenReturn(option);
        assertEquals(optionDto, toolsTest.globalCheck(new String[]{"2", "4"}, new String[] {"3"}, test));

    }

    @Test
    public void updateAllTariffsWithOption() {
        Tariff tariff1 = new Tariff();
        tariff1.setTariff("tar1");
        tariff1.setOptions(new HashSet<>());
        Tariff tariff2 = new Tariff();
        tariff2.setTariff("tar2");
        tariff2.setOptions(new HashSet<>());
        Tariff tariff3 = new Tariff();
        tariff3.setTariff("tar3");
        tariff3.setOptions(new HashSet<>());
        List<Tariff> tariffs = new ArrayList<>();
        tariffs.add(tariff1);
        tariffs.add(tariff2);
        tariffs.add(tariff3);
        Option optionTest = option;
        optionTest.getRequiredOptions().add(option2);
        toolsTest.updateAllTariffsWithOption(tariffs, optionTest);
        tariffs.forEach(e->verify(tariffDao, times(1)).update(e));
        verifyNoMoreInteractions(tariffDao);
    }
}
