import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.StatsCont;
import com.example.demo.model.Category;
import com.example.demo.model.Incomes;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepo;
import com.example.demo.repository.IncomesRepo;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StatsCont.class)
public class StatsContTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IncomesRepo incomesRepo;

    @MockBean
    private CategoryRepo categoryRepo;

    @BeforeEach
    public void setUp() {
        List<Incomes> incomes = Arrays.asList(
                new Incomes(1L, new Product("Product1"), 100),
                new Incomes(2L, new Product("Product2"), 150)
        );
        when(incomesRepo.findAll()).thenReturn(incomes);

        List<Category> categories = Arrays.asList(
                new Category("Electronics", 200),
                new Category("Books", 100)
        );
        when(categoryRepo.findAll()).thenReturn(categories);
    }

    @Test
    public void testStatsPage() throws Exception {
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("incomes", "stringIncomes", "intIncomes", "catString", "catInt"))
                .andExpect(model().attribute("stringIncomes", arrayContaining("Product2", "Product1"))) // Ensure sorted by count desc
                .andExpect(model().attribute("intIncomes", arrayContaining(150, 100)))
                .andExpect(model().attribute("catString", arrayContaining("Electronics", "Books")))
                .andExpect(model().attribute("catInt", arrayContaining(200, 100)))
                .andExpect(view().name("stats"));
    }
}
